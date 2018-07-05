概述
Kafka为分布式高吞吐量系统设计，kafka具有更好的吞吐量、内置分区、复制和固有的容错能力，非常适合大规模消息处理应用程序
消息系统
消息系统负责将数据从一个应用程序传输到另一个应用程序，因此应用程序可以专注于数据，而不用担心如何共享数据。分布式消息传递基于可靠消息队列的概念，消息在客户端应用程序和消息传递系统之间异步排队。
1.点对点纤细系统
消息被保留在队列中，一个或多个消费者可以消耗队列中的消息，但是特定消息只能由最多一个消费者消费，一旦消费者读取队列中的消息，消息就从队列里消失。此系统的典型示例就是订单处理系统，其中每个订单将由一个订单处理器处理，但是多个订单处理器可以同时工作。
2.发布-订阅消息系统
消息将保留在主题中，与点对点系统不同，消费者可以订阅一个或多个主题并使用该主题中的所有消息。在发布-订阅系统中，消息生产者称为发布者，消息使用这称为订阅者。现实例子就是数字电视频道。
Kafka
Apache Kafka是一个分布式发布-订阅系统和消息队列，可以处理大量的数据，能够是消息传递。适用于离线或在线消息消费。Kafka将消息保留在磁盘上，并在集群内复制放置数据丢失，构建与Zookeeper之上
好处
1.可靠性 Kafka是分布式、分区、复制和容错的
2.可扩展性 
3.耐用性 使用分布式提交日志，意味着消息会尽可能保留在磁盘上，持久化
4.性能 对于发布和订阅都有很高的吞吐量
Kafka非常快，并保证零停机和零数据丢失
基础
角色和规则说明
Topic 主题
属于特定类别的消息流称为主题，数据存储在主题中，主题被拆分为分区。每个分区包含不可变有序序列的消息，分区被实现为具有相等大小的一组分段文件
Partition 分区
主题可包含许多分区 因此可以应对不同数量的请求
Partition offset 分区偏移
每个分区消息具有称为offset的唯一序列标识
Replicas of partition 分区备份
副本只是一个分区的备份 副本从不读取或写入数据 用于防止数据丢失
Brokers 代理人
代理负责维护数据
Kafka Cluster
多个代理称为Kafka集群
Producers 生产者
发布消息，生产者发送数据到代理，然后由代理将消息添加到分区末尾，生产者可以指定分区也可以由代理自动分配
Consumers 消费者
消费者订阅topic，由代理人将符合的消息传递给消费者
Leader 领导者
负责读写的代理
Follower 追随者
负责同步Leader的数据 在Leader失败时代替成为Leader的节点

集群架构
Broker 代理
Kafka集群通常由多个代理组成以保持负载平衡
代理是无状态的，所以使用Zookeeper来维护集群状态，选举由Zookeeper完成
Zookeeper
Zookeeper用于管理和协调Kafka代理，Zookeeper主要用于通知生产者和消费者Kafka系统中代理的加入和退出。然后生产者和消费者决定如何协调之后的任务
Producers 生产者
生产者将数据推送给代理，不等待来自代理的确认
Consumers 消费者
Kafka代理是无状态的，意味着消费者必须维护分区偏移来确定以消费了多少消息。可以简单地通过改变偏移量来获取分区中任意位置的消息。消费者偏移量由Zookeeper通知


 0.9.0


复制代码
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# see kafka.server.KafkaConfig for additional details and defaults

############################# Server Basics #############################

# The id of the broker. This must be set to a unique integer for each broker.
# 节点的ID，必须与其它节点不同
broker.id=0

############################# Socket Server Settings #############################

listeners=PLAINTEXT://:9092

# The port the socket server listens on
# 监听端口
#port=9092

# Hostname the broker will bind to. If not set, the server will bind to all interfaces
# 节点需要绑定的主机名称。如果没有设置，服务器会绑定到所有接口
#host.name=localhost

