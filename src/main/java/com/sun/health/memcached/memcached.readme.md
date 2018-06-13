一、关于Memcached
1.Memcached
免费、开源、高效、分布式的内存对象缓存系统。本质上是通用的，但目的是通过减轻数据库负载来加速动态web应用程序。
Memcached是内存中键值对仓库，用于存储来自数据库调用、API调用和页面呈现结果的任意小数据（字符串、对象）
Memcached是简单而强大的。它的简单设计促进了快速部署、易于开发、并解决了大量数据库缓存面临的许多问题。API适用于大多数流行的语言。
2.工作原理
Memcached是一种开发工具，而不是代码加速器，也不是数据库中间件。
3.组成
A.客户端软件，提供一系列可用的memcached服务。
B.一种基于客户端的散列算法，用于基于'key'选择服务器
C.服务器软件，用于关联keys存储values到内部的散列表中。
D.LRU,用于决定何时抛弃旧数据(如果内存溢出)，或者重用内存。
4.设计思路
A.简单的键值对存储
服务器不在乎数据的格式。项由键、过期时间、可选标志和原始数据组成。不了解数据结构，必须上传预序列化的数据。有些命令(incr/decr)可能对底层数据进行操作，但是操作方式很简单。
B.逻辑一半在客户端，一半在服务器
"Memcached实现"部分在客户端，部分在服务器。客户端需要知道如果选择服务器读写数据，如何处理不能联系到服务器的情况。
服务器需要知道如果存储和获取数据。也管理何时回收和重用内存
C.服务器各自之间不连接
Memcached服务器不知道彼此之间的存在。没有串音、没有同步、没有广播、没有复制。增加服务器只是增加可用内存。缓存失效被简化，因为客户端直接在服务器上删除和重写数据。
D.O(1)
所有命令被尽可能地实现快速和锁友善。这为所有用例提供了近乎确定的查询速度。
慢速及其上的查询运行应该在1ms以下。高端服务器的吞吐量可以达到每秒百万级别。
E.遗忘是一个特色
默认情况下，Memcached是一种LRU缓存。数据在指定时间后过期。这两种方法都可以很好地解决许多问题。过期数据限制返回陈旧数据，或者刷新未使用的数据，已保留经常请求的信息。
F.缓存失效
客户端不向所有可用的主机广播更改，而是直接向持有失效数据的服务器地址发送请求。

二、安装
1.安装
Memcached可在大部分Linux和BSD类的操作系统上工作。没有官方的windows支持版本。
A.从安装包安装
apt-get install memcached
yum install memcached
B.从源代码安装
Memcached依赖于libevent库。
apt-get install libevent
yum install libevent
wget http://www.memcached.org/files/memcached-1.5.8.tar.gz
tar -zxvf memcached-1.5.8.tar.gz
./configure --prefix=/usr/local/memcached
make 
make test
make install
C.安装客户端
Memcached本身就是一个键值存储的守护进程。安装不会自动加速或缓存任何数据，需要编程才能使用。应用程序将需要客户端，其中客户端应该可以很容易地用于语言选择。

三、推荐配置
1.硬件需求
Memcached很容易说明硬件的用途。简而言之，CPU使用率通常较低，占用提供大小的内存，网络使用率也会根据项目的平均大小从轻微到中度不等。
2.CPU需求
Memcached是典型CPU轻度使用，因为它的目标时快速响应。Memcached是多线程的，默认启动4个工作线程。这不意味需要运行100个内核才能使Memcached满足要求，对于通常情况，任何一点CPU通常足够，大部分情况只需要一个线程。
3.内存需求
Memcached的主要目的是将多个主机的内存段拼接起来，并让你的app将它视为一个大块内存。越多内存越好，然而，不要从其他可能从中受益的服务中抽取内存。
让每个memcached服务器拥有大致相同的内存可用区是很有帮助的。集群一致性意味着可以简单的添加和删除服务器而不用关注某个特定的权重，或者某个服务器丢失会造成更大的伤害。
4.避免交换
分配物理内存，并额外增加几个百分点。不要过度分配内存，并期望交换可以拯救，并期望交换可以拯救，性能会非常差。特别注意监控服务器是否在交换，并在必要时进行调优。
5.高速内存不是必要的
6.NUMA（NUMA（Non Uniform Memory Access Architecture）技术可以使众多服务器像单一系统那样运转）注意事项
在NUMA系统中，Memcached在正常负载下可以正常工作。当memcached跨多个NUMA节点运行时，在基准测试条件下会出现可度量的性能下降。如果对性能非常敏感，并且有NUMA系统，最好的解决方法是在每个NUMA节点上运行一个memcached实例，并通过numacti或类似的方式绑定实例。
------------硬件布局
1.在Web服务上运行Memcached
使用空闲内存，损失并不严重，但是会受其他服务的影响，并毁影响其他服务。
2.在数据库上运行Memcached
不推荐
3.使用单独的主机
无需关心其他服务的影响，并易于扩展内存容量，但损失造成的结果比较严重

