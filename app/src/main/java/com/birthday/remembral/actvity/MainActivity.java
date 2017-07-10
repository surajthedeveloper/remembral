package com.birthday.remembral.actvity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.birthday.remembral.R;
import com.birthday.remembral.flurry.FlurryImpl;
import com.birthday.remembral.logger.LoggerImpl;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // log using logger library
        LoggerImpl.initializeAndroidLogAdapter(true);

        Button textView = (Button)findViewById(R.id.textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // start flurry session
        FlurryImpl.flurryStartSession(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // stop flurry Session
        FlurryImpl.flurryStopSession(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FlurryImpl.flurryLogEvent(" REMEMBRAL LOG EVENT");

        final Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("one", "one");
        stringStringMap.put("two", "two");
        stringStringMap.put("three", "three");
        stringStringMap.put("four", "four");

        FlurryImpl.flurryTimedLogEvent("REMEMBRAL", stringStringMap, true);

        FlurryImpl.flurryEndTimedEvent("REMEMBRAL");

        FlurryImpl.flurryLogError("REMEMBRAL ERROR ID", " remembral error message", new Exception());
    }
}