# Hostname the broker will advertise to producers and consumers. If not set, it uses the
# value for "host.name" if configured.  Otherwise, it will use the value returned from
# java.net.InetAddress.getCanonicalHostName().
# 节点的主机名会通知给生产者和消费者。如果没有设置，它将会使用"host.name"的值（前提是设置了host.name）。
# 否则他会使用java.net.InetAddress.getCanonicalHostName()的返回值
#advertised.host.name=<hostname routable by clients>

# The port to publish to ZooKeeper for clients to use. If this is not set,
# it will publish the same port that the broker binds to.
# 这个端口将暴露给Zookeeper以供客户端来使用。如果没有设置，将会暴露节点绑定的端口
#advertised.port=<port accessible by clients>

# The number of threads handling network requests
# 接受网络请求的线程数
num.network.threads=3

# The number of threads doing disk I/O、
# 进行磁盘IO的线程数
num.io.threads=8

# The send buffer (SO_SNDBUF) used by the socket server
# 套接字服务器使用的发送缓冲区大小
socket.send.buffer.bytes=102400

# The receive buffer (SO_RCVBUF) used by the socket server
# 套接字服务器使用的接收缓冲区大小
socket.receive.buffer.bytes=102400

# The maximum size of a request that the socket server will accept (protection against OOM)
# 单个请求最大能接收的数据量
socket.request.max.bytes=104857600


############################# Log Basics #############################

# A comma seperated list of directories under which to store log files
# 一个逗号分隔的目录列表，用来存储日志文件
log.dirs=/tmp/kafka-logs

# The default number of log partitions per topic. More partitions allow greater
# parallelism for consumption, but this will also result in more files across
# the brokers.
# 每个主题的日志分区的默认数量。更多的分区允许更大的并行操作，但是它会导致节点产生更多的文件
num.partitions=1

# The number of threads per data directory to be used for log recovery at startup and flushing at shutdown.
# This value is recommended to be increased for installations with data dirs located in RAID array.
# 每个数据目录中的线程数，用于在启动时日志恢复，并在关闭时刷新。
num.recovery.threads.per.data.dir=1

############################# Log Flush Policy #############################

# Messages are immediately written to the filesystem but by default we only fsync() to sync
# the OS cache lazily. The following configurations control the flush of data to disk.
# 消息直接被写入文件系统，但是默认情况下我们仅仅调用fsync()以延迟的同步系统缓存
# There are a few important trade-offs here:
# 这些有一些重要的权衡
#    1. Durability: Unflushed data may be lost if you are not using replication.
#    2. Latency: Very large flush intervals may lead to latency spikes when the flush does occur as there will be a lot of data to flush.
#    3. Throughput: The flush is generally the most expensive operation, and a small flush interval may lead to exceessive seeks.
#    1. 持久性:如果不使用复制，未刷新的数据可能会丢失。
#    2. 延迟:非常大的刷新间隔可能会在刷新时导致延迟，因为将会有大量数据刷新。
#    3. 吞吐量:刷新通常是最昂贵的操作，而一个小的刷新间隔可能会导致过多的搜索。
# The settings below allow one to configure the flush policy to flush data after a period of time or
# every N messages (or both). This can be done globally and overridden on a per-topic basis.
# 下面的设置允许你去配置刷新策略，每隔一段时间刷新或者一次N个消息（或者两个都配置）。这可以在全局范围内完成，并在每个主题的基础上重写。

# The number of messages to accept before forcing a flush of data to disk
# 在强制刷新数据到磁盘执勤啊允许接收消息的数量
#log.flush.interval.messages=10000

# The maximum amount of time a message can sit in a log before we force a flush
# 在强制刷新之前，消息可以在日志中停留的最长时间
#log.flush.interval.ms=1000

############################# Log Retention Policy #############################

# The following configurations control the disposal of log segments. The policy can
# be set to delete segments after a period of time, or after a given size has accumulated.
# 以下的配置控制了日志段的处理。策略可以配置为每隔一段时间删除片段或者到达一定大小之后。
# A segment will be deleted whenever *either* of these criteria are met. Deletion always happens
# from the end of the log.
# 当满足这些条件时，将会删除一个片段。删除总是发生在日志的末尾。

