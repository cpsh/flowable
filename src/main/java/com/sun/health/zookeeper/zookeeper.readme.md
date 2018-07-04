Zookeeper
Zookeeper作为Hadoop和Hbase的重要组件，可以为分布式应用程序协调服务，同时还能使用Java和C接口。
Zookeeper是一种分布式协调服务，用于管理大型主机。在分布式环境中协调和管理服务是一个复杂的过程。Zookeeper简单的架构和API很好的解决了这个问题。
Zookeeper概述
分布式应用
分布式应用可以在给定时间（同时）在网络中的多个系统上运行，通过协调以快速有效的方式完成特定任务。通常而言，对于复杂而耗时的任务，非分布式应用（单系统中应用）需要较长时间，而分布式系统则可以大幅缩短所需时间。
通过将分布式应用配置在更多系统上运行，可以进一步减少完成任务的时间。分布式应用正在运行的一组系统称为集群，而在集群中运行的媚态机器被称为节点。
分布式应用有两部分，Server（服务器）和Client（客户端）应用程序。服务器应用程序实际上是分布式的，并具有通用接口，以便客户端可以连接到集群中的任何服务器并获得相同的结果。客户端应用程序是与分布式应用进行交互的工具。
分布式应用的优点
1.可靠性：单个或几个系统的故障不会使整个系统出现故障
2.可扩展性：可以在需要时增加性能，通过添加更多机器，在应用程序配置中进行微小的更改，而不会有停机时间
3.透明性：隐藏系统的复杂性，并将其显示为单实体/应用程序
分布式应用的挑战
1.竞争条件：两个或多个机器尝试执行特定任务时，实际上只能在同一时间由单个机器完成，共享资源在同一时间只能由单个机器修改。
2.死锁：两个或多个操作等待彼此无限期完成
3.不一致：数据部分失败
Zookeeper
Apache Zookeeper是有集群（节点组）使用的一种服务，用于在自身之间协调，并通过稳健的同步技术维护共享数据。Zookeeper本身是一个分布式应用程序，为写入分布式应用程序提供服务。
Zookeeper提供常见的服务
1.命名服务 按名称标识集群中的节点，类似于DNS，但仅对于节点
2.配置管理 加入节点的最新的系统配置信息
3.集群管理 实时地在集群和节点状态中加入/移除节点
4.选举算法 选举一个节点作为协调目的的leader
5.锁定和同步服务 在修改数据的同时锁定数据 可帮助在链接其他分布式应用程序时进行自动故障恢复
6.高度可靠的数据注册表 即使一个或几个节点关闭时也可以获得数据
Zookeeper的好处
1.简单的分布式协调过程
2.同步 服务器进程之间的相互排斥和协作
3.序列化 根据特定规则对数据进行编码 确保应用程序运行一致
4.可靠性
5.原子性