四、配置服务器
1.更多的信息
查看protocol.txt文件
2.命令行参数
Memcached附带了关于其命令行参数的基本文档，使用memcached -h或man memcached以获得最新的文档。该服务力求拥有最合理的默认值。
第一次设置memcached时，应该关注memcached -m -d 和 -v
-m 告知memcached多少内存被分配用于存储(单位MB)。注意这不是限制的总内存，memcached可以多用一点。应该设置这个值为一个安全值。设置小于64MB可能被提升到64MB作为最小值。
-d 告诉memcached作为守护进程。如果通过初始脚本运行可能无需设置，但是如果第一次使用memcached，可能被教学不使用-d启动memcached并观察运行情况
-v 控制冗长STDOUT和STDERR,多个-v增加冗长，一个输出额外的启动的请求，多个输出会越来越详细地显示访问memcached的请求。如果想看测试脚本是否正在做期望的事，那么-v在前台运行memcached是个好主意。
3.初始脚本
341/5000  
如果您已经从操作系统的包管理系统中安装了memcached，那么很有可能它已经附带了一个init脚本。它们提供了一些替代方法来配置memcached接收的启动选项。例如通过/etc/sysconfig/缓存的memfile。在编辑init脚本或编写自己的脚本之前，一定要检查这些内容。
4.多实例
运行多重本地实例是无意义的。可以使用-p修改监听端口
5.网络
默认memcached监听的端口（TCP和UDP）都是11211，-l允许绑定到指定接口或IP地址。Memcached并没有花太多的精力(如果有的话)去确保它在随机的互联网连接上的可防御性。因此，您不能将memcached直接暴露给internet，或其他任何不受信任的用户。在这里使用SASL身份验证有帮助，但不应该完全信任。
6.TCP
-p改变监听端口，UDP也会调整
7.UDP
-U修改UDP端口
8.Unix Sockets
如果您希望将守护进程限制为单个本地用户可以访问，或者只是不希望通过网络公开它，那么可以使用unix域套接字。-s 是您需要的参数。如果启用此功能，将禁用TCP/UDP。
9.连接限制
默认最大并发连接数为1024,正确配置这一点很重要。与memcached的额外连接可以挂起，等待插槽释放。您可以通过发出一个stats命令并查看“listen_disabled_num”来检测您的实例是否已经耗尽了连接。这个值应该是零或者接近零。
Memcached可以用大量的连接进行伸缩，非常简单。每个连接的内存开销很低(如果连接是空闲的，甚至更低)，所以不要把它设置得太高。
假设您有5个webserver，每个都运行apache。每个apache进程的MaxClients设置为12。这意味着您可能接收到的最大并发连接数是5 x 12(60)。如果可以的话，为管理任务，添加更多的web服务器、crons/scripts/等等，保留一些额外的空档。
10.线程
线程用于跨CPU扩展memcached。该模型由“工作线程”执行，即每个线程处理并发连接。由于使用libevent允许并发连接具有良好的可扩展性，因此每个线程都能够处理许多客户机。
这与一些webserver不同，比如apache，它使用一个进程或一个线程的每个活动客户端连接。由于memcached是高效的，所以低数量的线程是可以的。在webserver领域，这意味着它更像nginx而不是apache。
默认情况下，分配4个线程。除非你非常努力地运行memcached，否则你不应该把这个数字设为更高。将其设置为非常大的值(80+)将使其运行速度大大降低。
11.查看运行配置
echo 'stats settings' | nc localhost 11211

