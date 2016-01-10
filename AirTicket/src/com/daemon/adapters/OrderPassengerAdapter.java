package com.daemon.adapters;

import static com.daemon.consts.Constants.*;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
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
import android.widget.Toast;

import com.daemon.activities.SelectActivity;
import com.daemon.airticket.R;

public class OrderPassengerAdapter extends BaseAdapter{

	private Activity activity;
	private int item_sums=0;
	private SparseIntArray certType_positions;
	private String[] cert_types;
	//private ArrayList<ViewHolder> holders;
	
	public ArrayList<String> data_passenger;  
	public ArrayList<String> data_certNum; 
	private TextView tv_order_cert_copy;
	
	public OrderPassengerAdapter(final Activity activity, int count,SparseIntArray type_positions) {
		super();
		this.activity = activity;
		this.item_sums = count;
		this.certType_positions = type_positions;
		cert_types = activity.getResources().getStringArray(R.array.TypeCert);
	}

	@Override
	public int getCount() {
		return item_sums;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setItemCount(int count){
		this.item_sums = count;
	}
	
	
	public void setType_positions(SparseIntArray type_positions) {
		this.certType_positions = type_positions;
	}

	public void setCertType(int view_position,int type_position){
		//holders.get(view_position).tv_order_cert.setText(cert_types[type_position]);
		tv_order_cert_copy.setText(cert_types[type_position]);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(R.layout.item_order_passenger_info, null,false);
			holder.tv_order_cert = (TextView)convertView.findViewById(R.id.tv_order_cert);
			
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
        holder.btn_order_moreCert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * 记录点击哪位乘机人“更多证件”的位置
				 */
				tv_order_cert_copy = holder_copy.tv_order_cert;
				Intent intent = new Intent(activity, SelectActivity.class);
				intent.putExtra(TYPE_KEY, TYPE_CERT_KEY);
				/**
				 * 传递点击哪位乘机人“更多证件”的位置对于的证件类型位置。
				 */
				intent.putExtra(TYPE_POSITION_KEY, certType_positions.get(position));
				/**
				 * 传递点击哪位乘机人“更多证件”的位置。
				 */
				intent.putExtra(TYPE_VIEW_POSITION_KEY, position);
				activity.startActivityForResult(intent,REQUEST_CODE_CERTIFICATE);
				activity.overridePendingTransition(0, 0);
			}
		});
		return convertView;
	}
	

	static class ViewHolder{
		TextView tv_order_cert;
		Button btn_order_moreCert,btn_order_deleteItem;
		EditText et_order_passengers,et_order_certNum;
	}

}
