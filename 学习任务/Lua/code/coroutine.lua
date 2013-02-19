
--[[
---- 一个协同程序处于4种不同的状态：suspended、running、normal、dead----
co = coroutine.create(function() print("hello") end)
print(co)
print("Status:" .. coroutine.status(co))
coroutine.resume(co)
print("Status:" .. coroutine.status(co))

print()
function f()
	for i = 1,  10 do
		print(i)
		coroutine.yield()
	end
end
co = coroutine.create(f)
coroutine.resume(co) -->1
coroutine.resume(co) -->2
coroutine.resume(co) -->3


co = coroutine.create(function() print("hello") end)
coroutine.resume(co) -->hello
print(coroutine.status(co)) -->dead
print(coroutine.resume(co)) -->alse	cannot resume dead coroutine

--]]

co = coroutine.create(function() 
			for i = 1, 10 do
				print("co", i)
				coroutine.yield()
			end
		 end)
coroutine.resume(co)  --> co	1
print(coroutine.status(co))  --> suspended
-- suspended 发生的活动都发生在yield调用中
--协同程序A调用协同程序B，A处于特殊状态，称为正常态

co = coroutine.create(function(a, b, c) 
			print("co", a, b, c)
		end)
coroutine.resume(co, 1, 2, 3)

co = coroutine.create(function(a, b) 
			coroutine.yield(a + b, a - b)
		end)
print(coroutine.resume(co, 25, 10))  --> true	35	15


--[[ 
	生产者/消费者问题
--]]
-- 生产者
function producer()
	return coroutine.create(
		function()
			while true do
				local x = io.read() -- 产生新的值
				send(x)  --发送给消费者
			end
		end)
end
-- 消费者
function consumer(prod)
	while true do
		local x = receive(prod) -- 从生产者接收值
		io.write(x, "\n")  --消费新的值
	end
end
-- 管道
function filter(prod)
	return coroutine.create(function()
		for line = 1, math.huge do
			local x = receive(prod)  --获取新值
			x = string.format("%5d%s", line, x)
			send(x)  -- 将新值发送给消费者
		end
	end)
end
--发送
function send(x)
	coroutine.yield(x)
end
function receive(prod)
	local status, value = coroutine.resume(prod)
	return value
end

consumer(filter(producer()))