# The minimum age of a log file to be eligible for deletion
# 一个日志的最小存活时间，可以被删除
log.retention.hours=168

# A size-based retention policy for logs. Segments are pruned from the log as long as the remaining
# segments don't drop below log.retention.bytes.
# 一个基于大小的日志保留策略。段将被从日志中删除只要剩下的部分段不低于log.retention.bytes。
#log.retention.bytes=1073741824

# The maximum size of a log segment file. When this size is reached a new log segment will be created.
# 每一个日志段大小的最大值。当到达这个大小时，会生成一个新的片段。
log.segment.bytes=1073741824

# The interval at which log segments are checked to see if they can be deleted according
# to the retention policies
# 检查日志段的时间间隔，看是否可以根据保留策略删除它们
log.retention.check.interval.ms=300000

############################# Zookeeper #############################

# Zookeeper connection string (see zookeeper docs for details).
# Zookeeper连接字符串（具体见Zookeeper文档）
# This is a comma separated host:port pairs, each corresponding to a zk
# 这是一个以逗号为分割的部分，每一个都匹配一个Zookeeper
# server. e.g. "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002".
# You can also append an optional chroot string to the urls to specify the
# root directory for all kafka znodes.
# 您还可以将一个可选的chroot字符串附加到url，以指定所有kafka znode的根目录。
zookeeper.connect=localhost:2181

# Timeout in ms for connecting to zookeeper
# 连接到Zookeeper的超时时间
zookeeper.connection.timeout.ms=6000

复制代码

 

 0.10.1


复制代码
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# see kafka.server.KafkaConfig for additional details and defaults

############################# Server Basics #############################

# The id of the broker. This must be set to a unique integer for each broker.
# 节点的ID，必须与其它节点不同
broker.id=0

# Switch to enable topic deletion or not, default value is false
# 选择启用删除主题功能，默认false
#delete.topic.enable=true

############################# Socket Server Settings #############################

# The address the socket server listens on. It will get the value returned from 
# java.net.InetAddress.getCanonicalHostName() if not configured.
# 套接字服务器坚挺的地址。如果没有配置，就使用java.net.InetAddress.getCanonicalHostName()的返回值
#   FORMAT:格式：
#     listeners = security_protocol://host_name:port
#   EXAMPLE:例子：
#     listeners = PLAINTEXT://your.host.name:9092
#listeners=PLAINTEXT://:9092

# Hostname and port the broker will advertise to producers and consumers. If not set, 
# it uses the value for "listeners" if configured.  Otherwise, it will use the value
# returned from java.net.InetAddress.getCanonicalHostName().
# 节点的主机名会通知给生产者和消费者。如果没有设置，如果配置了"listeners"就使用"listeners"的值。
# 否则就使用java.net.InetAddress.getCanonicalHostName()的返回值
#advertised.listeners=PLAINTEXT://your.host.name:9092

# The number of threads handling network requests
# 接受网络请求的线程数
num.network.threads=3

# The number of threads doing disk I/O
# 进行磁盘IO的线程数
num.io.threads=8

# The send buffer (SO_SNDBUF) used by the socket server
# 套接字服务器使用的发送缓冲区大小
socket.send.buffer.bytes=102400

# The receive buffer (SO_RCVBUF) used by the socket server
# 套接字服务器使用的接收缓冲区大小
socket.receive.buffer.bytes=102400

# The maximum size of a request that the socket server will accept (protection against OOM)
# 单个请求最大能接收的数据量
socket.request.max.bytes=104857600


############################# Log Basics #############################

# A comma seperated list of directories under which to store log files
# 一个逗号分隔的目录列表，用来存储日志文件
log.dirs=/tmp/kafka-logs

# The default number of log partitions per topic. More partitions allow greater
# parallelism for consumption, but this will also result in more files across
# the brokers.
# 每个主题的日志分区的默认数量。更多的分区允许更大的并行操作，但是它会导致节点产生更多的文件
num.partitions=1

