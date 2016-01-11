package com.daemon.activities;

import com.daemon.adapters.TicketResultAdapter;
import com.daemon.airticket.R;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class TicketResultActivity extends BaseActivity{
    private ExpandableListView elv_ticket_result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_result);
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		
		
		elv_ticket_result = (ExpandableListView)findViewById(R.id.elv_ticket_result);
		elv_ticket_result.setAdapter(new TicketResultAdapter(this,elv_ticket_result));
		
		/**
		 * 此处让title获取焦点，这样scrollview就滚到顶部了
		 */
		tv_title.setFocusable(true);
		tv_title.setFocusableInTouchMode(true);
		tv_title.requestFocus();
	}

	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

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
