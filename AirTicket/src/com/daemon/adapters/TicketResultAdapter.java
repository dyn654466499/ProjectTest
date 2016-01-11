package com.daemon.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.daemon.activities.TicketOrderActivity;
import com.daemon.airticket.R;
import com.daemon.utils.ImageUtil;

public class TicketResultAdapter extends BaseExpandableListAdapter{
        private Context mContext;
	    private ExpandableListView elv;
	    private Drawable ic_xiangshang;
	    
	    public TicketResultAdapter(Context mContext,ExpandableListView elv) {
			super();
			this.mContext = mContext;
			this.elv = elv;
			Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_xiangxia);
			bitmap = ImageUtil.rotateBitmap(bitmap, 180);
			ic_xiangshang = new BitmapDrawable(mContext.getResources(), bitmap);						
		}

		// Sample data set.  children[i] contains the children (String[]) for groups[i].
        public String[] groups = { "我的好友", "新疆同学", "亲戚", "同事","同事","同事","同事","同事","同事" };
        public String[][] children = {
                { "胡算林", "张俊峰", "王志军", "二人" },
                { "李秀婷", "蔡乔", "别高", "余音" },
                { "摊派新", "张爱明" },
                { "摊派新", "张爱明" },
                { "摊派新", "张爱明" },
                { "摊派新", "张爱明" },
                { "摊派新", "张爱明" },
                { "摊派新", "张爱明" },
                { "马超", "司道光" }
        };
        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }
        @Override
        public int getGroupCount() {
            return groups.length;
        }
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
        	ViewHolderGroup group = null;
        	if(convertView ==null){
        		group = new ViewHolderGroup();
        		convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ticket_result, null,false);
        		group.btn_ticket_result_unfold = (Button)convertView.findViewById(R.id.btn_ticket_result_unfold);
        		
        	    convertView.setTag(group);
        	}else{
        		group = (ViewHolderGroup)convertView.getTag();
        	}
        	final ViewHolderGroup group_copy = group;
        	group.btn_ticket_result_unfold.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(elv.isGroupExpanded(groupPosition)){
						elv.collapseGroup(groupPosition);
						group_copy.btn_ticket_result_unfold.setCompoundDrawablesWithIntrinsicBounds(
								null, 
								null,
								mContext.getResources().getDrawable(R.drawable.ic_xiangxia), 
								null);
					}else{
						elv.expandGroup(groupPosition);
						group_copy.btn_ticket_result_unfold.setCompoundDrawablesWithIntrinsicBounds(
								null, 
								null,
								ic_xiangshang, 
								null);
					}
					
				}
			});
        	return convertView;
        }
        
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }
        @Override
        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }
        
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
        	ViewHolderChild child = null;
        	if(convertView ==null){
        		child = new ViewHolderChild();
        		convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ticket_result_detail, null,false);
        		child.btn_ticket_result_details_book = (Button)convertView.findViewById(R.id.btn_ticket_result_details_book);
        		
        	    convertView.setTag(child);
        	}else{
        		child = (ViewHolderChild)convertView.getTag();
        	}
        	final ViewHolderChild child_copy = child;
        	child.btn_ticket_result_details_book.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mContext.startActivity(new Intent(mContext,TicketOrderActivity.class));
				}
			});
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
        
        static class ViewHolderGroup{
        	Button btn_ticket_result_unfold;
        }
        
        static class ViewHolderChild{
        	Button btn_ticket_result_details_book;
        }
}
