package edu.reveart.toilethelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    DatabaseReference ref;

    TextView fieldMessage, fieldReward;

    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ref = FirebaseDatabase.getInstance().getReference("help");

        fieldMessage = findViewById(R.id.txvMessage);
        fieldReward = findViewById(R.id.txvReward);

        Bundle bundle = getIntent().getExtras();
        Help help = bundle.getParcelable("helpinfo");

        Log.d(TAG, "수신 데이터"+help.toString());

        if (help != null) {
            fieldMessage.setText(help.getMessage());
            fieldReward.setText(help.getReward());
        }

        goBack = findViewById(R.id.btnBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
