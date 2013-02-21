package com.sise.dal;

import android.content.Context;

import com.sise.idal.IDALFactory;
import com.sise.idal.IDALSchedule;

public class DALFactory implements IDALFactory {

	public DALFactory() {
	}
	
	// ScheduleProvider，提供数据库增删改查的方法
	@Override
	public IDALSchedule createScheduleProvider(Context context) {
		return new ScheduleProvider(context);
	}

}
