package com.sise.dal;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.sise.entity.ScheduleEntity;
import com.sise.idal.IDALSchedule;

public class ScheduleProvider extends SQLiteBaseProvider implements
		IDALSchedule {

	protected ScheduleProvider(Context context) {
		super(context);
	}

	// 查询全部
	@Override
	public List<ScheduleEntity> selectAll() {
		getReadableDatabase(); // 打开数据库
		Cursor cursor = sqLiteDatabase.query(DBKey.Schedule.TABLE_SCHEDULE, null, 
				null, null, null, null, null);
		List<ScheduleEntity> scheduleLists = traverseSchedule(cursor);
		cursor.close();
		closeDatabase();  // 关闭数据库
		return scheduleLists;
	}
	private List<ScheduleEntity> traverseSchedule(Cursor cursor) {
		List<ScheduleEntity> scheduleList = new ArrayList<ScheduleEntity>();
		if(cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				ScheduleEntity scheduleEntity = new ScheduleEntity();
				scheduleEntity.setId(cursor.getInt(
						cursor.getColumnIndex(DBKey.Schedule.ID)));
				scheduleEntity.setExecutions(cursor.getInt(
						cursor.getColumnIndex(DBKey.Schedule.EXECUTIONS)));
				scheduleEntity.setActualExecutions(cursor.getInt(
						cursor.getColumnIndex(DBKey.Schedule.ACTUALEXECUTIONS)));
				scheduleEntity.setExecutionsResidue(cursor.getInt(
						cursor.getColumnIndex(DBKey.Schedule.EXECUTIONSRESIDUE)));
				scheduleEntity.setStartTime(cursor.getLong(
						cursor.getColumnIndex(DBKey.Schedule.STARTTIME)));
				scheduleEntity.setRecentTime(cursor.getLong(
						cursor.getColumnIndex(DBKey.Schedule.RECENTTIME)));
				scheduleEntity.setEndTime(cursor.getLong(
						cursor.getColumnIndex(DBKey.Schedule.ENDTIME)));
				scheduleEntity.setAdvanceTime(cursor.getLong(
						cursor.getColumnIndex(DBKey.Schedule.ADVANCETIME)));
				scheduleEntity.setIntervalTime(cursor.getLong(
						cursor.getColumnIndex(DBKey.Schedule.INTERVALTIME)));
				scheduleEntity.setDurationTime(cursor.getLong(
						cursor.getColumnIndex(DBKey.Schedule.DURATIONTIME)));
				scheduleEntity.setContent(cursor.getString(
						cursor.getColumnIndex(DBKey.Schedule.CONTENT)));
				scheduleEntity.setRemarks(cursor.getString(
						cursor.getColumnIndex(DBKey.Schedule.REMARKS)));
				scheduleEntity.setRemind((cursor.getInt(
						cursor.getColumnIndex(DBKey.Schedule.REMIND))==1)?true:false);
				scheduleEntity.setIntercept((cursor.getInt(
						cursor.getColumnIndex(DBKey.Schedule.INTERCEPT))==1)?true:false);
				scheduleEntity.setSMSReply((cursor.getInt(
						cursor.getColumnIndex(DBKey.Schedule.SMSREPLY))==1)?true:false);
				scheduleEntity.setEmailNotice((cursor.getInt(
						cursor.getColumnIndex(DBKey.Schedule.EMAILNOTICE))==1)?true:false);
				scheduleEntity.setWorking((cursor.getInt(
						cursor.getColumnIndex(DBKey.Schedule.WORKING))==1)?true:false);
				scheduleList.add(scheduleEntity);
			} while(cursor.moveToNext());
		}
		return scheduleList;
	}

	// 向数据库插入数据
	@Override
	public void insert(ScheduleEntity scheduleEntity) {
		getWritableDatabase();
		ContentValues values = setContentValues(scheduleEntity);
		sqLiteDatabase.insert(DBKey.Schedule.TABLE_SCHEDULE, null, values);
		closeDatabase();
	}
	private ContentValues setContentValues(ScheduleEntity scheduleEntity) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBKey.Schedule.EXECUTIONS, 
				scheduleEntity.getExecutions());
		contentValues.put(DBKey.Schedule.ACTUALEXECUTIONS, 
				scheduleEntity.getActualExecutions());
		contentValues.put(DBKey.Schedule.EXECUTIONSRESIDUE, 
				scheduleEntity.getExecutionsResidue());
		contentValues.put(DBKey.Schedule.STARTTIME, 
				scheduleEntity.getStartTime());
		contentValues.put(DBKey.Schedule.RECENTTIME, 
				scheduleEntity.getRecentTime());
		contentValues.put(DBKey.Schedule.ENDTIME, 
				scheduleEntity.getEndTime());
		contentValues.put(DBKey.Schedule.ADVANCETIME, 
				scheduleEntity.getAdvanceTime());
		contentValues.put(DBKey.Schedule.INTERVALTIME, 
				scheduleEntity.getIntervalTime());
		contentValues.put(DBKey.Schedule.DURATIONTIME, 
				scheduleEntity.getDurationTime());
		contentValues.put(DBKey.Schedule.CONTENT, 
				scheduleEntity.getContent());
		contentValues.put(DBKey.Schedule.REMARKS, 
				scheduleEntity.getRemarks());
		contentValues.put(DBKey.Schedule.REMIND, 
				scheduleEntity.isRemind());
		contentValues.put(DBKey.Schedule.INTERCEPT, 
				scheduleEntity.isIntercept());
		contentValues.put(DBKey.Schedule.SMSREPLY, 
				scheduleEntity.isSMSReply());
		contentValues.put(DBKey.Schedule.EMAILNOTICE, 
				scheduleEntity.isEmailNotice());
		contentValues.put(DBKey.Schedule.WORKING, 
				scheduleEntity.isWorking());
		return contentValues;
	}

	// 根据ID修改数据
	@Override
	public void update(ScheduleEntity scheduleEntity) {
		getWritableDatabase();
		ContentValues values = setContentValues(scheduleEntity);
		sqLiteDatabase.update(DBKey.Schedule.TABLE_SCHEDULE, values, 
				DBKey.Schedule.ID + "=" + scheduleEntity.getId(), null);
		closeDatabase();
	}

	// 根据ID删除数据
	@Override
	public void delete(ScheduleEntity scheduleEntity) {
		getWritableDatabase();
		sqLiteDatabase.delete(DBKey.Schedule.TABLE_SCHEDULE, 
				DBKey.Schedule.ID + "=" + scheduleEntity.getId(), null);
		closeDatabase();
	}

}