Zookeeper基础
Architecture(架构)
Client(客户端)     客户端，分布式应用集群中的一个节点，访问服务器信息。特定的时间间隔，每个客户端向服务器发送消息以使服务器知道客户端是存活状态。类似的，当客户端连接时，服务器发送确认码。如果服务器没有响应则将重定向至另一台服务器
Server(服务器)     节点，为客户端提供服务，向客户端发送确认码以表示存活
Ensemble           Zookeeper服务器组，形成Ensemble最少需要3台服务器
Leader             服务器节点，如果任何连接的节点失效，则执行自动恢复。Leader在启动时自动选举
Follower           跟随Leader指令的服务器节点
层次命名空间
采取文件系统的树结构。Zookeeper节点称为znode，每个znode由一个名称标识，并用路径/序列分隔
例如
首先有一个/命名的zonde，为根目录。在此下有两个逻辑命名空间config和workers
config命名空间用于集中式配置管理，workers命令空间用于命名
config命名空间下，每个znode最多可存储1MB的数据。这个结构的主要目的是存储同步数据并描述znode的元数据，次结构称为Zookeeper数据模型
Zookeeper数据模型中的每个znode都维护一个stat结构，一个stat仅提供一个znode的元数据，由版本号，操作控制列表（ACL），时间戳和数据长度组成
1.版本号 每个znode都有版本号 意味着每当与znode相关联的数据发生变化时，其对应的版本号也会增加。当多个zookeeper客户端尝试在同一znode上执行操作时，版本号的使用就很重要
2.操作控制列表（ACL) ACL基本是访问znode的认证机制，管理所有读写操作
3.时间戳 时间戳表示创建和修改znode的时间，通常以毫秒为单位。Zookeeper以事务ID(zxid)标识znode的每个更改。zxid是唯一的，并且为每个事务保留时间，以便确定请求经历的时间
4.数据长度 存储在znode中的数据总量是数据长度 最多存储1MB的数据
Znode的类型
分为Persistent持久节点，Sequential顺序节点，ephemeral临时节点
1.持久节点(Persistent) 即使在创建该znode的客户端断开连接后，持久节点依旧存在，默认情况下，节点是持久节点
2.临时节点（Ephemeral) 客户端活跃时，临时节点是有效的，断开后自动删除。临时节点不允许有子节点，如果临时节点被删除，则下一个合适的节点将填充，临时节点在选举中起到重要作用
3.顺序节点（Sequential） 顺序节点可以是临时或永久的，当一个新的顺序节点被创建时，Zookeeper通过将10位序列号附加到原始名称来设置znode路径。如，将路径/myapp的znode创建为顺序节点则会将路径改为/myapp0000000001,下一个则是/myapp0000000002。不会有相同数字的znode，顺序节点在锁定和同步中起到重要作用。
Session(会话)
会话对于Zookeeper的操作非常虫咬，会话中的请求按FIFO顺序执行，一旦客户端连接到服务器，将建立会话并向客户端分配会话ID
客户端以特定的时间间隔发送心跳以确保会话有效，如果Zookeeper集群超过服务器指定时间间隔未收到心跳，则判定客户端死机
会话超时通常以毫秒为单位，当会话由于任何原因结束，在该会话期间创建的临时节点也会删除
Watches(监视)
监视是一种简单的机制，使客户端收到关于Zookeeper集群中的更改通知。客户端可以在读取znode时设置Watches,Watches会想注册的客户端发送任何znode（客户端注册表）更改的通知
Znode更改只触发一次Watches。想要再次通知，需要再次设置Watches。当连接会话过期，相关Watches也会被删除

Zookeeper工作流
Zookeeper启动将等待客户端连接。客户端连接到Zookeeper集群中的一个节点，可以是leader或follower。一旦客户端连接，节点将分配会话ID并向客户端发送确认，若客户端未接受到确认则将尝试连接集群中的另一个节点。一旦连接到节点，客户端将以固定时间间隔向节点发送心跳，以确保连接不会丢失。
1.如果客户端想要读取特定的znode，将会向具有znode路径的节点发送读取请求，并且节点通过从其读取返回，速度很快
2.如果客户端想要存储数据到特定的znode，则会将znode路径和数据发送到服务器。连接的服务器将该请求转发给leader，然后leader向所有的follower重新发出写入请求，如果大部分节点成功响应则写入成功，将成功响应码发送到客户端，如果大部分失败则返回失败
Zookeeper集合中的节点
由于自动恢复需要半数以上的节点确认，因此最少需要3个。
写入      写入过程由leader节点处理，leader将写请求转发到所有节点，并等待从节点的回复，超过一半回复成功则写入成功
读取      读取由连接的节点执行，无需与集群进行交互
复制数据库 用于在zookeeper中存储数据，每个节点都有自己的数据库，在一致性的帮助下拥有相同的数据
Leader    负责处理写入请求的Znode
Follower  可以单独处理读请求，而写请求需要转发给Leader
请求处理器 只存在于Leader节点，管理来自follower节点的写请求
原子广播   负责广播从leader节点到follower节点的变化

Zookeeper Leader选举
1.所有节点创建具有相同路径/app/leader_election/guid_的顺序、临时节点
2.Zookeeper集合将附加10位序列到路径，创建的znode将是/app/leader_election/guid_0000000001,/app/leader_election/guid_0000000002 ...
3.创建的znode数字最小的节点成为leader，其余变为follower
3.每个follower监视比自己数字小一个名次的节点
4.如果leader关闭，则其相应的znode/app/leader_election/guid_将被删除
5.下一个在线的follower节点将通过Watches获得leader移除的通知
6.下一个在线的follower检查是否存在更小数字的节点，有则最小数字称为leader，没有则本身称为leader

