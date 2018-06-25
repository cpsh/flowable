A.安装

B.基本语法
1.lua -i # 进入lua交互模式
2.创建一个文件 lua lua_demo 执行代码
3.#!/usr/local/bin/lua 在lua文件第一行使用lua执行
Lua令牌
令牌可以是关键字、标识符、常量、字符串文字或符号
注释
-- 单行注释
--[[ --]] 多行注释
标识符
大小写区分 字符 数字 下划线
关键字
and
break
do 
else
elseif
end
false
for
function
if
in
local
nil
not
or
repeat
return
then
true
until
while
Lua中的空白符
仅包含空格，可能带有注释，被称为空行，Lua解释器将无视

C.Lua数据类型
Lua是动态类型语言，变量不需要类型定义，只需要为变量赋值。值可以存储在变量中，作为参数传递或结果返回。
Lua中有8个基本类型分别为:nil、boolean、number、string、userdata、function、thread和table。
nil         最简单，只有值nil属于该类，表示一个无效值（在条件表达式中相当于nil）
boolean     包含两个值:true（真）和false（假）
number      表示双精度类型的实浮点数
string      字符串由一对双引号或单引号来表示
function    由C或Lua编写的函数
userdata    便是任意存储在变量中的C数据结构
thread      表示执行的独立线程，用于执行协同程序
table       Lua中的表格（table）其实是一个关联数组（associative arrays），数组的索引可以是数字或者字符串。在Lua里，table的创建时通过构造表达式来完成的，最简单的构造表达式是{}，用来创建一个空表。
D.Lua变量
变量在使用前，必须在代码中进行声明，即创建该变量。
编译程序执行代码之前编译器需要知道如何给语句变量开辟存储区，用于存储变量的值。
Lua变量由三种类型：全局变量、局部变量、表中的域。
Lua中的变量全是全局变量，那怕是语句块或是函数里，除非用local显式声明为局部变量。
局部变量的作用域从声明位置开始到所在语句块结束。
变量的默认值均为nil。
赋值语句
赋值是改变一个变量的值和改变表域的最基本的方法
a = "hello' .. "world"
t.n = t.n + 1
Lua可以对多个变量同时赋值，变量列表和值列表的各个元素用逗号分开，赋值语句右边的值会一次赋值给左边的变量。
a, b = 10, 2 * x
遇到赋值语句Lua会先计算右边所有的值然后再执行赋值操作，所以可以如此交换变量值
x, y = y, x
a[i], a[j] = a[j], a[i]
当变量个数和值的个数不一致时，Lua会以变量个数为基础采取以下策略：
1.变量个数 > 值的个数   按变量个数补足nil
2.变量个数 < 值的个数   多余的值会被忽略
尽可能使用局部变量，好处
1.避免命名冲突
2.访问局部变量的速度比全局变量更快
索引
对table的索引使用方括号[],Lua也提供了.操作
t[i]
t.i -- 当索引为字符串类型时的一种简化写法
gettable_event(t, i) -- 采用索引访问本质上是一个类似这样的函数调用

E.Lua循环
一组被重复执行的语句称为循环体，能否继续重复，决定循环的终止条件。
循环结构是在一定条件下反复执行某段代码的流程结构，被反复执行的代码被称为循环体。
循环语句是有循环体即循环的终止条件两部分组成的。
Lua语言提供了以下几种循环处理方式:
1.while         在条件为true时，让程序重复地执行某些语句，执行语句前会先检查条件是否为true。
while(condition)
do
    statements
end
2.for           重复执行指定语句，重复次数可在for语句中控制
for分为数值for循环和泛型for循环
数值for循环
for var=exp1, exp2, exp3 
do
    statements
end
exp1 初始值
exp2 比较的值
exp3 每次执行改变的值 不指定时为0
泛型for循环
for i, v in ipairs(a)
do
    print(v)
end
ipairs()函数用于获取table的index和value
3.repeat...until重复执行循环，知道指定的条件为true为止
repeat
    statements
until(condition)
4.循环嵌套       可以在循环内嵌套一个或多个循环语句
循环控制语句
循环控制语句用于控制程序的流程，以实现程序的各种结构方式
break           退出当前循环或语句并开始脚本执行紧接着的语句
无限循环
while(true)
do
    pass
end
F.Lua流程控制
Lua编程语言流程控制语句通过程序设定一个或多个条件语句来设定，条件为true时执行指定程序代码，条件为false执行其他代码
if(condition)
then
    statements
elseif(condition)
then
    statements
else
    statements
end
注意Lua中0为true

