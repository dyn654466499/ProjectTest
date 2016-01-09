package com.daemon.consts;

import java.util.HashMap;

public class Constants {
	
	public static final String TYPE_KEY = "类型";
	public static final String TYPE_SPACE_KEY = "舱位类型";
	public static final String TYPE_CERT_KEY = "证件类型";
	public static final String TYPE_TICKET_DISTRIBUTE_KEY = "机票配送方式";
	
	public static final String TYPE_POSITION_KEY = "舱位记录位置";
	public static final String TYPE_VIEW_POSITION_KEY = "第几个乘机人证件";
	
	public static final String TYPE_DATE_KEY = "日期";
	
	public static final String TITLE_DATE_KEY = "标题";
	
	public static final String KEY_CITY_LEAVE = "出发城市";
	public static final String KEY_CITY_ARRIVE = "到达城市";
	
	public static final String KEY_INSURE_PRICE = "保险价格";
	public static final String KEY_INSURE_NAME = "保险名称";
	/**
	 * activity的result code
	 */
	public static final int REQUEST_CODE_SPACE = 100;
	public static final int REQUEST_CODE_CERTIFICATE = 101;
	public static final int REQUEST_CODE_DATE = 102;
	public static final int REQUEST_CODE_BACK_DATE = 103;
	public static final int REQUEST_CODE_DISTRIBUTE = 104;
	public static HashMap<String, String>  HOLIDAYS = new HashMap<String, String>();
	static {
		HOLIDAYS.put("2014-1-1", "元旦");
		HOLIDAYS.put("2014-1-30", "除夕");
		HOLIDAYS.put("2014-1-31", "春节");
		HOLIDAYS.put("2014-2-14", "元宵节");
		HOLIDAYS.put("2014-3-8", "妇女节");
		HOLIDAYS.put("2014-4-1", "愚人节");
		HOLIDAYS.put("2014-4-5", "清明节");
		HOLIDAYS.put("2014-5-1", "劳动节");
		HOLIDAYS.put("2014-6-2", "端午节");
		HOLIDAYS.put("2014-8-2", "七夕");
		HOLIDAYS.put("2014-9-10", "教师节");
		HOLIDAYS.put("2014-9-19", "中秋节");
		HOLIDAYS.put("2014-10-1", "国庆节");
		HOLIDAYS.put("2014-10-2", "重阳节");
		HOLIDAYS.put("2014-11-11", "光棍节");
		HOLIDAYS.put("2014-12-24", "平安夜");
		HOLIDAYS.put("2014-12-25", "圣诞节");
	}
}