五、客户端设置
1.Hashing 
所有客户端支持至少一种散列键到服务器的方法。记住客户端相互之间不一定兼容（compatible）.例如使用pcre和PHP。
2.一致的Hashing
一致哈希是一种模型，它允许在添加或删除服务器时更稳定地分配密钥。在通常的散列算法中，改变服务器的数量会导致许多键被重新映射到不同的服务器，导致大量的缓存丢失。一致的散列描述将键映射到服务器列表的方法，其中添加或删除服务器会导致键映射到的位置发生极小的变化。
使用正常的哈希函数，添加第11个服务器可能会导致40%以上的密钥突然指向不同的服务器。
但是，使用一致的散列算法，添加第11个服务器应该会导致小于10%的密钥被重新分配。在实践中，这种情况会有所不同，但肯定会有所帮助。
3.配置服务器一致性
在向您的配置添加服务器时，请注意，您向客户端提供的服务器列表是完全相同的。
如果您有三个webserver，并且每个webserver也运行一个memcached实例，那么您可能会试图将“local”实例定位为“localhost”。这将不会像预期的那样工作，因为服务器列表现在在web服务器之间是不同的。这意味着webserver 1将与server 2不同地映射键，从而在用户和业务开发人员中引起大规模的歇斯底里。
排序也很重要。有些客户端会对您提供给他们的服务器列表进行排序，但有些客户端不会。如果你有服务器“A, B, C”，把它们列成“A, B, C”。
使用Puppet/Chef/rsync/任何必要的东西来确保这些文件是同步的:)
4.权重
在不完美的环境下，有时您可能有一个memcached实例，它比其他实例具有更多的RAM可用性。一些客户机将允许您向较大的服务器应用更多的“权重”。另一些则允许您多次指定一个服务器，以获得更多的被选择的机会。
无论哪种方式，你都可以很好地验证“权重”是在做你所期望的事情。
5.失败或故障转移
当服务器不可用或提供无效响应时，客户端将做什么?
在memcached的黑暗日子里，默认情况下总是“故障转移”，通过在列表中尝试下一个服务器。这样，如果一个服务器崩溃，它的密钥将被重新分配到其他实例，一切都会顺利进行。
然而，有很多方法可以杀死一台机器。有时他们甚至不喜欢死。考虑到场景:
Sysadmin Bob经过服务器B，并将以太网电缆从其端口上敲出。
服务器B的密钥被“路由”到其他实例。
Sysadmin Bob是一个细心(如果有点笨拙的话)的人，他会尽职地从其分开的端口恢复以太网电缆。
服务器B的密钥被“重新路由”回自己。
Bob意识到他的错误时，您对缓存所做的任何更新都已丢失，旧数据将呈现给用户。如果:
服务器B的以太网剪辑由于Bob的愚蠢而被破坏，几个小时后从它的端口自动脱落。
现在你的数据又回到了非常过时的数据。烦人。
另一个错误的客户端特性会在服务器出故障时修改服务器列表，这最终会重新映射比它应该重映射的更多的键。
现代生活鼓励在可能的情况下使用“失败”。也就是说，如果您打算获取或存储缓存条目的服务器不可用，那么只需将其视为缓存丢失。如果您有服务器B的情况，您可能仍然会在新旧数据之间切换，但效果会降低。
6.压缩
压缩大的值是一个很好的方法，可以从你的记忆中获得更多的价值。压缩可以为一些值节省大量内存，而且还可以减少延迟，因为较小的值在网络上更快地获取。
大多数客户端支持通过项大小的阈值启用或禁用压缩，有些客户端支持基于项的压缩。较小的项目不一定会从数据减少中得到很多好处，而且只会浪费CPU。
7.管理连接对象
一个常见的初次失败是，无论您做什么，您似乎都在连接之外运行memcached。您的小服务器正在为memcached分配50,000个连接，您不知道发生了什么。
注意如何管理连接对象!如果您每次希望联系memcached时都在不断初始化连接对象，那么很有可能会泄漏连接。
有些客户端(比如PHP客户端)在管理打开的连接数量方面没有那么明显。继续调用“addServer()”可能只会泄漏您的连接，即使您已经添加了服务器。阅读客户的文档，以确定哪些操作创建连接，哪些操作不创建连接

