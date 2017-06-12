package test.final_practice;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SimpleCursorAdapter;
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

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class DisplayQueryActivity extends AppCompatActivity {

    /* display data and making queries based on time and prices */
    private static int sHour, sMinute;
    private static int eHour, eMinute;
    private TextView tv_from_to, tv_data_num;
    private Button btn_start_date, btn_end_date;
    private static final String STARTTAG = "startTime";
    private static final String ENDTAG = "endTime";

    private SQLiteDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_query);
        init();
    }


    private void init() {
        if (db == null) {
            db = new SQLiteDB(getApplicationContext());
        }
        /* setting the init time to current time */
        Calendar calendar = Calendar.getInstance();
        eHour = sHour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        eMinute = sMinute = calendar.get(java.util.Calendar.MINUTE);
        tv_from_to = (TextView) findViewById(R.id.tv_from_to);
        tv_data_num = (TextView) findViewById(R.id.tv_data_num);
        tv_data_num.setText("總共有：" + db.getCount() + "筆資料");
        btn_start_date = (Button) findViewById(R.id.btn_start_date);
        btn_end_date = (Button) findViewById(R.id.btn_end_date);

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

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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
