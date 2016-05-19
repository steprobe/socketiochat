package com.steware.socketiochatapp;

import android.os.Build;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.ResourceLoader;
import org.robolectric.shadows.ShadowLog;

//https://github.com/frmi/CISample

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP, packageName = "com.steware.socketiochatapp")
@RunWith(RobolectricGradleTestRunner.class)
public class RoboTest {

    private MainActivity mMainActivity;

    @Before
    public void setup() {
        ShadowLog.stream = System.out;
        mMainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void checkConnection() {

        ResourceLoader org = Robolectric.getShadowsAdapter().getResourceLoader();
        //TextView messages = (TextView)mMainActivity.findViewById(R.id.messages);
        Log.e("STEO", "we gots ");// + messages.getText());
    }

}
