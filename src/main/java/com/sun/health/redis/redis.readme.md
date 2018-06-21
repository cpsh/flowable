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


------------------------ 请求/响应协议和RTT
Redis是一种基于客户端-服务端模型以及请求/响应协议和TCP服务
一个请求通常会遵循：
客户端向服务端发送一个查询请求，并监听socket返回，通常是以阻塞模式，等待服务器端响应。
服务端处理命令，并将结果返回给客户端
客户端和服务器通过网络进行连接，可能很快也可能很慢。这个时间被称为RTT(Round Trip Time-往返时间)，当客户端需要在一次批处理中执行多次请求时很容易看到这是如何影响性能的。
有方法改善这种情况
1.Redis管道(Pipelining)
一次请求/响应服务器能实现处理新的请求即使旧的请求还未被响应。这样就可以将多个命令发送到服务器，而不用等待回复，最后在一个步骤中读取答复。
(printf "PING\r\nPING\r\nPING\r\n"; sleep 1;) | nc localhost 6379
没有为每个命令都花费RTT开销，只用了一个命令的RTT开销时间。
使用管道发送命令时，服务器将被迫回复一个队列答复，占用很多内存。所以，如果需要发送大量命令，最好按照合理数量分批次处理，例如10K的命令，读回复，然后再发送。这样速度几乎是相同的，但是在回复10K命令队列需要大量的内存用来组织返回数据内容。

Redis发布与订阅(PUBLISH/SUBSCRIBE)
客户端1 
SUBSCRIBE channel1
客户端2
PUBLISH channel1 Hello
UNSUBSCRIBE取消订阅所有频道
PSUBSCRIBE pattern 订阅给定模式的频道
PUNSUBSCRIBE pattern 取消订阅给定模式的频道

Redis过期时间设置
EXPIRE key seconds 
设置key的过期时间，超过时间后，将会删除key。设置过过期时间，只有在执行改变key的值的操作才会清除。
PERSIST命令可以清除过期时间，变为永久key
RENAME则会将过期时间转移到新key上
EXPIRE命令会刷新过期时间

过期精度
2.4版本及以前，过期时间可能有0-1秒的误差
2.6版本及以后，误差缩小到0-1毫秒
过期时间是以计算机时间为准，（UNIX时间戳），因此需要明确时间

Redis淘汰Key的方式
1.主动 当客户端尝试访问key时，会发现并主动的过期
2.被动    而有些过期key永远不会访问到，所以需要定时随机测试设置key的过期时间，删除过期的key
    Redis每十秒
    a.测试随机的20个keys进行相关过期测试
    b.删除所有已经过期的keys
    c.如果多于25%的keys过期，重复步骤a

AOF文件处理过期
当一个key过期，会DEL key。当存在主从时，不会独立过期keys，而是等到master执行DEL命令。过期的key仍然会在数据库中存在，所以当从服务器被当选为主服务器时，会先执行过期操作再成为主服务器

Redis作为LRU算法缓存使用
当Redis被当做缓存使用，新增数据时，自动回收旧数据是很方便的。
LRU是Redis唯一支持的回收方法。maxmemory指令用于将可用内存限制成一个固定大小，Redis使用LRU算法只是近似LRU。
Maxmemory配置指令
maxmemory配置指令用于配置Redis存储数据时指定限制的内存大小。可以通过redis.conf配置，--maxmemory 100m,或者CONFIG SET设置
maxmemory为0代表没有内存限制，对于64位系统这是默认值，对于32位系统默认内存限制为3GB。
当到达指定的限制内存大小时，需要选择不同行为，这就是策略。Redis可以仅仅对命令返回错误，这将使的内存被使用的更多，或者回收一些旧的数据来使得添加数据时可以避免内存限制。
回收策略
当maxmemory限制达到时，会使用maxmemory-policy指定的策略
可用策略
1.noeviction(不 eviction回收) 返回错误
2.allkey-lru 尝试回收最少使用的键，所有键都参与
3.volatile-lru 尝试回收最少使用的键，仅限于有过期时间的键
4.allkeys-random 回收随机的键，所有键都参与
5.volatile-random 回收随机的键，仅限于有过期时间的键
6.volatile-ttl 回收存在过期时间较短的键
回收进程如何工作
1.客户端执行新的命令添加新的数据
2.Redis检查内存使用情况，如果大于maxmemory限制，则根据设定好的策略进行回收
3.新命令执行
4.不断添加数据超过内存限制，则不断回收内存保证不超过内存限制。
近似LRU算法
Redis的LRU算法并非完整实现，这意味Redis并不选择最佳候选来回收。而是尝试运行一个近似LRU算法，通过对少量key进行取样，然后回收其中最早访问的key.
从Redis3.0算法被改进为回收键的候选池子，改善了算法的性能，更加近似LRU算法的行为。
RedisLRU算法有个很重要的点是可以调整每次回收时检查的采样数量，以实现算法精度。
maxmemory-samples 5

