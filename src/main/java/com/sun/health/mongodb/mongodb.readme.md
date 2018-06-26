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
