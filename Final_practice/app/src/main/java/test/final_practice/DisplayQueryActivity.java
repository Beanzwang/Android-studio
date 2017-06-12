package test.final_practice;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DisplayQueryActivity extends AppCompatActivity {

    /* display data and making queries based on time and prices */
    private static int sHour, sMinute;
    private static int eHour, eMinute;
    private TextView tv_from_to;
    private Button btn_start_date, btn_end_date;
    private static final String STARTTAG = "startTime";
    private static final String ENDTAG = "endTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_query);
        init();
    }

    private void init() {
        /* setting the init time to current time */
        Calendar calendar = Calendar.getInstance();
        eHour = sHour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        eMinute = sMinute = calendar.get(java.util.Calendar.MINUTE);
        tv_from_to = (TextView) findViewById(R.id.tv_from_to);
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
