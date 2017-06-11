package test.final_practice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    /* compile jsoup in grandle */
    Button btn_start_service, btn_confirm;
    EditText et_confirm;
    private InternetService mService;
    private boolean mBound = false;

    SQLiteDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if (db == null) {
            db = new SQLiteDB(getApplicationContext());
        }
    }

    private void init() {
        btn_start_service = (Button) findViewById(R.id.btn_start_service);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        et_confirm = (EditText) findViewById(R.id.et_confirm);
    }

    public boolean check_connection() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            Toast.makeText(this, "You need to connect to the Internet first!", Toast.LENGTH_LONG).show();
        }

        return isConnected;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            InternetService.InternetBinder binder = (InternetService.InternetBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public void startService(View view) {
        if(check_connection()) {
            Intent intent = new Intent(this, InternetService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            Toast.makeText(this, "Connection start", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void confirmTimer(View view) {
        String pattern = "[1-9]([0-9])*";
        String string = et_confirm.getText().toString();
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(string);
        if (m.find()) {
            mService.setDelay(Integer.parseInt(string));
        } else {
            Toast.makeText(this, "inValid pattern in edit text.", Toast.LENGTH_SHORT).show();
        }
    }
}
