package com.daemon.utils;

import java.util.ArrayList;

import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.ImageView;

import com.daemon.airticket.R;

public class AutoLoadingUtil {
	private static View view;
	private static ViewGroup rootView;
	private static ArrayList<View> saveView;
	
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
	
	public static void showFailView(){
		if(AutoLoadingUtil.rootView != null){
			 
		}
	}
	
//	public static View getAutoLoadingView(Activity mActivity){
//		view = LayoutInflater.from(mActivity).inflate(R.layout.autoloading, null, false);
//		ImageView image = (ImageView) view.findViewById(R.id.imageView_autoLoading);  
//		image.setBackgroundResource(R.anim.autoloading);  
//        AnimationDrawable anim = (AnimationDrawable) image.getBackground();  
//        anim.start();  
//        view.bringToFront();
//        return view;
//	}
//	
//	public static void cancelAutoLoadingView(ViewGroup rootView){
//		if(rootView != null && view != null){
//			rootView.removeView(view);
//			view = null;
//		}
//	}
//	
//	public static void showFailView(ViewGroup rootView){
//		if(rootView != null && view != null){
//			 
//		}
//	}
}
