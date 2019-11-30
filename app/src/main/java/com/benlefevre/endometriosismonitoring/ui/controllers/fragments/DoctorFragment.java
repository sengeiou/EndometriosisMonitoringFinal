package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;


import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.injection.Injection;
import com.benlefevre.endometriosismonitoring.injection.ViewModelFactory;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.benlefevre.endometriosismonitoring.utils.Constants.ACCESS_LOCATION;

public class DoctorFragment extends Fragment implements OnMapReadyCallback {


    @BindView(R.id.doctor_mapview)
    MapView mDoctorMapview;
    @BindView(R.id.doctor_search_txt)
    TextInputEditText mSearchTxt;
    @BindView(R.id.doctor_chip_gyneco)
    Chip mChipGyneco;
    @BindView(R.id.doctor_chip_generalist)
    Chip mChipGeneralist;
    @BindView(R.id.doctor_search_btn)
    MaterialButton mSearchBtn;
    @BindView(R.id.doctor_recycler_view)
    RecyclerView mDoctorRecyclerView;

    private Activity mActivity;
    private SharedViewModel mViewModel;
    private NavController mNavController;

    public DoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        if (mActivity != null)
            mNavController = Navigation.findNavController(mActivity,R.id.nav_host_fragment);
        getPermissionsAccessLocation();
        mDoctorMapview.onCreate(savedInstanceState);
        mDoctorMapview.getMapAsync(this);
        configureViewModel();
    }

    /**
     * Verifies if user has accept the needed permissions and if it is not the case, request needed
     * permissions before display the fragment.
     */
    @AfterPermissionGranted(ACCESS_LOCATION)
    private void getPermissionsAccessLocation(){
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (!EasyPermissions.hasPermissions(mActivity, perms)){
            EasyPermissions.requestPermissions(this,getString(R.string.doctor_rationale),ACCESS_LOCATION, perms);
            mNavController.navigate(R.id.dashboardFragment);
        }else{

        }
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(mActivity);
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @OnClick(R.id.doctor_search_btn)
    public void onViewClicked() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions, grantResults);
    }
}
