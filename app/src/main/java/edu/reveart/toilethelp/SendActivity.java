package edu.reveart.toilethelp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SendActivity extends AppCompatActivity {

    EditText fieldMessage, fieldReward;
    TextView fieldDate, gps1, gps2;
    Button help;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        /*LocationManager meter = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
        Location location = meter.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        String provider = location.getProvider();
        final double longitude = location.getLongitude();
        final double latitude = location.getLatitude();*/


        ref = FirebaseDatabase.getInstance().getReference("help");

        fieldMessage = findViewById(R.id.edtContent);
        fieldReward = findViewById(R.id.edtReward);
        fieldDate = findViewById(R.id.edtDate);
        /*gps1 = findViewById(R.id.gpsF);
        gps2 = findViewById(R.id.gpsS);*/

        help = findViewById(R.id.btnSend);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = fieldMessage.getText().toString();
                String reward = fieldReward.getText().toString();
                long date = System.currentTimeMillis();

                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(SendActivity.this, "어디계신지 알려주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Help help = new Help(message, reward, date, longitude, latitude);
                Help help = new Help(message, reward, date);

                ref.push().setValue(help);
                fieldMessage.setText("");
                fieldReward.setText("");
                fieldDate.setText("");
                /*gps1.setText("");
                gps2.setText("");*/

                startActivity(new Intent(SendActivity.this, ListActivity.class));
                finish();

            }
        });
    }



}
