package com.daemon.beans;

/**
 * 城市属性实体类
 * @author sy
 *
 */
public class CityModel
{
	private String CityName; //城市名字
	private String NameSort; //城市首字母
	private String AllName; 
	private boolean isItem;
	public String getCityName()
	{
		return CityName;
	}

	public void setCityName(String cityName)
	{
		CityName = cityName;
	}

	public String getNameSort()
	{
		return NameSort;
	}

	public void setNameSort(String nameSort)
	{
		NameSort = nameSort;
	}

	public String getAllName() {
		return AllName;
	}

	public void setAllName(String allName) {
		AllName = allName;
	}

	public boolean isItem() {
		return isItem;
	}

	public void setItem(boolean isItem) {
		this.isItem = isItem;
	}

}
