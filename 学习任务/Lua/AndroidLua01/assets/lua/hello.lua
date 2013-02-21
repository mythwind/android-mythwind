
--文件名：hello.lua 
--一个简单的函数，计算两个数相加  
function plus(a, b)
  return a + b;
end 
  
  --一个稍微不太简单的简单的函数，
  --参数是一个Java对象，函数内容是调用Java对象value的函数
 function heihei(value) 
  value:inc(); 
  value:inc();  
  return value;
  end
