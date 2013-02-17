
--[[
a = "one string"
b = string.gsub(a, "one", "another") -- 修改字符串
print(a)
print(b)
print()


print("one line\nnext line\n\"in quotes\", 'in quotes'")

print('a simple way \'\\\'')
--]]

-- lua 自动转换功能
--[[
print("10" + 1)
print("10 + 1")
print("-5.3e-10" * "2")


line = io.read()  -- 读取一行
n = tonumber(line);
if n == nil then
	error(line .. " is not a valid number")
else
	print(n * 2)
end



a = "hello"
print(#a)  -- # 输出字符串长度，即长度操作符
print(#"good\0byte")


--  创建一个 table，并且将引用存储到a
a = {}
k = "x"
a[k] = 10  -- key="x" value = 10
a[20] = "great"  -- key=20 value = "great"
print(a["x"]); --> 10
k = 20
print(a[k]);  --> great
a["x"] = a["x"] + 1;
print(a["x"]);  --> 11



--  创建一个 table，并且将引用存储到a
a = {}
--  创建1000个新条目
for i = 1, 1000
	do a[i] = i * 2
end
print(a[9])  --> 18
--a["x"] = 10
a.x = 10  -- a["x"] = 10 等同于 a.x = 10
print(a["x"]) --> 10
print(a["y"]) --> nil



a = {}  -- 以 1 为索引起始值
a[10000] = 2;
print(table.maxn(a))  --> 10000
print(a[10000])
print(#a)
for i = 1, 1000
	do a[i] = i * 2
end
print(#a) --> 1000
print(#a + 1)  --> 1001



-------- 10、"10"、"+10" 是不同的索引 -------
i = 10; j = "10"; k = "+10"
a = {}
a[i] = "one value"
a[j] = "another value"
a[k] = "yet another value"
print(a[i])
print(a[j])
print(a[k])
print(a[tonumber(j)])
print(a[tonumber(k)])

--]]
