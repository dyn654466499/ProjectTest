package com.daemon.beans;

import android.graphics.drawable.Drawable;

public class TicketInfo {
	/**
	 * 起飞时间
	 */
   public String takeOffTime;
	/**
	 * 降落时间
	 */
   public String landingTime;
	/**
	 * 起飞机场
	 */
   public String takeOffPort;
	/**
	 * 降落机场
	 */
   public String landingPort;
	/**
	 * 航空公司
	 */
   public String airLine;
	/**
	 * 价格
	 */
   public String price;
	/**
	 * 折扣
	 */
   public String discount;
	/**
	 * 票数
	 */
   public String amount;
   /**
    * 航空公司logo
    */
   public Drawable ariLineIcon;
   /**
    * 是否展开的标识
    */
   public boolean isExpanded;
}
