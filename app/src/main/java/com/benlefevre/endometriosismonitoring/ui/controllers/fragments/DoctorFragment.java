package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;


import android.Manifest;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.injection.Injection;
import com.benlefevre.endometriosismonitoring.injection.ViewModelFactory;
import com.benlefevre.endometriosismonitoring.models.ClusterMarker;
import com.benlefevre.endometriosismonitoring.models.Doctor;
import com.benlefevre.endometriosismonitoring.models.DoctorCommentaryCounter;
import com.benlefevre.endometriosismonitoring.models.Result;
import com.benlefevre.endometriosismonitoring.ui.adapters.DoctorAdapter;
import com.benlefevre.endometriosismonitoring.ui.viewholders.DoctorViewHolder;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private GoogleMap mGoogleMap;
    private Location mLastKnownLocation;
    private DoctorAdapter mDoctorAdapter;
    private List<Doctor> mDoctorList;
    private boolean mGeoFilter;
    private Map<String, String> mQueryMap;
    private ClusterManager<ClusterMarker> mClusterManager;


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
            mNavController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
        mDoctorMapview.onCreate(savedInstanceState);
        mDoctorMapview.getMapAsync(this);
        mDoctorList = new ArrayList<>();
        configureViewModel();
        setSearchTxtListener();
        configureRecyclerView();
        getPermissionsAccessLocation();
    }

    /**
     * Verifies if user has accepted the needed permissions and if it is not the case, request necessary
     * permissions before displaying the fragment.
     * If user has accepted the permissions, cal the method to locate user.
     */
    @AfterPermissionGranted(ACCESS_LOCATION)
    private void getPermissionsAccessLocation() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (!EasyPermissions.hasPermissions(mActivity, perms)) {
            EasyPermissions.requestPermissions(this, getString(R.string.doctor_rationale), ACCESS_LOCATION, perms);
        } else {
            getLastKnownLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,this);
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(mActivity);
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }

    /**
     * Sets a DoctorAdapter to the RecyclerView and defines the behavior of the OnClickListener.
     */
    private void configureRecyclerView() {
        final int[] i = {-1};
        mDoctorAdapter = new DoctorAdapter(mDoctorList);
        mDoctorAdapter.setOnClickListener(v -> {
            DoctorViewHolder holder = (DoctorViewHolder) v.getTag();
            int position = holder.getAdapterPosition();
            Doctor doctor = mDoctorList.get(position);
            mViewModel.setSelectedDoctor(doctor);
            if (i[0] == position) {
                mNavController.navigate(R.id.doctorDetailsFragment);
            } else {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(doctor.getCoordonnees().get(0),
                        doctor.getCoordonnees().get(1)), 18));
                i[0] = position;
                holder.setDetailsButtonVisibility();
            }
        });
        mDoctorRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
        mDoctorRecyclerView.setAdapter(mDoctorAdapter);
    }

    /**
     * Fetches the last known user location with Google Location Services and move the camera to the
     * user location.
     */
    private void getLastKnownLocation() {
        try {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mActivity);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null && mGoogleMap != null) {
                    mLastKnownLocation = location;
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 14));
                    mGoogleMap.setMyLocationEnabled(true);
                    getDoctorFromApi();
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a listener to the EditText to change the search button text.
     */
    private void setSearchTxtListener() {
        mSearchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchBtn.setText(R.string.search);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mSearchTxt.getText()))
                    mSearchBtn.setText(getString(R.string.around_me));
            }
        });
    }

    /**
     * Fetches doctor from the API according to user choice, add marker on map and create Doctor objects
     * to bind them in the RecyclerView.
     */
    private void getDoctorFromApi() {
        final double[] lat = {0};
        final double[] lng = {0};
        mDoctorList.clear();
        mGoogleMap.clear();
        configureQueryMap();
        mViewModel.getDoctor(mQueryMap).observe(getViewLifecycleOwner(), result -> {
            if (result != null && !result.getRecords().isEmpty()){
                List<Result.Record> records = result.getRecords();
                List<Double> latLngList = new ArrayList<>();
                String docName;
                int counter = 0;
                for (Result.Record record : records){
                    docName = record.getFields().getNom();
                    double currentLat = record.getFields().getCoordonnees().get(0);
                    double currentLng = record.getFields().getCoordonnees().get(1);
//                        Checks if different Doctor have the same location and if it is the case add their markers
//                        in a Cluster
                    if (!latLngList.contains(currentLat) && !latLngList.contains(currentLng)){
                        latLngList.add(currentLat);
                        latLngList.add(currentLng);
                        lat[0] = currentLat;
                        lng[0] = currentLng;
                        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat[0], lng[0])).title(docName));
                    }else {
                        double offset = counter/100000d;
                        lat[0] = currentLat + offset;
                        lng[0] = currentLng + offset;
                        ClusterMarker clusterMarker = new ClusterMarker(lat[0], lng[0],docName);
                        mClusterManager.addItem(clusterMarker);
                        counter++;
                    }
//                        Creates a Doctor object to add it in the mDoctorList and bind it in the RecyclerView
                    Doctor doctor = new Doctor(record.getRecordid(), record.getFields().getNom(),
                            record.getFields().getCivilite(),record.getFields().getAdresse(),
                            record.getFields().getLibelleProfession(), record.getFields().getConvention(),
                            record.getFields().getTelephone(), record.getFields().getActes(),
                            record.getFields().getTypesActes(),record.getFields().getCoordonnees());
                    mDoctorList.add(doctor);
                }
//                Creates subCollections to query FireStore
                get10DoctorIdToQuery();
//                    Move the camera on the user location or on the city location according to mGeoFilter
                if (mGeoFilter)
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()),14));
                else
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat[0],lng[0]),13));
            }
            mDoctorAdapter.notifyDataSetChanged();
        });
    }

    /**
     * Checks the mDoctorList size to create sub collection of 10 Strings because FireStore doesn't
     * accept Query with more of 10 values for 1 field.
     */
    private void get10DoctorIdToQuery(){
        List<String> doctorIdList = new ArrayList<>();
        int repeatMax = mDoctorList.size() / 10;
        int moduloRepeat = mDoctorList.size() % 10;
        int b = 0;
        int c = 0;
        int d = 0;
        while (b < repeatMax) {
            doctorIdList.clear();
            d += 10;
            while (c < d) {
                doctorIdList.add(mDoctorList.get(c).getId());
                c++;
            }
            getDoctorCommentaryCounter(doctorIdList);
            b++;
        }
        if (moduloRepeat != 0) {
            if (repeatMax != 0) {
                doctorIdList.clear();
                c = repeatMax * 10;
                d += moduloRepeat;
                while (c < d) {
                    doctorIdList.add(mDoctorList.get(c).getId());
                    c++;
                }
            } else {
                doctorIdList.clear();
                d = moduloRepeat;
                for (int a = 0; a < d; a++)
                    doctorIdList.add(mDoctorList.get(a).getId());
            }
            getDoctorCommentaryCounter(doctorIdList);
        }
    }

    /**
     * Fetches the DoctorCommentaryCounter in FireStore and adds it's values into the corresponding Doctor
     * @param doctorIdList the needed 10 doctorId to request FireStore
     */
    private void getDoctorCommentaryCounter(List<String> doctorIdList){
        mViewModel.getDoctorCommentaryCounter(doctorIdList).observe(getViewLifecycleOwner(), doctorCommentaryCounters -> {
            for (DoctorCommentaryCounter counter : doctorCommentaryCounters){
                for(Doctor doctor : mDoctorList){
                    if (doctor.getId().equals(counter.getDoctorId())){
                        doctor.setCommentaries(counter.getNbCommentaries());
                        doctor.setRating(counter.getRating());
                    }
                }
            }
            Comparator<Doctor> comparator = (Doctor doctor1, Doctor doctor2) -> doctor2.getCommentaries() - doctor1.getCommentaries();
            Collections.sort(mDoctorList,comparator);
            mDoctorAdapter.notifyDataSetChanged();
        });
    }

    /**
     * Sets the needed QueryMap according to the user input to send it  in a request to the APi
     */
    private void configureQueryMap() {
        mQueryMap = new HashMap<>();
        isGeoFilter();
        mQueryMap.put("dataset", "annuaire-des-professionnels-de-sante");
        mQueryMap.put("rows", "50");
        if (mChipGyneco.isChecked())
            mQueryMap.put("q", "Gynécologue obstétricien OR Gynécologue médical OR Gynécologue médical et obstétricien");
        else
            mQueryMap.put("q", "Médecin généraliste");

        if (mGeoFilter && mLastKnownLocation != null)
            mQueryMap.put("geofilter.distance", "" + mLastKnownLocation.getLatitude() + "," + mLastKnownLocation.getLongitude() + "," + 5000);
        else {
            mQueryMap.put("facet", "nom_com");
            mQueryMap.put("refine.nom_com", Objects.requireNonNull(mSearchTxt.getText()).toString());
        }
    }

    /**
     * Checks if mSearchTxt is empty to know if the user want see the doctor around him or for an other
     * location
     */
    private void isGeoFilter() {
        mGeoFilter = TextUtils.isEmpty(mSearchTxt.getText());
    }

    /**
     * Get a GoogleMap object and set different listeners with the ClusterManager
     * @param googleMap the needed GoogleMap object return by Google Services to handle the map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mClusterManager = new ClusterManager<>(mActivity,mGoogleMap);
        mGoogleMap.setOnCameraIdleListener(mClusterManager);
        mGoogleMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(cluster -> {
            LatLngBounds.Builder bounds = LatLngBounds.builder();
            for (ClusterMarker marker : cluster.getItems()){
                bounds.include(marker.getPosition());
            }
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(),100));
            return true;
        });
        mGoogleMap.setOnInfoWindowClickListener(marker -> {
            for (Doctor doctor : mDoctorList){
                if (marker.getTitle().equals(doctor.getName())){
                    mViewModel.setSelectedDoctor(doctor);
                    mNavController.navigate(R.id.doctorDetailsFragment);
                }
            }
        });
    }

    @OnClick(R.id.doctor_search_btn)
    public void onViewClicked() {
        getDoctorFromApi();
    }

    @Override
    public void onStart() {
        mDoctorMapview.onStart();
        super.onStart();
    }

    @Override
    public void onResume() {
        mDoctorMapview.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mDoctorMapview.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mDoctorMapview.onStop();
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        mDoctorMapview.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        mDoctorMapview.onDestroy();
        if (mGoogleMap != null)
            mGoogleMap.setMyLocationEnabled(false);
        super.onDestroy();
    }
}
