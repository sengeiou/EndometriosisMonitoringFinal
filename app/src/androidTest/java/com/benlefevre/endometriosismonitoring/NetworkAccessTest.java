package com.benlefevre.endometriosismonitoring;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.benlefevre.endometriosismonitoring.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class NetworkAccessTest {

    private Context mContext;

    @Before
    public void setup(){
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void networkAccessUtilsTest(){
        ConnectivityManager cm = (ConnectivityManager) mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            assertTrue(Utils.isNetworkAccessEnabled(mContext));
        }else
            assertFalse(Utils.isNetworkAccessEnabled(mContext));
    }
}
