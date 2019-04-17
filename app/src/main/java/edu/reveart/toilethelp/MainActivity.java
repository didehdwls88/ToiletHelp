package edu.reveart.toilethelp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    private static final String TAG = "lastday";
    Toolbar myToolbar;
    Button help;
    public static Context mContext;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");

        help = findViewById(R.id.btnHelp);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkLocationPermission();
                } else {
                    goHelp();
                }
            }
        });

        if (getIntent().getExtras() != null) {

            Log.d("MainActivity", "push Y");

            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
        } else {
            Log.d("MainActivity", "push N");
        }
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("이 앱은 사용자의 현재 위치권한을 필요로합니다! 권한 요청을 하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestLocationPermission();
                            }
                        })
                        .create()
                        .show();
            } else {
                requestLocationPermission();
            }


        } else {
            goHelp();
        }
    }

    public void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION);
    }

    

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION) {

            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goHelp();
            } else {
                Toast.makeText(MainActivity.this, "이 앱은 위치권한 서비스가 필요합니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void goHelp() {
        startActivity(new Intent(MainActivity.this, SendActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_help:
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                return true;

            default:
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

        }
    }

    public void distanceSelect() {
        SharedPreferences sharedPreferences = getSharedPreferences("listDistance", 0);
        String spData = sharedPreferences.getString("listDistance", "");

        LocationManager myMeter = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        /*Location myLocation = myMeter.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double mLng = myLocation.getLongitude();
        double mLat = myLocation.getLatitude();

        myLocation.setLongitude(mLng);
        myLocation.setLatitude(mLat);*/

        ref = FirebaseDatabase.getInstance().getReference("help");

        Bundle bundle = getIntent().getExtras();
        Help help = bundle.getParcelable("helpinfo");

        Log.d(TAG, "수신 데이터"+help.toString());

        /*double yLat = help.getLatitude();
        double yLng = help.getLongitude();

        Location yourLocation = new Location("yourLocation");
        yourLocation.setLatitude(yLat);
        yourLocation.setLongitude(yLng);

        double distance = myLocation.distanceTo(yourLocation);

        String one = "100m";
        String two = "200m";
        String three = "500m";
        String four = "1000m(1km)";
        String five = "2000m(2km)";
        String six = "5000m(5km)";
        String seven = "10000m(10km)";

        if (spData == one) {
            if (distance >= 100) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("push");
            }
        } else if (spData == two) {
            if (distance >= 200) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("push");
            }
        } else if (spData == three) {
            if (distance >= 500) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("push");
            }
        } else if (spData == four) {
            if (distance >= 1000) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("push");
            }
        } else if (spData == five) {
            if (distance >= 2000) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("push");
            }
        } else if (spData == six) {
            if (distance >= 5000) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("push");
            }
        } else if (spData == seven) {
            if (distance >= 10000) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("push");
            }
        } else {
            Toast.makeText(MainActivity.this, R.string.warningDistance, Toast.LENGTH_SHORT).show();
        }*/
    }
}



