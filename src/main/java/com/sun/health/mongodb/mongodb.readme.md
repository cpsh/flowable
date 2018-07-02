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
 
MongoDB备份(mongodump)和恢复(mongorestore)
MongoDB数据备份
使用mongodump命令来备份MongoDB数据，该命令可以导出所有数据到指定目录中。mongodump命令可以通过参数指定导出的数据量级转存的服务器
语法结构
mongodump -h dbhost -d dbname -o dbdirectory
参数说明
-h MongoDB所在服务器地址 可以指定端口号 host:port
-d 需要备份的数据库实例 
-o 备份的数据存放位置，此目录需要提前建立，在备份完成之后，系统自动创建一个-d指定数据库同名的文件夹，存放备份数据
-c 备份的collection
MongoDB数据恢复
使用mongorestore命令来恢复备份的数据
mongorestore -h host -d dbname <path>
可选参数说明
-h --host host<:port>   恢复数据到的MongoDB服务器地址
-d --db 需要恢复的数据库
<path>  最后一个参数 设置备份数据所在的目录 不能和--dir同时指定
--dir 指定备份的目录
--drop 恢复的时候 先删除大年数据 然后恢复备份的数据

MongoDB监控
启动MongoDB服务后，必须了解MongoDB的运行情况，并查看MongoDB的性能。这样在大流量情况下可以很好的应对并保证MongoDB正常运行
MongoDB提供了mongostat和mongotop两个命令来监控MongoDB的运行情况
mongostat命令
mongostat是mongodb自带的状态监测工具，在命令行下使用，会间隔固定时间获取mongodb的当前运行状态，并输出，如果发现数据库变慢或其他问题，第一反应使用mongostat查看MongoDB服务器运行情况
mongotop命令
mongotop也是MongoDB下的一个内置工具，mongotop提供了一个党阀，用来跟踪一个MongoDB的实例，查看花费大量时间用于读取和写入数据的任务、每一秒输出一次集合统计
输出结果字段说明
ns      包含数据库命名空间，后者结合了数据库名称和集合
db      包含数据库的名称 名为.的数据库针对全局锁定 而非特定数据库
total   mongod花费在此命名空间上的总时间
read    mongod花费在此命名空间上读取数据的时间
write   mongod花费在此命名空间上写数据的总时间

MongoDB关系
MongoDB的关系表示多个文档之间在逻辑上的相互联系
文档间可通过嵌入和引用来建立联系
MongoDB中的关系可以是
1:1
1:N
N:1
N:N
嵌入式关系
User
{
    name: "",
    ...,
    addresses: [
        {
            building: "",
            ...
        }
    ]
}
数据量大的不易维护
引用式关系
User 
{
    name: "",
    ...,
    addresses_ids: [
        ObjectId("..."),
        ...,
    ]
}
需要两次查询

MongoDB数据库引用(DBRefs(一个类型))
引用式关系有两种
1.手动引用
2.DBRefs
使用DBRefs
DBRefs的形式
{
    "$ref": "collection_name",
    "$id": "引用的_id",
    "$db": "database_name",
}
字段意义
$ref    集合名称
$id     引用的id
$db     数据库名称
User {
    name: "",
    ...,
    address: [
        {
            "$ref": "",
            "$id": ObjectId(""),
            "$db": "",
        }
    ]
}
存储之后的格式
User {
    "name": "",
    ...,
    address: [
        DBRefs(ref: "", id: ObjectId(""), db: "")
    ]
}

MongoDB覆盖索引查询
覆盖查询需要满足
1.所有的查询字段是索引的一部分
2.所有的查询返回字段在同一索引中
由于所有出现在查询中的字段是索引的一部分，MongoDB无需在整个数据文档中检索匹配查询条件和返回使用相同索引的查询结果
因为索引在RAM中，从索引中获取数据比通过扫描文档读取数据要快的多
db.user.createIndex({"name": 1, "gender": 1},{})
db.user.find({"gender":"男"}, {"name":1, _id:0})
由于_id在查询时会默认返回，所以需要手动设置为0排除才能在没有_id的索引中使用覆盖索引查询

MongoDB查询分析
MongoDB查询分析可以确保索引是否有效，是查询语句性能分析的重要工具
MongoDB查询分析常用函数有 explain() hint()
使用explain()
explain()操作提供了查询信息，使用索引及查询统计等，有利于对索引优化
db.user.explain().help() 查看explain可用的场景
一般db.user.find().explain()
使用hint()
强制使用指定的索引进行查询
db.user.find().hint().explain()

