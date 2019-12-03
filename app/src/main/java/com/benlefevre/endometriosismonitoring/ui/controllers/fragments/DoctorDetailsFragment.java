package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.injection.Injection;
import com.benlefevre.endometriosismonitoring.injection.ViewModelFactory;
import com.benlefevre.endometriosismonitoring.models.Commentary;
import com.benlefevre.endometriosismonitoring.models.Doctor;
import com.benlefevre.endometriosismonitoring.models.User;
import com.benlefevre.endometriosismonitoring.ui.adapters.CommentaryAdapter;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.benlefevre.endometriosismonitoring.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DoctorDetailsFragment extends Fragment {


    @BindView(R.id.doctor_details_name)
    MaterialTextView mDoctorDetailsName;
    @BindView(R.id.doctor_details_civilite)
    MaterialTextView mDoctorDetailsCivilite;
    @BindView(R.id.doctor_details_spec)
    MaterialTextView mDoctorDetailsSpec;
    @BindView(R.id.doctor_details_address)
    MaterialTextView mDoctorDetailsAddress;
    @BindView(R.id.phone_legend)
    MaterialTextView mPhoneLegend;
    @BindView(R.id.doctor_details_telephone)
    MaterialTextView mDoctorDetailsPhone;
    @BindView(R.id.types_actes_legend)
    MaterialTextView mTypesActesLegend;
    @BindView(R.id.doctor_details_type_acte)
    MaterialTextView mDoctorDetailsTypeActe;
    @BindView(R.id.actes_legend)
    MaterialTextView mActesLegend;
    @BindView(R.id.doctor_details_actes)
    MaterialTextView mDoctorDetailsActes;
    @BindView(R.id.doctor_details_commentary)
    MaterialTextView mDoctorDetailsCommentary;
    @BindView(R.id.doctor_details_no_commentary_txt)
    MaterialTextView mDoctorDetailsNoCommentaryTxt;
    @BindView(R.id.doctor_details_recycler_view)
    RecyclerView mDoctorDetailsRecyclerView;
    @BindView(R.id.doctor_details_nav)
    BottomNavigationView mDoctorDetailsNav;

    private Activity mActivity;
    private SharedViewModel mViewModel;
    private User mCurrentUser;
    private Doctor mDoctor;
    private List<Commentary> mCommentaryList;
    private CommentaryAdapter mCommentaryAdapter;

    public DoctorDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        configureViewModel();
        getCurrentUser();
        initDoctorNavigationBar();
        initSelectedDoctor();
        configureRecyclerView();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(mActivity);
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }

    /**
     * Configures the RecyclerView with a CommentaryAdapter
     */
    private void configureRecyclerView(){
        mCommentaryList = new ArrayList<>();
        mCommentaryAdapter = new CommentaryAdapter(mCommentaryList);
        mDoctorDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,RecyclerView.VERTICAL,false));
        mDoctorDetailsRecyclerView.setAdapter(mCommentaryAdapter);
    }

    /**
     * Fetches the user's selected doctor that is saved in a MutableLiveData with the ViewModel
     */
    private void initSelectedDoctor(){
        mViewModel.getCurrentDoctor().observe(getViewLifecycleOwner(), this::updateUiWithSelectedDoctor);
    }

    /**
     * Fetches the current user that is saved in a MutableLiveData with the ViewModel
     */
    private void getCurrentUser(){
        mViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> mCurrentUser = user);
    }

    /**
     * Sets the BottomNavigationBar item listener to execute different actions
     */
    private void initDoctorNavigationBar(){
        mDoctorDetailsNav.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.call){
                if (!mDoctorDetailsPhone.getText().equals("")){
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + mDoctorDetailsPhone.getText().toString()));
                    startActivity(callIntent);
                }else
                    Toast.makeText(mActivity, R.string.no_phone_doctor, Toast.LENGTH_SHORT).show();
            }else{
                Log.i("info", "initDoctorNavigationBar: " + mCurrentUser.toString());
                if (!mCurrentUser.getName().equals(getString(R.string.anonymous)) && Utils.isNetworkAccessEnabled(mActivity))
                    openCommentaryDialog();
                else if ((mCurrentUser.getName().equals(getString(R.string.anonymous))))
                    Toast.makeText(mActivity, R.string.need_logged,Toast.LENGTH_SHORT).show();
                else if (!Utils.isNetworkAccessEnabled(mActivity))
                    Toast.makeText(mActivity, R.string.network_commentary,Toast.LENGTH_SHORT).show();

            }
            return true;
        });
    }

    /**
     * Setup and show a custom AlertDialog for allow user to write a commentary.
     * If user save a commentary, ViewModel saves it in Firestore and updates a counter in FireStore
     */
    private void openCommentaryDialog(){
        View customDialog = LayoutInflater.from(mActivity).inflate(R.layout.dialog_commentary, null);
        TextInputEditText commentaryText = customDialog.findViewById(R.id.commentary_text);
        Slider ratingSlider = customDialog.findViewById(R.id.rating_slider);
        MaterialTextView ratingText = customDialog.findViewById(R.id.rating_txt);
        ratingSlider.setOnChangeListener((slider, value) -> ratingText.setText((String.valueOf(value))));
        new MaterialAlertDialogBuilder(mActivity)
                .setCancelable(false)
                .setView(customDialog)
                .setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton(R.string.save, (dialogInterface, i) -> {
                    if (!TextUtils.isEmpty(commentaryText.getText())) {
                        Commentary commentary = new Commentary(mDoctor.getId(),mCurrentUser.getName(),
                                mCurrentUser.getPhotoUrl(),(int) ratingSlider.getValue(), commentaryText.getText().toString(),
                                new Date());
                        mViewModel.createFirestoreCommentary(commentary);
                        mViewModel.createFirestoreDoctorCounter(mDoctor.getId(),(int)ratingSlider.getValue());
                    }
                })
                .show();
    }

    /**
     * Updates the UI with the selected doctor fields
     * @param doctor the user's selected doctor in the previous list
     */
    private void updateUiWithSelectedDoctor(Doctor doctor){
        mDoctor = doctor;
        mDoctorDetailsName.setText(doctor.getName());
        mDoctorDetailsCivilite.setText(doctor.getCivilite());
        mDoctorDetailsSpec.setText(doctor.getSpec());
        mDoctorDetailsAddress.setText(doctor.getAddress());
        if (!TextUtils.isEmpty(doctor.getPhone())) {
            mDoctorDetailsPhone.setText(doctor.getPhone());
        } else {
            mPhoneLegend.setVisibility(View.GONE);
            mDoctorDetailsPhone.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(doctor.getTypeActes())) {
            mDoctorDetailsTypeActe.setText(doctor.getTypeActes());
        } else {
            mTypesActesLegend.setVisibility(View.GONE);
            mDoctorDetailsTypeActe.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(doctor.getActes())) {
            mDoctorDetailsActes.setText(doctor.getActes());
        } else {
            mActesLegend.setVisibility(View.GONE);
            mDoctorDetailsActes.setVisibility(View.VISIBLE);
        }
        initCommentaries();
    }

    /**
     * Fetches Commentaries in FireStore according to the doctorId and add the response into a
     * List<Commentary> and notify the RecyclerView.Adapter
     */
    private void initCommentaries(){
        mViewModel.getCommentariesByDoctorId(mDoctor.getId()).observe(getViewLifecycleOwner(), commentaries -> {
            if (commentaries.isEmpty())
                mDoctorDetailsNoCommentaryTxt.setVisibility(View.VISIBLE);
            else{
                mCommentaryList.clear();
                mCommentaryList.addAll(commentaries);
                mCommentaryAdapter.notifyDataSetChanged();
                mDoctorDetailsNoCommentaryTxt.setVisibility(View.GONE);
            }
        });
    }
}
