package com.birthday.remembral.logger;


import android.os.Build;

import com.birthday.remembral.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class LoggerImplTest {

    @Before
    public void setup() {

    }

    @Test
    public void test() {

    }
}
