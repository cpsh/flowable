Redis安装
下载redis-xxx.tar.gz
解压 tar -zxvf redis-xxx.tar.gz
sudo make [PREFIX=/path] # 可选安装路径
sudo make test # 测试安装是否成功 
sudo make install # 将redis-server redis-cli等执行文件复制到/usr/local/bin目录下 可以直接执行

测试安装是否成功
sudo redis-server # 如果redis安装在需要权限的目录下 必须要要sudo 否则关闭redis无法保存当前快照 导致无法结束
redis-cli # 连接redis服务器
CONFIG GET * # 查看所有配置信息
shutdown # 关闭redis服务器
exit # 退出redis客户端

Redis配置文件
在解压后的redis目录下有redis.conf文件，作为redis的配置文件(如果redis-server不带参数则使用的内置配置而不是此文件)
1.include 
如果有多个配置文件时，可以在一个配置文件中include其他的完成引用。注意不会被CONFIG REWRITE重写
2.loadmodule
引用模块
3.bind 127.0.0.1
bind ip_address{1,}
只接受来自指定IP地址的请求,默认被设置为127.0.0.1，只接受本机请求，真正使用时需要修改
4.protected-mode yes
保护机制，当protected mode启用，并没有显示配置bind绑定IP地址，也没有配置password访问密码，此时只接受来自回环的IPV4/V6地址127.0.0.1,::1,和来自Unix域套接字
5.port 6379
监听端口号
6.tcp-backlog (backlog 待办事项)
在每秒请求数高的环境中，需要高效待办事项列表，避免客户端连接速度慢的问题。Linux内核会悄悄把它截断为/proc/sys/net/core/somaxconn的值，确保同时提高somaxconn和tcp_max_syn_backlog的值，确保获得预期的效果。
7.unixsocket
指定用于连接请求的Unix套接字目录。不是默认的，未指定就不监听unix套接字
8.tcp-keepalive 300
如果非零，则在缺少交流的情况下使用SO_KEEPALIVE发送TCP确认字符到客户端,用处
a.检测死链
b.从中间网络设备的视角保持连接活跃
9.daemon no
默认不是守护进程，注意redis会在守护进程模式时将进程ID写到/var/run/redis.pid中
10.supervised no / supervised upstart / supervised systemd supervised auto
暂时没看懂
11.pidfile /var/run/redis_6379.pid
如果指定pid文件，redis在启动时创建在停止时删除
非守护进程模式，未指定则不创建，守护进程则在/var/run/redis.pid,此文件创建失败不影响redis正常使用
12.loglevel notice
日志级别，
a.debug 最多信息 对开发/测试有帮助
b.verbose 较多，但比debug少
c.notice 常用于生产黄金
d.warning 只有重要/关键的消息记录
13.logfile ""
指定日志文件名。空字符串用于指导redis输出日志到标准输出中。但是如果是守护进程模式，则被发送到/dev/null文件中
14.syslog-enabled no
启用发送机日志到系统日志器
15.syslog-ident redis
指定在系统日志中的标识符
16.syslog-faility local0
指定系统日志设备。必须是USER或local0-local7
17.database 16
设置数据库数量。默认数据库是DB 0，可以使用SELECT <dbid>在每个连接上选择不同数据库 值在0和数据库数量之间
18.always-show-logo yes
启用时显示logo
19.save <seconds> <changes>
保存数据库快照到硬盘中
save 900 1 # 一个修改后过900秒保存
save 300 10 # 10次修改后过300秒保存
注意，没有save命令则不保存或者使用save ""
20.stop-writes-on-bgsave-error yes
需要保存但上次保存失败时，redis拒绝接受写请求。
21.rdbcompression yes
使用LZF压缩字符串数据,大部分情况是有益的，除非想要节约CPU资源
22.rdbchecksum yes
没看
23.dbfilename dump.db
保存db的文件名
24.dir ./
工作目录，数据库在此目录下写入dbfilename参数指定的文件中
25.slaveof <masterip> <masterport>
主从复制，使用slaveof使一个Redis实例复制另一个Redis服务器。
a.Redis复制时异步的，可以配置主服务器不接受写请求当被发现没有连接任何从服务器时。
b.从服务器可以执行部分在同步
c.复制时自动的，无需认为干预
26.masterauth <master-password>
如果主服务器时密码保护的。
27.slave-serve-stale-data yes
当从服务器丢失主服务器连接，或正在复制过程中时。yes则响应客户端请求，可能缺少实时数据，可能为空。no则返回"SYNC with master in progress"
28.slave-read-only yes
Redis2.6开始默认服务器时只读的。
29.repl-diskless-sync no
默认使用disk-based模式，在高速网络、低速硬盘的情况下使用repl-diskless-sync yes
30.repl-diskless-sync-delay 5
复制间隔,当复制开始后，新的从服务器只能等待下次再赋值，因此可以设置一个间隔时间以等待更多的从服务器连接一次复制。0为不等待，尽快复制。
31.repl-ping-slave-period 10
从服务器预定间隔发送Ping请求。
32.repl-timeout 60
a.从从服务器视角，同步期间的批量传输
b.主服务器超时()
c.从服务器超时()
33.repl-disabled-tcp-nodelay no
yes的时候，会使用更小的TCP信息包和更小的带宽发送数据到从服务器。显示到从服务器可以增加一个延迟，默认40微秒
no的时候，会减少延迟，加大带宽。
34.repl-backlog-size 1mb
设置复制待办列表的大小。当一个从服务器断开连接后再次连接，不必完整重同步，部分重同步即可
当有从服务器时，backlog才会被分配
35.repl-backlog-ttl 3600
一段时候内主服务器没有连接任何从服务器，将会释放backlog。
注意，从服务器从不释放backlog，因为从服务器可能会被提升为主服务器。
0表示不清空backlog
36.slave-priority 100
用于Redis哨兵按顺序选择提升为主服务器，越小的先被提升为主服务器
0则不会被选择提升为主服务器
37.min-slaves-to-write 3 min-slaves-max-lag 10
当主服务器连接的从服务器少于N时，从服务器数据落后主服务器M秒时，可能设定为不接受写操作。
0为禁用
38.slave-announce-ip 5.5.5.5 slave-announce-port 1234