Zookeeper安装
需要先安装JDK
然后下载压缩包，解压，手动创建两个文件夹作为data和log存储的目录，修改conf/路径下的zoo.cfg(复制自zoo_sample.cfg)中的配置信息。最后将bin/配置到环境变量中（使用软连接会有问题）

Zookeeper CLI
Zookeeper命令行界面(CLI)用于于Zookeeper集合进行交互，有助于调试和解决不同的选项。
CLI中可以执行以下操作：
1.创建znode
2.获取数据
3.监视znode的变化
4.设置数据
5.创建znode的子节点
6.列出znode的子节点
7.检查状态
8.移除/删除znode
创建znode
用给定的路径创建一个znode，flag参数指定创建的znode是顺序的，永久的还是临时的。默认情况下，所有znode都是持久的。
当会话过期或客户端断开连接时，临时节点将被自动删除
顺序节点保证znode路径将是唯一的。
语法结构
create [options] /path /data
options可选
-s 顺序节点
-e 临时节点
获取数据
返回znode关联数据和元数据，例如上次修改数据的时间，修改的位置以及数据的相关信息，此命令还用于分配监视器以显示数据相关的通知
get /path 
监视（Watch）
当指定的znode或znode的子数据更改时，监视器会显示通知，只能在get命令中设置watch
语法结构
get /path [watch] 1
直接输出类似于get命令 但是会在后台等待znode变化
当znode改变时 输出类似
WATCHER::
WatchedEvent state:SyncConnected type:NodeDataChanged path:/sjbuluo/watch
设置数据
设置指定znode的数据 完成此设置操作后 可以使用get命令获取数据
set /path /data
创建子项/子节点
create /parent/path/subnode/path /data
列出子项
ls /path
检查状态
指定znode的元数据
stat /path
移除znode
移除指定znode并递归移除所有子节点
rmr /path
删除节点
只适用于没有子节点的节点
delete /path

Zookeeper API
使用API，应用程序可以连接、交互、操作数据、协调，断开连接。
具有丰富的功能，以简单和安全的方式获得Zookeeper集合的所有功能，提供异步和同步的方法
基础知识
客户端应该遵循以步骤，与Zookeeper结合进行清晰和干净的交互
1.连接到Zookeeper服务器，服务器分配为客户端分配会话ID
2.定期向服务器发送心跳，否则可能会话ID过期需要重新连接
3.只要会话ID处于活跃状态，就可以获取/设置znode
4.所有任务完成后，断开与Zookeeper集群的连接，长期不活动也会自动断开。

