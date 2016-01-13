package com.daemon.activities;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daemon.adapters.SelectAdapter;
import com.daemon.airticket.R;
import com.daemon.beans.TicketInfo;


import static com.daemon.consts.Constants.*;
/**
 * 机票界面
 * @author 邓耀宁
 * @since 2016.1.8
 */
public class AirTicketActivity extends BaseActivity {
    /**
     * 单程按钮
     */
	private Button btn_airticket_oneWay;
    /**
     * 往返按钮
     */
	private Button btn_airticket_goAndBack;
    /**
     * 搜索按钮
     */
	private Button btn_airticket_search;
    /**
     * 出发城市按钮
     */
	private Button btn_airticket_leave;
    /**
     * 到达城市按钮
     */
	private Button btn_airticket_arrive;
    /**
     * 出发日期按钮
     */
	private Button btn_airticket_date;
    /**
     * 返回日期按钮
     */
	private Button btn_airticket_backDate;
    /**
     * 返回
     */
	private Button btn_back;
    /**
     * 舱位选择
     */
	private Button btn_airticket_spaceType;
    /**
     * 城市反向按钮
     */
	private ImageButton imageBtn_airticket_fanxiang;
	/**
	 * 返回日期的layout
	 */
	private LinearLayout linearLayout_airticket_backDate;
    /**
     * 记录舱位类型位置
     */
	private int position_spaceType = 0;
	@SuppressLint("NewApi") 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket);

		btn_airticket_oneWay = (Button) findViewById(R.id.btn_airticket_oneWay);
		btn_airticket_oneWay.setOnClickListener(this);

		btn_airticket_goAndBack = (Button) findViewById(R.id.btn_airticket_goAndBack);
		btn_airticket_goAndBack.setOnClickListener(this);

		btn_airticket_leave = (Button) findViewById(R.id.btn_airticket_leave);
		btn_airticket_leave.setOnClickListener(this);

		btn_airticket_arrive = (Button) findViewById(R.id.btn_airticket_arrive);
		btn_airticket_arrive.setOnClickListener(this);
		
		btn_airticket_date = (Button) findViewById(R.id.btn_airticket_date);
		btn_airticket_date.setOnClickListener(this);
		
		btn_airticket_backDate = (Button) findViewById(R.id.btn_airticket_backDate);
		btn_airticket_backDate.setOnClickListener(this);
		
		btn_airticket_spaceType = (Button) findViewById(R.id.btn_airticket_spaceType);
		btn_airticket_spaceType.setOnClickListener(this);
		

		btn_airticket_search = (Button) findViewById(R.id.btn_airticket_search);
		btn_airticket_search.setOnClickListener(this);

		imageBtn_airticket_fanxiang = (ImageButton) findViewById(R.id.imageBtn_airticket_fanxiang);
		imageBtn_airticket_fanxiang.setOnClickListener(this);

		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		
		linearLayout_airticket_backDate = (LinearLayout)findViewById(R.id.linearLayout_airticket_backDate);

		TextView textView_title = (TextView) findViewById(R.id.tv_title);
		textView_title.setText(getString(R.string.title_airTicket));
		
		btn_airticket_oneWay.callOnClick();
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
        /**
         * 为了先实现效果，先动态设置selector，后续再改进
         */
		case R.id.btn_airticket_oneWay:
			btn_airticket_oneWay.setSelected(true);
			btn_airticket_oneWay.setTextColor(getResources().getColor(R.color.ticket_title_color));
			
			btn_airticket_goAndBack.setSelected(false);
			btn_airticket_goAndBack.setTextColor(Color.WHITE);
			
			linearLayout_airticket_backDate.setVisibility(View.GONE);
			break;

		case R.id.btn_airticket_goAndBack:
			btn_airticket_oneWay.setSelected(false);
			btn_airticket_oneWay.setTextColor(Color.WHITE);
			
			btn_airticket_goAndBack.setSelected(true);
			btn_airticket_goAndBack.setTextColor(getResources().getColor(R.color.ticket_title_color));
			
			linearLayout_airticket_backDate.setVisibility(View.VISIBLE);
			break;
			
		case R.id.btn_airticket_leave:
			intent = new Intent();
			intent.setClass(AirTicketActivity.this, CitySearchActivity.class);
			startActivityForResult(intent, REQUEST_CODE_CITY_LEAVE);
			break;

		case R.id.btn_airticket_arrive:
			intent = new Intent();
			intent.setClass(AirTicketActivity.this, CitySearchActivity.class);
			startActivityForResult(intent, REQUEST_CODE_CITY_ARRIVE);
			break;

		case R.id.btn_airticket_date:
			intent = new Intent(AirTicketActivity.this, DatePickActivity.class);
			intent.putExtra(TITLE_DATE_KEY, getString(R.string.title_date_leave));
			startActivityForResult(intent,REQUEST_CODE_DATE);
			overridePendingTransition(0, R.anim.activity_up);
			break;
			
		case R.id.btn_airticket_backDate:
			intent = new Intent(AirTicketActivity.this, DatePickActivity.class);
			intent.putExtra(TITLE_DATE_KEY, getString(R.string.title_date_back));
			startActivityForResult(intent,REQUEST_CODE_BACK_DATE);
			overridePendingTransition(0, R.anim.activity_up);
			break;
		
		case R.id.btn_airticket_spaceType:
			intent = new Intent(AirTicketActivity.this, SelectActivity.class);
			intent.putExtra(TYPE_KEY, TYPE_SPACE_KEY);
			intent.putExtra(TYPE_POSITION_KEY, position_spaceType);
			startActivityForResult(intent,REQUEST_CODE_SPACE);
			overridePendingTransition(0, 0);
			break;
			
		case R.id.btn_airticket_search:
			String city_leave = btn_airticket_leave.getText().toString();
			String city_arrive = btn_airticket_arrive.getText().toString();
			String date_leave = btn_airticket_date.getText().toString();
			
			TicketInfo info = new TicketInfo();
			info.takeOffDate = date_leave;
            intent = new Intent(AirTicketActivity.this,TicketResultActivity.class);
            intent.putExtra(KEY_PARCELABLE, info);
            startActivity(intent);
			break;

		case R.id.btn_back:
			finish();
			break;

		case R.id.imageBtn_airticket_fanxiang:
			String temp = btn_airticket_leave.getText().toString();
			btn_airticket_leave.setText(btn_airticket_arrive.getText()
					.toString());
			btn_airticket_arrive.setText(temp);
			break;

		default:
			break;
		}
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case REQUEST_CODE_SPACE:
				btn_airticket_spaceType.setText(data.getStringExtra(TYPE_SPACE_KEY));
				position_spaceType = data.getIntExtra(TYPE_POSITION_KEY, 0);
				break;
				
			case REQUEST_CODE_DATE:
				Calendar localCalendar = Calendar.getInstance();
				long time = data.getLongExtra(TYPE_DATE_KEY,0);
				localCalendar.setTimeInMillis(time);
				setResult(RESULT_OK, getIntent().putExtra(TYPE_DATE_KEY, time));
				btn_airticket_date.setText((localCalendar.get(Calendar.MONTH) + 1) + "月"
						                + localCalendar.get(Calendar.DAY_OF_MONTH) + "日");
				break;
				
			case REQUEST_CODE_BACK_DATE:
				Calendar Calendar_back = Calendar.getInstance();
				long time_back = data.getLongExtra(TYPE_DATE_KEY,0);
				Calendar_back.setTimeInMillis(time_back);
				setResult(RESULT_OK, getIntent().putExtra(TYPE_DATE_KEY, time_back));
				btn_airticket_backDate.setText((Calendar_back.get(Calendar.MONTH) + 1) + "月"
						                + Calendar_back.get(Calendar.DAY_OF_MONTH) + "日");
				break;
				
			case REQUEST_CODE_CITY_LEAVE:
				String city_leave = data.getStringExtra(KEY_CITY);
				btn_airticket_leave.setText(city_leave);
				break;
				
			case REQUEST_CODE_CITY_ARRIVE:
				String city_arrive = data.getStringExtra(KEY_CITY);
				btn_airticket_arrive.setText(city_arrive);
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
