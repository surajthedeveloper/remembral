package com.birthday.remembral.activity;

import android.os.Build;
import android.widget.TextView;

import com.birthday.remembral.BuildConfig;
import com.birthday.remembral.R;
import com.birthday.remembral.actvity.MainActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private MainActivity mainActivity;


    @Before
    public void setup() {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void validateTestViewContents() {
        TextView textView = (TextView) mainActivity.findViewById(R.id.textview);

        assertNotNull("textview could not be found ", textView);
        assertTrue(" text contains incorrect text ", "Hello World!".equals(textView.getText().toString()));
    }
}
