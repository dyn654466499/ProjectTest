package com.daemon.utils;

import java.util.ArrayList;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.ImageView;

import com.daemon.airticket.R;

/**
 * 该类为原创的类，如果是在复杂的布局上设置加载动画，不知是否有卡顿现象，有待测试！
 * @author 邓耀宁
 *
 */
public class AutoLoadingUtil {
	private static View view;
	private static ViewGroup rootView;
	private static ArrayList<View> saveView;
	
	/**
	 * 设置加载界面
	 * @param rootView 你所要放置的加载动画的根布局
	 */
	public static void setAutoLoadingView(ViewGroup rootView){
		if(rootView != null){
		AutoLoadingUtil.rootView =rootView;
		view = LayoutInflater.from(rootView.getContext()).inflate(R.layout.autoloading, rootView, false);
		saveView = new ArrayList<View>();
		
		ImageView image = (ImageView) view.findViewById(R.id.imageView_autoLoading);  
		image.setBackgroundResource(R.anim.autoloading);  
        AnimationDrawable anim = (AnimationDrawable) image.getBackground();  
        anim.start();  
        rootView.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
			
			@Override
			public void onChildViewRemoved(View parent, View child) {
				// TODO Auto-generated method stub
				saveView.add(child);
			}
			
			@Override
			public void onChildViewAdded(View parent, View child) {
				// TODO Auto-generated method stub
				
			}
		});
        rootView.removeAllViews();
        rootView.addView(view);
        view.bringToFront();
		}
	}
	
	/**
	 * 取消加载界面，并设置之前的根布局
	 */
	public static void cancelAutoLoadingView(){
		if(AutoLoadingUtil.rootView != null && view != null){
			AutoLoadingUtil.rootView.removeView(view);
			saveView.remove(view);
			for (View view : saveView) {
				AutoLoadingUtil.rootView.addView(view);
			}
			view = null;
			AutoLoadingUtil.rootView = null;
			saveView = null;
		}
	}
	
	public static void setBackground(int color){
		if(view!=null){
			view.setBackgroundColor(color);
		}
	}
	
	public static void setBackground(Drawable drawable){
		if(view!=null){
			view.setBackgroundDrawable(drawable);
		}
	}
	
	public static void showFailView(){
		if(AutoLoadingUtil.rootView != null){
			 
		}
	}
	
}
