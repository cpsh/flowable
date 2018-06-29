MongoDB简介
MongoDB是由C++编写，基于分布式文件存储的开源数据库系统
在高负载下，添加更多节点，可以保证服务器性能
MongoDB旨在为WEB应用提供可扩展的高性能数据存储解决方案
MongoDB将数据存储为一个文档，数据结构由键值(key => value)对组成，MongoDB文档类似于JSON对象，字段值可以包含其他文档、数组及文档数组
主要特点
1.MongoDB是一个面向文档存储的数据库，操作起来比较简单和容易
2.可以在MongoDB记录中设置任何属性的索引来实现更快的跑徐
3.可以通过本地或网络创建数据镜像，更强的扩展性
4.如果负载的增加（需要更多的存储空间和更强的处理能力），可以分布在计算机网络中的其他节点上。
5.MongoDB支持丰富的查阅表达式，查询指令使用JSON形式的标记，可以轻易查询文档中内嵌的对象及数组
6.MongoDB使用update()命令可以实现替换完成的文档(数据)或者一些指定的数据字段
7.MongoDB中的Map/reduce主要用于对数据进行批量处理和聚合操作
8.Map和Reduce Map函数调用emit(key, value)遍历集合中所有记录，将key与value传给Reduce函数进行处理
9.Map函数和Reduce函数是使用Javascript编写的，并可以通过db.runCommand或mapreduce命令来执行MapReduce操作
10.GridFS是MongoDB中的一个内置功能，可以用于存放大量小文件
11.MongoDB允许在服务器端执行脚本，可以用Javascript编写某个函数，直接在服务器端执行，也可以把函数的定义存储在服务器端，下次直接调用即可
12.MongoDB支持各种编程语言
13.MongoDB安装简单

MongoDB概念解析
SQL             MongoDB             解释说明
database        database            数据库
table           collection          数据库表/集合
row             document            数据记录行/文档
column          field               数据字段/域
index           index               索引
table joins                         表连接 mongoDB不支持
primary key     primary key         主键，MongoDB自动将_id设置为主键

MongoDB连接
启动MongoDB服务
使用mongod可执行文件即可启动MongoDB服务。
执行启动操作后，mongodb输出一些必要信息，之后就等待客户端连接，当建立链接后，就会打印日志信息
标准连接语法
mongodb://[username:password@]host1[:port][,host2[:port]...][/database[?option]]
mongodb:// 固定格式
username:password@ 可选 连接时尝试使用此信息登录
host port 主机地址和端口号 端口号默认为27017
/database 如果指定username:password@ 连接并验证登录指定数据库 如不指定则默认登录test数据库
?options 连接选项 name=value以&分隔
options可选
1.replicaSet=name               验证replicaSet的名称 表明connect=replicaSet
2.slaveOk=true|false            true时在connect=direct时，会连接第一台服务器，即使不是主服务器。在connect=replicaSet时，会发送所有写请求到主服务器并把读操作分布到不同的从服务器上
                                false时在connect=direct时，会查询并连接到主服务器上，connect=replicaSet时，连接到主服务器并所有读写操作都在主服务器上
3.safe=true|false               true在执行更新操作后，会发送getkastError命令来确保更新成功。
                                false在更新后不会发送getLastError确保操作成功
4.w=n                           添加{ w: n }到getLastError命令中
5.wtimeoutMS=ms                 驱动添加{ wtimeout: ms }到getLastError命令
6.fsync=true|false              true时添加{ fsync: true }到getLastError命令
7.journal=true|false            
8.connectTimeoutMS=ms           连接超时时间
9.socketTimeoutMS=ms            ||Socket握手时间


MongoDB创建数据库
语法格式
use DATABASE_NAME
如果数据库不存在，则创建数据库，否则切换到指定数据库
help 查看帮助
常用
show dbs
db
show collections
use database_name

MongoDB删除数据库
语法格式
db.dropDatabase()
删除当前数据库 使用db查看当前数据库名称

MongoDB删除集合
语法格式
db.collection_name.drop()
删除指定名称的集合

MongoDB创建集合
语法格式
db.createCollection(name, options)
name为集合名称
options为可选参数 可选值以下
1.capped        boolean             如果为true则创建固定集合，固定集合是指有固定大小的集合，当达到最大值时，会自动覆盖最早的文档，值为true时必须指定size
2.autoIndexId   boolean             true时，自动为主键_id字段创建索引，默认为false
3.size          number              为固定集合指定一个最大值(以字节为单位) 
4.max           number              为固定集合指定可包含文档的最大数量
MongoDB不必显示创建collection，在插入document的时候，会自动创建

MongoDB插入文档
文件的数据结构合JSON基本一致 所有存储在集合中的数据都是BSON格式
BSON是一种类json的一种二进制形式的存储格式，Binary JSON
插入文档
db.collection_name.insert(document)
可以直接插入BSON格式的document
也可以定义为一个变量，再使用此变量插入