39.requiredpass pwd
要求客户端在执行其他命令之前发送验证密码。
40.rename-command CONFIG b840fc02d524045429941cc15f59e41cb7be6c52
可以给有危险的命令改变名称。比如吧CONFIG改为之后的参数。
也可以彻底禁用一个命令使用rename-command CONFIG ""，重命名为空字符串
41.maxclients 10000
同时间客户端最大连接数
42.maxmemory <bytes>
设置内存使用限制在一个指定的bytes数量
43.maxmemory-policy noevication
到达最大内存时执行的策略
lru least recently used         最长时间未使用
lfu least frequently used       一段时间内最少使用
a.volatile-lru  有过期时间的key使用lru
b.allkeys-lru   所有key使用lru
c.volatile-lfu  有过期时间的key使用lfu
d.allkeys-lfu   所有key使用lfu
e.volatile-random   有过期时间的key随机
f.allkeys-random    所有key随机
g.volatile-ttl  最接近过期时间都可以删除
h.noeviction    不删除,返回error
注意，没有合适的key释放时所有策略都会返回error
44.maxmemory-samples 5
每几个key做一次比较,选择合适的key释放
45.lazyfree-lazy-eviction no lazyfree-lazy-expire no lazyfree-lazy-server-del no slave-lazy-flush no
删除大量数据时可能造成服务器暂停，其他另一个进程在后台删除数据
45.appendonly no
RDB可能会丢失短时间内的数据，AOF则会保留输入的命令用于重建数据
RDB可以是AOF共同使用，兼容
46.appendfilename "appendonly.aof"
append only文件名
47.appendfsync everysec
系统将数据实际写入硬盘而不是等待输出缓冲区有更多数据。
a.no 随系统判断何时写入硬盘 最快
b.always 每一次缓冲区有新数据就写入硬盘 最安全
c.everysec 每一秒
48.no-appendfsync-on-rewrite no

49.auto-aof-rewrite-percentage 100 auto-aof-rewrite-min-size 64m
AOF文件增加多少后进行重写，也可以设定最小值
50.aof-load-truncated yes
当Redis启动时，加载AOF文件可能会发现数据在尾部被截断
yes时，Redis使用AOF启动并返回一个日志提示用户这个信息
no时，则报错并拒绝启动，用户需要使用redis-check-aof工具修复AOF文件
51.aof-use-rdb-preamble

