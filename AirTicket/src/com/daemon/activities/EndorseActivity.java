package com.daemon.activities;

import com.daemon.airticket.R;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndorseActivity extends BaseActivity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_endorse);
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText(getString(R.string.title_endorseOrBackDesc));
		
		Button btn_back = (Button)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
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
