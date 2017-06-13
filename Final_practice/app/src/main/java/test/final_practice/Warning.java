package test.final_practice;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by wangbeanz on 13/06/2017.
 */

public class Warning {

    private Context context;
    private Boolean hasSet;
    private String title;
    private String sign;
    private float price;

    public Warning(Context context) {
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences("Warnings", Context.MODE_PRIVATE);
        // default value is false
        hasSet = preferences.getBoolean("hasSetted", false);
        title = preferences.getString("title", "");
        sign = preferences.getString("sign", "");
        price = preferences.getFloat("price", 0);
    }

    public void settingCondition (String title, String sign, double price) {
        this.title = title;
        this.sign = sign;
        this.price = (float) price;
        SharedPreferences preferences = context.getSharedPreferences("Warnings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("hasSetted", true);
        editor.putString("title", this.title);
        editor.putString("sign", this.sign);
        editor.putFloat("price", this.price);
        editor.apply();
    }

    public boolean testCondition(double p) {
        Log.e("warning", "has set: " + hasSet);
        if(!hasSet) {
            Log.e("Warning", "return false");
            return false;
        }
        if (sign.equals(">") && p > price) {
            return true;
        } else if(sign.equals("<") && p < price) {
            return true;
        } else if (sign.equals("=") && p == price) {
            return true;
        }
        return false;
    }

    public boolean getHasSet() {
        return hasSet;
    }

    public String getTitle() {
        return title;
    }

    public String getSign() {
        return sign;
    }

    public double getPrice() {
        return price;
    }

}
