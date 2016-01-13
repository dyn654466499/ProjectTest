package com.daemon.utils;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.daemon.airticket.R;
import com.daemon.beans.TicketInfo;
import com.daemon.volley.GsonRequest;
import com.google.gson.Gson;

public class VolleyUtil {
	/**
	 * 利用Volley异步加载图片
	 * 
	 * 注意方法参数: getImageListener(ImageView view, int defaultImageResId, int
	 * errorImageResId) 第一个参数:显示图片的ImageView 第二个参数:默认显示的图片资源 第三个参数:加载错误时显示的图片资源
	 */
	public static void loadImage(Context mContext, ImageView mImageView,
			String imageUrl) {
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				20);
		ImageCache imageCache = new ImageCache() {
			@Override
			public void putBitmap(String key, Bitmap value) {
				lruCache.put(key, value);
			}

			@Override
			public Bitmap getBitmap(String key) {
				return lruCache.get(key);
			}
		};
		ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
		ImageListener listener = ImageLoader.getImageListener(mImageView,
				R.drawable.ic_duigou_lan, R.drawable.submit_edit_clear_normal);
		imageLoader.get(imageUrl, listener);
	}

	/**
	 * 利用NetworkImageView显示网络图片
	 */
	public static void showImageByNetworkImageView(Context mContext,
			NetworkImageView mNetworkImageView, String imageUrl) {
		// String imageUrl="http://avatar.csdn.net/6/6/D/1_lfdfhl.jpg";
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				20);
		ImageCache imageCache = new ImageCache() {
			@Override
			public void putBitmap(String key, Bitmap value) {
				lruCache.put(key, value);
			}

			@Override
			public Bitmap getBitmap(String key) {
				return lruCache.get(key);
			}
		};
		ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
		mNetworkImageView.setTag("url");
		mNetworkImageView.setImageUrl(imageUrl, imageLoader);
	}

	public static <T> void parseGson(Context mContext, Class<T> clazz,
			String jsonUrl) {
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		GsonRequest<T> gsonRequest = new GsonRequest<T>(jsonUrl, clazz,
				new Response.Listener<T>() {

					@Override
					public void onResponse(T arg0) {
						// TODO Auto-generated method stub

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub

					}
				});
		requestQueue.add(gsonRequest);
	}
	
	/**
	 * 利用Volley获取JSONObject数据 
	 * @param mContext
	 * @param jsonUrl
	 */
    public static void getJSONByVolley(Context mContext, 
    		String jsonUrl,
    		Listener<JSONObject> listener,
    		ErrorListener errorListener) { 
        RequestQueue requestQueue = Volley.newRequestQueue(mContext); 
        //String JSONDataUrl = "https://openapi.youku.com/v2/videos/show_basic.json?video_id=XNjY1NDA2MDAw&client_id=b10ab8588528b1b1"; 
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( 
                Request.Method.GET,  
                jsonUrl,  
                null, 
                listener, 
                errorListener); 
        requestQueue.add(jsonObjectRequest); 
    } 
}
