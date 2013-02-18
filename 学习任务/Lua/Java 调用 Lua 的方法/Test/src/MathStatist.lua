
--[[
请编写一个简单的统计库(Statist).实现对一个数字型数组进行以下统计功能:
a) 平均数（Avg）
b) 计数(Count)
c) 求和(Sum)
d) 最大值(Max)
e) 最小值(Min)
f) 方差(Varp)
g) 标准差(StdDevP) 提示：
标准差是方差开方后的结果(即方差的算术平方根) 
假设这组数据的平均值是m 方差公式s^2=1/n[(x1-m)^2+(x2-m)^2+...+(xn-m)^2]
--]]

arr = { 10, 20, 15, 40, 25, 35, 30 }
--c 求和
function arrSum(arr)
	local sum = 0
	for i, v in pairs(arr) do
		sum = sum + v
	end
	return sum
end

-- 计数
function arrCount(arr)
	local count = 0
	for i, v in pairs(arr) do
		count = count + 1
	end
	return count
end

-- 求平均数
function arrAvg(arr)
	return arrSum(arr) / arrCount(arr)
end

-- 最大值
function arrMax(arr)
	local mi = 1  --最大的索引值
	local m = arr[mi]  --最大值
	for i, v in pairs(arr) do
		if v > m then
		mi = i
		m = v
		end
	end
	return m, mi
end

-- 最小值
function arrMin(arr)
	local mi = 1  --最小的索引值
	local m = arr[mi]  --最小值
	for i, v in pairs(arr) do
		if v < m then
		mi = i
		m = v
		end
	end
	return m, mi
end

-- 方差 Varp 假设这组数据的平均值是m 方差公式s^2=1/n[(x1-m)^2+(x2-m)^2+...+(xn-m)^2]
function arrVarp(arr)
	local m
	local n
	local temp = 0
	m = arrAvg(arr)
	n = arrCount(arr)
	for i, v in pairs(arr) do
		temp = temp + (v - m) ^ 2
	end
	return temp / n
end

-- 标准差 StdDevP
function arrStdDevP(arr)
	local varp
	local v
	varp = arrVarp(arr)
	for var = 1, varp do
		if var * var == varp then
			v = var
		break
		end
	end
	return v
end

print("数组 { 10, 20, 15, 40, 25, 35, 30 } 的和 sum = " .. arrSum(arr))
print("数组 { 10, 20, 15, 40, 25, 35, 30 } 的长度 count = " .. arrCount(arr))
print("数组 { 10, 20, 15, 40, 25, 35, 30 } 的平均数 avg = " .. arrAvg(arr))
a, b = arrMax(arr)
print("数组 { 10, 20, 15, 40, 25, 35, 30 } 的最大值 max = " .. a .. ", 索引值 maxIndex = " .. b)
a, b = arrMin(arr)
print("数组 { 10, 20, 15, 40, 25, 35, 30 } 的最小值 min = " .. a .. ", 索引值 minIndex = " .. b)
print("数组 { 10, 20, 15, 40, 25, 35, 30 } 的方差 varp = " .. arrVarp(arr))
print("数组 { 10, 20, 15, 40, 25, 35, 30 } 的标准差 StdDevP = " .. arrStdDevP(arr))