Redis事务
MULTI、EXEC、DISCARD、WATCH、UNWATCH是Redis事务相关的命令。事务可以一次执行多个命令，并有两个重要保证
1.事务是一个单独的隔离操作，事务中所有命令都会序列化，按顺序执行。事务在执行过程中，不会被其他客户端发送的命令请求打断。
2.事务是一个原子操作，事务中的命令要么全部被执行，要么全部都不执行
EXEC命令负责触发并执行事务中的所有命令
如果客户端执行MULTI后没有执行EXEC，则事务范围内所有命令都不会执行
另一个方面，如果成功执行EXEC,则事务范围内所有命令都会被执行
使用AOF方式做持久化时，Redis会使用单个write(2)命令将事务写入磁盘中
用法
MULTI命令用于开启一个事务，总是返回OK。MULTI执行之后，客户端可以继续向服务器发送任意多条命令，这些命令不会立即被执行，而是被放置于一个队列中，当EXEC被执行时，所有队列中的命令才会被执行
另一方面，通过调用DISCARD,客户端可以清空事务队列，并放弃执行事务。
EXEC命令的回复是数组，每个元素都是执行事务中的命令产生的回复。先后顺序一致。
处于事务状态时，所有传入命令都会返回一个内容为QUEUED的状态回复。
事务中的错误
使用事务可能遇到两种情况：
1.在执行EXEC之前，入队的命令可能会出错。比如，语法错误或其他更严重的错误如内存不足。以前的做法是检查命令入队所得的返回值，如果返回QUEUED，那么入队成功，否则入队失败。如果有命令在入队时失败，大部分客户端都会停止并取消事务。而从2.6.5版本开始，服务器会命令入队失败的情况进行记录，并在执行EXEC时拒绝执行并自动放弃此事务。之前，Redis只执行入队成功的命令而忽略失败的命令。
2.在执行EXEC之后。没有特别处理，即使事务中某个/某些命令在执行时产生错误，事务中的其他命令仍然会继续执行
Redis为何不支持回滚
优点
1.Redis命令只因为错误的语法失败，或是命令用在了错误类型的key上，也就是说，失败的命令由编程错误造成的，而这些错误应该在开发的过程中被发现，而不应该出现在生产中。
2.不需要对回滚进行支持,Redis内部可以保持简单且快速


大量数据插入
cat data.txt | redis-cli --pipe
从文件中批量导入
编辑一个文件写入命令
redis-cli只支持DOS格式的换行符\r\n，需要转码(unix2dos)

分区
主要目的：
1.分区可以让Redis管理更大的内存，Redis将可以使用所有机器的内存。
2.分区使Redis的计算能力通过简单地增加计算机得到成倍提升
基本概念
有很多分区标准，有很多存储方案，有很多映射方案
一种最简单的方式范围分区，就是将不同范围的对象映射到不同Redis实例。
另一种可选的方案是散列分区，要求更低，不需要key必须是object_name:<id>的形式。
1.使用散列函数将键名称转换为数字
2.对转换后的散列值进行取模，产生一个数字决定使用的Redis实例
缺点：
1.涉及多个key的操作通常不会被支持
2.不能使用事务操作多个key
3.数据处理会非常复杂
4.分区时动态扩容会锁容会非常复杂
持久化和缓存
1.如果Redis被当做缓存使用，使用一致性哈希算法实现动态扩容缩容
2.如果作为持久化存储，必须使用固定的key-to-node映射关系，节点的数量一旦确定不能改变。
分区实现
Redis集群
Twemproxy


---------------------- 
客户端使用
redis-cli
实践时再查询