--------------------------- 详解
ZooKeeper数据模型
Znode
Zookeeper拥有一个层次的命名空间，和标准的文件系统非常相似。
采用树形层次结构，Zookeeper树中的每个节点称为Znode
1.引用方式
Znode通过路径引用，类似于Unix中的文件路径。路径必须是绝对的，必须由斜杠符开头。除此之外，也必须是唯一的，也就是每个路径只有一个表示，因此路径不能改变。路径由Unicode字符串组成，并且有限制，比如字符串/zookeeper用以保存管理信息
2.Znode结构
Zookeeper命名空间的Znode，兼具文件和目录两种特点，既像文件一样维护着数据、元信息、ACL、时间戳等数据结构，又像目录一样可以作为路径标识的一部分
每个Znode由3部分组成
stat        状态信息，描述Znode的版本、权限等信息
data        与该Znode关联的数据
children    该Znode下的子节点
Zookeeper虽然可以关联一些数据，但没有被设计为常规的数据库或大数据存储，相反，用于管理调度数据，比如分布式应用中的配置文件信息，状态信息，汇集位置等。这些数据的共同特性就是很小，通常以KB为单位，Zookeeper的服务器和客户端都被设计为严格检查并限制每个Znode的数据最多1MB。
3.数据访问
Zookeeper的每个节点存储的数据都要被原子性操作，也就是说读操作将获取与节点相关的所有数据，写操作将替换节点的所有数据。另外，每个节点都拥有自己的ACL（访问控制列表），这个列表规定了用户的权限，即限定了特定用户对目标节点可以执行的操作。
4.节点类型
两种，分为永久节点和临时节点。节点的类型在创建时被确定，不能修改。
a.临时节点
节点的生命周期依赖于创建的会话，一旦会话（session）结束，临时节点将被自动删除，当然可以也可以手动删除节点。虽然每个临时节点都会绑定到一个客户端会话，但是对所有客户端可见。另外，临时节点不允许拥有子节点
b.永久节点
生命周期不依赖与创建的会话，并且只有在客户端显示执行删除操作的时候，才能被删除。
5.顺序节点
创建Znode的时候，用户可以请求在Zookeeper的路径结尾添加一个递增的计数，这个计数对于此节点的父节点来说是唯一的，格式%10d(10位数字，没有数值的数位用0补充)。
6.观察
客户端可以在节点上设置watch，称为监视器。当节点状态发生改变时（Znode的增、删、改）将会触发watch所对应的操作。当watch被触发时，Zookeeper将会向客户端发送且仅发送一条通知，因为watch只能被触发一次。
Zookeeper中的时间
致使Znode状态改变的每一个操作都将使Znode接受到一个Zxid格式的时间戳，并且这个时间戳全局有序。也就是说，每个对节点的改变都将产生一个唯一的Zxid。如果Zxid1的值小于Zxid2的值，则1对应的事件发生在2之前。
实际上，Zookeeper每个Znode维护着3个Zxid的值，分别为cZxid、mZxid、pZxid
cZxid   节点创建时间对应的Zxid格式的时间戳
mZxid   节点修改时间对应的Zxid格式的时间戳
pZxid   是与该节点的子节点（包含自身）最近一次创建/删除的时间戳对应
Zxid是一个64位的数字，高32位时epoch用来标识leader是否改变，每个leader新选出来，都会有一个新的epoch,低32位时个递增计数
版本号Version
对Znode每一个操作都将致使版本号增加，每个节点维护3个版本号
version 节点数据版本号
cversion 子节点版本号
aversion ACL版本号
Zookeeper节点属性 stat
czxid   创建zxid
mzxid   修改zxid
pzxid   自身或子节点创建/删除的zxid
ctime   创建时间
mtime   修改时间
version 数据版本号
cversion 子节点版本号
aversion ACL版本号
ephemeralOwner 若为临时节点则是拥有者的sessionId,若为永久节点则是0
dataLength  数据长度
numChildren 子节点长度
Zookeeper服务中操作
有9个基本操作
create                  创建Znode 父Znode必须存在
delete/remove           删除没有子节点Znode/递归删除此Znode下所有Znode
exists                  是否存在此Znode
getACL/setACL           获取/设置ACL
getChildren             获取子Znode
getData/setData         获取/设置数据
sync                    使客户端的Znode视图与Zookeeper同步
更新Zookeeper操作是有限制的，delete或setData必须明确要跟新的Znode的版本号，可以使用exists查询。如果版本号不同，更新失败。
更新Zookeeper操作是非阻塞的。
尽管Zookeeper可以被看做一个文件系统，但是出于便利，摒弃了一些文件系统的操作原语。以为文件非常小且都是原子操作，所以不需要打开、关闭或寻址操作
监听机制
Watch触发器
watch概述
Zookeeper可以为所有读操作设置watch，这个读操作包括exists()/getChildren()/getData().watch事件是一次性的触发器，当watch的对象状态发生改变时，将会触发此对象上watch所对应的事件。watch事件将被异步发送给客户端，并且Zookeeper为watch机制提供了有序的一致性保证。
watch类型
分为两类
1.data watch getData和exists用于设置此类watch
2.child watch getChildren用于设置此类watch
可以通过操作返回的数据来设置不同的watch
1.getData个exists返回关于节点的数据和元数据信息
2.getChildren返回子节点列表
因此
1.成功的setData触发data watch
2.成功的create/delete触发child watch
watch注册与触发
1.exists操作的watch，在监视的Znode创建(NodeCreated)、删除(NodeDeleted)或数据更改(NodeDataChanged)时被触发
2.getData操作的watch，在监视的Znode删除(NodeDeleted)、数据更改(NodeDataChanged)时触发
3.getChildren操作的watch，在监视的Znode删除(NodeDeleted)、子节点创建(NodeChildrenChanged)、子节点删除(NodeDeletedChanged)时触发
Watch由客户端连接的Zookeeper服务器在本地维护，因此watch非常容易设置、管理和分派。当客户端连接到一个新的服务器时，任何会话事件都将可能触发watch。当从服务器断开连接时，watch将不会被接受，但是当一个客户端重新建立连接的时候，任何先前注册过的watch都会重新注册。
需要注意
Zookeeper的watch实际上要处理两类事件
1.连接状态事件（type=None,path=null)
这类事件不需要注册，也不需要连续触发，只需处理即可
2.节点事件
节点的建立，删除，数据的修改都触发。是一次性的，需要不停注册触发，还可能发生时间丢失的情况。
两类都在Watcher类中处理，也就是重载的process(Event event)方法
监听本身的功能
Zookeeper的Watcher机制主要包括客户端线程、客户端WatcherManager、Zookeeper服务器三部分、客户端向Zookeeper服务器注册的同时，会将Watcher对象存储在WatcherManager中，当Zookeeper服务器触发Watcher事件后，会向客户端发送通知，客户端线程从WatcherManager中取出对应的Watcher对象来执行回调逻辑。
Zookeeper集群
Zookeeper配置文件
基本配置
1.tickTime
心跳基本事件单位，毫秒级，ZK基本上所有的时间都是这个时间的倍数
2.initLimit
tickTIme的倍数，表示在leader选举之后，follower同步需要的时间。这个值也是follower和observer在开始同步leader的数据时最大等待时间。
3.syncLimit
tickTime的个数，表示follower和observer与leader交互时的最大等待时间，只不过是同步完毕之后，正常请求、妆发或ping等消息交互时的超时时间
4.dataDir
内存数据快照存放地址，如果没有指定事务日志存放地址（dataLogDir）默认也是存放在此目录下，建议分开存储
5.clientPort
配置ZK监听客户端连接的端口
6.dataLogDir
事务日志存放地址
7.server.serverid=host:tickport:electionport
集群配置
server          固定写法
serverid        每个服务器指定的ID（必须是1-255之间，不能重复）
host            主机名
tickport        心跳通信端口
electionport    选举端口
高级配置
1.globalOutstandingLimit
默认值是1000，限定了所有连接到服务器但是还没有返回响应的请求个数（所有客户端请求总数），不宜过大
2.preAllocSize
默认值是64M，以KB为单位，预先分配额定控件用于后续的transaction写入，不足4k时则再分配64M
3.snapCount
默认值时100000,当transaction每达到snapCount/2+rand.nextInt(snapCount/2)时，就做一次SNAPSHOT,默认情况下是50,000~100,000条transactionlog就会做一次，之所以用随机数是为了避免所有服务器可能在同一时间做snapshot.
4.traceFile