G.Lua函数
在Lua中，函数时对语句和表达式进行抽象的主要方式。既可以用来处理一些特殊的工作，也可以用来计算一些值。Lua提供了许多的内建函数，可以很方便的在程序中调用，比如print()函数输出到控制台
Lua函数只要有两种用途
1.完成指定的任务，这种情况下函数作为调用语句使用
2，计算并返回值，这种情况下函数作为赋值语句的表达式使用
函数定义
Lua函数格式
optional_function_scope function function_name(argument1, argument2, ...)
    function_body
    return result_params_comma_separated
end
解析
1.option_function_scope 可选指定函数时全局还是局部，未设置默认是全局，如果需要设置为局部函数需要使用local关键字
2.function_name 函数名
3.argument1,... 函数参数，多个参数以逗号隔开，可以没有
4.function_body 函数体，函数中需要执行的代码语句块
5.result_params_comma_separated 函数返回值，Lua函数可以返回多个值，以逗号隔开
Lua中可以将函数作为参数传递给其他函数
多返回值
start, end = string.find("www.sunhealth.com", "sunhealth")
可变参数
Lua函数可以接受可变数目的参数，和C语言类似，在函数参数列表中使用三点...表示函数有可变参数
function add(...)
    local result = 0
    for i, v in ipairs{...}
    do
        result = result + v
    end
    return result
end
#{...}获取可变参数的长度
也可以使用select("#", ...)
select("#", ...) 可变参数的长度
select(i, ...) 可变参数的第几个参数
需要固定参数则放在变长参数之前即可

H.Lua运算符
运算符是一种特殊的符号，用于高速解释器执行特定的数学或逻辑运算
1.算术运算符
+ - * / % 取余 ^ 乘幂 -
2.关系运算符
== ~= > < >= <=
3.逻辑运算符
and or not
4.其他运算符
.. 连接两个字符串 # 一元复算符 返回字符串或表格的长度
运算符优先级
^
not -(负号的时候 不是减号) 
* /
+ -(减号的时候)
..
< > <= >= ~= ==
and 
or

I.Lua字符串
字符串或串(string)是由数字、字母、下划线组成的一串字符。
Lua语言中字符串可以使用一下三种方式来表示
1.单引号间的一串字符
2.双引号间的一串字符
3.[[和]]间的一串字符
转义字符用于表示不能直接显示的字符，比如后退键、回车键、等。比如在字符串中添加双引号可以\"
\a      响铃(BEL)                 007
\b      退格(BS)                  008
\f      换页(FF)                  012
\n      换行(LF)                  010
\r      回车(CR)                  013
\t      水平制表(HT)               009
\v      垂直制表(VT)               011
\\      反斜线字符                 092
\'      单引号                     039
\"      双引号                     034
\0      空字符(NULL)               000
\ddd    1到3位八进制数所代表的字符  三位八进制
\xhh    1到2位十六进制代表哦字符    二位十六进制
字符串操作
string.upper(string)
string.lower(string)
string.gsub(string, findString, replaceString, num)  替换字符串，将string中的findString替换为replaceString，替换num次
string.find(string, findString, [start, [end]]) 查找位置
string.reverse(string)
string.format(format, ...)
string.char(int)和string.byte(string[, int]) 将整型转为字符 和 把字符串中一个字符转为整型，不传入int则默认第一个
string.len(string)
string.rep(string, num) 重复一个字符串几遍
.. 连接字符串（运算符）
string.gmatch(string, pattern) 返回匹配的数组
string.match(string, pattern[, start]) 返回匹配的第一个字符串
字符串格式化
Lua提供了string.format()函数来生成具有特定格式的字符串，函数的第一个参数是格式，之后是对应格式中每个代号的各种数据
1.%c        接受一个数字，并将其转化为ASCII码表中对应的字符
2.%d %i     接受一个数字，并将其转化为有符号的整数格式
3.%o        接受一个数字，并将其转化为八进制数格式
4.%u        接受一个数字，并将其转化为无符号整数格式
5.%x        接受一个数字，并将其转化为十六进制数格式，小写字母
6.%X        接受一个数字，并将其转化为十六进制数格式，大写字母
7.%e        接受一个数字，并将其转化为科学计数法格式，使用小写字母e
8.%E        接受一个数字，并将其转化为科学计数法格式，使用大写字母E
9.%f        接受一个数字，并将其转换为浮点数格式
10.%g(%G)   接受一个数字，并将其转换为%e (%E 对应 %G) %f中较短的一种格式
11.%q       接受一个字符串，并将其转化为可安全被Lua编译器读入的格式
12.%s       接受一个字符串，并按照给定参数格式化改字符串
细化 参数将以如下的顺序读入
1.符号 一个+号表示后面的数字转义符号将让正数显示正号 默认情况下只有负号显示符号
2.占位符 一个0 在后面指定字符串宽度时占位使用，不填时默认占位符是空格
3.对其标识 在指定字符串宽度时，默认为右对齐，增加-好可改为左对齐
4.宽度数值
5.小数位数/字符串裁切 在宽度数值后增加的小数部分，若后接f(浮点数如%6.2f)，则设定该浮点数小数点后只保留2位，若为s则表示字符串只显示前n位
匹配模式
1. .(点)与任何字符匹配
2.%a 与任何字母匹配
3.%c 与任何控制符匹配（如\n）
4.%d 与任何数字匹配
5.%l 与任何小写字母匹配
6.%p 与任何标点匹配
7.%s 与任何空白字符匹配
8.%u 与任何大写字母匹配
9.%w 与任何字母/字母匹配
10.%x 与任何十六进制数匹配
11.%z 与任何代表0的字符匹配
12.%x 此处x是非字母非数字字符 与字符x匹配 如%%与%匹配
13.[数个字符类] 其中之一匹配
14，[^数个字符类] 不在这其中的匹配
以上字符大写表示反义匹配
* 任意个
+ 一个或多个
- 任意个 但是尽可能匹配少
? 0个或1个
%n n可以是1到9 表示匹配等n个group
%bxy 匹配从x开始到y结束 表示平衡 比如%b()表示匹配一个括号里的内容
%f[set] 