# The number of threads per data directory to be used for log recovery at startup and flushing at shutdown.
# This value is recommended to be increased for installations with data dirs located in RAID array.
# 每个数据目录中的线程数，用于在启动时日志恢复，并在关闭时刷新。
num.recovery.threads.per.data.dir=1

############################# Log Flush Policy #############################

# Messages are immediately written to the filesystem but by default we only fsync() to sync
# the OS cache lazily. The following configurations control the flush of data to disk.
# 消息直接被写入文件系统，但是默认情况下我们仅仅调用fsync()以延迟的同步系统缓存
# There are a few important trade-offs here:
# 这些有一些重要的权衡
#    1. Durability: Unflushed data may be lost if you are not using replication.
#    2. Latency: Very large flush intervals may lead to latency spikes when the flush does occur as there will be a lot of data to flush.
#    3. Throughput: The flush is generally the most expensive operation, and a small flush interval may lead to exceessive seeks.
#    1. 持久性:如果不使用复制，未刷新的数据可能会丢失。
#    2. 延迟:非常大的刷新间隔可能会在刷新时导致延迟，因为将会有大量数据刷新。
#    3. 吞吐量:刷新通常是最昂贵的操作，而一个小的刷新间隔可能会导致过多的搜索。
# The settings below allow one to configure the flush policy to flush data after a period of time or
# every N messages (or both). This can be done globally and overridden on a per-topic basis.
# 下面的设置允许你去配置刷新策略，每隔一段时间刷新或者一次N个消息（或者两个都配置）。这可以在全局范围内完成，并在每个主题的基础上重写。

# The number of messages to accept before forcing a flush of data to disk
# 在强制刷新数据到磁盘之前允许接收消息的数量
#log.flush.interval.messages=10000

# The maximum amount of time a message can sit in a log before we force a flush
# 在强制刷新之前，消息可以在日志中停留的最长时间
#log.flush.interval.ms=1000

############################# Log Retention Policy #############################

# The following configurations control the disposal of log segments. The policy can
# be set to delete segments after a period of time, or after a given size has accumulated.
# 以下的配置控制了日志段的处理。策略可以配置为每隔一段时间删除片段或者到达一定大小之后。
# A segment will be deleted whenever *either* of these criteria are met. Deletion always happens
# from the end of the log.
# 当满足这些条件时，将会删除一个片段。删除总是发生在日志的末尾。

# The minimum age of a log file to be eligible for deletion
# 一个日志的最小存活时间，可以被删除
log.retention.hours=168

# A size-based retention policy for logs. Segments are pruned from the log as long as the remaining
# segments don't drop below log.retention.bytes.
# 一个基于大小的日志保留策略。段将被从日志中删除只要剩下的部分段不低于log.retention.bytes。
#log.retention.bytes=1073741824

# The maximum size of a log segment file. When this size is reached a new log segment will be created.
# 每一个日志段大小的最大值。当到达这个大小时，会生成一个新的片段。
log.segment.bytes=1073741824

# The interval at which log segments are checked to see if they can be deleted according
# to the retention policies
# 检查日志段的时间间隔，看是否可以根据保留策略删除它们
log.retention.check.interval.ms=300000

############################# Zookeeper #############################

# Zookeeper connection string (see zookeeper docs for details).
# Zookeeper连接字符串（具体见Zookeeper文档）
# This is a comma separated host:port pairs, each corresponding to a zk
# 这是一个以逗号为分割的部分，每一个都匹配一个Zookeeper
# server. e.g. "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002".
# You can also append an optional chroot string to the urls to specify the
# root directory for all kafka znodes.
# 您还可以将一个可选的chroot字符串附加到url，以指定所有kafka znode的根目录。
zookeeper.connect=localhost:2181

# Timeout in ms for connecting to zookeeper
# 连接到Zookeeper的超时时间
zookeeper.connection.timeout.ms=6000

复制代码

 

0.11.0

 


复制代码
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# see kafka.server.KafkaConfig for additional details and defaults

############################# Server Basics #############################