5.maxClientCnxns
默认只是10，一个客户端能够连接到同一个服务器上的最大连接数，根据IP来区别。如果设置为0，表示没有任何限制。一方面为了防止DoS攻击。
6.clientPortAddress
与clientPort匹配
7.minSessionTimeout
最小的session存活时间，默认是2被tickTime，如果客户端设置的session timeout小于此值 则调整到此值
8.maxSessionTimeout
最大的session存活时间，默认是20倍tickTime 如果客户端设置的session timeout大于此值 则调整至此值
集群配置选项
1.electionAlg
选举算法 默认是3(fast leader election 基于TCP) 
0表示leader选举算法
1表示非授权快速选举算法
2表示授权快速选举算法
除了3不应该使用
2.initLimit
3.syncLimit
4.leaderServes
不为no时表示leader需要处理客户端请求，一般服务器3台以上建议设置为no
cnxTimeout
默认值时5000，单位是ms表示leader election时打开连接的超时时间
ZK的不安全配置项
1.skipACL
默认值是no 忽略所有ACL检查，相当于开放所有数据权限
2.forceSync
默认值时yes，表示transaction log在commit时是否立即写到磁盘上 如果关闭可能存在丢失风险
3.jute.maxbuffer
默认值是0xfffff,单位KB，表示节点数据最多1M，如果要设置此值，所有服务器上需相同
4.DigestAuthenticationProvider.superDigest
设置一个超级用户
集群配置时
需要在dataDir目录下新建一个myid文件并设置当前服务器的id对应server.myserverid配置的值
Zookeeper特定/设计目的
Zookeeper作为一个集群提供数据一致的协调服务，自然，最好的方式就是整个集群中的各个服务节点进行数据的复制和同步。
数据复制的好处
1.容错 一个节点出错 不至于让整个集群无法提供服务
2.扩展性 通过增加服务器节点能提高Zookeeper系统的负载能力
3.高性能 客户端可访问本地Zookeeper节点或者访问就近的节点
设计目的
1、最终一致性：client不论连接到哪个Server，展示给它都是同一个视图，这是zookeeper最重要的性能。 
2、可靠性：具有简单、健壮、良好的性能，如果消息被到一台服务器接受，那么它将被所有的服务器接受。 
3、实时性：Zookeeper保证客户端将在一个时间间隔范围内获得服务器的更新信息，或者服务器失效的信息。但由于网络延时等原因，Zookeeper不能保证两个客户端能同时得到刚更新的数据，如果需要最新数据，应该在读数据之前调用sync()接口。 
4、等待无关（wait-free）：慢的或者失效的client不得干预快速的client的请求，使得每个client都能有效的等待。 
5、原子性：更新只能成功或者失败，没有中间状态。 
6、顺序性：包括全局有序和偏序两种：全局有序是指如果在一台服务器上消息a在消息b前发布，则在所有Server上消息a都将在消息b前被发布；偏序是指如果一个消息b在消息a后被同一个发送者发布，a必将排在b前面。 
Zookeeper典型应用场景
1.命名服务
命名服务是分布式系统中较为常见的一类场景，分布式系统中，被命名的实体通常是集群中的机器、提供的服务地址或远程对象等，通过命名服务，客户端可以根据指定名称来获取资源的实体、服务地址和提供者的信息。Zookeeper也可帮助应用系统通过资源引用的方式来实现对资源的定位和使用，广义上的命名服务的资源定位都不是真正意义上的实体资源，在分布式环境中，上层应用仅仅需要一个全局唯一的名字。Zookeeper可以实现一套分布式全局唯一ID的分配机制。
2.配置管理
如果程序分散部署在多台机器上，逐个改变比较困难，若将配置存储在Zookeeper上，统一保存在某个特定的目录节点中，然后所有相关的应用程序对这个节点监视，一旦配置信息发生变化，每个应用程序会受到Zookeeper的通知，然后获取新的配置信息应用到系统中
3.集群管理
集群管理在于两点：服务器的加入和退出、选举master
服务器的加入和退出
所有机器在事先约定的特定目录下创建临时节点，当机器退出时临时节点删除，其他的服务器收到通知即可知道此服务器退出，加入的原理相同，创建新的临时节点其他服务器收到通知。
选举master
创建顺序节点，最小的节点称为master（只是一种策略）
4.分步式锁
通过Zookeeper的一致性文件系统，分布式锁实现变得容易，锁服务分为3类
1.写锁 保持独占 别称 排它锁 独占锁
2.读锁 可共享访问 释放锁之后才可进行事务操作 也叫共享锁
3.控制时序 叫做时序锁
对于读写锁，将Zookeeper上的znode看做锁，所有客户端都创建/distribute_lock节点，创建成功的拥有锁，事务完成后删除创建的节点就释放了锁
对于时序锁，/distribute_lock锁已经预先存在，所有客户端在下面创建顺序节点，和选举类似，最小的节点对应的客户端获取锁,用完删除
5.队列管理
两种类型的队列
1.同步队列 所有成员聚齐时，此队列才可使用
2.先进先出队列 FIFO
对于1，在事先约定的目录下创建临时节点，监视达到指定数量
对于2，和时序锁以及选举类似，创建顺序临时节点，按照编号操作
Zookeeper原理解析
Zookeeper中的各种角色
Leader      负责投票的发起和决议，更新系统状态
Follower    用于接受客户端请求并向客户端返回结果 在选举时参与投票
ObServer    可以接受客户端请求并向客户端年返回结果 但不参与选举 只同步Leader的状态 目的是扩展系统 提高读取速度
Client      请求发起方
服务器在工作过程中有三种状态
1.LOOKING 正在搜素Leader
2.LEADING   作为Leader工作
3.FOLLOWING 作为Follower或ObServer工作

