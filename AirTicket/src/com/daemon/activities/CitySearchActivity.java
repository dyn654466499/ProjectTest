package com.daemon.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.daemon.airticket.R;
import com.daemon.beans.CityModel;
import com.daemon.utils.DBManager;

import static com.daemon.consts.Constants.*;
public class CitySearchActivity extends BaseActivity {
	public final static int RESULT_CODE = 100;
	public final static String CITY_NAME = "cityName";

	private CityAdapter adapter;
	private ListView mCityLit;
	private EditText edtSearch;
	private Button btn_back;

	private SQLiteDatabase database;
	private ArrayList<CityModel> mCityNames;

	private DBManager mDBManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_search);

		mCityLit = (ListView) findViewById(R.id.lv_city);
		edtSearch = (EditText) findViewById(R.id.edt_search);
		btn_back = (Button) findViewById(R.id.btn_back);
		mDBManager = new DBManager(this);

		/**
		 * 点击事件
		 */
		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

				String text = s.toString();
				if (text == null || text.isEmpty()) {
					if (database != null) {
						mCityNames = getCityNames();
					}
				} else {
					if (database != null) {
						mCityNames = getSelectCityNames(text);
					}
				}

				if (adapter == null) {
					adapter = new CityAdapter(CitySearchActivity.this, mCityNames);
					mCityLit.setAdapter(adapter);
				} else {
					adapter.setList(mCityNames);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		mCityLit.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				CityModel model = (CityModel) adapter.getItem(position);

				if (!model.isItem()) {
					Intent i = new Intent();
					i.putExtra(KEY_CITY, model.getAllName());
					setResult(RESULT_OK, i);
					finish();
				}
			}
		});

		btn_back.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		database = mDBManager.openDateBase();

		if (database != null) {
			mCityNames = getCityNames();
			if (adapter == null) {
				adapter = new CityAdapter(this, mCityNames);
				mCityLit.setAdapter(adapter);
			} else {
				adapter.setList(mCityNames);
			}
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		if (database != null) {
			mDBManager.closeDatabase(database);
			database = null;
		}
	}

	/**
	 * 从数据库获取城市数据
	 * 
	 * @return
	 */
	private ArrayList<CityModel> getCityNames() {
		ArrayList<CityModel> names = new ArrayList<CityModel>();

		// 获取热门城市
		String[] hotCity = getResources().getStringArray(R.array.hot_city);
		CityModel hotItem = new CityModel();
		hotItem.setAllName(getResources().getString(R.string.string_hot_city));
		hotItem.setItem(true);
		names.add(hotItem);
		
		for (String string : hotCity) {
			CityModel temp = new CityModel();
			temp.setAllName(string);
			temp.setItem(false);
			names.add(temp);
		}

		// 全国城市
		Cursor cursor = database.rawQuery("SELECT * FROM T_city ORDER BY CityName", null);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			CityModel cityModel = new CityModel();
			cityModel.setAllName(cursor.getString(cursor.getColumnIndex("AllNameSort")));
			cityModel.setCityName(cursor.getString(cursor.getColumnIndex("CityName")));
			cityModel.setNameSort(cursor.getString(cursor.getColumnIndex("NameSort")));
			cityModel.setItem(false);

			if (i == 0) {
				CityModel cityItem = new CityModel();
				cityItem.setAllName(cityModel.getCityName());
				cityItem.setItem(true);
				names.add(cityItem);
			} else {
				CityModel lastCityModel = names.get(names.size() - 1);
				if (!cityModel.getCityName().equalsIgnoreCase(lastCityModel.getCityName()) && !lastCityModel.isItem()) {
					CityModel cityItem = new CityModel();
					cityItem.setAllName(cityModel.getCityName());
					cityItem.setItem(true);
					names.add(cityItem);
				}
			}
			names.add(cityModel);
		}
		cursor.close();
		return names;
	}

	private ArrayList<CityModel> getSelectCityNames(String con) {
		ArrayList<CityModel> names = new ArrayList<CityModel>();
		// 判断查询的内容是不是汉字
		Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
		Matcher m = p_str.matcher(con);
		String sqlString = null;
		if (m.find() && m.group(0).equals(con)) {
			sqlString = "SELECT * FROM T_city WHERE AllNameSort LIKE " + "\"" + con + "%" + "\"" + " ORDER BY CityName";
		} else {
			sqlString = "SELECT * FROM T_city WHERE NameSort LIKE " + "\"" + con + "%" + "\"" + " ORDER BY CityName";
		}
		Cursor cursor = database.rawQuery(sqlString, null);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			CityModel cityModel = new CityModel();
			cityModel.setAllName(cursor.getString(cursor.getColumnIndex("AllNameSort")));
			cityModel.setCityName(cursor.getString(cursor.getColumnIndex("CityName")));
			cityModel.setNameSort(cursor.getString(cursor.getColumnIndex("NameSort")));
			cityModel.setItem(false);
			names.add(cityModel);
		}
		cursor.close();
		return names;
	}

	class CityAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private List<CityModel> list;

		public CityAdapter(Context context, List<CityModel> list) {
			// TODO Auto-generated constructor stub
			this.inflater = LayoutInflater.from(context);
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		public void setList(List<CityModel> list) {
			this.list = list;
			notifyDataSetChanged();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			if (list.get(position).isItem()) {
				convertView = inflater.inflate(R.layout.item_city_search_tag, null);
				TextView tv = (TextView) convertView.findViewById(R.id.tv_city_name);
				tv.setText(list.get(position).getAllName());
			} else {
				convertView = inflater.inflate(R.layout.item_city_search, null);
				TextView tv = (TextView) convertView.findViewById(R.id.tv_city_name);
				tv.setText(list.get(position).getAllName());
			}
			return convertView;
		}

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
