package com.neuedu;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

public class Test {
	public static void main(String[] args) {
		LuaState L = LuaStateFactory.newLuaState();
		// 加载lua标准库,否则一些lua基本函数无法使用
		L.openLibs();
		System.out.println("这里是Java程序调用Lua脚本");
		// 加载脚本MathStatist.lua,并执行
		L.LdoFile("src/MathStatist.lua");
	}
}