---------------------
复制
使用和配置主从复制非常简单，能使得Redis从服务器（slave）能精确复制Redis主服务器（master）的内容。每当slave和master连接断开，slave会自动重连到master上，并且无论期间发生什么，slave都会尝试让自身成为master的精确副本。
主要依靠
1.当master和slave实例连接正常时，master会发送一系列命令来保持对slave的更新，以便于将自身数据的改变复制给slave
2.当master和slave连接断开时，slave重新连接上master并会尝试进行部分重同步，这意味会尝试只获取在断开连接期间丢失的命令
3.当无法进行部分重同步时，slave会请求进行全量重同步，这会涉及到一个更复杂的过程，例如master需要创建所有数据的快照，发送给slave，再在数据更改时同步命令给slave
Redis默认使用的是异步复制，特点是高延迟和高性能，是绝大多数Redis实例的自然复制模式。但是，slave会异步确认其master周期接收到的数据量。
客户端可以使用WAIT命令来请求同步复制某些特定的数据。但是WAIT命令只能确保在其他Redis实例中有指定数量的已确定的副本。
Redis基本复制功能的基本特性
1.Redis使用异步复制，slave和master之间异步地确认处理的数据量
2.master可以拥有多个slave
3.slave可以接受其他slave的连接 4.0版本后，sub-slave将会从master处收到完全一样的复制流
4.Redis复制在master侧是非阻塞的，意味着master在同步时是可以继续处理查询请求
5.复制在slave侧大部分也是非阻塞的，同步时可以使用旧数据继续处理请求。但是完成同步后，旧数据必须被删除，同时加载新数据，slave在这个短暂的时间窗口中会阻塞连接请求。Redis4.0之后，可以配置删除在另一个线程中进行，加载新数据必须在主线程中，并阻塞请求。
6.复制既可以被用在可伸缩性，也可以仅用于数据安全
7.可以使用复制来避免master将全部数据集写入磁盘造成的开销。
使用复制时，强烈建议master和slave中启用持久化，当不可能启用时，应该配置实例来避免重置后自动重启。
Redis复制工作原理
每个master都有一个ReplicationId,是一个较大的伪随机字符串，标记了一个给定的数据集。每个master持有一个偏移量，master将自己产生的复制流发送给slave时，发送多少字节的数据，自身的偏移量就会增加多少，目的是当有新的操作修改数据集时，可以以此更新slave的状态。即使没有slave连接到master也会自增，所以每一对给定的replicationId和offset都是会标识一个master数据集的精确版本
slave连接到master时，使用PSYNC命令发送记录的旧的master replicationId和offset。通过这种方式master能够金发送slave所需要的增量部分。但是如果master的缓冲区没有足够的命令积压缓冲记录，或者slave引用了不知道的replicationId，则会转而进行一次全量同步
全量同步工作细节
master开启一个后台保存进程，产生一个RDB文件，同时缓冲所有从客户端接收到的新的写入命令。当后台保存完成时，master将数据集文件发送给slave，slave保存到磁盘上再加载到内存中。再然后master会发送所有缓冲的命令给slave
无需磁盘参与的复制
Redis2.8.18第一次支持无硬盘复制，子进程直接发送RDB给slave。
配置
配置基本的Redis复制功能很简单，添加配置信息到slave的conf中即可
slaveof host port
也可以使用SLAVEOF命令
无磁盘复制可以使用repl-diskless-sync配置参数，repl-diskness-sync-delay参数可以延迟启动数据传输，目的可以在第一个slave就绪后等到更多的slave就绪。
只读性质的slave
Redis2.6后，slave支持只读模式且默认开启。redis.conf中的slave-read-only参数控制，可以使用CONFIG SET动态配置
密码验证
masterauth password
从Redis2.8开始，只有当至少N个slave连接到master时，才允许master接受写请求。
1.slave每秒都会ping master，确认已处理的复制流的数量
2.master会记得上一次从每个slave收到ping的时间
3.用户可以配置最少slave数量和最大滞后时间，master才接受写操作
min-slaves-to-write
min-slaves-max-lag
Redis复制如何处理过期
1.slave不会让key过期，而是等待master让key过期，当key过期时，master会发送一个DEL命令

