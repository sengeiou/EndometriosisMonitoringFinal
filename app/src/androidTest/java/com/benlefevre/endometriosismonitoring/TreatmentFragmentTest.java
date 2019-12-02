package com.benlefevre.endometriosismonitoring;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.benlefevre.endometriosismonitoring.ui.controllers.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.benlefevre.endometriosismonitoring.utils.Constants.LAST_CYCLE_DAY;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PILL_CHECK;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PILL_HOUR;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PREFERENCES;
import static com.benlefevre.endometriosismonitoring.utils.Constants.TREATMENT_LIST;
import static com.schibsted.spain.barista.assertion.BaristaCheckedAssertions.assertChecked;
import static com.schibsted.spain.barista.assertion.BaristaCheckedAssertions.assertUnchecked;
import static com.schibsted.spain.barista.assertion.BaristaErrorAssertions.assertError;
import static com.schibsted.spain.barista.assertion.BaristaHintAssertions.assertHint;
import static com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertContains;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaAutoCompleteTextViewInteractions.writeToAutoComplete;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo;
import static com.schibsted.spain.barista.interaction.BaristaPickerInteractions.setDateOnPicker;
import static com.schibsted.spain.barista.interaction.BaristaPickerInteractions.setTimeOnPicker;
import static org.junit.Assert.assertEquals;

public class TreatmentFragmentTest {

    private SharedPreferences mSharedPreferences;
    private Calendar mCalendar;

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void  setup(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mCalendar = Calendar.getInstance();
        mSharedPreferences = context.getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE);
        mSharedPreferences.edit().remove(LAST_CYCLE_DAY).apply();
        mSharedPreferences.edit().remove(PILL_HOUR).apply();
        mSharedPreferences.edit().remove(TREATMENT_LIST).apply();
        mSharedPreferences.edit().remove(PILL_CHECK).apply();
    }

    @Test
    public void treatmentFragmentUiTest(){
        clickOn(R.id.treatmentFragment);
        assertDisplayed(R.id.treatment_pilule);
        assertDisplayed(R.id.treatment_first_day_legend);
        assertDisplayed(R.id.treatment_first_day_txt);
        assertDisplayed(R.id.treatment_hour_pills_label);
        assertDisplayed(R.id.treatment_hour_pills_txt);
        assertDisplayed(R.id.treatment_add_btn);
        assertHint(R.id.treatment_first_day_legend,R.string.the_first_day_of_your_last_menstruation);
        assertHint(R.id.treatment_hour_pills_label,R.string.the_time_you_want_to_be_notified_of_taking_your_pill);
    }

    @Test
    public void saveUserInputInSharedPreferences(){
        clickOn(R.id.treatmentFragment);
        clickOn(R.id.treatment_first_day_txt);
        setDateOnPicker(2019,12,02);
        assertContains(R.id.treatment_first_day_txt,"02/12/19");
        assertEquals("02/12/19",mSharedPreferences.getString(LAST_CYCLE_DAY,null));

        clickOn(R.id.treatment_hour_pills_txt);
        setTimeOnPicker(20,0);
        assertContains(R.id.treatment_hour_pills_txt,"20:00");
        assertEquals("20:00",mSharedPreferences.getString(PILL_HOUR,null));

        clickBack();
        assertContains(R.string.dashboard);
        clickOn(R.id.treatmentFragment);
        assertContains(R.id.treatment_first_day_txt,"02/12/19");
        assertContains(R.id.treatment_hour_pills_txt,"20:00");
    }

    @Test
    public void addSaveAndDeletTreatmentTest(){
        mCalendar.add(Calendar.DAY_OF_YEAR,1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

        clickOn(R.id.treatmentFragment);
        assertNotDisplayed(R.id.treatment_recycler_view);
        clickOn(R.id.treatment_add_btn);
        clickOn(R.id.dialog_treatment_pos_btn);
        assertError(R.id.dialog_treatment_name_legend,R.string.enter_medicament_name);
        assertError(R.id.dialog_treatment_dosage_legend,R.string.enter_medicament_dosage);
        assertError(R.id.dialog_treatment_condi_legend,R.string.enter_medicament_conditioning);
        writeTo(R.id.dialog_treatment_name_txt,"Doliprane");
        writeTo(R.id.dialog_treatment_dosage_txt,"2");
        writeToAutoComplete(R.id.dialog_treatment_duration_txt,"1 day");
        writeToAutoComplete(R.id.dialog_treatment_condi_txt,"tablets");
        clickOn(R.id.dialog_treatment_morning_chip);
        setTimeOnPicker(8,0);
        assertChecked(R.id.dialog_treatment_morning_chip);
        clickOn(R.id.dialog_treatment_pos_btn);

        assertDisplayed(R.id.treatment_recycler_view);
        assertRecyclerViewItemCount(R.id.treatment_recycler_view,1);
        assertContains(R.id.item_treatment_name,"Doliprane");
        assertContains(R.id.item_treatment_duration,dateFormat.format(mCalendar.getTime()));
        assertContains(R.id.item_treatment_dosage,"2");
        assertContains(R.id.item_treatment_hour,"08:00");

        clickBack();
        assertContains(R.string.dashboard);
        clickOn(R.id.treatmentFragment);

        assertContains(R.id.item_treatment_name,"Doliprane");
        assertContains(R.id.item_treatment_duration,dateFormat.format(mCalendar.getTime()));
        assertContains(R.id.item_treatment_dosage,"2");
        assertContains(R.id.item_treatment_hour,"08:00");

        clickOn(R.id.item_treatment_delete);
        assertNotDisplayed(R.id.treatment_recycler_view);
    }

    @Test
    public void saveCheckboxState(){
        clickOn(R.id.treatmentFragment);
        clickOn(R.id.check01);
        clickOn(R.id.check02);
        clickOn(R.id.check025);
        assertChecked(R.id.check01);
        assertChecked(R.id.check02);
        assertChecked(R.id.check025);

        clickOn(R.id.check02);
        assertUnchecked(R.id.check02);

        clickBack();
        assertDisplayed(R.id.dashboardFragment);

        clickOn(R.id.treatmentFragment);
        assertChecked(R.id.check01);
        assertChecked(R.id.check025);
        assertUnchecked(R.id.check02);
    }
}