52.lua-time-limit 5000
Lua脚本最大执行时间（单位微秒)
53.cluster-enabled yes
普通的redis服务器不能作为集群中的节点，需要设置cluster-enabled yes
54.cluster-config-file node-6379.conf
集群配置文件
55.cluster-node-timeout 15000
集群超时时间
56.cluster-slave-validity-factor 10



----------------------------------- Redis配置暂时没看完 日后补齐 ------------------
一、Redis命令
1.APPEND key valu 
追加一个值到key上
2.AUTH password
验证服务器密码命令(对应redis.conf中的requirepass password指令)
3.BGREWRITEAOF
异步重写AOF文件命令
4.BGSAVE
异步保存数据集到磁盘上
5.BITCOUNT key [start end]
统计字符串指定起始位置到结束位置的字节数
6.BITFIELD key [GET type offset] [SET type offset value] [INCRBY type offset increment] [OVERFLOW WRAP|SAT|FAIL]
会把Redis字符串当做位数组，并能对变长位宽和任意未字节对齐的指定整型位域进行寻址。
7.BITOP operation destkey [key...]
对一个或多个保存二进制位的字符串key进行位元操作，并将结果保存到desckey上
BITOP中operation支持AND、OR、NOT、XOR四种中的任意一种
8.BITPOS key bit [start] [end]
返回字符串里第一个被设置为1或0的bit位
9.BLPOP key [key...] timeout
删除，并获取该列表中的第一个元素，或阻塞，直到有一个可用
10.BRPOP key [key...] timeout
删除，并获取该列表中的最后一个元素，或阻塞，直到有一个可用
11.BRPOPLPUSH source destination timeout
弹出一个列表的最后一个值并把值推到另一个列表的头
12.CLIENT KILL [ip:port] [ID client-id] [TYPE normal|slave|pubsub] [ADDR ip:port] [SKIPME yes/no]
管理客户端连接
CLIENT KILL ADDR ip:port 按照客户端地址和端口号关闭
CLIENT KILL ID client-id 可以通过唯一ID字段关闭客户端,唯一ID可以通过Redis2.8.12之后的CLIENT LIST命令查询
CLIENT KILL TYPE type 这里的type可以是normal、slave、pubsub 将关闭所有特殊类的客户端，请注意被认为是属于正常类型的客户端会被MONITOR命令监视到。
CLIENT SKIPME yes/no 默认值是yes，执行此命令的客户端不会被关闭，设置为no时则也会被关闭
13.CLIENT LIST
获得客户端连接列表
14.CLIENT GETNAME
获得当前连接名称
15.CLIENT PAUSE timeout
暂停处理客户端命令
16.CLIENT SETNAME connection-name
设置当前连接名称
-------------------- 集群相关暂时无法深入理解 日后再说 ----------------------
17.CLUSTER ADDSLOTS slot [slot...]
用于修改某个节点上的集群配置。具体而言把一组hash slots分配给接受命令的节点。如果命令执行成功，节点将制定的hash slots映射到自身，节点将获得指定的hash slots，同时开始想集群广播新的配置。
18.CLUSTER COUNT-FAILURE-REPORTS node-id
返回一个指定节点的故障报告，故障报告是Redis Cluster用来使节点的PFAIL(以为不可达)晋升到FAIL状态的方式，这以为这集群中大多数的主节点在一个时间窗口内同意节点不可达。
19.CLUSTER COUNTKEYSINSLOT slot
返回节点负责的指定hash slot的key的数量。该命令只查询连接节点的数据集，所以如果连接节点指派到该hash slot会返回的0.
20.CLUSTER DELSOTS slot [slot...]