---------------------------------
Redis持久化
Redis提供了不同级别的持久化方式
1.RDB持久化方式能够在指定时间间隔对数据进行快照存储
2.AOF持久化方式记录每次对服务器写的操作，当服务器重启时会重新执行这些命令来恢复原始数据，AOF命令以redis协议追加保存每次写操作到文件末尾。Redis还能对AOF文件进行后台重写，避免体积过大。
3.如果只希望数据在服务器运行时存在，可以不适用持久化技术
4.可以同时开启两种持久化方式，重启时会优先载入AOF文件。
RDB优点
1.RDB是一个非常紧凑的文件，保存了某个时间点的数据集，非常适用于数据集的备份。
2.RDB是一个紧凑的单一文件，很方便传输
3.RDB保存时父进程唯一需要做的就是fork出子进程
4.与AOF相比，在恢复大量数据时，RBD方式会更快一些
RDB缺点
1.希望Redis服务器意外停止的情况下丢失数据量最少，不适宜使用RBD
2.RDB需要经常fork出子进程保存数据集，当数据集较大时，fork过程十分耗时。
AOF优点
1.使用AOF会使Redis更耐久，可以使用不同的fsync策略，无,每秒，每次写操作，默认每秒，性能依旧很好，出现故障最多损失以秒的数据
2.AOF文件是一个只进行追加的日志文件，所以不需要写入seek,即使由于某些原因（磁盘已满，写过程中宕机）未执行完整的写入命令，也可以使用redis-check-aof工具修复。
3.Redis可以在AOF文件过大时，自动在后台对AOF进行重写，重写后的新AOF文件包含了恢复当前数据集所需的最小命令集合。重写过程是绝对安全的。
4.AOF文件有序的保存了对数据库执行的所有写入操作，以Redis协议的格式保存，内容易懂，分析和导出也非常简单。
AOF缺点
1.对于相同的数据集而言，AOF文件的体积通常较大
2.根据所使用fsync策略，AOF的速度可能会慢于RDB.
一般而言，应该同时使用两种持久化功能。
快照
在默认情况下，Redis将数据库快照保存在名字为dump.rdb的二进制文件中。可以对Redis进行设置，让"N秒内数据集至少M此改动”条件满足时，自动保存数据集。也可以通过BGSAVE和SAVE手动保存。
save 60 1000 # 60秒内涉及1000个键的变动
这个持久化方式称为快照snapshotting
RDB工作方式
1.Redis调用fork命令，复制一个子进程
2.子进程将数据集写入一个临时的RDB文件中
3.子进程完成数据集保存，将新RDB文件替换旧RDB文件，再删除旧RDB文件
AOF(Append-only File)
appendonly yes # 启动AOF持久化
可以使用BGREWRITEAOF命令重写AOF文件，包含重建当前数据集最少的命令集。
fsync
1.每次有新命令就fsync一次
2.每秒fsync一次
3.从不fsync，将数据交给操作系统处理
AOF损坏
先备份，再使用redis-check-aof工具
redis-check-aof -fix
AOF重写工作原理
1.Redis执行fork，拥有一个子进程
2.子进程开始新的AOF文件的内容写入临时文件
3.对于新执行的写命令，父进程一边累积到内存缓冲中，一边将改动追加到现有AOF文件的末尾
4.子进程完成重写工作时，给父进程发送一个信号，父进程接受到信号后，将内存缓冲中的所有数据追加到新AOF文件末尾
5.新AOF文件替换旧的

-------------
redis安全
Redis并没有最大优化安全方面，而是尽最大可能优化性能和易用性。
网络安全
bind 127.0.0.1 指定HOST才能访问Redis
认证
在redis.conf中配置
requirepass sjfajfpawjoawgo
AUTH或-a 验证密码
禁用特殊命令
rename-command CONFIG atagugsoehgohgpseg 
rename-command CONFIG "" # 禁用

