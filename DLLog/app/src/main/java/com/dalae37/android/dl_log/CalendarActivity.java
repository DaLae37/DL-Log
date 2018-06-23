package com.dalae37.android.dl_log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    LinearLayout t_Linear, l_Linear;


    private TextView t_date, l_date;

    private GridAdapter_t gridAdapter_t;
    private GridAdapter_l gridAdapter_l;

    private ArrayList<String> t_dayList, l_dayList;

    private GridView t_gridView, l_gridView;

    private Calendar t_cal, l_cal;


    float curX = 0;

    int position = 0;


    //String[][] dateArray = {{"2018", "5", "1"}, {"2018", "5", "14"}, {"2018", "5", "14"}, {"2018", "5", "14"}, {"2018", "5", "15"}, {"2018", "6", "1"}, {"2018", "6", "1"}, {"2018", "6", "1"}, {"2018", "6", "1"}, {"2018", "6", "1"}, {"2018", "6", "1"}, {"2018", "6", "5"}, {"2018", "6", "12"}, {"2018", "6", "15"}, {"2018", "6", "16"}, {"2018", "6", "17"}};
    //String[][] dateArray=new String[20][3];
    ArrayList<DateItem> dateArray;
    int gameDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        t_date = findViewById(R.id.t_date);
        l_date = findViewById(R.id.l_date);
        t_gridView = findViewById(R.id.t_gridView);
        l_gridView = findViewById(R.id.l_gridView);
        t_Linear = findViewById(R.id.thisMonth);
        l_Linear = findViewById(R.id.lastMonth);


        //DB
        dateArray = new ArrayList<>();
        createDB();


        //터치이벤트(스와이프/아이템터치)
        t_gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        curX = event.getX();
                        position = t_gridView.pointToPosition((int) event.getX(), (int) event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        float diffX = curX - event.getX();
                        if (diffX < -50) {
                            l_Linear.setVisibility(View.VISIBLE);
                            t_Linear.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getApplicationContext(), "" + t_gridView.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                        }
                        break;
                }

                return true;
            }
        });


        l_gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();


                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        curX = event.getX();
                        position = l_gridView.pointToPosition((int) event.getX(), (int) event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        float diffX = curX - event.getX();
                        if (diffX > 50) {
                            l_Linear.setVisibility(View.GONE);
                            t_Linear.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getApplicationContext(), "" + l_gridView.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                        }
                        break;
                }

                return true;
            }
        });

        //오늘 날짜
        long now = System.currentTimeMillis();
        final Date date = new Date(now);


        //년월일 저장
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("M", Locale.KOREA);

        //한달전(1월 예외처리 필요)
        int lastMonthFormat = Integer.parseInt(curMonthFormat.format(date)) - 1;


        //현재 년/월 지정
        l_date.setText(curYearFormat.format(date) + "/" + lastMonthFormat);
        t_date.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date));


        //gridView 요일 추가

        l_dayList = new ArrayList<String>();
        l_dayList.add("일");
        l_dayList.add("월");
        l_dayList.add("화");
        l_dayList.add("수");
        l_dayList.add("목");
        l_dayList.add("금");
        l_dayList.add("토");

        t_dayList = new ArrayList<String>();
        t_dayList.add("일");
        t_dayList.add("월");
        t_dayList.add("화");
        t_dayList.add("수");
        t_dayList.add("목");
        t_dayList.add("금");
        t_dayList.add("토");


        t_cal = Calendar.getInstance();
        l_cal = Calendar.getInstance();//

        //달 1일 파악

        l_cal.set(Integer.parseInt(curYearFormat.format(date)), lastMonthFormat - 1, 1);
        int dayNum = l_cal.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < dayNum; i++) {
            l_dayList.add("");
        }
        setCalendarDate_l(l_cal.get(Calendar.MONTH));


        t_cal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        dayNum = t_cal.get(Calendar.DAY_OF_WEEK);


        for (int i = 1; i < dayNum; i++) {
            t_dayList.add("");
        }
        setCalendarDate_t(t_cal.get(Calendar.MONTH) + 1);


        //어댑터


        gridAdapter_l = new GridAdapter_l(getApplicationContext(), l_dayList);
        l_gridView.setAdapter(gridAdapter_l);

        gridAdapter_t = new GridAdapter_t(getApplicationContext(), t_dayList);
        t_gridView.setAdapter(gridAdapter_t);

    }

    //날짜 채우기
    private void setCalendarDate_t(int month) {
        t_cal.set(Calendar.MONTH, month - 1);

        for (int i = 1; i <= t_cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            t_dayList.add("" + (i));

        }

    }

    private void setCalendarDate_l(int month) {
        l_cal.set(Calendar.MONTH, month - 1);

        for (int i = 1; i <= l_cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            l_dayList.add("" + (i));
        }

    }

    private class GridAdapter_t extends BaseAdapter {

        private final List<String> list;

        private final LayoutInflater inflater;

        public GridAdapter_t(Context context, List<String> list) {
            this.list = list;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int i, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item, parent, false);
                holder = new ViewHolder();

                holder.ItemGridView = (TextView) convertView.findViewById(R.id.item);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ItemGridView.setText("" + getItem(i));


            //오늘 표시
            t_cal = Calendar.getInstance();

            Integer today = t_cal.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);
            String thisMonth = String.valueOf(t_cal.get(Calendar.MONTH) + 1);

            if (sToday.equals(getItem(i))) {
                holder.ItemGridView.setTextColor(R.color.colorAccent);
            }


            int todayGame = 0;
            gameDay = 0;
            while (dateArray.size() > gameDay) {
                if (dateArray.get(gameDay).month.equals(thisMonth) && dateArray.get(gameDay).day.equals(getItem(i))) {
                    todayGame++;
                }
                gameDay++;
            }
            switch (todayGame) {
                case 0:
                    break;
                case 1:
                case 2:
                    holder.ItemGridView.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case 3:
                case 4:
                    holder.ItemGridView.setBackgroundColor(getResources().getColor(R.color.yellow));
                    break;
                case 5:
                case 6:
                    holder.ItemGridView.setBackgroundColor(getResources().getColor(R.color.orange));
                    break;
                default:
                    holder.ItemGridView.setBackgroundColor(getResources().getColor(R.color.red));
            }


            return convertView;
        }
    }

    private class GridAdapter_l extends BaseAdapter {

        private final List<String> list;

        private final LayoutInflater inflater;

        public GridAdapter_l(Context context, List<String> list) {
            this.list = list;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item, parent, false);
                holder = new ViewHolder();

                holder.ItemGridView = (TextView) convertView.findViewById(R.id.item);


                convertView.setTag(holder);

            } else {
                gameDay = 0;
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ItemGridView.setText("" + getItem(i));


            String thisMonth = String.valueOf(Integer.valueOf(t_cal.get(Calendar.MONTH)));

            int todayGame = 0;
            gameDay = 0;
            while (dateArray.size() > gameDay) {
                if (dateArray.get(gameDay).month.equals(thisMonth) && dateArray.get(gameDay).day.equals(getItem(i))) {
                    todayGame++;
                }
                gameDay++;
            }
            switch (todayGame)

            {
                case 0:
                    break;
                case 1:
                case 2:
                    holder.ItemGridView.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case 3:
                case 4:
                    holder.ItemGridView.setBackgroundColor(getResources().getColor(R.color.yellow));
                    break;
                case 5:
                case 6:
                    holder.ItemGridView.setBackgroundColor(getResources().getColor(R.color.orange));
                    break;
                default:
                    holder.ItemGridView.setBackgroundColor(getResources().getColor(R.color.red));
            }

            return convertView;
        }
    }


    private class ViewHolder {
        TextView ItemGridView;
    }

    public void createDB() {
        DateDB helper = new DateDB(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select year,month,day from date_tb where nickname=?;",new String[]{DL_Manager.getInstance().getNickname().toLowerCase()}, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();

            String y = cursor.getString(0);
            String m = cursor.getString(1);
            String d = cursor.getString(2);
            DateItem dateItem = new DateItem(y, m, d);
            dateArray.add(dateItem);
        }
        db.close();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),FunctionActivity.class);
        startActivity(intent);
        finish();
    }
}

class DateItem{
    String year,month,day;
    DateItem(String year, String month, String day){
        this.year = year;
        this.month = month;
        this.day = day;
    }
}