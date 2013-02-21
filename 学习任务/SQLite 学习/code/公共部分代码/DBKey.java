package com.sise.dal;

public class DBKey {
	
	/**
	 *  日程存放的数据库
	 * @author Neu
	 */
	public class Schedule {
		public static final String TABLE_SCHEDULE = "schedule";
		public static final String ID = "id";
		public static final String EXECUTIONS = "executions";
		public static final String ACTUALEXECUTIONS = "actualExecutions";
		public static final String EXECUTIONSRESIDUE = "executionResidue";
		public static final String CONTENT = "content";
		public static final String REMARKS = "remarks";
		public static final String STARTTIME = "startTime";
		public static final String RECENTTIME = "recentTime";
		public static final String ENDTIME = "endTime";
		public static final String ADVANCETIME = "advanceTime";
		public static final String DURATIONTIME = "durationTime";
		public static final String INTERVALTIME = "intervalTime";
		public static final String REMIND = "remind";
		public static final String INTERCEPT = "intercept";
		public static final String SMSREPLY = "SMSReply";
		public static final String EMAILNOTICE = "EmailNotice";
		public static final String WORKING = "wroking";
		
		//  创建数据库
		public static final String CREATETABLE_SCHEDULE = "create table "
			+ TABLE_SCHEDULE + " (" 
			+ ID + " integer primary key autoincrement, "
			+ EXECUTIONS + " text, "
			+ ACTUALEXECUTIONS + " text, "
			+ EXECUTIONSRESIDUE + " text, "
			+ CONTENT + " text, "
			+ REMARKS + " text, "
			+ STARTTIME + " text, "
			+ RECENTTIME + " text, "
			+ ENDTIME + " text, "
			+ ADVANCETIME + " text, "
			+ DURATIONTIME + " text, "
			+ INTERVALTIME + " text, "
			+ REMIND + " integer, "
			+ INTERCEPT + " integer, "
			+ SMSREPLY + " integer, " 
			+ EMAILNOTICE + " integer, "
			+ WORKING + " integer"
			+ ");";
	}
	
}

