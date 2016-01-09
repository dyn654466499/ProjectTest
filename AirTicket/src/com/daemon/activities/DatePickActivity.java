package com.daemon.activities;

import java.util.Calendar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daemon.airticket.R;
import com.daemon.beans.DatePickInfo;
import com.daemon.consts.Constants;
import com.daemon.utils.AutoLoadingUtils;
import com.daemon.utils.DateTimeUtils;
import com.daemon.utils.ScreenUtil;
import com.daemon.airticket.MyApplication;


import static com.daemon.consts.Constants.*;
/**
 * 日历界面，暂时先用别人的代码实现功能，后续优化
 * @author 邓耀宁
 *
 */
public class DatePickActivity extends BaseActivity{
    private final String TAG = getTAG();
	private ScrollView mScrollView;
	private DatePickInfo datePickInfo;
	private Context mContext;
	private int scrollHeight = 0;
	private LinearLayout mLinearLayoutSelected;
	private Handler mHandler = new Handler() { // 点击直接跳转到选择日的对应月份
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 11) {
				mScrollView.scrollTo(0, scrollHeight);
				mScrollView.setVisibility(View.VISIBLE);
			}
			super.handleMessage(msg);
		};
	};
	
	// 获取对应的属性值 Android框架自带的属性 attr
	int pressed = android.R.attr.state_pressed;
	int enabled = android.R.attr.state_enabled;
	int selected = android.R.attr.state_selected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_date_select);
		/**
		 * 设置标题
		 */
		TextView textView_title = (TextView)findViewById(R.id.tv_title);
		textView_title.setText(getIntent().getStringExtra(TITLE_DATE_KEY));
		
		mScrollView = (ScrollView)findViewById(R.id.scrollView_date_pick);
		mScrollView.setVisibility(View.INVISIBLE);
		
		final LinearLayout linearLayout_date_pick = (LinearLayout)findViewById(R.id.linearLayout_date_pick);
		
		Button btn_back = (Button)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		
		
				datePickInfo = new DatePickInfo();
				datePickInfo.startDate = DateTimeUtils.getCurrentDateTime();
				datePickInfo.dateRange = 180;
				datePickInfo.selectedDay = DateTimeUtils.getCalendar("2014-2-13");

				Calendar localCalendar1 = (Calendar) datePickInfo.startDate.clone();
				Calendar calendarToday = (Calendar) localCalendar1.clone();
				Calendar calendarTomorrow = (Calendar) localCalendar1.clone();
				calendarTomorrow.add(Calendar.DAY_OF_MONTH, 1);
				Calendar calendarTwoMore = (Calendar) localCalendar1.clone();
				calendarTwoMore.add(Calendar.DAY_OF_MONTH, 2);
				Calendar selectedCalendar = (Calendar) datePickInfo.selectedDay
						.clone();
				int yearOfLocalCalendar1 = localCalendar1.get(Calendar.YEAR);
				int monthOfLocalCalendar1 = localCalendar1.get(Calendar.MONTH);
				Calendar localCalendarEnd = (Calendar) datePickInfo.startDate
						.clone();
				localCalendarEnd.add(Calendar.DAY_OF_MONTH,
						datePickInfo.dateRange - 1);
				int yearOfLocalCalendar2 = localCalendarEnd.get(Calendar.YEAR);
				int monthOfLocalCalendar2 = localCalendarEnd.get(Calendar.MONTH);

				int differOfYear = yearOfLocalCalendar2 - yearOfLocalCalendar1;
				int differOfMonth = monthOfLocalCalendar2 - monthOfLocalCalendar1;

				// 涉及到的月份数
				int totalDiffer = differOfYear * 12 + differOfMonth + 1;

				for (int i = 0; i < totalDiffer; i++) {
					LinearLayout item_date_pick = (LinearLayout) View.inflate(
							mContext, R.layout.item_date_pick, null);
					linearLayout_date_pick.addView(item_date_pick);
					
					TextView tv_cal_year = (TextView) item_date_pick
							.findViewById(R.id.tv_cal_year);
					TextView tv_cal_month = (TextView) item_date_pick
							.findViewById(R.id.tv_cal_month);
					
					Calendar tempCalendar = (Calendar) localCalendar1.clone();
					tempCalendar.add(Calendar.YEAR, i / 11);
					tv_cal_year.setText(tempCalendar.get(Calendar.YEAR) + "年");
					Calendar tempCalendar2 = (Calendar) localCalendar1.clone();
					tempCalendar2.add(Calendar.MONTH, i);
					tv_cal_month.setText(tempCalendar2.get(Calendar.MONTH) + 1 + "月");
					tempCalendar2.set(Calendar.DAY_OF_MONTH, 1);
					// 星期天-星期六 Calendar.DAY_OF_WEEK = 1-7
					int weekOfDay = tempCalendar2.get(Calendar.DAY_OF_WEEK) - 1;
					Log.i(TAG, "weekOfDay:" + weekOfDay);
					int maxOfMonth = tempCalendar2
							.getActualMaximum(Calendar.DAY_OF_MONTH);
					Log.i(TAG, "maxOfMonth:" + maxOfMonth);
					int lines = (int) Math.ceil((weekOfDay + maxOfMonth) / 7.0f);
					Log.i(TAG, "lines:" + lines);
					// 开始日期之前和结束日期之后的变灰
					int startDay = localCalendar1.get(Calendar.DAY_OF_MONTH);

					for (int j = 0; j < lines; j++) {
						LinearLayout oneLineLinearLayout = getOneLineDayLinearLayout();
						if (j == 0) {// 第一行
							for (int k = 0; k < 7; k++) {
								TextView localTextView = (TextView) (((RelativeLayout) oneLineLinearLayout
										.getChildAt(k)).getChildAt(0));
								RelativeLayout localSelectedRela = (RelativeLayout) (((RelativeLayout) oneLineLinearLayout
										.getChildAt(k)).getChildAt(1));
								TextView localTextViewSelected = (TextView) localSelectedRela
										.getChildAt(0);
								if (k >= weekOfDay) {
									int index = k - weekOfDay + 1;
									localTextView.setText(index + "");
									localTextViewSelected.setText(index + "");
									Calendar tempCalendar3 = (Calendar) tempCalendar2
											.clone();
									tempCalendar3.set(Calendar.DAY_OF_MONTH, index);
									String date = tempCalendar3.get(Calendar.YEAR)
											+ "-"
											+ (tempCalendar3.get(Calendar.MONTH) + 1)
											+ "-"
											+ tempCalendar3.get(Calendar.DAY_OF_MONTH);

									localTextView.setTag(Long.valueOf(tempCalendar3
											.getTimeInMillis()));
									localSelectedRela.setTag(Long.valueOf(tempCalendar3
											.getTimeInMillis()));

									if (compareCal(tempCalendar3, calendarToday) == -1) {// 小于当天
										localTextView.setTextColor(getResources()
												.getColor(R.color.calendar_color_gray));
										localTextView.setEnabled(false);
									}

									if (Constants.HOLIDAYS.get(date) != null) {
										localTextView.setText(Constants.HOLIDAYS
												.get(date));
										localTextViewSelected
												.setText(Constants.HOLIDAYS.get(date));
										localTextView.setTextSize(
												TypedValue.COMPLEX_UNIT_SP, 14.0f);
										localTextViewSelected.setTextSize(
												TypedValue.COMPLEX_UNIT_SP, 14.0f);
										localTextView.setTextColor(getTextColorGreen());
									}

									if (compareCal(tempCalendar3, calendarToday) == 0) {// 今天
										localTextView.setTextColor(getTextColorRed());
										localTextView.setText("今天");
										localTextViewSelected.setText("今天");
										localTextView.setTextSize(
												TypedValue.COMPLEX_UNIT_SP, 16.0f);
									}
									if (compareCal(tempCalendar3, calendarTomorrow) == 0) {// 明天
										localTextView.setTextColor(getTextColorRed());
										localTextView.setText("明天");
										localTextViewSelected.setText("明天");
										localTextView.setTextSize(
												TypedValue.COMPLEX_UNIT_SP, 16.0f);
									}
									if (compareCal(tempCalendar3, calendarTwoMore) == 0) {// 后天
										localTextView.setTextColor(getTextColorRed());
										localTextView.setText("后天");
										localTextViewSelected.setText("后天");
										localTextView.setTextSize(
												TypedValue.COMPLEX_UNIT_SP, 16.0f);
									}

									if (compareCal(tempCalendar3, selectedCalendar) == 0) {// 选择日
										localTextView.setVisibility(View.INVISIBLE);
										localSelectedRela.setVisibility(View.VISIBLE);
										localSelectedRela.setSelected(true);
										mLinearLayoutSelected = item_date_pick;
									}

									if (compareCal(tempCalendar3, localCalendarEnd) == 1) {// 大于截止日
										localTextView.setTextColor(getResources()
												.getColor(R.color.calendar_color_gray));
										localTextView.setEnabled(false);
									}

								} else {
									localTextView.setVisibility(View.INVISIBLE);
								}
							}
						} else if (j == lines - 1) {// 最后一行
							int temp = maxOfMonth - (lines - 2) * 7 - (7 - weekOfDay);
							for (int k = 0; k < 7; k++) {
								TextView localTextView = (TextView) (((RelativeLayout) oneLineLinearLayout
										.getChildAt(k)).getChildAt(0));
								RelativeLayout localSelectedRela = (RelativeLayout) (((RelativeLayout) oneLineLinearLayout
										.getChildAt(k)).getChildAt(1));
								TextView localTextViewSelected = (TextView) localSelectedRela
										.getChildAt(0);
								if (k < temp) {
									int index = (7 - weekOfDay) + (j - 1) * 7 + k + 1;
									localTextView.setText(index + "");
									localTextViewSelected.setText(index + "");
									Calendar tempCalendar3 = (Calendar) tempCalendar2
											.clone();
									tempCalendar3.set(Calendar.DAY_OF_MONTH, index);
									String date = tempCalendar3.get(Calendar.YEAR)
											+ "-"
											+ (tempCalendar3.get(Calendar.MONTH) + 1)
											+ "-"
											+ tempCalendar3.get(Calendar.DAY_OF_MONTH);
									localTextView.setTag(Long.valueOf(tempCalendar3
											.getTimeInMillis()));
									localSelectedRela.setTag(Long.valueOf(tempCalendar3
											.getTimeInMillis()));
									if (compareCal(tempCalendar3, calendarToday) == -1) {// 小于当天
										localTextView.setTextColor(getResources()
												.getColor(R.color.calendar_color_gray));
										localTextView.setEnabled(false);
									}
									if (Constants.HOLIDAYS.get(date) != null) {
										localTextView.setText(Constants.HOLIDAYS
												.get(date));
										localTextViewSelected
												.setText(Constants.HOLIDAYS.get(date));
										localTextView.setTextSize(
												TypedValue.COMPLEX_UNIT_SP, 14.0f);
										localTextViewSelected.setTextSize(
												TypedValue.COMPLEX_UNIT_SP, 14.0f);
										localTextView.setTextColor(getTextColorGreen());
									}

									if (compareCal(tempCalendar3, calendarToday) == 0) {// 今天
										localTextView.setTextColor(getTextColorRed());
										localTextView.setText("今天");
										localTextViewSelected.setText("今天");
										localTextView.setTextSize(
												TypedValue.COMPLEX_UNIT_SP, 16.0f);
									}
									if (compareCal(tempCalendar3, calendarTomorrow) == 0) {// 明天
										localTextView.setTextColor(getTextColorRed());
										localTextView.setText("明天");
										localTextViewSelected.setText("明天");
										localTextView.setTextSize(
												TypedValue.COMPLEX_UNIT_SP, 16.0f);
									}
									if (compareCal(tempCalendar3, calendarTwoMore) == 0) {// 后天
										localTextView.setTextColor(getTextColorRed());
										localTextView.setText("后天");
										localTextViewSelected.setText("后天");
										localTextView.setTextSize(
												TypedValue.COMPLEX_UNIT_SP, 16.0f);
									}

									if (compareCal(tempCalendar3, selectedCalendar) == 0) {// 选择日
										localTextView.setVisibility(View.INVISIBLE);
										localSelectedRela.setVisibility(View.VISIBLE);
										localSelectedRela.setSelected(true);
										mLinearLayoutSelected = item_date_pick;

									}
									if (compareCal(tempCalendar3, localCalendarEnd) == 1) {// 大于截止日
										localTextView.setTextColor(getResources()
												.getColor(R.color.calendar_color_gray));
										localTextView.setEnabled(false);
									}

								} else {
									localTextView.setVisibility(View.INVISIBLE);
								}
							}

						} else {// 中间
							for (int k = 0; k < 7; k++) {
								// TextView localTextView = (TextView)
								// oneLineLinearLayout
								// .getChildAt(k);
								TextView localTextView = (TextView) (((RelativeLayout) oneLineLinearLayout
										.getChildAt(k)).getChildAt(0));
								RelativeLayout localSelectedRela = (RelativeLayout) (((RelativeLayout) oneLineLinearLayout
										.getChildAt(k)).getChildAt(1));
								TextView localTextViewSelected = (TextView) localSelectedRela
										.getChildAt(0);
								int index = (7 - weekOfDay) + (j - 1) * 7 + k + 1;
								localTextView.setText(index + "");
								localTextViewSelected.setText(index + "");
								Calendar tempCalendar3 = (Calendar) tempCalendar2
										.clone();
								tempCalendar3.set(Calendar.DAY_OF_MONTH, index);
								String date = tempCalendar3.get(Calendar.YEAR) + "-"
										+ (tempCalendar3.get(Calendar.MONTH) + 1) + "-"
										+ tempCalendar3.get(Calendar.DAY_OF_MONTH);
								localTextView.setTag(Long.valueOf(tempCalendar3
										.getTimeInMillis()));
								localSelectedRela.setTag(Long.valueOf(tempCalendar3
										.getTimeInMillis()));
								if (compareCal(tempCalendar3, calendarToday) == -1) {// 小于当天
									localTextView.setTextColor(getResources().getColor(
											R.color.calendar_color_gray));
									localTextView.setEnabled(false);
								}
								if (Constants.HOLIDAYS.get(date) != null) {
									localTextView.setText(Constants.HOLIDAYS.get(date));
									localTextViewSelected.setText(Constants.HOLIDAYS
											.get(date));
									localTextView.setTextSize(
											TypedValue.COMPLEX_UNIT_SP, 14.0f);
									localTextViewSelected.setTextSize(
											TypedValue.COMPLEX_UNIT_SP, 14.0f);
									localTextView.setTextColor(getTextColorGreen());
								}

								if (compareCal(tempCalendar3, calendarToday) == 0) {// 今天
									localTextView.setTextColor(getTextColorRed());
									localTextView.setText("今天");
									localTextViewSelected.setText("今天");
									localTextView.setTextSize(
											TypedValue.COMPLEX_UNIT_SP, 16.0f);
								}
								if (compareCal(tempCalendar3, calendarTomorrow) == 0) {// 明天
									localTextView.setTextColor(getTextColorRed());
									localTextView.setText("明天");
									localTextViewSelected.setText("明天");
									localTextView.setTextSize(
											TypedValue.COMPLEX_UNIT_SP, 16.0f);
								}
								if (compareCal(tempCalendar3, calendarTwoMore) == 0) {// 后天
									localTextView.setTextColor(getTextColorRed());
									localTextView.setTextSize(
											TypedValue.COMPLEX_UNIT_SP, 16.0f);
									localTextView.setText("后天");
									localTextViewSelected.setText("后天");
								}

								if (compareCal(tempCalendar3, selectedCalendar) == 0) {// 选择日
									localTextView.setVisibility(View.INVISIBLE);
									localSelectedRela.setVisibility(View.VISIBLE);
									localSelectedRela.setSelected(true);
									mLinearLayoutSelected = item_date_pick;
								}
								if (compareCal(tempCalendar3, localCalendarEnd) == 1) {// 大于截止日
									localTextView.setTextColor(getResources().getColor(
											R.color.calendar_color_gray));
									localTextView.setEnabled(false);
								}

							}
						}
						item_date_pick.addView(oneLineLinearLayout);
					}
				}
		
	}

	/**
	 * 获取一行 七天的LinearLayout
	 * 
	 * @return
	 */
	private LinearLayout getOneLineDayLinearLayout() {
		LinearLayout localLinearLayout = new LinearLayout(this);
		localLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		for (int i = 0; i < 7; i++) {
			float height = (MyApplication.getScreenWidth()
					- ScreenUtil.dip2px(mContext, 10f) - ScreenUtil.dip2px(
					mContext, 1.5f * 6)) / 7;
			Log.i(TAG, "height:" + height);
			LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, (int) height, 1.0F);
			RelativeLayout localRelativeLayout = new RelativeLayout(mContext);
			localRelativeLayout.setLayoutParams(localLayoutParams4);
			localLayoutParams4.setMargins(ScreenUtil.dip2px(this, 1.5F),
					ScreenUtil.dip2px(this, 1.5F),
					ScreenUtil.dip2px(this, 1.5F),
					ScreenUtil.dip2px(this, 1.5F));
			TextView localTextView3 = new TextView(this);
			localTextView3.setLayoutParams(localLayoutParams4);
			localTextView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0F);
			localTextView3.setBackgroundDrawable(getBackGroundDrawable());
			localTextView3.setTextColor(getTextColorBlack());
			localTextView3.setGravity(Gravity.CENTER);
			localTextView3.setOnClickListener(this);
			localTextView3.setVisibility(View.VISIBLE);
			localRelativeLayout.addView(localTextView3, 0);

			RelativeLayout localRelativeLayout2 = new RelativeLayout(this);
			localRelativeLayout2.setLayoutParams(localLayoutParams4);
			localRelativeLayout2.setOnClickListener(this);
			localRelativeLayout2.setBackgroundDrawable(getBackGroundDrawable());
			TextView localTextView1 = new TextView(this);
			localTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0F);
			localTextView1.setId(1);
			localTextView1.setTextColor(getTextColorBlack());
			TextView localTextView2 = new TextView(this);
			localTextView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.0F);
			localTextView2.setTextColor(mContext.getResources().getColor(
					R.color.calendar_color_white));
			localTextView2.setText("出发");
			RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			localLayoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
			localLayoutParams2.topMargin = ScreenUtil.dip2px(mContext, 4f);
			localRelativeLayout2.addView(localTextView1, 0, localLayoutParams2);
			RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			localLayoutParams3.addRule(RelativeLayout.CENTER_HORIZONTAL);
			localLayoutParams3.addRule(RelativeLayout.BELOW, 1);
			localRelativeLayout2.addView(localTextView2, 1, localLayoutParams3);
			localRelativeLayout2.setVisibility(View.INVISIBLE);
			localRelativeLayout.addView(localRelativeLayout2, 1);

			localLinearLayout.addView(localRelativeLayout, i);

		}
		return localLinearLayout;
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(0, R.anim.activity_down);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			
			break;

		default:
			break;
		}
		
		if (v.getTag() != null) {
			Calendar localCalendar = Calendar.getInstance();
			long time = ((Long) v.getTag()).longValue();
			localCalendar.setTimeInMillis(time);
			setResult(RESULT_OK, getIntent().putExtra(TYPE_DATE_KEY, time));
			Toast.makeText(
					mContext,
					localCalendar.get(Calendar.YEAR) + "年"
							+ (localCalendar.get(Calendar.MONTH) + 1) + "月"
							+ localCalendar.get(Calendar.DAY_OF_MONTH) + "日",
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	/**
	 * 比较两个日期的大小
	 * 
	 * @param paramCalendar1
	 * @param paramCalendar2
	 * @return
	 */
	private int compareCal(Calendar paramCalendar1, Calendar paramCalendar2) {
		if (paramCalendar1.get(Calendar.YEAR) > paramCalendar2
				.get(Calendar.YEAR)) {
			return 1;
		} else if (paramCalendar1.get(Calendar.YEAR) < paramCalendar2
				.get(Calendar.YEAR)) {
			return -1;
		} else {
			if (paramCalendar1.get(Calendar.MONTH) > paramCalendar2
					.get(Calendar.MONTH)) {
				return 1;
			} else if (paramCalendar1.get(Calendar.MONTH) < paramCalendar2
					.get(Calendar.MONTH)) {
				return -1;
			} else {
				if (paramCalendar1.get(Calendar.DAY_OF_MONTH) > paramCalendar2
						.get(Calendar.DAY_OF_MONTH)) {
					return 1;
				} else if (paramCalendar1.get(Calendar.DAY_OF_MONTH) < paramCalendar2
						.get(Calendar.DAY_OF_MONTH)) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	/**
	 * 点击背景切换
	 * 
	 * @return
	 */
	private StateListDrawable getBackGroundDrawable() {
		// 获取对应的属性值 Android框架自带的属性 attr
		int pressed = android.R.attr.state_pressed;
		int enabled = android.R.attr.state_enabled;
		int selected = android.R.attr.state_selected;

		StateListDrawable localStateListDrawable = new StateListDrawable();
		ColorDrawable localColorDrawable1 = new ColorDrawable(mContext
				.getResources().getColor(android.R.color.transparent));
		Drawable localColorDrawable2 = new ColorDrawable(mContext
				.getResources().getColor(R.color.title_color));
		ColorDrawable localColorDrawable3 = new ColorDrawable(mContext
				.getResources().getColor(android.R.color.transparent));
		localStateListDrawable.addState(new int[] { pressed, enabled },
				localColorDrawable2);
		localStateListDrawable.addState(new int[] { selected, enabled },
				localColorDrawable2);
		localStateListDrawable.addState(new int[] { enabled },
				localColorDrawable1);
		localStateListDrawable.addState(new int[0], localColorDrawable3);
		return localStateListDrawable;
	}

	/**
	 * 字体颜色 切换
	 * 
	 * @return
	 */
	private ColorStateList getTextColorBlack()

	{
		return new ColorStateList(new int[][] { { pressed, enabled },
				{ selected, enabled }, { enabled }, new int[0] }, new int[] {
				-1, -1,
				mContext.getResources().getColor(R.color.calendar_color_black),
				mContext.getResources().getColor(R.color.calendar_color_white) });
	}

	private ColorStateList getTextColorRed()

	{
		return new ColorStateList(new int[][] { { pressed, enabled },
				{ selected, enabled }, { enabled }, new int[0] }, new int[] {
				-1, -1,
				mContext.getResources().getColor(R.color.calendar_color_orange),
				mContext.getResources().getColor(R.color.calendar_color_white) });
	}

	private ColorStateList getTextColorGreen()

	{
		return new ColorStateList(new int[][] { { pressed, enabled },
				{ selected, enabled }, { enabled }, new int[0] }, new int[] {
				-1, -1,
				mContext.getResources().getColor(R.color.calendar_color_green),
				mContext.getResources().getColor(R.color.calendar_color_white) });
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		if(mLinearLayoutSelected!=null)scrollHeight = mLinearLayoutSelected.getTop();
		mHandler.sendEmptyMessageDelayed(11, 100l);
		Log.i(TAG, "scrollHeight:" + scrollHeight);
	}

	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
