package com.daemon.activities;

import java.util.ArrayList;
import java.util.List;

import com.daemon.adapters.TicketResultAdapter;
import com.daemon.airticket.R;
import com.daemon.beans.TicketDetailInfo;
import com.daemon.beans.TicketInfo;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ScrollView;
import android.widget.TextView;

public class TicketResultActivity extends BaseActivity{
    private ExpandableListView elv_ticket_result;
    private List<TicketInfo> ticketInfos;
    private List<TicketDetailInfo> ticketDetailInfos;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_result);
		
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		
		Button btn_back = (Button)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		
		ticketInfos = new ArrayList<TicketInfo>();
		for (int i = 0; i < 5; i++) {
			TicketInfo info = new TicketInfo();
			
			ticketInfos.add(info);
		}
		
		ticketDetailInfos = new ArrayList<TicketDetailInfo>();
		for (int i = 0; i < 5; i++) {
			TicketDetailInfo info = new TicketDetailInfo();
			ticketDetailInfos.add(info);
		}
		
		elv_ticket_result = (ExpandableListView)findViewById(R.id.elv_ticket_result);
		final TicketResultAdapter adapter = new TicketResultAdapter(this,ticketInfos,ticketDetailInfos);
		adapter.setExpandableListView(elv_ticket_result);
		elv_ticket_result.setAdapter(adapter);
		elv_ticket_result.setOnGroupExpandListener(new OnGroupExpandListener() {    
		    
	        @Override    
	        public void onGroupExpand(int groupPosition) {    
//	            for (int i = 0; i < adapter.getGroupCount(); i++) {    
//	                if (groupPosition != i) {    
//	                	elv_ticket_result.collapseGroup(i);
//	                }    
//	            }    
	            //adapter.setExpanded_position(groupPosition);
	        }    
	    
	    }); 
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
		switch (v.getId()) {
		case R.id.btn_back:
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
