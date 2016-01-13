package com.daemon.beans;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class TicketInfo implements Parcelable {
	
	/**
	 * 起飞日期
	 */
	public String takeOffDate;
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
	 * 舱位类型
	 */
	public String spaceType;
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
	/**
	 * 燃油费
	 */
	public String oilPrice;
	/**
	 * 机场建设费
	 */
	public String airPortBuildPrice;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
        dest.writeString(takeOffDate);
        dest.writeString(takeOffTime);
        dest.writeString(takeOffPort);
        dest.writeString(landingTime);
        dest.writeString(landingPort);
        dest.writeString(spaceType);
        dest.writeString(price);
        dest.writeString(oilPrice);
        dest.writeString(airPortBuildPrice);
        dest.writeString(discount);
        dest.writeString(amount);
        dest.writeString(airLine);
        
	}
	
	public static final Parcelable.Creator<TicketInfo> CREATOR = new Creator<TicketInfo>() {
		
		@Override
		public TicketInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new TicketInfo[size];
		}
		
		@Override
		public TicketInfo createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			TicketInfo info = new TicketInfo();
			info.setParcel(source);
			return info;
		}
	}; 
	
	private void setParcel(Parcel in){
		this.takeOffDate = in.readString();
		this.takeOffTime = in.readString();
		this.takeOffPort = in.readString();
		this.landingTime = in.readString();
		this.landingPort = in.readString();
		this.spaceType = in.readString();
		this.price = in.readString();
		this.oilPrice = in.readString();
		this.airPortBuildPrice = in.readString();
		this.discount = in.readString();
		this.amount = in.readString();
		this.airLine = in.readString();
	}
}