# The id of the broker. This must be set to a unique integer for each broker.
# 节点的ID，必须与其它节点不同
broker.id=0

# Switch to enable topic deletion or not, default value is false
# 选择启用删除主题功能，默认false
#delete.topic.enable=true

############################# Socket Server Settings #############################

# The address the socket server listens on. It will get the value returned from 
# java.net.InetAddress.getCanonicalHostName() if not configured.
# 套接字服务器坚挺的地址。如果没有配置，就使用java.net.InetAddress.getCanonicalHostName()的返回值
#   FORMAT:
#     listeners = listener_name://host_name:port
#   EXAMPLE:
#     listeners = PLAINTEXT://your.host.name:9092
#listeners=PLAINTEXT://:9092

# Hostname and port the broker will advertise to producers and consumers. If not set, 
# it uses the value for "listeners" if configured.  Otherwise, it will use the value
# returned from java.net.InetAddress.getCanonicalHostName().
# 节点的主机名会通知给生产者和消费者。如果没有设置，如果配置了"listeners"就使用"listeners"的值。
# 否则就使用java.net.InetAddress.getCanonicalHostName()的返回值
#advertised.listeners=PLAINTEXT://your.host.name:9092

# Maps listener names to security protocols, the default is for them to be the same. See the config documentation for more details
# 将侦听器的名称映射到安全协议，默认情况下它们是相同的。有关详细信息，请参阅配置文档
#listener.security.protocol.map=PLAINTEXT:PLAINTEXT,SSL:SSL,SASL_PLAINTEXT:SASL_PLAINTEXT,SASL_SSL:SASL_SSL

# The number of threads that the server uses for receiving requests from the network and sending responses to the network
# 服务器用来接受请求或者发送响应的线程数
num.network.threads=3

# The number of threads that the server uses for processing requests, which may include disk I/O
# 服务器用来处理请求的线程数，可能包括磁盘IO
num.io.threads=8

# The send buffer (SO_SNDBUF) used by the socket server
# 套接字服务器使用的发送缓冲区大小
socket.send.buffer.bytes=102400

# The receive buffer (SO_RCVBUF) used by the socket server
# 套接字服务器使用的接收缓冲区大小
socket.receive.buffer.bytes=102400

# The maximum size of a request that the socket server will accept (protection against OOM)
# 单个请求最大能接收的数据量
socket.request.max.bytes=104857600


############################# Log Basics #############################

# A comma seperated list of directories under which to store log files
# 一个逗号分隔的目录列表，用来存储日志文件
log.dirs=/tmp/kafka-logs

# The default number of log partitions per topic. More partitions allow greater
# parallelism for consumption, but this will also result in more files across
# the brokers.
# 每个主题的日志分区的默认数量。更多的分区允许更大的并行操作，但是它会导致节点产生更多的文件
num.partitions=1

# The number of threads per data directory to be used for log recovery at startup and flushing at shutdown.
# This value is recommended to be increased for installations with data dirs located in RAID array.
# 每个数据目录中的线程数，用于在启动时日志恢复，并在关闭时刷新。
num.recovery.threads.per.data.dir=1

############################# Internal Topic Settings  #############################
# 内部主题设置
# The replication factor for the group metadata internal topics "__consumer_offsets" and "__transaction_state"
# For anything other than development testing, a value greater than 1 is recommended for to ensure availability such as 3.
# 对于除了开发测试之外的其他任何东西，group元数据内部主题的复制因子“__consumer_offsets”和“__transaction_state”，建议值大于1，以确保可用性(如3)。
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1

############################# Log Flush Policy #############################

