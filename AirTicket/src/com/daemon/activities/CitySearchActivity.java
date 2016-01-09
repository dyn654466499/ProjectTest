package com.daemon.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.daemon.airticket.R;

public class CitySearchActivity extends BaseActivity {

	private String[] cityNames,cityTags;
	
	private List<String> listCityNames = new ArrayList<String>();  
	private List<String> listCityTags = new ArrayList<String>();  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_list);
		cityNames = getResources().getStringArray(R.array.CityNames);
		cityTags = getResources().getStringArray(R.array.CityTags); 
		getData();
		
		ListView listView_citylist_names = (ListView)findViewById(R.id.listView_citylist_names);
		ListView listView_citylist_tags = (ListView)findViewById(R.id.listView_citylist_tags);
		
		
	}

	public void getData() {  
	    // ///////////////////////////  
	    int listsize[] = { 0, 19, 5, 6, 9, 7, 1, 3, 6, 13, 13, 5, 8, 5, 7, 7,  
	            10, 6, 11, 7, 11, 9 };  
	  
	    for (int j = 1; j < listsize.length; j++) {  
	        listCityNames.add(cityTags[j - 1]);  
	        listCityTags.add(cityTags[j - 1]);  
	        listsize[j] = listsize[j - 1] + listsize[j];  
	        for (int i = listsize[j - 1]; i < listsize[j]; i++) {  
	            listCityNames.add(cityNames[i]);  
	        }  
	    }  
	}  
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub
		
	}
}
