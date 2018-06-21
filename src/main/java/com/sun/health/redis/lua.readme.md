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