J.Lua数组
数组就是仙童数据类型的与元素按照一定顺序排列的集合，可以是一维数组和多维数组
Lua数组的索引值可以使用整数表示，数组的大小时不固定的。
一维数组
一维数组是最简单的数组，逻辑结构是线性表，一维数组可以用for循环出数组中的元素
Lua索引值是1开始的，也可以指定从0开始
还可以以负数为数组索引值
多维数组
多维数组即数组中包含数组或一维数组的索引键对应一个数组

K.Lua迭代器
迭代器(iterator)是一种对象，能够用来遍历标准模板库容器中的部分或全部元素，每个迭代器对象表示容器中确定的地址。Lua中迭代器是一种支持指针类型的解构，可以遍历集合的每一个元素
泛型for迭代器
泛型for在内部保存迭代函数，实际保存三个值：迭代函数、状态常量、控制变量
for k, v in pairs(iter) 
do
    print(k, v)
end
泛型for的执行过程
1.初始化，计算in后面表达式的值，表达式应该返回泛型for需要的三个值：迭代函数、状态常量、控制变量
2.将状态常量和控制变量作为参数调用迭代函数（注意：对于for结构来说，状态常量没有用处，仅仅在初始化的时候获取值并传递给迭代函数）
3.将迭代函数返回的值赋给变量列表
4.如果第一个返回值是nil则循环结束，否则执行循环体
5.回到第二步造次调用迭代函数
Lua迭代器分为
1.无状态迭代器
无状态的迭代器是指不保留任何状态的迭代器，因此在循环中可以利用无状态迭代器避免创建闭包话费额外的代价。
每一次迭代，迭代函数都是用两个变量（状态常量和控制变量）的值作为参数被调用，一个无状态迭代器只利用这两个值可以获取下一个元素。
2.有状态迭代器
很多情况下，迭代器需要保存多个状态信息而不是简单的状态常量和控制变量即可，最简单的方法是使用闭包，还有一种方法是将状态信息封装进table

K.Lua Table
table是Lua的一种数据结构用来帮助创建不同的数据类型，比如数组、字典等
Lua table使用关联型数组，可以用任意类型的值来做数组的索引，但这个值不能是nil
Lua table是不固定大小的，可以根据自己需要进行扩容
Lua也是通过table来解决模块(module)、包（package）和对象（Object）的
table的构造
构造器是创建和初始化表的表达式。表示Lua特有的功能强大的东西，最简单的构造函数是{},用来创建一个空表，可以直接初始化数组。
--初始化
mytable  = {}
mytable[1] ="A"
mytable = nil
-- lua垃圾回收会释放内存
table操作
table.concat(table[, sep[, start[, end]]]) js中的join
table.insert(table[, pos], value) 插入 不填写pos则在末尾
table.remove(table[, pos]) 删除 不填写pos则删除末尾
table.sort(table[, comp]) 排序

L.Lua模块和包
模块类似于一个封装库，从Lua5.1开始，Lua加入了标准的模块管理机制，可以把一些公用的代码放在一个文件里。以API接口的形式在其他地方调用，有利于代码重用和降低代码耦合
Lua的模块是由变量、函数等已知元素组成的table，因此创建一个模块很简单，就是创建一个table，然后把需要导出的常量、函数放入，最后返回这个table即可。文件代码格式如下：
-- 文件名为module.lua
-- 定义一个名为module的模块
module = {}

-- 定义一个常量
module.constant = "这是一个常量"

-- 定义一个函数
function module.func1()
    io.write("这是一个工友函数!\n")
end

-- 私有函数
local function func2()
    print("这是一个私有函数")
end

-- 私有函数暴露
function module.func3() 
    func2()
