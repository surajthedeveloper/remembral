package com.birthday.remembral.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.birthday.remembral.R;
import com.birthday.remembral.flurry.FlurryImpl;
import com.birthday.remembral.logger.LoggerImpl;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // log using logger library
        LoggerImpl.initializeAndroidLogAdapter(true);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult!=null){
                    loginResult.getAccessToken().getUserId();;
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
