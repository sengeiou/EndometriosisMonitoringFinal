package com.benlefevre.endometriosismonitoring;

import com.benlefevre.endometriosismonitoring.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class UtilsTest {

    @Test
    public void dateFormatTest(){
        Calendar mcalendar = Calendar.getInstance();
        mcalendar.set(Calendar.DAY_OF_MONTH,1);
        mcalendar.set(Calendar.MONTH,0);
        assertEquals("01/01", Utils.formatDate(mcalendar.getTime()));
    }
}
