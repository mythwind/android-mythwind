package com.neuedu;

import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

public class Hello {
	public static void main(String[] args) {
		LuaState lua = LuaStateFactory.newLuaState();
		// 加载lua标准库,否则一些lua基本函数无法使用
		lua.openLibs();
		// doFile
		lua.LdoFile("src/test01.lua");
		// ---------------------------------------------值传递测试
		// 找到函数 sum
		lua.getField(LuaState.LUA_GLOBALSINDEX, "sum");
		// 参数1压栈
		lua.pushNumber(100);
		// 参数2压栈
		lua.pushNumber(50);
		// 调用，共2个参数1个返回值
		lua.call(2, 1);
		// 保存返回值到result中
		lua.setField(LuaState.LUA_GLOBALSINDEX, "result");
		// 读入result
		LuaObject lobj = lua.getLuaObject("result");
		// 打印结果
		System.out.println(lobj.getNumber());
		// ---------------------------------------------对象传递测试
		Value v = new Value();
		lua.getField(LuaState.LUA_GLOBALSINDEX, "test1");
		try {
			lua.pushObjectValue(v);
		} catch (LuaException e) {
			e.printStackTrace();
		}
		lua.call(1, 0);
		v.print();
	}
	
}

class Value {
	private int i;
	public void init(){
		i = 111;
	}
	public void print(){
		System.out.println(i);
	}
}
