package jp.kenschool.mycalendar.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.kenschool.mycalendar.ListActivity;
import jp.kenschool.mycalendar.MainActivity;
import jp.kenschool.mycalendar.R;
import jp.kenschool.mycalendar.dao.ScheduleDao;

/**
 * 指定した年月日のカレンダーを表示するクラス
 */

public class MyCalendarView extends LinearLayout {
    //カレンダーの列数（横）
    public static final int DAYS_IN_WEEK = 7;
    //カレンダーの行数（縦）
    public static final int MAX_WEEKS = 6;

    //SimpleDateFormatの書式
    public static final SimpleDateFormat DATE_FORMAT_YEAR_MONTH = new SimpleDateFormat("yyyy/MM");
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat DATE_FORMAT_WEEK = new SimpleDateFormat("E");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    //既存の定数の別名
    public static final int MP = LayoutParams.MATCH_PARENT;
    public static final int WC = LayoutParams.WRAP_CONTENT;

    //カレンダーの選択されている日付
    private Calendar mCalendar;
    //カレンダーヘッダーのレイアウト
    private LinearLayout mCalendarHeaderLayout;
    //曜日ヘッダーのレイアウト
    private LinearLayout mWeekHeaderLayout;
    //カレンダーの日にち表示用レイアウトのリスト
    private ArrayList<LinearLayout> mDaysLayoutList;

    //背景色
    public final int COLOR_ICE_GREEN;
    public final int COLOR_ALICE_BLUE;

    /**
     * コンストラクター
     *
     * @param context
     */
    public MyCalendarView(Context context) {
        this(context, null);
    }

