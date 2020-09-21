package gr.hua.it21326_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by xrhstos on 5/2/2018.
 */

public class CheckAirMode extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if(isEnabled(context) == false ){

            return;
        }else {

            Toast.makeText(context,"close the airplane mode",Toast.LENGTH_SHORT).show();

        }
    }

    public static boolean isEnabled(Context context){
           return Settings.System.getInt(context.getContentResolver(),Settings.System.AIRPLANE_MODE_ON, 0) == 1;
    }
}

