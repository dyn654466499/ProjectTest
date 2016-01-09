package com.daemon.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.daemon.airticket.R;

public class CityListAdapter extends ArrayAdapter<String>{  
    
    private List<String> cityTags = null;  
    public CityListAdapter(Context context, List<String> cityNames, List<String> cityTags) {  
        super(context, 0, cityNames);  
        this.cityTags = cityTags;  
    }  
       
    @Override  
    public boolean isEnabled(int position) {  
        if(cityTags.contains(getItem(position))){  
            return false;  
        }  
        return super.isEnabled(position);  
    }  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
		View view;
		if (convertView == null) {
			if (cityTags.contains(getItem(position))) {
				view = LayoutInflater.from(getContext()).inflate(
						R.layout.item_citylist_tag, null);
			} else {
				view = LayoutInflater.from(getContext()).inflate(
						R.layout.item_citylist_city, null);
			}
		} else {
			view = convertView;
		}

		TextView textView = (TextView) view
				.findViewById(R.id.letterTextView);
		textView.setText(getItem(position));
		return view;
    }  
}  