六、客户端

七、API命令
1.标准协议
memcached的标准协议内容涉及（involve）对项执行命令
项目组成：
A.键（可达250字节的任意字符串，没有空格和换行，ASCII格式）
B.32位的标志值
C.一个过期时间，单位是秒，0表示永不过期，最大为30天。30天后，表示一个确切日期的unix时间戳。
D.64位CAS值，唯一
E.任意数据
F.CAS是可选的（-C完全禁用）
2.无需回应
大多数ASCII命令允许NoReply版本。
大多数ASCII命令都允许“正常”版本。通常不应该在ASCII协议中使用它，因为不可能将错误与请求对齐。其目的是为了避免在执行了一个突变命令(例如设置或添加)后等待返回数据包。
二进制协议恰当地实现了noreply (quiet)语句。如果您有一个支持或使用二进制协议的客户端，您可以利用这个优势
3.存储命令
A.set
最常用的命令。存储数据，可能重写任何已存在的数据。新项目放在LRU的最上面。
B.add
只在不存在数据的时候存储数据。新加的项目被加到LRU顶部。已存在的添加失败但是升级项目到LRU顶部。
C.replace
只在存在数据的时候存储数据。几乎不适用，为了协议完整性存在。
D.append
在已存在的项目后添加数据。这不允许扩展超出项目限制。对管理列表有帮助。
E.prepend
和append类似，但是在已存在的数据之前添加数据
F.cas
检查和设置（或者比较和交换）.一个操作只在上次读取之后没有其他人更新的情况下存储数据。用于解决更新缓存数据的竞争条件。
4.检索命令
1.get
检索数据命令，使用一个或多个键并返回找到的项目
2.gets
使用CAS检索，返回CAS标识符（64位数字）和数据一起。使用cas返回此数字，若有人更改则不存储数据。
5.删除
delete
如果存在则删除数据
6.incr/decr
增加和减少。如果一个项目已64位整型的字符串形式存储，可以使用incr/decr来修改数字。值接受正数，不接受负数。
7.统计（statistics）
有一些命令返回memcached服务器的计数器和设置。这些可以通过大量工具进行检查，也可以通过telnet或netcat进行检查。这些在协议文档中有进一步的解释
A.stats
B.stats items
返回一些关于存储在memcached内的项的信息，这些信息按片分类。
C.stats slabs
返回关于存储在memcached内的项目的更多信息，被平板分解。更集中于平板的性能而不是特定的项目。
D.stats sizes
一个特殊的命令，它向您显示如果将面板分解为32字节的bucket而不是当前的面板数量，那么项目将如何分布。这对于确定板坯尺寸的效率非常有用。
警告这是一个开发命令。从1.4开始，它仍然是唯一一个将在一段时间内锁定memcached实例的命令。如果您有数百万的存储项，它可能会在几分钟内失去响应。你自己承担风险。它被映射到使这个特性可选或者至少加速它。
8.flush_all
使所有现有的缓存项无效。可选地获取一个参数，这意味着在传递了N秒之后，所有项都无效。
此命令不会暂停服务器，因为它会立即返回。它根本不会释放或刷新内存，它只会导致所有项过期。

----------------- 暂时没看完 先实践 -------------------------




--------------- 实践
安装
wget memcached_url
tar -zxvf memcachedxxx.tar.gz
./configure --prefix=/usr/local/memcached
make 
[make test]
make install
cd /usr/local/memcached/bin
     # 启动时可选参数 -d 守护进程 -m 分配的内存大小单位MB -u 运行时使用的用户名 -l 监听的服务器IP地址可为多个 -p 监听端口 -c 最大并发连接数  -P 设置保存Memcached的pid文件
./memcached -m 32 -p 11211 -vv # 前台启动
./memcached -m 32 -l 127.0.0.1 -p 11211 -d # 后台启动（守护进程）
telnet localhost 11211
set foo 0 0 3 # set -> 命令 foo -> key 0 -> flag 0 -> expire time 3 -> bytes
bar # 设置foo对应的数据为bar
get foo # 检索foo

