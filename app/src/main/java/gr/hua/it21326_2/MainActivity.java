package gr.hua.it21326_2;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView t;
    private LocationManager locationManager;
    private LocationListener listener;
    public static final String COL_2 = "userid" ;
    public static final String COL_3 = "longitude" ;
    public static final String COL_4 = "latitude" ;
    public static final String COL_5 = "dt" ;
    private static final String AUTH = "gr.hua.it21326.DataProvider";
    public static final Uri COORDINATES_URI = Uri.parse("content://" + AUTH + "/data");
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAPM();


        t = (TextView) findViewById(R.id.textView);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                t.setText("\n " + location.getLongitude() + " " + location.getLatitude());

                ContentValues values = new ContentValues();
                values.put(COL_2, "userid");
                values.put(COL_3, String.valueOf(location.getLongitude()));
                values.put(COL_4, String.valueOf(location.getLatitude()));
                values.put(COL_5, new SimpleDateFormat("d/M/yyyy HH:mm:ss", Locale.ENGLISH).format(new Date()));
                getContentResolver().insert(COORDINATES_URI, values);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

       getcoordinates();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 21326:
                getcoordinates();
                break;
            default:
                break;
        }
    }

    public void getcoordinates(){

            // first check for permissions
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                            , 21326);
                }
                return;
            }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 3000, 1, listener);
    }

    private void checkAPM(){

       // IntentFilter intentFilter = new IntentFilter("android.intent.action.AIRPLANE_MODE");
        BroadcastReceiver broadcastRxeceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if(Settings.System.getInt(context.getContentResolver(),Settings.System.AIRPLANE_MODE_ON, 0) == 1 ){

                   //startService(new Intent(context, MainActivity.class));

                    return;
                }else {
                    Toast.makeText(context,"close airplane mode",Toast.LENGTH_SHORT).show();
                    //context.stopService()(new Intent(context, MainActivity.class));

                }
            }
        };

       // registerReceiver(broadcastReceiver,intentFilter);

    }

/*    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);

    }*/
}
