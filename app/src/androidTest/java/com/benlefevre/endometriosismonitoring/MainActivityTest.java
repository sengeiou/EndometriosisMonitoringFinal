package com.benlefevre.endometriosismonitoring;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.benlefevre.endometriosismonitoring.ui.controllers.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.schibsted.spain.barista.assertion.BaristaDrawerAssertions.assertDrawerIsClosed;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertContains;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainBottomNavTest(){
//       check if bottom is displayed with the right menu
        assertDisplayed(R.id.main_bottom_nav);
        assertDisplayed(R.id.dashboardFragment);
        assertDisplayed(R.id.treatmentFragment);
        assertDisplayed(R.id.fertilityFragment);
        assertDisplayed(R.id.doctorFragment);
//        check that DashboarFragment is displayed and toolbar title is updated
        assertContains(R.string.dashboard);
//        check navigation to treatmentFragment
        clickOn(R.id.treatmentFragment);
//        check if TreatmentFragment is displayed and toolbar title is updated
        assertContains(R.string.treatment);
        clickBack();
//        check if the navigation is right
        assertContains(R.string.dashboard);
//        the same check for FertilityFragment
        clickOn(R.id.fertilityFragment);
        assertContains(R.string.fertility);
        clickBack();

        assertContains(R.string.dashboard);
//        the same check for DoctorFragment
        clickOn(R.id.doctorFragment);
        assertContains(R.string.doctor);
        clickBack();

        assertContains(R.string.dashboard);
    }

    @Test
    public void mainDrawerNavTest(){
        openDrawer();
        assertDisplayed(R.id.dashboardFragment);
        assertDisplayed(R.id.treatmentFragment);
        assertDisplayed(R.id.fertilityFragment);
        assertDisplayed(R.id.doctorFragment);
        assertContains(R.string.settings);
        assertContains(R.string.log_out);
        clickBack();
        assertDrawerIsClosed();

        openDrawer();
        clickOn(R.id.treatmentFragment);
        assertContains(R.string.treatment);
        clickBack();
        assertContains(R.string.dashboard);

        openDrawer();
        clickOn(R.id.fertilityFragment);
        assertContains(R.string.fertility);
        clickBack();
        assertContains(R.string.dashboard);

        openDrawer();
        clickOn(R.id.doctorFragment);
        assertContains(R.string.doctor);
        clickBack();
        assertContains(R.string.dashboard);

        openDrawer();
        clickOn(R.id.settingFragment);
        assertContains(R.string.settings);
        clickBack();
        assertContains(R.string.dashboard);
    }

    @Test
    public void mainFabTest(){
        clickOn(R.id.dashboard_fab);
        assertContains(R.string.my_pain);
        clickBack();
        assertContains(R.string.dashboard);
    }
}
