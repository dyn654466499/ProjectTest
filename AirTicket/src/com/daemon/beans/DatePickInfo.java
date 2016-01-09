package com.daemon.beans;

import java.io.Serializable;
import java.util.Calendar;

public class DatePickInfo implements Serializable {
	public Calendar selectedDay = null;
	public Calendar startDate = null;
	//售票范围
	public int dateRange = 0;
	public String title = "出发日期";
}
