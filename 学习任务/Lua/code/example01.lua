
--[[
--c1、c2是建立在同一个函数上，但作用在同一个局部变量的不同实例上的两个不同的闭包。
--技术上来讲，闭包指值而不是指函数，函数仅仅是闭包的一个原型声明；
--尽管如此，在不会导致混淆的情况下我们继续使用术语函数代指闭包。
function newCounter()
    local i = 0
    return function()     -- anonymous function
       i = i + 1
        return i
    end
end
c1 = newCounter()
print(c1())  --> 1
print(c1())  --> 2
c2 = newCounter()
print(c2())  --> 1
print(c1())  --> 3
print(c2())  --> 2


-- 函数调用 sample01 --
function foo(...)
	for i = 1, arg.n do
		print("Foo:" .. arg[i])
	end
end
foo("one", "two")

-- 函数调用 sample02 --
function goo(t)
	for k,v in pairs(t) do
		print(k .. "=" .. v)
	end
end
goo({x=10, y=20})
--]]

--function contact(t)
	-- add the contact ‘t’, which is
	-- stored as a table, to a database
--	for k,v in pairs(t) do
--		print(k .. "=" .. v)
--	end
--end
--符号【【...】】是表示多行字符串的方法
--contact {
--	name = "Game Developer",
--	email = "hack@ogdev.net",
--	url = "http://www.ogdev.net",
--	quote = [[
--		There are 10 types of people
--		who can understand binary.]]
--}
--contact {
	-- some other contact
--}

----- 生成一个随机 5 位数  --------
math.randomseed(os.time())
a = ""
for i=1, 5 do
b = math.random(0, 9)
a = a .. b
end
print(a)

--[[
古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，
小兔子长到第3个月后每个月又生一对兔子，假如兔子都不死，
问10个月的兔子总数为多少？ 
程序分析： 兔子的规律为数列1,1,2,3,5,8,13,21.... 
--]]
function fibo(i)
	
	if i > 2 then return fibo(i - 1) + fibo(i - 2)
	else return 1
	end
end
print(fibo(8))

--[[
题目：判断101-200之间有多少个素数，并输出所有素数。 
判断素数的方法：用一个数分别去除2到sqrt(这个数)，如果能被整除， 则表明此数不是素数，反之是素数。
--]]
function prime()
	result = ""
	for i = 101, 200 do
		b = true
		for j = 2, math.sqrt(i) do
			if i % j == 0 then
				b = false
				break
			end
		end
		if b then result = result .. " " .. i end
	end
	return result
end
print(prime())

--[[
打印出所有的 "水仙花数 "，所谓 "水仙花数 "是指一个三位数，其各位数字立方和等于该数本身。
例如：153是一个 "水仙花数 "，因为153=1的三次方＋5的三次方＋3的三次方。 
程序分析：利用for循环控制100-999个数，每个数分解出个位，十位，百位
--]]
function shuixianhua()
	local str
	for i = 100,  999 do
		str = tostring(i)
		a = string.sub(str, 1, 1);
		b = string.sub(str, 2, 2);
		c = string.sub(str, 3, 3);
		if (tonumber(a) * tonumber(a) * tonumber(a) + 
			tonumber(b) * tonumber(b) * tonumber(b) + 
			tonumber(c) * tonumber(c) * tonumber(c)) == i 
		then
			print(i)
		end
	end
end
print("水仙花数")
shuixianhua()

--[[
一个整数，它加上100后是一个完全平方数，再加上168又是一个完全平方数，请问该数是多少？
 1.程序分析：在10万以内判断，先将该数加上100后再开方，再将该数加上268后再开方，
 如果开方后的结果满足如下条件，即是结果
--]]
function getNumber()
	for i = 1, 100000 do
		if (math.sqrt(i + 100) % 1 == 0) and (math.sqrt(i + 268) % 1 == 0) then
			print(i)
		end
	end
end
print("求数字")
getNumber()

--[[
判断某一年是否是闰年
--]]
function isLeapYear(ii)
	if (tonumber(ii) % 4 == 0) and (tonumber(ii) % 100 ~= 0)  or (tonumber(ii) % 400 == 0) then 
	return true
	else return false
	end
end
print("2012是否是闰年？", isLeapYear(2012))

--[[
求1+2!+3!+...+20!的和 1.程序分析：此程序只是把累加变成了累乘。 
--]]
print("求阶乘累加:")
function factorial(i)
	local sum
	sum = 1
	for v = 1, i do
		sum = sum * v
	end
	return sum
end
print(factorial(5))
function addFactorial()
	local sum
	sum = 0
	for i = 1, 20 do
		sum = sum + factorial(i)
	end
	return sum
end
print(addFactorial())



