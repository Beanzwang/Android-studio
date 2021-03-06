package test.final_practice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

import static android.provider.ContactsContract.Intents.Insert.ACTION;

/**
 * Created by wangbeanz on 11/06/2017.
 */

public class InternetService extends Service{

    private Document docs_gold, docs_oil;

    int mStartMode;       // indicates how to behave if the service is killed
    private IBinder mBinder = new InternetBinder();      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used
    private final URL gold_url = new URL("https://finance.yahoo.com/quote/GC=F?p=GC=F");
    private final URL oil_url = new URL("https://finance.yahoo.com/quote/CL=F?p=CL=F");
    private final String GOLD = "GOLD";
    private final String OIL = "OIL";
    private long delay = (long) (10 * 1000);  /* millisecond */
    private Timer timer;
    private SQLiteDB db;
    public static final String ACTION = "Stock Service";
    private Warning warning;

    class InternetBinder extends Binder {
        InternetService getService() { return InternetService.this; }
    }

    public InternetService() throws MalformedURLException {

    }


    private void schedule() {
        Toast.makeText(this, "Schedule", Toast.LENGTH_LONG).show();
        Log.e("Scheduled", "schedule!");
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            public void run() {
                // send broadcast
                try {
                    /* setting timeout to 10s */
                    docs_gold = Jsoup.parse(gold_url, 10000);
                    docs_oil = Jsoup.parse(oil_url, 10000);
                    Elements eles_gold = docs_gold.select("span[class=Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)]");
                    Elements eles_oil = docs_oil.select("span[class=Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)]");
                    Number n_gold = NumberFormat.getNumberInstance(java.util.Locale.US).parse(eles_gold.first().text());
                    Number n_oil = NumberFormat.getNumberInstance(java.util.Locale.US).parse(eles_oil.first().text());
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    Log.e("Scrapping", eles_gold.first().text());
                    Log.e("Scrapping", eles_oil.first().text());
                    Log.e("Scrapping", String.valueOf(timestamp.getTime()));
                    long time = timestamp.getTime() / 1000;

                    double price;
                    if (warning.getTitle().equals(GOLD)) {
                        price = n_gold.doubleValue();
                    } else {
                        price = n_oil.doubleValue();
                    }
                    if (warning.testCondition(price)) {
                        sendBroadcast(new Intent(ACTION));
                    }
                    db.insert(new Stock(GOLD, n_gold.doubleValue(), time));
                    db.insert(new Stock(OIL, n_oil.doubleValue(), time));
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        // set time
        timer.schedule(timerTask, 0, delay);
    }

    public void setDelay(int delay) {
        // millisecond
        this.delay = delay * 1000;
        schedule();
    }


    @Override
    public void onCreate() {
        // The service is being created
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        return mStartMode;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        warning = new Warning(this);
        if (db == null) {
            db = new SQLiteDB(getApplicationContext());
        }
        schedule();
        return mBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        return mAllowRebind;
    }
    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called

    }
    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
        Log.e("onDestroy", "destroy Timer");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
