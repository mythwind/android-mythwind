

reserved = {
	["while"] = true, ["end"] = true,
	["function"] = true, ["local"] = true,
}

-------包 bag -------
function insert(bag, element)
	bag[element] = (bag[element] or 0) + 1
end
function remove(bag, element)
	local count = bag[element]
	bag[element] = (count and count > 1) and count - 1 or nil
end


---========= 字符串缓冲------
-- 读取文件的代码，面对极大的文件，性能开销极大
local buff = ""
for line in io.lines() do
	buff = buff .. line .. "\n"
end
-- 以下的实现取代上面的实现，可以减小开销
local t = { }
for line in io.lines() do
	t[#t + 1] = line .. "\n"
end
local s = table.concat(t)
---========= 注意：读取文件，还是io,read和*all实现



