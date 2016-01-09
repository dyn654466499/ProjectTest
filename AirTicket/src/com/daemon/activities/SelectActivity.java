package com.daemon.activities;

import com.daemon.adapters.SelectAdapter;
import com.daemon.airticket.R;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import static com.daemon.consts.Constants.*;

public class SelectActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);
		String type = getIntent().getStringExtra(TYPE_KEY);
		LinearLayout linearLayout_select_blank = (LinearLayout)findViewById(R.id.linearLayout_select_blank);
		linearLayout_select_blank.setOnClickListener(this);
		/**
		 * 先实现功能，后续可抽取公共的代码进行优化，减少代码量。
		 */
		
		/**
		 * 如果是舱位选择
		 */
		if (TYPE_SPACE_KEY.equals(type)) {
			TextView textView_select_title = (TextView)findViewById(R.id.tv_select_title);
			textView_select_title.setText(getString(R.string.title_select_space));
			
			int position_spaceType = getIntent().getExtras().getInt(
					TYPE_POSITION_KEY, 0);
			final SelectAdapter adapter = new SelectAdapter(this,
					R.layout.item_select, getResources().getStringArray(R.array.TypeSapce));
			ListView listView = (ListView) findViewById(R.id.listView_select);
			adapter.setSelectedPosition(position_spaceType);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					adapter.setSelectedPosition(position);
					adapter.notifyDataSetChanged();
					Intent intent = new Intent();
					intent.putExtra(TYPE_SPACE_KEY, adapter.getItem(position));
					intent.putExtra(TYPE_POSITION_KEY, position);
					setResult(RESULT_OK, intent);
					finish();
				}
			});
		}
        /**
         * 如果是证件选择
         */
		if (TYPE_CERT_KEY.equals(type)) {
			TextView textView_select_title = (TextView)findViewById(R.id.tv_select_title);
			textView_select_title.setText(getString(R.string.title_select_certificate));
			
			int position_spaceType = getIntent().getExtras().getInt(
					TYPE_POSITION_KEY, 0);
			final int view_position = getIntent().getIntExtra(TYPE_VIEW_POSITION_KEY, 0);
			final SelectAdapter adapter = new SelectAdapter(this,
					R.layout.item_select, getResources().getStringArray(R.array.TypeCert));
			ListView listView = (ListView) findViewById(R.id.listView_select);
			adapter.setSelectedPosition(position_spaceType);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					adapter.setSelectedPosition(position);
					adapter.notifyDataSetChanged();
					Intent intent = new Intent();
					/**
					 * 证件号名字
					 */
					intent.putExtra(TYPE_CERT_KEY, adapter.getItem(position));
					/**
					 * 记录证件类型位置
					 */
					intent.putExtra(TYPE_POSITION_KEY, position);
					/**
					 * 记录点击哪位乘机人的位置
					 */
					intent.putExtra(TYPE_VIEW_POSITION_KEY, view_position);
					setResult(RESULT_OK, intent);
					finish();
				}
			});
		}
		/**
		 * 如果是机票配送方式
		 */
		if (TYPE_TICKET_DISTRIBUTE_KEY.equals(type)) {
			TextView textView_select_title = (TextView)findViewById(R.id.tv_select_title);
			textView_select_title.setText(getString(R.string.title_select_ticket_distribute));
			
			int position_spaceType = getIntent().getExtras().getInt(TYPE_POSITION_KEY, 0);
			final SelectAdapter adapter = new SelectAdapter(this,
					R.layout.item_select, getResources().getStringArray(R.array.TypeTicketDestribute));
			ListView listView = (ListView) findViewById(R.id.listView_select);
			adapter.setSelectedPosition(position_spaceType);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					adapter.setSelectedPosition(position);
					adapter.notifyDataSetChanged();
					Intent intent = new Intent();
					intent.putExtra(TYPE_TICKET_DISTRIBUTE_KEY, adapter.getItem(position));
					intent.putExtra(TYPE_POSITION_KEY, position);
					setResult(RESULT_OK, intent);
					finish();
				}
			});
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(0, 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
        switch (v.getId()) {
		case R.id.linearLayout_select_blank:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub

	}

}