Memcached set 命令
格式
set flags expire_time bytes [noreply]
data
key 键值队中的键
flags 用于存储键值对外的额外信息
expire_time 在缓存中保存键值对的时间长度
bytes 在缓存中存储的字节数
data 键值对的值

add命令
add key flags expire_time bytes [noreply]
data
同上
add只在不存在key的时候添加成功，已存在则只放于LRU头而不设置新值

replace命令
replace key flags expire_time bytes [noreply]
data
只在存在时替换

append命令
append key flags expire_time bytes [noreply]
data
在尾部添加

prepend命令
prepend key flags expire_time bytes [noreply]
data
在头部添加


cas命令
cas key flags expire_time bytes unique_cas_token [noreply]
data
unique_cas_token gets命令带出的参数
返回值
STORED 保存成功
ERROR 爆粗你失败
EXISTS 在最后一次取值后另外一个用户也更新此数据
NOT_FOUND Memcached服务器上不存在改键值

get命令
get key 
get key1 key2 key3
返回
key flags bytes 
data

get命令
gets key
gets key1 key2 key3
返回
key flags bytes unique_cas_token 
data

delete命令
delete key

incr/decr
incr key increment_value
decr key decrement_value

stats命令
用于返回统计信息例如PID(进程号)、版本号、连接数等
pid：	memcache服务器进程ID
uptime：服务器已运行秒数
time：服务器当前Unix时间戳
version：memcache版本
pointer_size：操作系统指针大小
rusage_user：进程累计用户时间
rusage_system：进程累计系统时间
curr_connections：当前连接数量
total_connections：Memcached运行以来连接总数
connection_structures：Memcached分配的连接结构数量
cmd_get：get命令请求次数
cmd_set：set命令请求次数
cmd_flush：flush命令请求次数
get_hits：get命令命中次数
get_misses：get命令未命中次数
delete_misses：delete命令未命中次数
delete_hits：delete命令命中次数
incr_misses：incr命令未命中次数
incr_hits：incr命令命中次数
decr_misses：decr命令未命中次数
decr_hits：decr命令命中次数
cas_misses：cas命令未命中次数
cas_hits：cas命令命中次数
cas_badval：使用擦拭次数
auth_cmds：认证命令处理的次数
auth_errors：认证失败数目
bytes_read：读取总字节数
bytes_written：发送总字节数
limit_maxbytes：分配的内存总大小（字节）
accepting_conns：服务器是否达到过最大连接（0/1）
listen_disabled_num：失效的监听数
threads：当前线程数
conn_yields：连接操作主动放弃数目
bytes：当前存储占用的字节数
curr_items：当前存储的数据总数
total_items：启动以来存储的数据总数
evictions：LRU释放的对象数目
reclaimed：已过期的数据条目来存储新数据的数目

stats items命令
用于显示各个 slab 中 item 的数目和存储时长(最后一次访问距离现在的秒数)。
stats items
STAT items:1:number 1
STAT items:1:age 7
STAT items:1:evicted 0
STAT items:1:evicted_nonzero 0
STAT items:1:evicted_time 0
STAT items:1:outofmemory 0
STAT items:1:tailrepairs 0
STAT items:1:reclaimed 0
STAT items:1:expired_unfetched 0
STAT items:1:evicted_unfetched 0
END

stats slabs命令
用于显示各个slab的信息，包括chunk的大小、数目、使用情况等。
stats slabs
STAT 1:chunk_size 96
STAT 1:chunks_per_page 10922
STAT 1:total_pages 1
STAT 1:total_chunks 10922
STAT 1:used_chunks 1
STAT 1:free_chunks 10921
STAT 1:free_chunks_end 0
STAT 1:mem_requested 71
STAT 1:get_hits 0
STAT 1:cmd_set 1
STAT 1:delete_hits 0
STAT 1:incr_hits 0
STAT 1:decr_hits 0
STAT 1:cas_hits 0
STAT 1:cas_badval 0
STAT 1:touch_hits 0
STAT active_slabs 1
STAT total_malloced 1048512
END

stats sizes命令
用于显示所有item的大小和个数。
该信息返回两列，第一列是 item 的大小，第二列是 item 的个数。

flush_all命令
用于清理缓存中的所有 key=>value(键=>值) 对。
该命令提供了一个可选参数 time，用于在制定的时间后执行清理缓存操作。