    /**
     * コンストラクター
     *
     * @param context
     * @param attrs
     */
    public MyCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);

        //colors.xmlから背景色を取得
        COLOR_ALICE_BLUE = ContextCompat.getColor(context, R.color.color_alice_blue);
        COLOR_ICE_GREEN = ContextCompat.getColor(context, R.color.color_ice_green);

        //カレンダーの選択されている日付と本日に設定
        mCalendar = Calendar.getInstance();
        //日にち表示用レイアウトのリスト
        mDaysLayoutList = new ArrayList<LinearLayout>(MAX_WEEKS);
        //カレンダーヘッダー、曜日ヘッダー、日にちのレイアウトの作成
        createCalendarHeaderLayout();
        createHorizontalBorder();
        createWeekHeaderLayout();
        createHorizontalBorder();
        createDayLayout();
        //カレンダーヘッダー、曜日ヘッダー、日にちのレイアウトの初期設定
        initCalendarHeader();
        initWeekHeader();
        updateDaysLayout();
    }

    /**
     * 引数に指定されたカレンダーオブジェクトが等しいか比較
     *
     * @param calendar1
     * @param calendar2
     * @return 引数に指定されたカレンダーオブジェクトを比較した結果
     */
    public static boolean compareDate(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
    }

    /**
     * 引数で指定されたwidthとheightをもとに、LayoutParamsオブジェクトを生成する
     *
     * @param width  横幅
     * @param height 縦幅
     * @return LayoutParamsオブジェクト
     */
    public static LayoutParams createLayoutParams(int width, int height) {
        return new LayoutParams(width, height);
    }

    /**
     * レイアウト内のテキストビューを取得する
     *
     * @param layout
     * @param index
     * @return
     */
    public static TextView getTextView(LinearLayout layout, int index) {
        return (TextView) layout.getChildAt(index);
    }

    /**
     * デフォルトのテキストビューを生成して、レイアウトに追加する
     *
     * @param layout
     * @param layoutParams
     */
    public void addDefaultTextView(LinearLayout layout,
                                   LayoutParams layoutParams) {
        TextView textView = new TextView(getContext());
        //画面のdensityを取得し、dpをpxに変換する計算
        float density = getResources().getDisplayMetrics().density;
        int px = (int) Math.ceil(5 * density);
        textView.setPadding(0, px, 0, px);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(textView, layoutParams);
    }

    /**
     * 　指定された日付のカレンダーの状態に更新する
     *
     * @param selectedData
     */
    public void updateCalendar(Date selectedData) {
        mCalendar.setTime(selectedData);
        updateDaysLayout();
    }

    /**
     * カレンダーヘッダーを生成する
     */
    private void createCalendarHeaderLayout() {
        //カレンダーヘッダーのレイアウト
        mCalendarHeaderLayout = new LinearLayout(getContext());
        //横幅は画面に対して均等になるように割合 1 、縦幅は文字に合わせる
        LayoutParams layoutParams = createLayoutParams(0, WC);
        layoutParams.weight = 1;
        //テキストビューを作成
        for (int i = 0; i < 3; i++) {
            addDefaultTextView(mCalendarHeaderLayout, layoutParams);
        }
        addView(mCalendarHeaderLayout, createLayoutParams(MP, WC));
    }

    /**
     * 曜日ヘッダーを生成する
     */
    private void createWeekHeaderLayout() {
        //曜日ヘッダーのレイアウト
        mWeekHeaderLayout = new LinearLayout(getContext());
        //横幅は画面に対して均等になるように割合 1 、縦幅は文字に合わせる
        LayoutParams layoutParams = createLayoutParams(0, WC);
        layoutParams.weight = 1;
        //1週間分のテキストビューを作成
        for (int counter = 0; counter < DAYS_IN_WEEK; counter++) {
            addDefaultTextView(mWeekHeaderLayout, layoutParams);
        }
        addView(mWeekHeaderLayout, createLayoutParams(MP, WC));
    }

    /**
     * カレンダーの日にちを表示するレイアウトを生成する
     */
    private void createDayLayout() {
        //横幅は画面に対して均等になるように割合 1 、縦幅は文字に合わせる
        LayoutParams layoutParams = createLayoutParams(0, WC);
        layoutParams.weight = 1;
        // カレンダーの日にちを表示するレイアウトを生成する(最大6週間分必要)
        for (int row = 0; row < MAX_WEEKS; row++) {
            //各週のレイアウトを作成し、リストに登録
            LinearLayout weekLayout = new LinearLayout(getContext());
            mDaysLayoutList.add(weekLayout);
            //1週間分のテキストビューを作成
            for (int col = 0; col < DAYS_IN_WEEK; col++) {
                addDefaultTextView(weekLayout, layoutParams);
            }
            addView(weekLayout, createLayoutParams(MP, WC));
        }
    }

    /**
     * 水平線を引く
     * 横幅は画面と同じ幅、縦幅は1dp
     */
    private void createHorizontalBorder() {
        View border = new View(getContext());
        border.setBackgroundColor(Color.GRAY);
        //横幅は画面と同じ幅、縦幅は1dp
        float density = getResources().getDisplayMetrics().density; //画面のdensityを指定
        int heightPx = (int) Math.ceil(1 * density);
        addView(border, MP, heightPx);
    }

    private void initCalendarHeader() {
        // < (前月)
        TextView previousTextView = getTextView(mCalendarHeaderLayout, 0);
        previousTextView.setText("＜");
        previousTextView.setBackgroundColor(COLOR_ALICE_BLUE);
        previousTextView.setTypeface(null, Typeface.BOLD);
        previousTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //カレンダーの選択されている日付を1か月前にセット
                mCalendar.add(Calendar.MONTH, -1);
                //カレンダーの選択されている日付を1か月後にセット
                TextView yearMonthTextView = getTextView(mCalendarHeaderLayout, 1);
                yearMonthTextView.setText(DATE_FORMAT_YEAR_MONTH.format(mCalendar.getTime()));
                updateDaysLayout();
            }
        });
        // yyyy / MM
        TextView yearMonthTextView = getTextView(mCalendarHeaderLayout, 1);
        yearMonthTextView.setText(DATE_FORMAT_YEAR_MONTH.format(mCalendar.getTime()));
        yearMonthTextView.setTypeface(null, Typeface.BOLD);
        //横幅は他のテキストビューより大きくなるように割合 2 、縦幅は文字に合わせる
        LayoutParams layoutParams = createLayoutParams(0, WC);
        layoutParams.weight = 2;
        yearMonthTextView.setLayoutParams(layoutParams);

        // > (次月)
        TextView nextTextView = getTextView(mCalendarHeaderLayout, 2);
        nextTextView.setText("＞");
        nextTextView.setTypeface(null, Typeface.BOLD);
        nextTextView.setBackgroundColor(COLOR_ALICE_BLUE);
        nextTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //カレンダーの選択されている日付を1か月後にセット
                mCalendar.add(Calendar.MONTH, 1);
                //yyyy/MM のテキストビューに1か月後の日付をセット
                TextView yearMonthTextView = getTextView(mCalendarHeaderLayout, 1);
                yearMonthTextView.setText(DATE_FORMAT_YEAR_MONTH.format(mCalendar.getTime()));
                //カレンダーの日にち部分を更新
                updateDaysLayout();
            }
        });
    }

    private void initWeekHeader() {
        Calendar cal = Calendar.getInstance();
        for (int position = 0; position < mWeekHeaderLayout.getChildCount(); position++) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY + position);
            TextView weekTextView = getTextView(mWeekHeaderLayout, position);
            weekTextView.setText(DATE_FORMAT_WEEK.format(cal.getTime()));
            weekTextView.setTypeface(null, Typeface.BOLD);
        }
    }

    /**
     * カレンダーの日にち表示用レイアウトのリスト
     */
    private void updateDaysLayout() {
        // yyyy/MM の更新
        TextView yearMonthTextView = getTextView(mCalendarHeaderLayout, 1);
        yearMonthTextView.setText(DATE_FORMAT_YEAR_MONTH.format(mCalendar.getTime()));
        //現在の日付データを持ったカレンダー
        Calendar todayCalendar = Calendar.getInstance();
        //選択されている日付データを持ったカレンダー
        //選択されている日付とは、MainActivity以外で様々な処理をする対象日
        Calendar calendarClone = (Calendar) mCalendar.clone();
        calendarClone.set(Calendar.DAY_OF_MONTH, 1);
        //カレンダーの月初日前の空白の数
        int blankCounter = calendarClone.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
        //カレンダーの月末日
        int lastDay = calendarClone.getActualMaximum(Calendar.DATE);
        //データベースからスケジュールが登録されている日をリストで取得
        //表示されているカレンダーに登録日がない場合は null
        List<String> dateList = ScheduleDao.getDateList(getContext(), DATE_FORMAT_YEAR_MONTH.format(mCalendar.getTime()) + "%");
        //登録日の日にち部分の値、表示されているカレンダーに登録日がない場合は -1 にする
        int scheduleDay = -1;
        if (dateList != null) {
            //リストから登録日を取得して、登録日の日にち部分を取得
            String dateString = dateList.get(0);
            scheduleDay = Integer.parseInt(dateString.substring(dateString.lastIndexOf("/") + 1));
        }
        //カレンダーに表示した日にちの値
        int dayCounter = 1;
        //カレンダーの最大週数(6回)の繰り返し
        for (int weekCounter = 0; weekCounter < MAX_WEEKS; weekCounter++) {
            LinearLayout weekLayout = mDaysLayoutList.get(weekCounter);
            //1週間分(7回)の繰り返し(日曜日は0、土曜日は6)
            for (int dayOfWeekCounter = 0; dayOfWeekCounter < DAYS_IN_WEEK;
                 dayOfWeekCounter++) {
                TextView dayTextView = (TextView) getTextView(weekLayout, dayOfWeekCounter);
                //背景色を白に設定
                dayTextView.setBackgroundColor(Color.WHITE);
                // 第一週目で、かつ blankCounter が 0 より大きい場合
                // または、dayCounter が最終日より大きい場合
                if ((weekCounter == 0 && blankCounter-- > 0) || lastDay < dayCounter) {
                    dayTextView.setText("");
                    dayTextView.setOnClickListener(null);
                    continue;
                }
                //テキストビューとカレンダークローンに日にちを設定
                dayTextView.setText(String.valueOf(dayCounter));
                calendarClone.set(Calendar.DAY_OF_MONTH, dayCounter);

                //文字色を黒に設定
                dayTextView.setTextColor(Color.BLACK);
                //ノーマルに設定
                dayTextView.setTypeface(null, Typeface.NORMAL);
                //イベントを設定
                dayTextView.setOnClickListener(dayTextViewClickListener);
                //カレンダーの日にちと今日が一致しているか判定
                if (compareDate(todayCalendar, calendarClone)) {
                    //背景色をアイスグリーンに設定
                    dayTextView.setBackgroundColor(COLOR_ICE_GREEN);
                    //太字に設定
                    dayTextView.setTypeface(null, Typeface.BOLD);
                }
                //dayCounter とスケジュールが登録されている日か一致しているか判定
                if (scheduleDay != -1 && scheduleDay == dayCounter) {
                    //下線の設定
                    dayTextView.setPaintFlags(dayTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    //リストから登録日を削除
                    dateList.remove(0);
                    //リストの登録日の数が 0 より大きいか確認
                    if (dateList.size() > 0) {
                        //リストから登録日を取得して、登録日の日にち部分を取得
                        String dateString = dateList.get(0);
                        scheduleDay = Integer.parseInt(dateString.substring(dateString.lastIndexOf("/") + 1));
                    } else {
                        //リストの登録日の数が 0 になったので、登録日の日にち部分を -1 にする
                        scheduleDay = -1;
                    }
                } else {
                    //下線の削除
                    dayTextView.setPaintFlags(dayTextView.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                }
                dayCounter++;
            }
        }
    }

    /**
     * カレンダーの日にちがクリック時のイベント処理
     */
    private OnClickListener dayTextViewClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //カレンダーの選択されている日付にセット
            String dayString = ((TextView) v).getText().toString();
            mCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayString));
            //インテントに選択された日付をセット
            Intent intent = new Intent(getContext(), ListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(MainActivity.INTENT_SELECTED_DATE, DEFAULT_DATE_FORMAT.format(mCalendar.getTime()));
            //アクティビティをスタート
            getContext().startActivity(intent);
        }
    };
}