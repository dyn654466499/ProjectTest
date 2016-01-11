package com.daemon.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ImageUtil {
	/**
     * 旋转bitmap
     * @param bitmap 缩放的二位图
     * @param degrees 进行角度转换，如0度、90度。
     * @return
     */
 	public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
 		int width = bitmap.getWidth(); // 图片宽度
 		int height = bitmap.getHeight(); // 图片高度
 		Matrix matrix = new Matrix();
 		matrix.postRotate(degrees);
 		Bitmap bmResult = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);// 声明位图
 		return bmResult; // 返回被缩放的图片
 	}
}
