package com.daemon.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.daemon.activities.TicketOrderActivity;
import com.daemon.airticket.R;
import com.daemon.beans.TicketDetailInfo;
import com.daemon.beans.TicketInfo;
import com.daemon.utils.ImageUtil;
import com.daemon.utils.VolleyUtil;

public class TicketResultAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private ExpandableListView elv;
	private Drawable ic_xiangshang;
	private List<TicketInfo> ticketInfos;
	private List<TicketDetailInfo> ticketDetailInfos;
	
	public TicketResultAdapter(Context mContext, List<TicketInfo> ticketInfos,
			List<TicketDetailInfo> ticketDetailInfos) {
		super();
		this.mContext = mContext;
		this.ticketInfos = ticketInfos;
		this.ticketDetailInfos = ticketDetailInfos;
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_xiangxia);
		bitmap = ImageUtil.rotateBitmap(bitmap, 180);
		ic_xiangshang = new BitmapDrawable(mContext.getResources(), bitmap);
	}

	public void setExpandableListView(ExpandableListView params_elv) {
		this.elv = params_elv;
	}

	@Override
	public TicketInfo getGroup(int groupPosition) {
		return ticketInfos.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return ticketInfos.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}


	@Override
	public View getGroupView(final int groupPosition,final boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolderGroup group = null;
		if (convertView == null) {
			group = new ViewHolderGroup();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ticket_result, parent, false);
			group.btn_ticket_result_unfold = (Button) convertView
					.findViewById(R.id.btn_ticket_result_unfold);
			
			group.tv_ticket_result_takeOffTime = (TextView) convertView
					.findViewById(R.id.tv_ticket_result_takeOffTime);
			group.tv_ticket_result_landingTime = (TextView) convertView
					.findViewById(R.id.tv_ticket_result_landingTime);
			group.tv_ticket_result_price = (TextView) convertView
					.findViewById(R.id.tv_ticket_result_price);
			group.tv_ticket_result_takeOffPort = (TextView) convertView
					.findViewById(R.id.tv_ticket_result_takeOffPort);
			group.tv_ticket_result_landingPort = (TextView) convertView
					.findViewById(R.id.tv_ticket_result_landingPort);
			group.tv_ticket_result_discount = (TextView) convertView
					.findViewById(R.id.tv_ticket_result_discount);
			group.tv_ticket_result_airLine = (TextView) convertView
					.findViewById(R.id.tv_ticket_result_airLine);
			group.tv_ticket_result_amount = (TextView) convertView
					.findViewById(R.id.tv_ticket_result_amount);
			
			group.imageView_ticket_result_airLine = (ImageView) convertView
					.findViewById(R.id.imageView_ticket_result_airLine);
			VolleyUtil.loadImage(mContext,
					group.imageView_ticket_result_airLine,
					"http://avatar.csdn.net/6/6/D/1_lfdfhl.jpg"
					);
			convertView.setTag(group);
		} else {
			group = (ViewHolderGroup) convertView.getTag();
		}
		
		
		if(!ticketInfos.get(groupPosition).isExpanded){
			group.btn_ticket_result_unfold
			.setCompoundDrawablesWithIntrinsicBounds(
					null, 
					null, 
					mContext.getResources().getDrawable(R.drawable.ic_xiangxia), 
					null);
			
		}else{
			group.btn_ticket_result_unfold
			.setCompoundDrawablesWithIntrinsicBounds(
					null, 
					null, 
					ic_xiangshang, 
					null);
			
		}
		 group.tv_ticket_result_takeOffTime.setText(ticketInfos.get(groupPosition).takeOffTime);
		 group.tv_ticket_result_landingTime.setText(ticketInfos.get(groupPosition).landingTime);
		 group.tv_ticket_result_price.setText(ticketInfos.get(groupPosition).price);
		 group.tv_ticket_result_takeOffPort.setText(ticketInfos.get(groupPosition).takeOffPort);
		 group.tv_ticket_result_landingPort.setText(ticketInfos.get(groupPosition).landingPort);
		 group.tv_ticket_result_discount.setText(ticketInfos.get(groupPosition).discount);
		 group.tv_ticket_result_airLine.setText(ticketInfos.get(groupPosition).airLine);
		 group.tv_ticket_result_amount.setText(ticketInfos.get(groupPosition).amount);
		 group.btn_ticket_result_unfold.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Log.e("groupPosition", "groupPosition="+groupPosition);
					if(ticketInfos.get(groupPosition).isExpanded){
						elv.collapseGroup(groupPosition);
						ticketInfos.get(groupPosition).isExpanded = false;
					}else{
						elv.expandGroup(groupPosition);
						ticketInfos.get(groupPosition).isExpanded = true;
					}
					notifyDataSetChanged();
				}
			});
		return convertView;
	}

	@Override
	public TicketDetailInfo getChild(int groupPosition, int childPosition) {
		return ticketDetailInfos.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return ticketDetailInfos.size();
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolderChild child = null;
		if (convertView == null) {
			child = new ViewHolderChild();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_ticket_result_detail, parent, false);
			child.btn_ticket_result_details_book = (Button) convertView
					.findViewById(R.id.btn_ticket_result_details_book);
			child.tv_ticket_result_details_space = (TextView) convertView
					.findViewById(R.id.tv_ticket_result_details_space);
			child.tv_ticket_result_details_discount = (TextView) convertView
					.findViewById(R.id.tv_ticket_result_details_discount);
			child.tv_ticket_result_details_price = (TextView) convertView
					.findViewById(R.id.tv_ticket_result_details_price);

			convertView.setTag(child);
		} else {
			child = (ViewHolderChild) convertView.getTag();
		}
		
		child.btn_ticket_result_details_book
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mContext.startActivity(new Intent(mContext,
								TicketOrderActivity.class));
						Log.e("btn_ticket_result_details_book", "groupPosition="+groupPosition+",childPosition="+childPosition);
					}
				});

		 child.tv_ticket_result_details_space.setText(ticketDetailInfos.get(childPosition).spaceType);
		 child.tv_ticket_result_details_discount.setText(ticketDetailInfos.get(childPosition).discount);
		 child.tv_ticket_result_details_price.setText(ticketDetailInfos.get(childPosition).price);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	static class ViewHolderGroup {
		Button btn_ticket_result_unfold;
		TextView tv_ticket_result_takeOffTime, tv_ticket_result_landingTime,
				tv_ticket_result_price, tv_ticket_result_takeOffPort,
				tv_ticket_result_landingPort, tv_ticket_result_discount,
				tv_ticket_result_airLine, tv_ticket_result_amount;
		ImageView imageView_ticket_result_airLine;
		int position = -1;
		public void setPosition(int position){
			this.position = position;
		}
	}

	static class ViewHolderChild {
		Button btn_ticket_result_details_book;
		TextView tv_ticket_result_details_space,
				tv_ticket_result_details_discount,
				tv_ticket_result_details_price;
	}
	

}