end

return module
由上可知，模块的结构是一个table的结构，因此可以像调用table里的元素那样来操作调用模块里的常量或函数
require函数
Lua提供了一个名为require的函数用来加载模块，要加载一个模块，只需调用即可
require("<模块名>")
或者
require "<模块名>"
执行require之后会返回一个有模块常量或函数组成的table，并且还会定义一个包含该table的全局变量
加载机制
对于自定义的模块，模块文件不是放在哪个文件目录下都行，函数require有自己的文件路径加载策略，会尝试从Lua文件或C程序库中加载模块
require用于搜索Lua文件的路径是存放在全局变量package.path中，当Lua启动后，会以环境变量LUA_PATH的值来初始这个变量，如果没有找到，则使用编译时定义的默认路径来初始化
可以自定义设置
export LUA_PATH = "~/lua/?.lua;;" ;隔开文件路径 ;;表示加上原来的默认路径
如果找到目标文件，则会调用package.loadfile来加载模块，否则就会去找C程序库
搜索的文件路径是从全局变量package.cpath获取的，会使用LUA_CPATH初始
C包
Lua和C包很容易结合
loadlib(path, initFunction)
此方法返回一个初始化函数，调用返回的初始化函数才是真正打开库

M.Lua元表(Metatable)
Lua table可以访问对应的key来获取value值，但是却无法对table进行操作
因此Lua提供元表)Metatable),允许改变table的行为，每个行为关联了对应的元方法
例如，使用元素可以定义Lua如何计算table的相加操作
当Lua视图相加两表时，先检查两者之一是否有元表，之后检查是否有一个__add的字段，找到则调用对应的方法
有两个函数处理元表
1.setmetatable(table, metatable) 对指定的table设置元表， 如果元表中存在__metatable键值，setmetatable会失败
2.getmetatable(table) 返回table对应的元表

mytable = {}
mymetatable = {}
setmetatable(mytable, mymetatable)

元表方法
1.__index方法
这是metatable最常用的键
当你通过键来访问table的时候，如果这个键没有对应的值，那么Lua就会寻找table对应的metatable中的__index键，如果__index包含一个表格，Lua就会在表格中查找对应的键
若果__index包含一个函数，Lua则调用此函数，table和键会作为参数传递给函数
总结
Lua查找一个表元素时的规则，其实是如下3个步骤
1.在表中查找，如果找到，返回该元素，找不到则继续
2.判断是否有元表，没找到则返回nil，有则继续
3.判断元表中是否有__index元素，如果没有返回nil,如果__index的值是一个table则继续1、2、3步骤，如果是个方法则调用返回返回值
2.__newindex元方法
用来对表更新，__index则用于对表访问
当给表的一个缺少的索引赋值，解释器就会查找__newindex元方法，如果存在则调用这个函数而不进行赋值操作
3.位表添加操作符
__add +
__sub -
__mul *
__div /
__mod %
__unm -
__concat ..
__eq ==
__lt <
__le <=
__ge >
__gt >=
4.__call元方法
table可执行
5.__tostring元方法
用于字符串

N.Lua协同程序
Lua协同程序(coroutine)与线程比较类似:拥有独立的堆栈、独立的局部变量、独立的指令指针，同时又与其他协同程序共享全局变量和其他大部分东西。
协同程序是非常强大的功能，但用起来也很复杂
线程和协同程序的区别
线程与协同程序的主要区别在于，一个具有多个线程的程序可以同时运行几个线程，而协同程序却需要彼此协作的运行。在任一指定时刻只有一个协同程序在运行，并且这个正在运行的协同程序只有在明确的被要求挂起时才会被挂起。协同程序有点类似于同步的多线程，在等待同一个线程锁的几个线程有点类似协同
基本语法
1.coroutine.create() 创建coroutine，返回coroutine，参数是一个函数，当和resume配合使用的时候就唤醒函数调用
2.coroutine.resume() 重启coroutine, 和create配合使用
3.coroutine.yield()  挂起coroutine，将coroutine设置为挂起状态，配合resume唤醒
4.coroutine.status() 查看coroutine状态 有3种状态 dead suspend running （suspend 挂起） 
5.coroutine.warp()   创建coroutine,返回一个函数，一旦进入函数则进入coroutine，和create功能重复
6.coroutine.running()返回正在运行的coroutine，返回运行coroutine的进程号

coroutine.running就可以看出来coroutine在底层实现就是一个线程
当create一个coroutine的时候就是在新线程中注册了一个事件
当使用resume触发事件的时候，create的coroutine函数被执行，当遇到yield的时候就代表挂起当前线程，等待再次resume触发事件

每一次resume会执行到协同程序的yield或return或结尾处，会获得一些返回值 第一个是协同程序操作是否成功 其后就是返回值 