MongoDB原子操作
MongoDB不支持事务，在实际使用中不能要求MongoDB保证数据完整性。
但是MongoDB提供了许多原子操作，比如文档的保存，修改，删除等，都是原子操作
原子操作数据模型
db.collection_name.findAndModify({
    query: <document>,
    sort: <document>,
    remove: <document>,
    update: <document>,
    new: <document>,
    fields: <document>,
    upsert: <document>,
    bypassDocumentValidation: <document>,
    writeConcern: <document>,
    collation: <document>,
})
findAndModify可以保证修改+返回结果（修改前或者修改后都可以）这两个步骤的原子性。 
修改并返回单个文档。 默认情况下，返回的文档不包括对更新所做的修改。
原子操作常用命令
$set: {field: value} 指定一个键并更新，若键不存在则创建
$unset: {field: 1} 删除指定的键
$inc: {field: value} 更新指定的number类型字段的值
$push: {field: value} 把value追加到field里面，必须为数组类型，若不存在则创建一个新的数组
$pushAll: {field: arr} 把一个数组整个追加
$pull: {field: _value} 从数组field中删除一个等于_value的值
$addToSet 不存在时追加一个值
$pop: {field: 1} 删除数组中的第一个或最后一个元素
$rename: {field_name: new_field_name} 重命名一个字段名
$bit: {field: {and: 5}} 位运算 and or not

MongoDB高级索引
会为数组索引中所有的值创建索引
可以为子文档创建索引

MongoDB索引限制
额外开销
每个索引占据一定的存储空间，在进行插入，更新和删除操作时也需要对索引进行操作。
内存(RAM)使用
由于索引是存储在内存中，应该确保索引大小不超过内存的限制
如果索引的大小大于内存的限制，MongoDB会删除一部分
查询限制
索引不能被一下的查询使用
1.正则表达式以及非操作符，如$nin,$not等
2.算术运算符，如$mod等
3.$where子句
索引键限制
如果超过限制，不会创建索引
最大范围
集合中索引不能超过64个
索引名的长度不能超过128个字符
一个符合索引最多可以有31个字段

MongoDB ObjectId
ObjectId是一个12字节BSON类型数据 格式
1.前4个字节表示时间戳
2.接下来3个字节是机器标识码
3.接下来2个字节是进程Id（PID）
4.最后三个字节是随机数
MongoDB中存储的文档必须有_id键，这个键的值可以是任何类型的，默认是ObjectId对象
创建新的ObjectId
newObjectId = ObjectId()
获取时间戳
newObjectId.getTimestamp()
获取字符串
newObjectId.str

MongoDB Map Reduce
Map-Reduce是一种计算模型，简单的说就是讲大批量的工作(数据)分解(Map)执行，然后再将结果合并成最终结果(Reduce)
mapReduce命令 语法格式
db.collection_name.mapReduce({
    function() {emit(key, value)}
    function(key, vakye) {
        return reduceFunction()
    },
    out: collection,
    query: document,
    sort: document,
    limit: number,
})
使用MapReduce要实现2个函数map和reduce函数，map函数可调用emit(key, value)遍历collection中所有的记录，将key和value传递给reduce函数
参数说明
1.map           映射函数（生成简直对序列，作为reduce函数参数）
2.reduce        统计函数 reduce函数的任务就是将key-values变成key-value，也就是将values数组变为单一value
3.out           统计结果存放集合 不指定则使用临时结合 在客户端断开后自动删除
4.query         赛选条件
5.sort          排序
6.limit         上限
类似于$group聚合 
------ 暂时未测试 ------------

MongoDB全文检索
db.collection_name.createIndex({key: index_name})
db.collection_name.find({$index_name: {$search: "content"}})
db.collection_name.dropIndex()

MongoDB正则表达式
正则表达式使用单个字符串来描述、匹配一系列符合句法规则的字符串
使用$regex操作符来设置匹配字符串的正则表达式
使用PCRE作为正则表达式语言
不同于全文检索，不需要做任何配置
db.collection_name.find({key: /pattern/mode})
或
db.collection_name.find({key: {$regex: pattern[, options: mode]}})
优化正则表达式
如果你的文档中字段设置了索引，那么使用索引相比于正则表达式匹配查找所有的数据查询速度更快。
如果正则表达式是前缀表达式，所有匹配的数据将以指定的前缀字符串为开始。例如： 如果正则表达式为 ^tut ，查询语句将查找以 tut 为开头的字符串。
注意
在正则表达式中使用变量一定要使用eval进行转换
title: eval("/" + title + "/i") 相当于title: {$regex: title, $options: "i"}