MongoDB更新文档
MongoDB使用update()和save()方法来更新集合中的文档
update()方法
update()方法用于更新已存在的文档，语法格式
db.collection_name.update(
    <query>,
    <update>,
    {
        upsert: <boolean>,
        multi: <boolean>,
        writeConcern: <document>
    }
)
参数说明
query update的查询条件 类似sql中where后面的部分
update update的对象和一些更新的操作符(如$,$inc...) 类似于sql中set后面的部分
upsert 可选 如果不存在update的记录时 是否将update作为新document插入 默认是false不插入
multi 是否更新所有找到的数据 默认是false只更新找到的第一条数据
writeConcern 指定抛出异常的级别
save()方法
save()方法通过传入的文档来替换已有的文档 语法格式
db.collection.save(
    <document>,
    {
        writeConcern: <document>
    }
)
参数说明
document 文档数据
writeConcern 输出异常级别 （concern 涉及 关心 关系)

update()用query来判断更新哪些数据 save()则使用_id主键来判断更新那一条数据

MongoDB删除文档
MongoDB中remove函数用于删除文档
在执行remove()方法之前使用find()方法来判断条件是否正确是比较好的习惯
语法结构
db.collection_name.remove(
    <query>,
    {
        justOne: <boolean>,
        writeConcern: <document>
    }
)
参数说明
query 删除条件
justOne true或1时只删除第一条匹配到的文档 默认是false
writeConcern 发生异常时的级别

MongoDB查询文档
find()以非结构化的方式来显示所有文档
语法结构
db.collection.find(query, projection)
参数说明
query 使用查询操作指定的查询条件
projection 指定返回的键，若返回所有键则不传
需要以易读方式来读取数据 使用pretty()方法
db.collection_name.find().pretty()
除了find()方法以外还有一个findOne()方法
query格式
{"key":"value"} where key = value
{"key": {$gt/$gte/$lt/$lte:"value"}} where key>/>=/</<=value
AND条件
db.collection_name.find({key1:vale1, key2:value2})
OR条件
db.collection_name.find($or: [{key1:value1},{key2:value2}])

MongoDB $type操作符
$type操作符是基于BSON类型来检索集合中匹配的数据类型 并返回结果
MongoDB中可以使用的类型
Double              1
String              2
Object              3
Array               4
Binary Data         5
Undefined           6 (已废弃)
Object Id           7
Boolean             8
Date                9
Null                10
Regular Expression  11
Javascript          13
Symbol              14
Javascript(With scope) 15
32-bit integer      16
Timestamp           17
64-bit integer      18
Min Key             255
Max key             127
使用
db.collection_name.find(key: {$type: x}) 查询key的数据类型为x的document

MongoDB Limit与Skip方法
需要读取指定数量的数据记录，limit接受一个数字参数，该参数指定从MongoDB中读取的记录条数
语法格式
db.collection_name.find().limit(NUMBER)
需要跳过指定数量的记录 使用skip()方法 同样接受一个数字参数
语法格式
db.collection_name.find().skip(NUMBER)

MongoDB排序
sort()方法
可以通过参数指定排序的字段和顺序 1 表示升序 -1 表示降序
db.collection_name.find().sort({key: 1/-1})

MongoDB索引
索引通常能够极大提高查询的效率，如果没有索引，MongoDB在读取数据时必须扫描集合中的每个文件并选取符合查询条件的记录
这种扫描全集合的效率非常低，特别在处理大量数据的时候，查询可能需要花费很长时间。
索引是特殊的数据结构，索引存储在一个易于遍历读取的数据集合中，索引是对数据库表中的一列或多列的值进行排序的一种结构。
createIndex()方法
注意在3.0.0版本之前创建索引方法为db.collection_name.ensureIndex()，之后版本使用db.collection_name.createIndex()方法，ensureIndex()方法仍然可以使用 只是createIndex()的别名
语法结构
db.collection_name.createIndex(keys, options)
字段属性
keys 创建的索引字段 1为升序 -1位降序 {key1:1/-1,key2:-1/-1,....}
options 可选项 如下
1.background                boolean     创建索引过程是否阻塞其他操作，true时指定以后台方式创建索引，默认是false
2.unique                    boolean     建立的索引是否唯一 指定为true创建唯一索引 默认为false
3.name                      string      索引的名称 如果为指定 通过连接索引的字段名和排序顺序生成一个索引名称
4.dropDups                  boolean     在建立唯一索引时是否删除重复记录 指定true删除重复记录 默认false
5.sparse                    boolean     对文档中不存在的字段数据不启用索引 需要注意 如果设置为true 则查询不到不包含此字段的文档 默认为false
6.expireAfterSeconds        integer     指定一个以秒为单位的数值，TTL设定集合的生存时间
7.v                         version     索引的版本号 默认的索引版本取决于mongodb创建索引时运行的版本
8.weights                   document    索引权重值，数值在1~99999之间，表示该索引相对于其他索引的权重
9.default_language          string      对于文本索引，决定了停用词及词干和词器的规则的列表，默认为英语
10.language_override        string      对于文本索引，该参数指定了包含在文档中的字段名

