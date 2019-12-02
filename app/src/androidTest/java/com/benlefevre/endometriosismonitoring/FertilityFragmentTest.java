package com.benlefevre.endometriosismonitoring;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.benlefevre.endometriosismonitoring.ui.controllers.activities.MainActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.benlefevre.endometriosismonitoring.utils.Constants.DURATION_CYCLE;
import static com.benlefevre.endometriosismonitoring.utils.Constants.LAST_CYCLE_DAY;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PREFERENCES;
import static com.schibsted.spain.barista.assertion.BaristaHintAssertions.assertHint;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertContains;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertTextColorIs;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo;
import static com.schibsted.spain.barista.interaction.BaristaPickerInteractions.setDateOnPicker;
import static com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class FertilityFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(LAST_CYCLE_DAY).apply();
        sharedPreferences.edit().remove(DURATION_CYCLE).apply();
    }

    @Test
    public void fertilityFragmentUitest(){
        clickOn(R.id.fertilityFragment);
        assertDisplayed(R.id.fertility_first_day_legend);
        assertHint(R.id.fertility_first_day_legend,R.string.the_first_day_of_your_last_menstruation);
        assertDisplayed(R.id.fertility_first_day_txt);
        assertDisplayed(R.id.fertility_duration_legend);
        assertHint(R.id.fertility_duration_legend,R.string.the_duration_of_your_cycle_menstruation);
        assertDisplayed(R.id.fertility_duration_txt);
        assertDisplayed(R.id.fertility_update_calendar_btn);
        scrollTo(R.id.temp_chart);
        assertDisplayed(R.id.fertility_save_btn);
        assertDisplayed(R.id.temp_exp_txt);
        assertDisplayed(R.id.temp_slider);
        clickOn(R.id.temp_slider);
        assertContains(R.id.temp_txt,"37.5°");
    }

    @Test
    public void drawCalendarTest(){
        clickOn(R.id.fertilityFragment);

        //        Check previous month drawing
        assertTextColorIs(R.id.box1,R.color.colorBackground);
        assertTextColorIs(R.id.box2,R.color.colorBackground);
        assertTextColorIs(R.id.box3,R.color.colorBackground);
        assertTextColorIs(R.id.box4,R.color.colorBackground);
        assertTextColorIs(R.id.box5,R.color.colorBackground);
        assertTextColorIs(R.id.box6,R.color.colorBackground);

//        Check color of first mens period before drawing
        assertTextColorIs(R.id.box7,R.color.colorPrimary);
        assertTextColorIs(R.id.box8,R.color.colorPrimary);
        assertTextColorIs(R.id.box9,R.color.colorPrimary);
        assertTextColorIs(R.id.box10,R.color.colorPrimary);
        assertTextColorIs(R.id.box11,R.color.colorPrimary);

//        Check color of ovulation period before drawing
        assertTextColorIs(R.id.box17,R.color.colorPrimary);
        assertTextColorIs(R.id.box18,R.color.colorPrimary);
        assertTextColorIs(R.id.box19,R.color.colorPrimary);
        assertTextColorIs(R.id.box20,R.color.colorPrimary);
        assertTextColorIs(R.id.box21,R.color.colorPrimary);

//        Check next month drawing
        assertTextColorIs(R.id.box38,R.color.colorBackground);
        assertTextColorIs(R.id.box39,R.color.colorBackground);
        assertTextColorIs(R.id.box40,R.color.colorBackground);
        assertTextColorIs(R.id.box41,R.color.colorBackground);
        assertTextColorIs(R.id.box42,R.color.colorBackground);

        clickOn(R.id.fertility_first_day_txt);
        setDateOnPicker(2019,12,1);
        assertContains(R.id.fertility_first_day_txt,"01/12/19");
        writeTo(R.id.fertility_duration_txt,"28");
        clickOn(R.id.fertility_update_calendar_btn);

//      Check first mens
        assertTextColorIs(R.id.box7,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box8,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box9,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box10,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box11,R.color.colorOnPrimary);

//        Checks fertilization
        assertTextColorIs(R.id.box17,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box18,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box19,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box20,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box21,R.color.colorOnPrimary);

//        Checks last mens
        assertTextColorIs(R.id.box35,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box36,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box37,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box38,R.color.colorOnPrimary);
        assertTextColorIs(R.id.box39,R.color.colorOnPrimary);
    }

    @Test
    public void tempChartTest(){
        clickOn(R.id.fertilityFragment);
        scrollTo(R.id.temp_chart);
        clickOn(R.id.temp_slider);
        clickOn(R.id.fertility_save_btn);
        LineChart tempChart = mActivityTestRule.getActivity().findViewById(R.id.temp_chart);
        LineData lineData = tempChart.getLineData();
        assertEquals(1,lineData.getEntryCount());
    }
}