------------ Redis信号处理
SIGTERM信号处理
会让Redis安全关闭。Redis收到信号时并不立即退出，而是开启一个定时任务，这个任务就类似执行一次SHUTDOWN命令。这个定时关闭任务会在当前执行命令终止后立即执行，通常由0.1秒或更短的延迟。
万一被LUA脚本阻塞，会SCRIPT KILL终止LUA脚本，再执行定时任务。
Shutdown过程通常包含步骤
1.如果存在正在执行RDB文件保存或AOF重写的子进程，子进程终止。
2.如果AOF开启，会通过系统调用fsync将AOF内存缓冲区的命令强制输出到磁盘中
3.如果Redis配置RDB，那么此时就会进行同步保存。由于保存时同步的，就不需要额外的内存。
4，如果Server是守护进程，PID文件会被移除
5.Unix域的Socket可用，也会被移除
6.Server退出，结果为0
SIGSEGV,SIGBUS,SIGFPF,SIGILL信号
Redis会崩溃并执行以下步骤
1包括调用栈信息，寄存器信息，以及clients信息会以bug报告的形式写入日志文件。
2自从Redis2.8（当前为开发版本）之后，Redis会在系统崩溃时进行一个快速的内存检测以保证系统的可靠性。
3如果Server是守护进程，PID文件会被移除。
4最后server会取消自己对当前所接收信号的信号处理器的注册，并重新把这个信号发给自己，这是为了保证一些默认的操作被执行，比如把Redis的核心Dump到文件系统。
子进程被终止发生什么
1.AOF重写的子进程终止，Redis会当成一次错误并丢弃这个AOF文件
2.RDB保存的子进程终止，当成一次严重错误

---------------------
Redis如何处理客户端连接
客户端的连接建立
客户端连接最大值
maxclients 10000
输出缓冲限制
Redis需要为每个客户端处理可变长度的输出，因为简单命令也可能产生巨大的数据量
当客户端处理新消息的速度比服务端的速度还慢，特别是Pub/Sub客户端
这两个原因将导致客户端输出缓冲增长及内存消耗增多。
Redis使用两种类型的限制:
1.硬限制是个固定的限制，当大小达到限制时Redis会尽快关闭客户端连接
2.软限制依赖于时间
不同类型客户端有不同默认限制
1.普通客户端默认为0限制，意味着没有限制，大部分普通客户端使用阻塞实现发送单个命令，并且在发送下一个命令前等待答复以完全读取，因此去关闭普通客户端连接没有必要
2.Pub/Sub客户端有默认32兆字节的硬限制和每60秒8兆字节的软限制
3.从机有默认256兆的硬限制和每60秒64兆的软限制
客户端超时
默认不会在客户端空闲很久后自动关闭连接， 可以使用timeout配置
CLIENT LIST


---------------------
Redis Sentinel(哨兵)
Redis中的Sentinel系统用于管理多个Redis服务器，执行以下三个任务
1.监控(Monitoring),Sentinel会不断检查主服务器和从服务器的运行状态
2.提醒(Notification),当被监控的某个Redis服务器出现问题时，Sentinel可以通过API想管理员或其他应用程序发出提醒
3.自动故障转移(Automatic failover),当主服务器不能正常工作时，Sentinel会开始一次自动故障转移操作。会将主服务器的其中一个从服务器提升为新的主服务器，并让失效主服务器的其他从服务器成为新主服务器的从服务器，当客户端尝试连接失效的主服务器时，会想客户端发送新主服务器的地址。
Redis Sentinel是一个分布式系统，可以在一个架构中运行多个Sentinel进程，使用流言协议(gossip protocols)来接受关于主服务器是否下线的信息，并使用投票协议(agreement protocols)来决定是否执行自动故障转移
虽然Sentinel有单独的可执行文件redis-sentinel，但实际上是一个运行在特殊模式下的Redis服务器。可以在启动一个普通Redis服务器时通过--sentinel选项来启动Redis Sentinel
获取Sentinel
2.8之后默认就有
启动Sentinel
redis-sentinel sentinel.conf
redis-server sentinel.conf --sentinel
启动Sentinel必须制定相应的配置文件，系统会使用配置文件保存Sentinel状态，并在重启时还原
配置Sentinel
运行Sentinel最少配置
sentinel monitor mymaster 127.0.0.1 6379 2
sentinel down-after-milliseconds mymaster 60000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1

