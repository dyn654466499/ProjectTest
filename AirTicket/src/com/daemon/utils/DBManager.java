package com.daemon.utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.daemon.airticket.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * 将raw中得数据库文件写入到data数据库中
 * 
 * @author sy
 *
 */
public class DBManager {
	private final int BUFFER_SIZE = 400000;
	public static final String DB_NAME = "china_city.db";
	public  String DB_PATH; // 存放路径
	private Context mContext;
	private SQLiteDatabase database;

	public DBManager(Context context) {
		this.mContext = context;
		DB_PATH=context.getFilesDir().getPath();
	}

	/**
	 * 被调用方法
	 */
	public SQLiteDatabase openDateBase() {
		return openDateBase(DB_PATH + "/" + DB_NAME);

	}

	/**
	 * 打开数据库
	 * 
	 * @param dbFile
	 * @return SQLiteDatabase
	 * @author sy
	 */
	private SQLiteDatabase openDateBase(String dbFile) {
		File file = new File(dbFile);
		if (!file.exists()) {
			// // 打开raw中得数据库文件，获得stream流
			InputStream stream = this.mContext.getResources().openRawResource(R.raw.china_city);
			try {

				// 将获取到的stream 流写入道data中
				FileOutputStream outputStream = new FileOutputStream(dbFile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = stream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
				stream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
		return db;
	}

	public void closeDatabase(SQLiteDatabase db) {
		if (db != null && db.isOpen()) {
			db.close();
		}
	}
}
