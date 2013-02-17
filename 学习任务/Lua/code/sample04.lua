
--[[
x = math.pi
print(x % 1) -- pi 的小数部分
print(x - x % 0.01)  -- pi 的整数部分

local tolerance = 10
function isturnback(angle)
	angle = angle % 360
	return math.abs(angle - 180) < tolerance
end

print(isturnback(-180))   -->true


a = {}; a.x = 1; a.y = 0
b = {}; b.x = 1; b.y = 0
c = a
print(c == a) -- true
print(b == a) -- false
print(b ~= a) -- true

----- 逻辑操作 -----
-- and 第一个操作数为假，返回第一个操作数；否则返回第二个操作数
-- or 第一个操作数为真，返回第一个操作数；否则返回第二个操作数
print(4 and 5)
print(nil and 13)
print(false and 13)
print(4 or 5)
print(false or 5)
print(nil or 5)



------ 字符串连接 -----
print("hello " .. "world")
print(0 .. 1)

days = {
	"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
}
print(days[4])
print(days[#days])
print(days[#days + 1])



list = nil;
for line in io.lines() do
	list = { next=list, value=line}
end
local l = list
while l do
	print(l.value)
	l = l.next
end



----- 局部变量与全局变量 ------
x = 10
local i = 1  --程序块的局部变量
while i <= x do  -- while 循环体中的局部变量
	local x = i * 2
	print(x)
	i = i + 1
end
if i > 20 then  -- if then 中的局部变量
	local x
	x = 20
	print(x + 2)
else
	print(x)  -- 全局变量
end


------ 打印输入的第一行不为空的内容 ------
repeat
	line  = io.read()
until line ~= ""
print(line)


local sqr = x / 2
repeat
	sqr = (sqr + x / sqr) / 2
	local error = math.abs(sqr ^ 2 - x)
until error < x / 10000



-------- 泛型 for ---------
for i, v in ipairs(a) do print(v) end
for k in ipairs(t) do print(k) end
--- Lua 的基础库提供了ipairs，用于遍历数组的迭代器函数
--]]
days = {
	"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
}

revDays = {
	["Sunday"] = 1, ["Monday"] = 2, ["Tuesday"] = 3, ["Wednesday"] = 4,
	["Thursday"] = 5, ["Friday"] = 6, ["Saturday"] = 7
}
print(revDays["Tuesday"])  --> 3




