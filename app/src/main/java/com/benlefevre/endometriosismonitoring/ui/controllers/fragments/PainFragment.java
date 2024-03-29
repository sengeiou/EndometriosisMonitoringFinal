package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.injection.Injection;
import com.benlefevre.endometriosismonitoring.injection.ViewModelFactory;
import com.benlefevre.endometriosismonitoring.models.Action;
import com.benlefevre.endometriosismonitoring.models.FirestorePain;
import com.benlefevre.endometriosismonitoring.models.Mood;
import com.benlefevre.endometriosismonitoring.models.Pain;
import com.benlefevre.endometriosismonitoring.models.Symptom;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PainFragment extends Fragment {


    @BindView(R.id.pain_slider)
    Slider mPainSlider;
    @BindView(R.id.pain_value)
    MaterialTextView mPainValueTxt;
    @BindView(R.id.pain_chip_abdo)
    Chip mChipAbdo;
    @BindView(R.id.pain_chip_bladder)
    Chip mChipBladder;
    @BindView(R.id.pain_chip_back)
    Chip mChipBack;
    @BindView(R.id.pain_chip_head)
    Chip mChipHead;
    @BindView(R.id.pain_chip_intestine)
    Chip mChipIntestine;
    @BindView(R.id.pain_chip_breast)
    Chip mChipBreast;
    @BindView(R.id.pain_chip_vagina)
    Chip mChipVagina;
    @BindView(R.id.pain_chip_burns)
    Chip mChipBurns;
    @BindView(R.id.pain_chip_cramps)
    Chip mChipCramps;
    @BindView(R.id.pain_chip_bleeding)
    Chip mChipBleeding;
    @BindView(R.id.pain_chip_fever)
    Chip mChipFever;
    @BindView(R.id.pain_chip_chills)
    Chip mChipChills;
    @BindView(R.id.pain_chip_bloating)
    Chip mChipBloating;
    @BindView(R.id.pain_chip_hot_flush)
    Chip mChipHotFlush;
    @BindView(R.id.pain_chip_diarrhea)
    Chip mChipDiarrhea;
    @BindView(R.id.pain_chip_constipation)
    Chip mChipConstipation;
    @BindView(R.id.pain_chip_nausea)
    Chip mChipNausea;
    @BindView(R.id.pain_chip_tired)
    Chip mChipTired;
    @BindView(R.id.pain_card_sport)
    CardView mCardSport;
    @BindView(R.id.pain_card_sleep)
    CardView mCardSleep;
    @BindView(R.id.pain_card_zen)
    CardView mCardZen;
    @BindView(R.id.pain_card_stress)
    CardView mCardStress;
    @BindView(R.id.pain_card_sex)
    CardView mCardSex;
    @BindView(R.id.pain_sad_chip)
    Chip mSadChip;
    @BindView(R.id.pain_sick_chip)
    Chip mSickChip;
    @BindView(R.id.pain_irritated_chip)
    Chip mIrritatedChip;
    @BindView(R.id.pain_happy_chip)
    Chip mHappyChip;
    @BindView(R.id.pain_very_happy_chip)
    Chip mVeryHappyChip;
    @BindView(R.id.pain_save_btn)
    MaterialButton mSaveBtn;
    @BindView(R.id.frameLayout2)
    FrameLayout mFrameLayout2;
    @BindView(R.id.materialTextView8)
    MaterialTextView mMaterialTextView8;
    @BindView(R.id.slider_pain_legend)
    MaterialTextView mSliderPainLegend;
    @BindView(R.id.materialTextView2)
    MaterialTextView mMaterialTextView2;
    @BindView(R.id.chipGroup_pain_location)
    ChipGroup mChipGroupPainLocation;
    @BindView(R.id.linearLayout2)
    LinearLayout mLinearLayout2;
    @BindView(R.id.materialTextView)
    MaterialTextView mMaterialTextView;
    @BindView(R.id.chipGroup_symptoms)
    ChipGroup mChipGroupSymptoms;
    @BindView(R.id.frameLayout3)
    FrameLayout mFrameLayout3;
    @BindView(R.id.activity_txt)
    MaterialTextView mActivityTxt;
    @BindView(R.id.frameLayout4)
    FrameLayout mFrameLayout4;
    @BindView(R.id.materialTextView4)
    MaterialTextView mMaterialTextView4;
    @BindView(R.id.chipGroup_mood)
    ChipGroup mChipGroupMood;

    private Activity mActivity;
    private NavController mNavController;
    private SharedViewModel mViewModel;
    private Pain mPain;
    private FirestorePain mFirestorePain;
    private Mood mUserMood;
    private String mUserActivityChoice;
    private List<Action> mActionList;

    private View mCustomDialog;
    private MaterialAutoCompleteTextView mActionName;
    private MaterialTextView mDurationTxt;
    private MaterialTextView mIntensityTxt;
    private Slider mIntensitySlider;
    private Slider mDurationSlider;

    public PainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        if (mActivity != null)
            mNavController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
        mActionList = new ArrayList<>();
        configureViewModel();
        configureSlider();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(mActivity);
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }

    /**
     * Configures an OChangedListener for the slider to bind the slider value into a TextView
     */
    private void configureSlider() {
        mPainSlider.setOnChangeListener((slider, value) -> mPainValueTxt.setText(String.valueOf((int) slider.getValue())));
    }

    /**
     * Fetches the user input to create a Pain and a FirestorePain
     */
    private void getUserPainInput() {
        int painValue = (int) mPainSlider.getValue();
        Date date = new Date();
        String location = "";
        if (mChipAbdo.isChecked())
            location += mChipAbdo.getText();
        if (mChipBladder.isChecked())
            location += mChipBladder.getText();
        if (mChipBack.isChecked())
            location += mChipBack.getText();
        if (mChipBreast.isChecked())
            location += mChipBreast.getText();
        if (mChipHead.isChecked())
            location += mChipHead.getText();
        if (mChipIntestine.isChecked())
            location += mChipIntestine.getText();
        if (mChipVagina.isChecked())
            location += mChipVagina.getText();
        mPain = new Pain(date, painValue, location);
        mFirestorePain = new FirestorePain(date,painValue,location);
    }

    /**
     * Verifies if a chip is checked and creates a Mood with the corresponding values
     */
    private void createMood() {
        if (mSadChip.isChecked())
            mUserMood = new Mood(0, mSadChip.getText().toString());
        if (mSickChip.isChecked())
            mUserMood = new Mood(0, mSickChip.getText().toString());
        if (mIrritatedChip.isChecked())
            mUserMood = new Mood(0, mIrritatedChip.getText().toString());
        if (mHappyChip.isChecked())
            mUserMood = new Mood(0, mHappyChip.getText().toString());
        if (mVeryHappyChip.isChecked())
            mUserMood = new Mood(0, mVeryHappyChip.getText().toString());
    }

    /**
     * Saves all the user inputs into Room with the ViewModel.
     * Begins by save the pain in Room and fetch the PrimaryKey to save Symptoms, Actions and Mood with it
     */
    private void saveUserInputInRoom(){
        long rowId = mViewModel.createPain(mPain);
        if (rowId != -1){
            saveSymptomsInDb(rowId);
            saveActionsInDb(rowId);
            if (mUserMood != null){
                saveMoodInDb(rowId);
            }
        }
        saveUserInputInFirestore();
    }

    /**
     * Saves the FirestorePain set with the use's input in FireStore
     */
    private void saveUserInputInFirestore() {
        mViewModel.createFirestorePain(mFirestorePain);
    }

    /**
     * Saves the user mood in Room after sets the painId into the mood. Sets the mood to the Firestore
     * Pain to save it in FireStore
     * @param rowId the id of the saved pain in Room
     */
    private void saveMoodInDb(long rowId){
        mUserMood.setPainId(rowId);
        mViewModel.createMood(mUserMood);
        mFirestorePain.setMood(mUserMood.getValue());
    }

    /**
     * Saves all user's Actions in Room and FireStore
     * @param rowId the id of the user pain saved in Room
     */
    private void saveActionsInDb(long rowId){
        for (Action action : mActionList){
            action.setPainId(rowId);
        }
        mViewModel.createActions(mActionList);
        mViewModel.createFirestoreAction(mActionList);
    }

    /**
     * Checks which chip are checked and save them in Room with the Pain PrimaryKey ad ForeignKey
     * @param rowId the saved Pain PrimaryKey
     */
    private void saveSymptomsInDb(long rowId) {
        Date date = new Date();
        List<Symptom> symptomsList = new ArrayList<>();
        List<String> symptomsName = new ArrayList<>();
        if (mChipBurns.isChecked()) {
            Symptom s1 = new Symptom(rowId, mChipBurns.getText().toString(),date);
            symptomsList.add(s1);
            symptomsName.add(mChipBurns.getText().toString());
        }
        if (mChipCramps.isChecked()) {
            Symptom s2 = new Symptom(rowId, mChipCramps.getText().toString(),date);
            symptomsList.add(s2);
            symptomsName.add(mChipCramps.getText().toString());
        }
        if (mChipBleeding.isChecked()) {
            Symptom s3 = new Symptom(rowId, mChipBleeding.getText().toString(),date);
            symptomsList.add(s3);
            symptomsName.add(mChipBleeding.getText().toString());
        }
        if (mChipFever.isChecked()) {
            Symptom s4 = new Symptom(rowId, mChipFever.getText().toString(),date);
            symptomsList.add(s4);
            symptomsName.add(mChipFever.getText().toString());
        }
        if (mChipBloating.isChecked()) {
            Symptom s5 = new Symptom(rowId, mChipBloating.getText().toString(),date);
            symptomsList.add(s5);
            symptomsName.add(mChipBloating.getText().toString());
        }
        if (mChipChills.isChecked()) {
            Symptom s6 = new Symptom(rowId, mChipChills.getText().toString(),date);
            symptomsList.add(s6);
            symptomsName.add(mChipChills.getText().toString());
        }
        if (mChipConstipation.isChecked()) {
            Symptom s7 = new Symptom(rowId, mChipConstipation.getText().toString(),date);
            symptomsList.add(s7);
            symptomsName.add(mChipConstipation.getText().toString());
        }
        if (mChipDiarrhea.isChecked()) {
            Symptom s8 = new Symptom(rowId, mChipDiarrhea.getText().toString(),date);
            symptomsList.add(s8);
            symptomsName.add(mChipDiarrhea.getText().toString());
        }
        if (mChipHotFlush.isChecked()) {
            Symptom s9 = new Symptom(rowId, mChipHotFlush.getText().toString(),date);
            symptomsList.add(s9);
            symptomsName.add(mChipHotFlush.getText().toString());
        }
        if (mChipNausea.isChecked()){
            Symptom s10 = new Symptom(rowId, mChipNausea.getText().toString(),date);
            symptomsList.add(s10);
            symptomsName.add(mChipNausea.getText().toString());
        }
        if (mChipTired.isChecked()){
            Symptom s11 = new Symptom(rowId, mChipTired.getText().toString(),date);
            symptomsList.add(s11);
            symptomsName.add(mChipTired.getText().toString());
        }
        mViewModel.createSymptoms(symptomsList);
        mFirestorePain.setSymptoms(symptomsName);
    }

    private void openCustomDialog(int viewId){
        configureCustomDialog(viewId);

        ArrayAdapter<CharSequence> sportAdapter = new ArrayAdapter<>(mActivity,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.sport));
        mActionName.setAdapter(sportAdapter);
        mActionName.setOnItemClickListener((parent, view, position, id) -> mUserActivityChoice = parent.getItemAtPosition(position).toString());
        mIntensitySlider.setOnChangeListener((slider, value) -> mIntensityTxt.setText(String.valueOf((int)value)));
        mDurationSlider.setOnChangeListener((slider, value) -> mDurationTxt.setText(String.valueOf((int)value)));

        new MaterialAlertDialogBuilder(mActivity)
                .setCancelable(false)
                .setView(mCustomDialog)
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    Action action = new Action (0, mUserActivityChoice,(int)mDurationSlider.getValue(),
                            (int)mIntensitySlider.getValue(),(int)mPainSlider.getValue(),new Date());
                    mActionList.add(action);
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel())
                .show();

    }

    /**
     * Configures all needed views for the opened custom alert dialog when user press a card
     * @param viewId the pressed view id
     */
    private void configureCustomDialog(int viewId) {
        mCustomDialog = mActivity.getLayoutInflater().inflate(R.layout.dialog_action,null);
        TextInputLayout actionTxt = mCustomDialog.findViewById(R.id.action_legend);
        mActionName = mCustomDialog.findViewById(R.id.action_text);

        mDurationTxt = mCustomDialog.findViewById(R.id.duration_txt);
        MaterialTextView durationLegend = mCustomDialog.findViewById(R.id.duration_legend);
        mDurationTxt.setText("0");

        mIntensityTxt = mCustomDialog.findViewById(R.id.intensity_txt);
        MaterialTextView intensityLegend = mCustomDialog.findViewById(R.id.intensity_legend);
        mIntensityTxt.setText("0");

        mIntensitySlider = mCustomDialog.findViewById(R.id.intensity_slider);
        mDurationSlider = mCustomDialog.findViewById(R.id.duration_slider);

        if (viewId == R.id.pain_card_sleep || viewId == R.id.pain_card_stress){
            actionTxt.setVisibility(View.GONE);
            mActionName.setVisibility(View.GONE);
            durationLegend.setVisibility(View.GONE);
            mDurationSlider.setVisibility(View.GONE);
            mDurationTxt.setVisibility(View.GONE);
            mIntensityTxt.setVisibility(View.VISIBLE);
            mIntensitySlider.setVisibility(View.VISIBLE);
            intensityLegend.setVisibility(View.VISIBLE);
        }else if (viewId == R.id.pain_card_sport){
            actionTxt.setVisibility(View.VISIBLE);
            mActionName.setVisibility(View.VISIBLE);
            durationLegend.setVisibility(View.VISIBLE);
            mDurationSlider.setVisibility(View.VISIBLE);
            mDurationTxt.setVisibility(View.VISIBLE);
            mIntensityTxt.setVisibility(View.VISIBLE);
            mIntensitySlider.setVisibility(View.VISIBLE);
            intensityLegend.setVisibility(View.VISIBLE);
            intensityLegend.setText(R.string.stress_intensity_scale);
        }else {
            actionTxt.setVisibility(View.GONE);
            mActionName.setVisibility(View.GONE);
            mIntensityTxt.setVisibility(View.GONE);
            mIntensitySlider.setVisibility(View.GONE);
            intensityLegend.setVisibility(View.GONE);
        }

        if (viewId == R.id.pain_card_sleep)
            intensityLegend.setText(R.string.sleep_quality_rate);
        if (viewId == R.id.pain_card_stress)
            intensityLegend.setText(R.string.stress_intensity_scale);
    }


    @OnClick({R.id.pain_card_sport, R.id.pain_card_sleep, R.id.pain_card_zen, R.id.pain_card_stress, R.id.pain_card_sex, R.id.pain_save_btn})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pain_card_sport:
                openCustomDialog(view.getId());
                break;
            case R.id.pain_card_sleep:
                openCustomDialog(view.getId());
                mUserActivityChoice = getString(R.string.sleep);
                break;
            case R.id.pain_card_zen:
                openCustomDialog(view.getId());
                mUserActivityChoice = getString(R.string.relaxation);
                break;
            case R.id.pain_card_stress:
                openCustomDialog(view.getId());
                mUserActivityChoice = getString(R.string.stress);
                break;
            case R.id.pain_card_sex:
                openCustomDialog(view.getId());
                mUserActivityChoice = getString(R.string.sex);
                break;
            case R.id.pain_save_btn:
                getUserPainInput();
                createMood();
                saveUserInputInRoom();
                mNavController.navigate(R.id.dashboardFragment);
                break;
        }
    }
}
