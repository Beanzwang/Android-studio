package test.final_practice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsActivity extends AppCompatActivity {

    private Spinner spi_title, spi_condition;
    private Button btn_confirm;
    private EditText et_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init() {
        spi_title = (Spinner) findViewById(R.id.spi_title);
        spi_condition = (Spinner) findViewById(R.id.spi_condition);
        ArrayAdapter titleAdapter = ArrayAdapter.createFromResource(this, R.array.title,
                android.R.layout.simple_spinner_item);
        ArrayAdapter conditionAdapter = ArrayAdapter.createFromResource(this, R.array.conditions,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spi_title.setAdapter(titleAdapter);
        spi_condition.setAdapter(conditionAdapter);
        btn_confirm = (Button) findViewById(R.id.btn_confirm_settings);
        et_price = (EditText) findViewById(R.id.et_price);
    }

    public void confirmSettings(View view) {
        String title;
        String conditions;
        if (checkPrice(et_price.getText().toString())) {
            title = spi_title.getSelectedItem().toString();
            conditions = spi_condition.getSelectedItem().toString();
            settingConditions(title, conditions);
        } else {
            Toast.makeText(this, "Invalid price format.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPrice(String p) {
        String pattern = "[1-9]([0-9])*(.)?([0-9])*";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(p);
        return m.find();
    }

    private void settingConditions(String title, String conditions) {
        // DB queries
        if (title.equals("GOLD")) {
            if (conditions.equals(">")) {

            } else if (conditions.equals("<")) {

            } else {

            }
        } else {
            if (conditions.equals(">")) {

            } else if (conditions.equals("<")) {

            } else {

            }
        }
    }

}
