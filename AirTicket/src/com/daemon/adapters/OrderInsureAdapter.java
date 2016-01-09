package com.daemon.adapters;

import static com.daemon.consts.Constants.KEY_INSURE_NAME;
import static com.daemon.consts.Constants.KEY_INSURE_PRICE;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daemon.airticket.R;

public class OrderInsureAdapter extends BaseAdapter{
    private List<? extends Map<String, ?>> data;
	
	public OrderInsureAdapter(Context context,List<? extends Map<String, ?>> data) {
		// TODO Auto-generated constructor stub
		mContext = context;
		this.data = data;
	}

	private Context mContext;
    private int position = 0;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Map<String, ?> getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order_insure, null,false);
			holder.tv_order_insure_name = (TextView)convertView.findViewById(R.id.tv_order_insure);
			holder.tv_order_insure_price = (TextView)convertView.findViewById(R.id.tv_order_insure_price);
			holder.tv_order_insure_item = (TextView)convertView.findViewById(R.id.tv_order_insure_item);
			holder.imageBtn_order_insureDesc = (ImageButton)convertView.findViewById(R.id.imageBtn_order_insureDesc);
			holder.imageBtn_order_insureDesc.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/**
					 * 弹出空险说明
					 */
					View view = LayoutInflater.from(mContext).inflate(R.layout.layout_insure_desc, null,false);
					TextView tv_insure_desc_title = (TextView)view.findViewById(R.id.tv_insure_desc_title);
					tv_insure_desc_title.setText((String)data.get(position).get(KEY_INSURE_NAME));
					
					TextView tv_insure_desc = (TextView)view.findViewById(R.id.tv_insure_desc);
					
					new AlertDialog.Builder(mContext).setView(view).create().show();
				}
			});
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.tv_order_insure_name.setText((String)data.get(position).get(KEY_INSURE_NAME));
		holder.tv_order_insure_price.setText("￥"+(String)data.get(position).get(KEY_INSURE_PRICE));
		if(this.position == position){
			holder.tv_order_insure_item.setCompoundDrawablesWithIntrinsicBounds(null, 
					null, 
					mContext.getResources().getDrawable(R.drawable.ic_duigou_lan), 
					null);
		}else{
			holder.tv_order_insure_item.setCompoundDrawablesWithIntrinsicBounds(null, 
					null, 
					null, 
					null);
		}
		return convertView;
	}
	
	static class ViewHolder{
		TextView tv_order_insure_name,tv_order_insure_price,tv_order_insure_item;
		ImageButton imageBtn_order_insureDesc;
	}

}
