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
b.
33.repl-disabled-tcp-nodelay no