# Messages are immediately written to the filesystem but by default we only fsync() to sync
# the OS cache lazily. The following configurations control the flush of data to disk.
# 消息直接被写入文件系统，但是默认情况下我们仅仅调用fsync()以延迟的同步系统缓存
# There are a few important trade-offs here:
# 这些有一些重要的权衡
#    1. Durability: Unflushed data may be lost if you are not using replication.
#    2. Latency: Very large flush intervals may lead to latency spikes when the flush does occur as there will be a lot of data to flush.
#    3. Throughput: The flush is generally the most expensive operation, and a small flush interval may lead to exceessive seeks.
#    1. 持久性:如果不使用复制，未刷新的数据可能会丢失。
#    2. 延迟:非常大的刷新间隔可能会在刷新时导致延迟，因为将会有大量数据刷新。
#    3. 吞吐量:刷新通常是最昂贵的操作，而一个小的刷新间隔可能会导致过多的搜索。
# The settings below allow one to configure the flush policy to flush data after a period of time or
# every N messages (or both). This can be done globally and overridden on a per-topic basis.
# 下面的设置允许你去配置刷新策略，每隔一段时间刷新或者一次N个消息（或者两个都配置）。这可以在全局范围内完成，并在每个主题的基础上重写。

# The number of messages to accept before forcing a flush of data to disk
# 在强制刷新数据到磁盘之前允许接收消息的数量
#log.flush.interval.messages=10000

# The maximum amount of time a message can sit in a log before we force a flush
# 在强制刷新之前，消息可以在日志中停留的最长时间
#log.flush.interval.ms=1000

############################# Log Retention Policy #############################

# The following configurations control the disposal of log segments. The policy can
# be set to delete segments after a period of time, or after a given size has accumulated.
# 以下的配置控制了日志段的处理。策略可以配置为每隔一段时间删除片段或者到达一定大小之后。
# A segment will be deleted whenever *either* of these criteria are met. Deletion always happens
# from the end of the log.
# 当满足这些条件时，将会删除一个片段。删除总是发生在日志的末尾。

# The minimum age of a log file to be eligible for deletion
# 一个日志的最小存活时间，可以被删除
log.retention.hours=168

# A size-based retention policy for logs. Segments are pruned from the log as long as the remaining
# segments don't drop below log.retention.bytes.
# 一个基于大小的日志保留策略。段将被从日志中删除只要剩下的部分段不低于log.retention.bytes。
#log.retention.bytes=1073741824

# The maximum size of a log segment file. When this size is reached a new log segment will be created.
# 每一个日志段大小的最大值。当到达这个大小时，会生成一个新的片段。
log.segment.bytes=1073741824

# The interval at which log segments are checked to see if they can be deleted according
# to the retention policies
# 检查日志段的时间间隔，看是否可以根据保留策略删除它们
log.retention.check.interval.ms=300000

############################# Zookeeper #############################

# Zookeeper connection string (see zookeeper docs for details).
# Zookeeper连接字符串（具体见Zookeeper文档）
# This is a comma separated host:port pairs, each corresponding to a zk
# 这是一个以逗号为分割的部分，每一个都匹配一个Zookeeper
# server. e.g. "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002".
# You can also append an optional chroot string to the urls to specify the
# root directory for all kafka znodes.
# 您还可以将一个可选的chroot字符串附加到url，以指定所有kafka znode的根目录。
zookeeper.connect=localhost:2181

# Timeout in ms for connecting to zookeeper
# 连接到Zookeeper的超时时间
zookeeper.connection.timeout.ms=6000


############################# Group Coordinator Settings #############################

# The following configuration specifies the time, in milliseconds, that the GroupCoordinator will delay the initial consumer rebalance.
# The rebalance will be further delayed by the value of group.initial.rebalance.delay.ms as new members join the group, up to a maximum of max.poll.interval.ms.
# The default value for this is 3 seconds.
# We override this to 0 here as it makes for a better out-of-the-box experience for development and testing.
# However, in production environments the default value of 3 seconds is more suitable as this will help to avoid unnecessary, and potentially expensive, rebalances during application startup.
group.initial.rebalance.delay.ms=0

可以看出，目前最新版本的配置文件没有了

#port=9092

#host.name=localhost

#advertised.host.name=<hostname routable by clients>

#advertised.port=<port accessible by clients>
 

取而代之的是

#listeners=PLAINTEXT://:9092

#advertised.listeners=PLAINTEXT://your.host.name:9092
