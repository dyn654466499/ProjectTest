package com.daemon.adapters;

import static com.daemon.consts.Constants.REQUEST_CODE_CERTIFICATE;
import static com.daemon.consts.Constants.TYPE_CERT_KEY;
import static com.daemon.consts.Constants.TYPE_KEY;
import static com.daemon.consts.Constants.TYPE_POSITION_KEY;
import static com.daemon.consts.Constants.TYPE_VIEW_POSITION_KEY;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.daemon.activities.SelectActivity;
import com.daemon.airticket.R;
import com.daemon.beans.PassengerInfo;

public class OrderPassengerAdapter extends BaseAdapter{

	private Activity activity;
	private ArrayList<PassengerInfo> infos;
	
	public OrderPassengerAdapter(final Activity activity, ArrayList<PassengerInfo> infos,SparseIntArray type_positions) {
		super();
		this.activity = activity;
		this.infos = infos;
	}

	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public PassengerInfo getItem(int position) {
		// TODO Auto-generated method stub
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(R.layout.item_order_passenger_info, null,false);
			holder.tv_order_certType = (TextView)convertView.findViewById(R.id.tv_order_certType);
			
			holder.et_order_passengers = (EditText)convertView.findViewById(R.id.et_order_passengers);
			holder.et_order_certNum = (EditText)convertView.findViewById(R.id.et_order_certNum);

			holder.btn_order_moreCert = (Button)convertView.findViewById(R.id.btn_order_moreCert);
            holder.btn_order_deleteItem = (Button)convertView.findViewById(R.id.btn_order_deleteItem);
			
			convertView.setTag(holder);
			//Log.e("if", "position = "+position);

		}else{
			holder = (ViewHolder)convertView.getTag();
			//Log.e("else", "position = "+position);
		}
		final ViewHolder holder_copy = holder;
		
        holder.et_order_certNum.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(holder_copy.et_order_certNum.getTag()!=null){
				int position = (Integer)holder_copy.et_order_certNum.getTag();
				infos.get(position).certNum = s.toString();
			    }
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
		});

		holder.et_order_passengers.setText(infos.get(position).name);
		holder.et_order_passengers.setTag(position);
		
		holder.et_order_certNum.setText(infos.get(position).certNum);
		holder.et_order_certNum.setTag(position);
		
		holder.et_order_passengers.addTextChangedListener(new Test(holder));
		
		holder.tv_order_certType.setText(infos.get(position).certType);
		
		/**
		 * 下面两个button的位置一定要放到上面的if else外面
		 */
        holder.btn_order_moreCert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * 记录点击哪位乘机人“更多证件”的位置
				 */
				Intent intent = new Intent(activity, SelectActivity.class);
				intent.putExtra(TYPE_KEY, TYPE_CERT_KEY);
				/**
				 * 传递点击哪位乘机人“更多证件”的位置对于的证件类型位置。
				 */
				intent.putExtra(TYPE_POSITION_KEY, infos.get(position).cert_position);
				/**
				 * 传递点击哪位乘机人“更多证件”的位置。
				 */
				intent.putExtra(TYPE_VIEW_POSITION_KEY, position);
				activity.startActivityForResult(intent,REQUEST_CODE_CERTIFICATE);
				activity.overridePendingTransition(0, 0);
			}
		});
        /**
         * 删除
         */
        holder.btn_order_deleteItem.setText(String.valueOf(position+1));
        holder.btn_order_deleteItem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				infos.remove(position);
				Log.e("remove", "position = "+position);
				notifyDataSetChanged();
			}
		});
        
		holder.et_order_passengers.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					holder_copy.btn_order_deleteItem.setTextColor(activity.getResources().getColor(R.color.title_color));
				}else{
					holder_copy.btn_order_deleteItem.setTextColor(activity.getResources().getColor(R.color.font_gray));
				}
			}
		});
        holder.et_order_certNum.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					holder_copy.btn_order_deleteItem.setTextColor(activity.getResources().getColor(R.color.title_color));
				}else{
					holder_copy.btn_order_deleteItem.setTextColor(activity.getResources().getColor(R.color.font_gray));
				}
			}
		});
		return convertView;
	}
	

	static class ViewHolder{
		TextView tv_order_certType;
		Button btn_order_moreCert;
		Button btn_order_deleteItem;
		EditText et_order_passengers,et_order_certNum;
		
	}
	
	class Test implements TextWatcher{
		private ViewHolder holder;
		
		public Test(ViewHolder holder) {
			super();
			this.holder = holder;
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if(holder.et_order_passengers.getTag()!=null){
			int position = (Integer)holder.et_order_passengers.getTag();
			infos.get(position).name = s.toString();
			}
		}
	}

}
