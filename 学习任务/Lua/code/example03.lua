require "socket"
require "socket.http"

strURL = "http://www.cnblogs.com/"  -- 网址 
fileSizeLimit = 0                   -- 图片文件大小限制 
  
folderName1, folderName2 = strURL:match("%.(.-)%.com.*%/(.*)") 
  
if #folderName2 > 1 then 
    folderName = folderName1 .. '_' .. folderName2 
else
    folderName = folderName1 
end 
  
os.execute("mkdir " .. folderName) 
print("folder: " .. folderName) 
print("--------------------") 

  
b, c, h = socket.http.request(strURL) 
  
imgBegin = 1 
imgEnd = 0 
while imgBegin ~= nil do
    imgBegin, imgEnd = b:find("<img.->", imgBegin) 
    if imgBegin == nil then 
        break
    end 
  
    local str = b:sub(imgBegin, imgEnd) 
    imgBegin = imgEnd 
    str = str:match('.*src=[\"\'](.-)[\"\'].*') 
    local i = str:find("http://") 
    if i == nil then 
        str = strURL .. str 
    end 
  
    if str ~= nil then 
        local strName = str:match(".*/(.*)$") 
  
        local imgData = socket.http.request(str) 
        if imgData ~= nil then 
            if #imgData > fileSizeLimit then 
                local file = io.open(folderName .. '/' .. strName, "wb+") 
                if file ~= nil then 
                    print(strName) 
                    file:write(imgData) 
                    file:flush() 
                    file:close() 
                end 
            end 
        end 
    end 
end 
 