MongoDB聚合
MongoDB中聚合(aggregate)主要用于处理数据（诸如统计平均值，求和等），并返回计算后的数据结果
aggregate()方法
语法结构
db.collection_name.aggregate(AGGREGATE_OPERATION)
例如 db.scores.aggregate([{$group: {_id:"$name", sum_score: {$sum: "$score"}}}])
聚合表达式
$sum        计算总和
$avg        计算平均值
$min        最小值
$max        最大值
$push       在结果中插入一个值
$addToSet   类似于$push,但只添加不存在的值
$first      获取第一个文档
$last       获取最后一个文档
管道的概念
管道在Unix和Linux中一般用于将当前命令的输出结果作为下一个命令的输入
MongoDB的聚合管道将MongoDB文档在一个管道处理完毕后将结果传递给下一个管道处理，管道操作时可以重复的
表达式：处理输入文档并输出，表达式是无状态，只能用于计算当前聚合管道的文档，不能处理其他的文档
聚合框架中常用的几个操作
1.$project      修改输入文档的解构 可以用于重命名、增加或删除域 也可以用于创建计算结果以及嵌套文档
2.$match        用于过滤数据，只输入符合条件的文档，$match使用MongoDB标准查询操作
3.$limit        用来限制MongoDB聚合管道返回的文档数
4.$skip         在聚合管道中跳过指定数量的文档 
5.$unwind       将文档中的某一个数组类型字段拆分为多条，每条包含数组中的一个值
6.$group        将集合中的文档分组，可用于统计结果
7.$sort         将输入文档排序后输出
8.$geoNear      输出接近某一个地理位置的有序文档
管道操作符实例
$project
db.collection.aggregate([{$proejct: {_id: 1/0, id: "$_id"}}]) 非0表示展示 可以重名
$match
db.collection.aggregate([{$match: {key: value}}])

MongoDB复制

MongoDB复制是将数据同步在多个服务器的过程
复制提供了数据冗余备份，并在多个服务器上存储数据副本，提高了数据的可用性，并可以保证数据的安全性
复制还允许从硬件故障和服务中断中恢复数据
什么是复制
1.保障数据的安全性
2.数据高可用性
3.灾难恢复
4.无需停机维护（如备份、重建索引、压缩）
5.分布式读取数据
MongoDB复制原理
MongoDB复制至少需要两个节点，其中一个是主节点，负责处理客户端请求，其余的都是从节点，负责复制主节点上的数据。
主节点记录在其上的所有操作oplog,从节点定期轮询主节点获取这些操作，然后对自己的数据副本执行这些操作，从而保证从节点的数据与主节点一致。
副本集特征
1.N个节点的集群
2.任何节点可作为主节点
3.自动故障转移
4.自动恢复
5.所有写入操作都在主节点上
MongoDB副本集设置
1.关闭正在运行的MongoDB服务器
2.通过--replSet replication_name参数来启动MongoDB服务器
3.使用mongo shell连接上服务器
4.rs.initial()启动副本集 rs.conf()查看副本集的配置 rs.status()查看副本集运行情况
5.给副本集添加成员 rs.add(HOST_NAME:PORT)
6.只能通过主节点添加从服务器，使用db.isMaster()

MongoDB分片
MongoDB里面存在另一种集群，就是分片技术，可以满足MongoDB数据量大量增长的需求
在MongoDB存储海量数据时，一台机器可能不足以存储数据，也能不足以提供可接受的读写吞吐量
为什么使用分片
1.复制所有的写入操作到主节点
2.延迟敏感的数据会在主节点查询
3.单个副本集限制在12个节点
4.当请求量巨大时会出现内存不足
5.本地磁盘不足
6.垂直扩展昂贵
Shard   用于存储实际的数据 实际生产环境中一个shard server角色可由几台机器组成一个replicationSet承担 放置主机单点故障
Config Server   mongod实例 存储整个ClusterMetadata，其中包括chunk信息
Query Routers   前端路由 客户端由此接入 
配置分片
Shard配置
mongod --port 27018 --dbpath=/usr/local/mongodb/data/db_27018/ --logpath=/usr/local/mongodb/data/db_27018/shard_27018.log --logappend --fork
Config Server配置
mongod --port 27016 --dbpath=/usr/local/mongodb/data/db_27016/ --logpath=usr/local/mongodb/data/db_27016/config_27016.log --logappend --fork
Router配置
mongos --port 27017 --configdb conf/localhost:27016 --fork --logpath=/usr/local/mongodb/data/db/router_27017.log --chunkSize=500
使用mongo命令行交互工具 配置Shard到Config Server
需要使用admin db
db.runCommand({addShard: "localhost:27018"})
db.runCommand({addShard: "localhost:27019"})
db.runCommand({enablesharding: "test"}) # 设置分片存储的数据库
db.runCommand({shardcollection: "test.log", key: {id:1,time:1})
 ------ 暂时分片没通过 ------------