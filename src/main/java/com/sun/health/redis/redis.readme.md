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





