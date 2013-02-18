
------------ 迭代器与泛型 -----------
--[[

t = { 10, 20, 30 }
for element in values(t)
	print(element)
end

-- values 就是一个工厂。
function values(t)
	local i = 0
	return function() i = i + 1; return t[i] end
end

--]]





function goo(t)
	for k,v in t do
		print(k .. "=" .. v)
	end
end
goo({x=10, y=20})
