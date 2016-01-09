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
import com.daemon.utils.DialogUtils;
import com.daemon.utils.ScreenUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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

	private Button btn_order_morePassenger,btn_order_destribute; 
	/**
	 * 保存各个乘机人的证件类型位置
	 */
	private SparseIntArray certType_positions;
    /**
     * 初始乘机人的人数
     */
	private int item_sums = 1;
	
	private int position_destribute = 0;
	private OrderPassengerAdapter passengerAdapter;
	
	private EditText et_order_phoneNum,et_order_email;
	
	private LinearLayout linearLayout_order_destribute;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_order);
		
		certType_positions = new SparseIntArray();
		certType_positions.append(item_sums-1, 0);
		/**
		 * 乘机人列表
		 */
		ListView lv_order_passengerInfo = (ListView)findViewById(R.id.lv_order_passengerInfo);
		if(passengerAdapter == null)passengerAdapter = new OrderPassengerAdapter(this, item_sums, certType_positions);
		lv_order_passengerInfo.setAdapter(passengerAdapter);
		
		/**
		 * --------------------------------空险列表start---------------------------------
		 */
		ListView lv_order_insure = (ListView)findViewById(R.id.lv_order_insure);
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
		case R.id.btn_order_morePassenger:
			item_sums++;
			certType_positions.append(item_sums-1, 0);
			passengerAdapter.setType_positions(certType_positions);
			passengerAdapter.setItemCount(item_sums);
			passengerAdapter.notifyDataSetChanged();
			break;
			
		case R.id.btn_back:
			DialogUtils.showDialog(TicketOrderActivity.this, getString(R.string.title_order_edit), "您正在填写订单，是否要退出？", new Commands() {
				
				@Override
				public void executeCommand() {
					// TODO Auto-generated method stub
					finish();
				}
			});
			break;
			
		case R.id.btn_order_destribute:
			intent = new Intent(TicketOrderActivity.this, SelectActivity.class);
			intent.putExtra(TYPE_KEY, TYPE_TICKET_DISTRIBUTE_KEY);
			intent.putExtra(TYPE_POSITION_KEY, position_destribute);
			startActivityForResult(intent,REQUEST_CODE_DISTRIBUTE);
			overridePendingTransition(0, 0);
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
            DialogUtils.showDialog(TicketOrderActivity.this, getString(R.string.title_order_edit), "您正在填写订单，是否要退出？", new Commands() {
				
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
				
				//showTip("type_position="+type_position+",view_position="+view_position);
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
							ScrollView sv_order = (ScrollView)findViewById(R.id.sv_order);
							sv_order.fullScroll(ScrollView.FOCUS_DOWN);
						}
					});
				}
				btn_order_destribute.setText(data.getStringExtra(TYPE_TICKET_DISTRIBUTE_KEY));
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
