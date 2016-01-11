package com.daemon.activities;

import static com.daemon.consts.Constants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daemon.adapters.OrderInsureAdapter;
import com.daemon.adapters.OrderPassengerAdapter;
import com.daemon.airticket.R;
import com.daemon.interfaces.Commands;
import com.daemon.utils.DialogUtil;
import com.daemon.utils.ScreenUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


import static com.daemon.consts.Constants.*;
public class TicketOrderActivity extends BaseActivity{

	private Button btn_order_morePassenger,
	btn_order_destribute,
	btn_order_endorse,
	btn_order_city
	; 
	/**
	 * 保存各个乘机人的证件类型位置
	 */
	private SparseIntArray certType_positions;
    /**
     * 初始乘机人的人数
     */
	private ArrayList<Integer> passenger_list_data;
	private int passenger_item_count = 0;
	
	private int position_destribute = 0;
	private OrderPassengerAdapter passengerAdapter;
	
	private EditText et_order_phoneNum,et_order_email;
	
	private LinearLayout linearLayout_order_destribute;
	
	private ListView lv_order_passengerInfo,lv_order_insure;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_order);
		passenger_list_data = new ArrayList<Integer>();
		passenger_list_data.add(0);
		
		certType_positions = new SparseIntArray();
		certType_positions.append(passenger_list_data.size()-1, 0);
		/**
		 * 乘机人列表
		 */
		lv_order_passengerInfo = (ListView)findViewById(R.id.lv_order_passengerInfo);
		if(passengerAdapter == null)
			passengerAdapter = new OrderPassengerAdapter(this, passenger_list_data, certType_positions);
		lv_order_passengerInfo.setAdapter(passengerAdapter);
		passengerAdapter.setListView(lv_order_passengerInfo);
		

		
		
		/**
		 * --------------------------------空险列表start---------------------------------
		 */
		lv_order_insure = (ListView)findViewById(R.id.lv_order_insure);
		
		
		String[] insures = getResources().getStringArray(R.array.TypeInsure);
		List<Map<String, String>> data = new ArrayList<Map<String,String>>();
		for (String insure : insures) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_INSURE_NAME, insure);
			map.put(KEY_INSURE_PRICE, "40");
			data.add(map);
		}
		final OrderInsureAdapter insureAdapter = new OrderInsureAdapter(this, data);
		lv_order_insure.setAdapter(insureAdapter);
		
		lv_order_insure.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				insureAdapter.setPosition(position);
				insureAdapter.notifyDataSetChanged();
			}
		});
		/**
		 * --------------------------------空险列表end---------------------------------
		 */
		
		
		btn_order_morePassenger = (Button) findViewById(R.id.btn_order_morePassenger);
		btn_order_morePassenger.setOnClickListener(this);
		
		btn_order_destribute = (Button) findViewById(R.id.btn_order_destribute);
		btn_order_destribute.setOnClickListener(this);
		
		et_order_phoneNum = (EditText)findViewById(R.id.et_order_phoneNum);
		et_order_email = (EditText)findViewById(R.id.et_order_email);
		
		linearLayout_order_destribute = (LinearLayout)findViewById(R.id.linearLayout_order_destribute);
		linearLayout_order_destribute.setVisibility(View.GONE);
		
		btn_order_endorse = (Button) findViewById(R.id.btn_order_endorse);
		btn_order_endorse.setOnClickListener(this);
		
		Button btn_order_commit = (Button) findViewById(R.id.btn_order_commit);
		btn_order_commit.setOnClickListener(this);
		
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText(getString(R.string.title_order_edit));
		
		Button btn_back = (Button)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		/**
		 * 增加乘客人
		 */
		case R.id.btn_order_morePassenger:
			passenger_list_data.add(0);
			certType_positions.append(passenger_list_data.size()-1, 0);
			passengerAdapter.setType_positions(certType_positions);
			passengerAdapter.setItemCount(passenger_list_data);
			/**
			 * 在乘机人adapter刷新前，让空险列表（或其他列表）获取焦点，这样就导致乘机人的editText失去焦点，从而数据不改变。
			 */
			lv_order_insure.requestFocus();

			passengerAdapter.notifyDataSetChanged();
			break;
	
		case R.id.btn_back:
			DialogUtil.showDialog(TicketOrderActivity.this, getString(R.string.title_order_edit), "您正在填写订单，是否要退出？", new Commands() {
				
				@Override
				public void executeCommand() {
					// TODO Auto-generated method stub
					finish();
				}
			});
			break;
		/**	
		 * 选择配送方式
		 */
		case R.id.btn_order_destribute:
			intent = new Intent(TicketOrderActivity.this, SelectActivity.class);
			intent.putExtra(TYPE_KEY, TYPE_TICKET_DISTRIBUTE_KEY);
			intent.putExtra(TYPE_POSITION_KEY, position_destribute);
			startActivityForResult(intent,REQUEST_CODE_DISTRIBUTE);
			overridePendingTransition(0, 0);
			break;
		/**
		 * 退改签说明	
		 */
		case R.id.btn_order_endorse:
			startActivity(new Intent(TicketOrderActivity.this,EndorseActivity.class));
			break;
		/**
		 * 如有配送方式，选择城市
		 */
		case R.id.btn_order_city:
			intent = new Intent();
			intent.setClass(TicketOrderActivity.this, CitySearchActivity.class);
			startActivityForResult(intent, REQUEST_CODE_CITY);
			break;
		/**
		 * 提交订单	
		 */
		case R.id.btn_order_commit:
			/**
			 * 获取每个editText
			 */
			for (int i = 0; i < lv_order_passengerInfo.getChildCount(); i++) {
			     LinearLayout layout = (LinearLayout)lv_order_passengerInfo.getChildAt(i);// 获得子item的layout
			     EditText et_order_passengers = (EditText) layout.findViewById(R.id.et_order_passengers);// 从layout中获得控件,根据其id
			     EditText et_order_certNum = (EditText) layout.findViewById(R.id.et_order_certNum);//或者根据位置,在这我假设TextView在前，EditText在后
			     TextView tv_order_certType = (TextView) layout.findViewById(R.id.tv_order_cert);
			     Log.e(getTAG(), "name="+et_order_passengers.getText().toString()+
			    		 ",cert_num ="+et_order_certNum.getText().toString()+
			    		 ",certType="+tv_order_certType.getText());
			}
			break;
		
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
            DialogUtil.showDialog(TicketOrderActivity.this, getString(R.string.title_order_edit), "您正在填写订单，是否要退出？", new Commands() {
				
				@Override
				public void executeCommand() {
					// TODO Auto-generated method stub
					finish();
				}
			});
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case REQUEST_CODE_CERTIFICATE:
				int type_position = data.getIntExtra(TYPE_POSITION_KEY, 0);
				int view_position = data.getIntExtra(TYPE_VIEW_POSITION_KEY, 0);
				
				showTip("type_position="+type_position+",view_position="+view_position);
				certType_positions.put(view_position, type_position); 
				
				passengerAdapter.setCertType(view_position, type_position);
				break;
				
			case REQUEST_CODE_DISTRIBUTE:
				position_destribute = data.getIntExtra(TYPE_POSITION_KEY, 0);
				if(position_destribute == 0){
					linearLayout_order_destribute.setVisibility(View.GONE);
				}else{
					linearLayout_order_destribute.setVisibility(View.VISIBLE);
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							/**
							 * 使scrollView向底部滑动
							 */
							ScrollView sv_order = (ScrollView)findViewById(R.id.sv_order);
							sv_order.fullScroll(ScrollView.FOCUS_DOWN);
						}
					});
				}
				btn_order_destribute.setText(data.getStringExtra(TYPE_TICKET_DISTRIBUTE_KEY));
				
				btn_order_city = (Button) findViewById(R.id.btn_order_city);
				btn_order_city.setOnClickListener(this);
				break;
			
			case REQUEST_CODE_CITY:
				btn_order_city.setTextColor(Color.BLACK);
				btn_order_city.setText(data.getStringExtra(KEY_CITY));
				break;
				
			default:
				break;
			}
			
		}
	}
	
	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub
		
	}
	

}
