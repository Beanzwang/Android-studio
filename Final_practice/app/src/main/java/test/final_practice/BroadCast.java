package test.final_practice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * Created by wangbeanz on 13/06/2017.
 */

public class BroadCast extends BroadcastReceiver {

    private Context context;
    private Activity activity;
    private Notification notification;

    public BroadCast(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        notification = new Notification(context, activity);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast toast = Toast.makeText(context, "Time over!", Toast.LENGTH_LONG);
//        toast.show();
//        MediaPlayer mp = MediaPlayer.create(activity, R.raw.alarm);
//        try {
//            mp.start();
//        } catch (Exception e) {
//            //例外發生時處理
//        }
        notification.popping();
    }
}
