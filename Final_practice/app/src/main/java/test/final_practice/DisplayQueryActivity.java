package test.final_practice;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static test.final_practice.R.id.btn_confirm_time;


public class DisplayQueryActivity extends AppCompatActivity {

    private TextView list_count;
    public static List<Stock> stockList = new ArrayList<>();
    private Context ctx;
    private SpotAdapter SA;
    public static boolean P_FLAG = false;
    private Button Gold_b;
    private Button Oil_b;
    private Button stn_confirm;
    private static final String STARTTAG = "startTime";
    private static final String ENDTAG = "endTime";
    private static int sHour, sMinute;
    private static int eHour, eMinute;
    private SQLiteDB db;
    private Button btn_start_date, btn_end_date;

    @Override
    protected  void onResume(){
        super.onResume();
        list_count.setText("共" + stockList.size() + "筆資料");
        SA.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_query);
        if (db == null) {
            db = new SQLiteDB(this);
        }
        ctx = this;
        stockList = db.getData();
        Log.d("help", stockList.toString());
        stn_confirm = (Button) findViewById(R.id.btn_confirm_time);
        list_count = (TextView)findViewById(R.id.tv_data_num);
        list_count.setText("共" + stockList.size() + "筆資料");
        btn_start_date = (Button) findViewById(R.id.btn_start_date);
        btn_end_date = (Button) findViewById(R.id.btn_end_date);



        ListView lvPlist = (ListView) findViewById(R.id.lvPList);

        SA = new SpotAdapter(this);

        lvPlist.setAdapter(SA);

        lvPlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // item click
            }
        });

        Gold_b = (Button) findViewById(R.id.btn_gold);
        Gold_b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                stockList = db.getAllGold();
                SA.notifyDataSetChanged();
                list_count.setText("共" + stockList.size() + "筆資料");
                P_FLAG = false;
            }
        });
        Oil_b = (Button) findViewById(R.id.btn_oil);
        Oil_b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                stockList = db.getAllOil();
                SA.notifyDataSetChanged();
                list_count.setText("共" + stockList.size() + "筆資料");
                P_FLAG = false;
            }
        });

    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private class SpotAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private boolean flag = false;

        public SpotAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
            if(flag == false) {

                //memberList.add(new Spot(0, 87, 5566));
                flag = true;
            }
        }

        @Override
        public int getCount() {
            return stockList.size();
        }

        @Override
        public Object getItem(int position) {
            return stockList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return stockList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.d("Position","is:"+position);
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.display_list, parent, false);
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

            Stock stock = stockList.get(position);
            //Log.d("NeetoKnow","is:"+member.getName()+"position:"+position);

            TextView tvName = (TextView) convertView
                    .findViewById(R.id.tv_id);
            tvName.setText(String.valueOf(stock.getId()));

            TextView tvAge = (TextView) convertView
                    .findViewById(R.id.tv_timestamp);
            SimpleDateFormat ft =
                    new SimpleDateFormat("MM.dd 'at' HH:mm:ss");
            //Log.d("display time:",String.valueOf(spot.getTime()));
            Date time = new Date((long)stock.getTimestamp()*1000);
            tvAge.setText(ft.format(time));

            TextView tvPrice = (TextView) convertView
                    .findViewById(R.id.tv_price);
            tvPrice.setText(String.valueOf(stock.getPrice()));

            TextView tvTitle = (TextView) convertView
                    .findViewById(R.id.tv_title);
            tvTitle.setText(stock.getTitle());

            return convertView;
        }


    }

    public void filterByTime(View view){
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, sHour);
        date.set(Calendar.MINUTE, sMinute);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        long sTime = date.getTimeInMillis();

        date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, eHour);
        date.set(Calendar.MINUTE, eMinute);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        long eTime = date.getTimeInMillis();

        stockList = db.getByTime(sTime/1000,eTime/1000);
        SA.notifyDataSetChanged();
        list_count.setText("共" + stockList.size() + "筆資料");

    }

    public void startTime(View view) {
        TimePickerDialogFragment timePickerFragment = new TimePickerDialogFragment();
        FragmentManager fm = getSupportFragmentManager();
        timePickerFragment.show(fm, STARTTAG);
    }

    public void endTime(View view) {
        TimePickerDialogFragment timePickerFragment = new TimePickerDialogFragment();
        FragmentManager fm = getSupportFragmentManager();
        timePickerFragment.show(fm, ENDTAG);
    }

    public static class TimePickerDialogFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String tag = getTag();
            if (STARTTAG.equals(tag)) {
                return new TimePickerDialog(
                        getActivity(), this, sHour, sMinute, false);
            } else {
                return new TimePickerDialog(
                        getActivity(), this, eHour, eMinute, false);
            }
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String tag = getTag();
            if (STARTTAG.equals(tag)) {
                sHour = hourOfDay;
                sMinute = minute;
            } else {
                eHour = hourOfDay;
                eMinute = minute;
            }
        }
    }
}