sentinel monitor resque 192.168.1.3 6380 4
sentinel down-after-milliseconds 10000
sentinel failover-timeout resque 180000
sentinel parallel-syncs resque 5
第一行配置指示Sentinel监视名为mymaster的主服务器，IP地址为127.0.0.1，端口为6379，而将这个主服务器判断为失效至少需要2个Sentinel同意（只要同意的Sentinel的数量不达标，自动故障迁移就不会执行)
需要注意，无论设置需要多少Sentinel同意才能判断服务器失效，Sentinel都需要获得多数Sentinel的支持，才能发起一次自动故障转移，并预留一个给定的配置纪元(就是一个新主服务器配置的版本号)
sentinel monitor <主服务器名> <HOST IP地址> <PORT> <失效需要确认的Sentinel数>
其他选项否基本格式
sentinel <选项名称> <主服务器名> <选项的值>
1.down-after-milliseconds 指定Sentinel认为服务器已经断线所需的毫秒数
如果服务器在给定毫秒数之内，没有返回Sentinel发送的PING命令回复，或者返回一个错误，那么就将此服务器标记为主观下线。
不过只有一个Sentinel将服务器标记为主观下线并不一定会引起服务器的自动故障转义，只有在足够数量的Sentinel都将服务器标记主观下线，服务器才会被标记为客观下线，此时自动故障转移才能执行。
2.parallel-syncs 指定在执行故障转移时，最多可以有多少个从服务器同时对新的主服务器进行同步，这个数字越少完成故障转移所需时间就越长
主观下线和客观下线
1.主观下线（Subjectively Down，简称SDOWN）指的是单个Sentinel实例对服务器做出下线判断
2.客观下线（Objectively Down,简称ODONW）指的是多个Sentinel实例对同一服务器做出SDOWN判断，并且通过Sentinel is-master-down-by-addr命令相互交流之后，得出服务器下线判断。
如果服务器在down-after-milliseconds毫秒内没有响应或返回，则标记为主观下线。
服务器对PING命令的有效回复可以是
1.+PONG
2.-LOADING # 错误
3.-MASTERDOWN # 错误
服务器返回除以上回复，或者超过则认为是主观下线。
从主观下线到客观下线，Redis没有使用严格的法定人数算法，而是使用流言协议。接收到足够多的下线报告，就标记为主动
客观下线只适用于主服务器，其他的无需协商。
每个Sentinel都需要定时执行的任务
1.每个Sentinel每秒一次向所知的主服务器、从服务器和其他Sentinel实例发送一个PING命令
2.如果一个实例距离最后一次有效回复PING命令的时间超过down-after-milliseconds指定的值，那么就被视为主观下线，PING命令有效回复可以使PONG LOADING MASTERDOWN
3.如果一个主服务器被标记为主观下线，并且有足够多的Sentinel在指定时间内同意，则此服务器被标记为客观下线
4.在一般情况下，每个Sentinel会以10秒一次的频率向已知的所有主服务器、从服务器发送INFO命令。当一个主服务器被标记为主观下线，则向下线主服务器的所有从服务器发送INFO的频率提升到1秒一次
5.当没有足够数量的Sentinel同意主服务器已经下线，主服务器的客观下线状态就会被移除。当主服务器重新发送PING并返回有效回复时，主观下线标记移除。
自动发现Sentinel和从服务器
Sentinel可以跟其他Sentinel连接，相互检查对方的可用性，并进行信息交换。无需为Sentinel设置其他Sentinel地址，因为Sentinel可以通过发布与订阅功能来自动发现正在监视相同主服务器的其他Sentinel，这一功能是通过向sentinel:hello发送信息来实现的。
与此类似，也不必手动列出主服务器属下的所有从服务器，因为Sentinel可以通过询问主服务器来获得所有从服务器的信息。
1.每个Sentinel会以2秒一次，通过发布与订阅功能，向监视的所有服务器的sentinel:hello频道发送一条消息，信息中包含了Sentinel的IP地址/端口号/运行ID
2.每个Sentinel都订阅了被他监视的所有主服务器和从服务器的sentinel:hello频道，当发现新的Sentinel，则添加到一个保存了所有监视同一服务器的Sentinel的列表中。
3.Sentinel发送的信息还包括完整的主服务器当前配置，如果一个Sentinel的配置较旧则更新。
4.添加Sentinel到列表会替换
Sentinel API
Sentinel接受Redis协议格式的命令请求
1.发送命令到Sentinel
2.发布与订阅
Sentinel命令
1.PING
2.SENTINEL masters 列出所有监视的主服务器 及当前状态
3.SENTINEL slaves 列出给定主服务的所有从服务器 及当前状态
4.SENTINEL get-master-addr-by-name 返回给定名字的主服务器的IP地址和端口号 故障转移返回新的IP和端口
5.SENTINEL reset 重置所有名字匹配的主服务器
6.SENTINEL failover 主服务失效时，不询问其他Sentinel意见，强制开始故障转移
发布与订阅消息
客户端可以将Sentinel看作是一个只提供订阅功能的Redis服务器，不可以使用PUBLISH命令向服务器发送信息，但可以用SUBSCRIBE或PSUBSCRIBE命令，通过订阅给定的频道获取响应的事件提醒
订阅返回消息格式 <instance-type> <name> <ip> <port> @ <master-name> <master-ip> <master-port>
+reset-master ：主服务器已被重置。
+slave ：一个新的从服务器已经被 Sentinel 识别并关联。
+failover-state-reconf-slaves ：故障转移状态切换到了 reconf-slaves 状态。
+failover-detected ：另一个 Sentinel 开始了一次故障转移操作，或者一个从服务器转换成了主服务器。
+slave-reconf-sent ：领头（leader）的 Sentinel 向实例发送了 [SLAVEOF](/commands/slaveof.html) 命令，为实例设置新的主服务器。
+slave-reconf-inprog ：实例正在将自己设置为指定主服务器的从服务器，但相应的同步过程仍未完成。
+slave-reconf-done ：从服务器已经成功完成对新主服务器的同步。
-dup-sentinel ：对给定主服务器进行监视的一个或多个 Sentinel 已经因为重复出现而被移除 —— 当 Sentinel 实例重启的时候，就会出现这种情况。
+sentinel ：一个监视给定主服务器的新 Sentinel 已经被识别并添加。
+sdown ：给定的实例现在处于主观下线状态。
-sdown ：给定的实例已经不再处于主观下线状态。
+odown ：给定的实例现在处于客观下线状态。
-odown ：给定的实例已经不再处于客观下线状态。
+new-epoch ：当前的纪元（epoch）已经被更新。
+try-failover ：一个新的故障迁移操作正在执行中，等待被大多数 Sentinel 选中（waiting to be elected by the majority）。
+elected-leader ：赢得指定纪元的选举，可以进行故障迁移操作了。
+failover-state-select-slave ：故障转移操作现在处于 select-slave 状态 —— Sentinel 正在寻找可以升级为主服务器的从服务器。
no-good-slave ：Sentinel 操作未能找到适合进行升级的从服务器。Sentinel 会在一段时间之后再次尝试寻找合适的从服务器来进行升级，又或者直接放弃执行故障转移操作。
selected-slave ：Sentinel 顺利找到适合进行升级的从服务器。
failover-state-send-slaveof-noone ：Sentinel 正在将指定的从服务器升级为主服务器，等待升级功能完成。
failover-end-for-timeout ：故障转移因为超时而中止，不过最终所有从服务器都会开始复制新的主服务器（slaves will eventually be configured to replicate with the new master anyway）。
failover-end ：故障转移操作顺利完成。所有从服务器都开始复制新的主服务器了。
+switch-master ：配置变更，主服务器的 IP 和地址已经改变。 这是绝大多数外部用户都关心的信息。
+tilt ：进入 tilt 模式。
-tilt ：退出 tilt 模式。
故障转移
1.发现主服务进入客观下线
2.对当前纪元自增，并尝试在纪元中当选
3.如果当选失败，那么在设定的故障转移超时时间两倍之后，重新尝试当选。如果当选成功，则执行以下步骤
4.选出一个从服务器，并升级为主服务器
5.向被选中的从服务器发送SLAVE NO ONE命令，转变为主服务器
6.通过发布与订阅功能，将更新后的配置传播给所有其他Sentinel,其他Sentinel对配置进行更新
7.向已下线主服务器的从服务器发送SLAVEOF,复制新的主服务器
8.当所有从服务器都复制新的主服务器，领头Sentinel终止这次故障迁移操作
Sentinel使用一下规则选择新的主服务器
1.在失效主服务器属下的从服务器中，标记为主观下线，断线，最后一次回复PING超过5秒淘汰
2.在失效主服务器属下的从服务器中，与失效主服务器连接断开时长超过down-after-milliseconds指定时长10倍的从服务器都会被淘汰
3.剩下的从服务器中，选出复制偏移量(replication offset)最大的从服务器，若偏移量相同则使用运行id（runid）最小的


------------------------ 集群暂时没看 比较复杂 -----------------------