----------------------- 暂时跳过集群相关 --------------
35.COMMAND
获取Redis命令的集合
36.COMMAND COUNT
Redis命令的总数
37.COMMAND GETKEYS 
获取Redis命令执行后设置的key
38.COMMAND INFO command-name [command-name...]
获取指定Redis命令的详情
39.CONFIG GET parameter
获取配置参数的值
40.CONFIG REWRITE
重写内存中的配置文件
41.CONFIG SET parameter value
设置配置信息
42.CONFIG RESETSTAT
重置INFO命令中的某些统计数据，包括
Keyspace hits 命中次数
keyspace misses 不命中次数
Number of commands processed 执行命令次数
Number of connections received 连接服务器次数
Number of expired keys 过期key的数量
Number of rejected connection 拒绝的连接数量
Latest fork(2) time 最后执行fork(2)的时间
The aof_delayed_fsync_counter aof_delayed_fsync计数器的值
43.DBSIZE
获取当前数据库里key的数量
44.DEBUG OBJECT key
获取一个key的debug信息
45.DEBUG SEGFAULT
是服务器崩溃命令,模拟开发中遇到的错误
46.DECR key
整数减一
47.DECRBY key decrement
整数减去指定的整数
48.DEL key [key...]
删除指定的key(一个或多个)
49.DISCARD
丢弃所有MULTI之后发布的命令(刷新一个事务中所有在排队等待的指令，并且将连接状态恢复到正常)
50.DUMP key
序列化key的值,可以使用restore命令将此值反序列化
SET mykey 10
DUMP mykey 
DEL mykey
RESTORE mykey 0 "序列化返回的值"
序列化生成的值有以下几个特点：
它带有 64 位的校验和，用于检测错误，RESTORE 在进行反序列化之前会先检查校验和。
值的编码格式和 RDB 文件保持一致。
RDB 版本会被编码在序列化值当中，如果因为 Redis 的版本不同造成 RDB 格式不兼容，那么 Redis 会拒绝对这个值进行反序列化操作。
51.ECHO message
回显输入的字符串
52.EVAL script numkeys key [key...] arg [arg...]
在服务器端执行LUA脚本
53.EVALSHA sha1 numkeys key [key...] arg [arg...]
在服务器端执行LUA脚本
----------- LUA脚本和Redis的关系暂时未深入 ----------
----------- LUA脚本语言暂时还没看 ------------
54.EXEC
执行所有MULTI之后发布的命令
55.EXISTS key [key...]
查询一个或多个key是否存在
56.EXPIRE key seconds
设置一个key的过期时间（单位秒)
57.EXPIREAT key timestamp
设置一个UNIX时间戳的过期时间(LINUX获取时间戳命令 date +%s)
58.FLUSHALL
清空所有数据库数据
59.FLUSHDB
清空当前数据库数据
------------- GEO算法暂时不了解 -----------------
60.GEOADD key longitude latitude member [longitude latitude member ...]
添加一个或多个地理空间位置到sorted set
61.GEOHASH key member [member ...]
返回一个标准的地理空间GeoHash字符串
62.GEOPOS key member [member ...]
返回地理空间的经纬度
63.GEODIST key member1 member2 [unit]
返回两个地理空间之间的距离
64.GEORADIUS key longitude latitude radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count]
查询指定半径内所有的地理空间元素的集合
65.GEORADIUSBYMEMBER key member radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count]
查询一个成员指定半径内匹配到的地理空间元素的集合
66.GET key
返回key的value
67.GETBIT key offset
返回key对应字符串再offset处的bit值 offset超出范围或key不存在时返回0
68.GETSET key value
设置一个key的值，并获取设置前的值
69.HDEL key field [field ...]
删除一个或多个Hash的field
70.HEXISTS key field
判断hash中是否存在field
71.HGET key field
获取hash中field的值
72.HGETALL key
获取hash中全部的域和值
73.HINCRBY key field increment
将hash中指定域的值增加给定的数字
74.HINCRBYFLOAT key field increment
将hash中指定域的值增加给定的浮点数
75.HKEYS key
获取hash中的所有字段
76.HLEN key
获取hash里所有字段的数量
77.HMGET key field [field ...]
批量获取hash中指定域的值
78.HMSET key field value [field value ...]
批量设置hash字段值
79.HSET key field value
设置hash字段的值
80.HSETNX key field value
当且仅当hash中不存在指定域时，设置值
81.HSTRLEN key field
获取hash里指定field的长度
82.HVALS key
获取hash中所有值
83.INCR key
执行加一操作
84.INCRBY key increment
指定key的值增加一个整数
85.INCRBYFLOAT key increment
指定key的值增加一个浮点数
86.INFO [section]
获取服务器的详细信息
87.KEYS pattern
查找是所有匹配给定正则表达式的key
88.LASTSAVE
获得最后一次同步磁盘的时间
89.LINDEX key index
根据列表的索引获取元素值
90.LINSERT key BEFORE|AFTER pivot value
在列表中的另一个元素之前或之后插入一个元素
91.LLEN key
获取队列的长度
92.LPOP key
从队列的左边出队一个元素
93.LPUSH key value [value ...]
从队列的左边入队一个或多个元素
94.LPUSHX key value
当队列存在时，从队左边入队一个元素
95.LRANGE key start stop
从列表中获取指定范围的元素
96.LREM key count value
从列表中删除元素
97.LSET key index value
设置队列里面指定下标的元素的值
98.LTRIM key start stop
保留列表指定范围的值，其余删除
99.MGET key [key ...]
获取所有给定的key的值
100.MIGRATE host port key destination-db timeout [COPY] [REPLACE]
将 key 原子性地从当前实例传送到目标实例的指定数据库上，一旦传送成功， key 保证会出现在目标实例上，而当前实例上的 key 会被删除
101.MONITOR
实时监控服务器,返回服务器处理的每一条命令。帮助了解在数据库上发生了什么，可以通过redis-cli和telnet命令使用
102.move key db
移动一个key到另一个数据库
103.MSET key value [key value ...]
设置多个key的值
104.MSETNX key value [key value ...]
当且仅当不存在时设置多个key的值(所有key都不能存在 有存在就全部保存失败)
105.MULTI 
标记一个事务块开始（DISCARD回滚 EXEC提交)
106.OBJECT subcommand [arguments [arugments ...]]
检查内部的再分配对象
107.PERSIST key
移除key的过期时间
108.PERPIRE key milliseconds
设置key的有效时间(expire以秒为单位 pexpire以豪秒为单位)
109.PEXPIREAT key milliseconds-timestamp
设置key的到期unix时间戳以毫秒为单位
110.PFADD key element [element ...]
将指定元素添加到HyperLogLog
111.PFCOUNT key [key ...]
当参数为一个key时,返回存储在HyperLogLog结构体的该变量的近似基数，如果该变量不存在,则返回0.
当参数为多个key时，返回这些HyperLogLog并集的近似基数，这个值是将所给定的所有key的HyperLoglog结构合并到一个临时的HyperLogLog结构中计算而得到的.
HyperLogLog可以使用固定且很少的内存（每个HyperLogLog结构需要12K字节再加上key本身的几个字节）来存储集合的唯一元素.
返回的可见集合基数并不是精确值， 而是一个带有 0.81% 标准错误（standard error）的近似值.
112.PFMERGE destkey sourcekey [sourcekey ...]
合并N个不同的HyperLogLogs到一个
113.PING
ping服务器，查询是否连接正常
114.PSETEX key milliseconds value
相当于两条命令 SETEX key value 和 PEXPIRE key milliseconds
设置key的值，并设置一个过期时间
115.PSUBSCRIBE pattern [pattern ...]
订阅给定的模式
116.PUBSUB subcommand [argument [argument ...]]
检查发布/订阅系统状态
117.PTTL key
获取key的有效毫秒数
118.PUBLISH channel message
发布一条消息到频道
119.PUNSUBSCIBE [pattern [pattern ...]]
停止发布到匹配给定模式的渠道
120.QUIT
关闭连接 退出
121.RANDOMKEY
返回一个随机的key
123.READONLY
开启与 Redis Cluster 从节点连接的读请求
122.READWRITE
禁用与 Redis Cluster 从节点连接的读请求
123.RENAME key newkey
将一个key重命名
124.RENAMENX key newkey
重命名一个key，新的key必须是不存在的key
125.RESOTRE key ttl serialized-value [REPLACE]
使用DUMP指令序列化的值创建一个key
126.ROLE
返回在复制上下文中的实例的规则
127.RPOP key
从一个列表右边出队
128.RPOPLPUSH source destination
从一个列表右边出队，再将出队元素从另一个列表左边入队
129.RPUSH key value [value ...]
从队列的右边入队一个元素
130.RPUSHX key value
当且仅当队列存在时，从队列的右边入队一个元素
131.SADD key member [member ...]
添加一个或多个元素到集合中
132.SAVE
同步到磁盘上
133.SCARD key
获取集合里面的元素数量
134.SCRIPT EXISRS script [script ...]
检查脚本缓存中是否存在指定脚本
135.SCRIPT FLUSH
删除服务器缓存中所有LUA脚本
136.SCRIPT KILL
杀死当前正在运行的LUA脚本
137.SCRIPT LOAD script
从服务器缓存中装在一个LUA脚本
138.SDIFF key [key ...]
获取第一个集合和其他集合中不同的元素
139.SDIFFSTORE destination key [key ...]
获取第一个集合和其他集合中不同的元素并存储在一个新的结果集中
140.SELECT index
选择新的数据库
141.SET key value [EX seconds] [PX milliseconds] [NX|XX]
设置key的值
142.SETBIT key offset value
设置key的值在offset偏移上的bit值
143.SETEX key seconds value
设置key的值并设置过期时间 相当于两条命令 SET key value 和 EXPIRE key seconds
144.SETNX key value
当且仅当key不存在时，设置key的值
145.SETRANGE key offset value
从offset偏移开始设置key的值
146.SHUTDOWN [NOSAVE] [SAVE]
关闭服务
147.SINTER key [key ...]
获取多个集合的交集
148.SINTERSTORE destination key [key ...]
获取多个集合的交集并保存到一个指定的结果集中
149.SISMEMBER key member 
确定一个给定的值是否是一个集合的成员
150.SLAVEOF host port
指定当前服务器的主服务器
151.SLOWLOG subcommand [argument]
管理再分配的慢查询日志(暂时不懂)
152.SMEMBERS key
获取集合里的所有元素
153.SMOVE source destination member
移动集合里的一个元素到另一个集合
154.SORT key [BY pattern] [LIMIT offset count] [GET pattern [GET pattern ...]] [ASC|DESC] [ALPHA] destination
对队列、集合和有序集合的元素排序 ALPHA字段顺序
155.SPOP key [count]
删除并获取集合里的元素
156.SRANDMEMBER key [count]
从集合中随机获取元素
157.SREM key member [member]
从集合中删除元素
158.STRLEN key
获取key对应value的长度
159.SUBSCRIPT channel [channel ...]
监听频道发布的消息
160.SUNION key [key ...]
集合的并集
161.SUNIONSTORE destination key [key ...] 
集合的并集，并存储到一个新的结果集中
162.SYNC
用于复制
163.TIME
返回当前服务器时间
164.TTL key
获取key的有效时间单位秒
165.TYPE key
获取key的类型
166.UNSUBSCRIBE [channel [channel ...]]
停止监听
167.UNWATCH
取消事务命令
168.WAIT numslaves timeout
此命令阻塞当前客户端，直到所有以前的写命令都成功的传输和指定的slaves确认。如果超时，指定以毫秒为单位，即使指定的slaves还没有到达，命令任然返回。
169.WATCH key [key ...]
锁定key知道执行了MULTI/EXEC
170.ZADD key [NX|XX] [CH] [INCR] score member [source member ...]
添加到有序set的一个或多个成员，或更新分数
171.ZCARD key
获取一个排序的集合中的成员数量
172.ZCOUNT key min max
返回分数范围内的成员数量
173.ZINCRBY key increment member
增加一个成员在排序设置的分数
174.ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
多个有序集合的交集 存储到一个新的结果集中
175.ZLEXCOUNT key min max
用于计算有序集合中指定成员之间的成员数量。
176.ZRANGE key start stop [WITHSCORES]
根据指定的下标返回有序集合的成员列表
177.ZRANGEBYLEX key min max [LIMIT offset count]
返回指定成员区间内的成员 字典正序
178.ZREVRANGEBYLEX key max min [LIMIT offset count]
返回指定成员区间内的成员 字典倒序
179.ZRANGEBYSCORE key min max [WITHSCORE] [LIMIT offset count]
返回指定分数区间内的成员，分数由低到高
180.ZRANK key member
确定成员的下标
181.ZREM key member [member ...]
有序集合中删除成员
182.ZREMRANGEBYLEX key min max
成员间的元素删除
183.ZREMRANGEBYRANK key start stop
给定下标之间的成员删除
184.ZREMRANGEBYSCORE key min max
删除分数之间的元素
185.ZREVRANGE key start stop [WITHSCORE]
分数从高到低
186.ZREVRANGEBYSCORE key max min [WITHSCORE] [LIMIT offset count]
分数区间从高到低
187.ZREVRANK key member
从高到低排序时的下标
188.ZSCORE key member
元素的分数
189.ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight [weight...]] [AGGREGATE SUM|MIN|MAX]
并集
190.SCAN cursor [MATCH pattern] [COUNT count]
增量迭代key 
191.SSCAN key cursor [MATCH pattern] [COUNT count]
迭代集合中的元素
192.HSCAN key cursor [MATCH pattern] [COUNT count]
迭代hash里面的元素
193.ZSCAN key cusor [MATCH pattern] [COUNT count]
迭代有序集合中的元素