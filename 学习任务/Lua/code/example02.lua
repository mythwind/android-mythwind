
--[[
输入一行数字字符（用“#”结束），字符之间以空格分隔。编程把这一行中的数字转换成一个整数。
（注意，必须先转化成一个整数，然后再将这个整数输出，而不是把非空的字符一个个输出）
     输入格式：3 2 4 5#
     输出格式：3245
     若输入字符不符合要求，则输出“Failure Input”。
--]]
function formatInput()
	print("请输入数字并且以#号结束")
	iin = io.read()
	print(iin)
end


--[[
好数问题：如果某一个四位数，恰好其中只有两个数字相同，则称为“好数”，例如1223、3464和9001就算好数，
但若有三数相同或多于一组的数字相同，如1333、5535和2244 就不算好数。请写一个可以判断是否是“好数”的程序。
如果是好数，则输出“Good Number”，若输入的数字不是四位数，例如1、12、123和12345就不是四位数，
请输出“Failure Input”，否则输出“Not Good Number”。
     输入格式：1223
     输出格式：Good Number
--]]
function gooNumber()
	iin = io.read()
	if #iin ~= 4 or type(tonumber(iin)) == nil then print("Failure Input")
	else 
		a = { }
		for i = 1, #iin do
			a[i] = string.sub(iin, i, i)
		end
		--判断相同
		
	end
end
gooNumber()


