package com.benlefevre.endometriosismonitoring.ui.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.injection.Injection;
import com.benlefevre.endometriosismonitoring.injection.ViewModelFactory;
import com.benlefevre.endometriosismonitoring.models.User;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_bottom_nav)
    BottomNavigationView mBottomNav;
    @BindView(R.id.main_nav_view)
    NavigationView mNavView;
    @BindView(R.id.main_drawer)
    DrawerLayout mDrawer;

    private SharedViewModel mViewModel;
    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        } else {
            setContentView(R.layout.activity_main);
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            ButterKnife.bind(this);
            configureViewModel();
            setupNavigation();
            updateHeaderWithUser(firebaseUser);
        }
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(SharedViewModel.class);
    }

    private void updateHeaderWithUser(FirebaseUser firebaseUser) {
        User currentUser = new User();
        View header = mNavView.getHeaderView(0);
        AppCompatImageView userPhoto = header.findViewById(R.id.user_photo_profile);
        MaterialTextView userName = header.findViewById(R.id.user_name);
        MaterialTextView userMail = header.findViewById(R.id.user_mail);
        if (firebaseUser.getPhotoUrl() != null) {
            Glide.with(this).load(firebaseUser.getPhotoUrl()).apply(RequestOptions.circleCropTransform())
                    .into(userPhoto);
            currentUser.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
        } else {
            Glide.with(this).load(R.drawable.girl).into(userPhoto);
            currentUser.setPhotoUrl(null);
        }
        if (!TextUtils.isEmpty(firebaseUser.getDisplayName())) {
            userName.setText(firebaseUser.getDisplayName());
            currentUser.setName(firebaseUser.getDisplayName());
        } else {
            userName.setText(R.string.anonymous);
            currentUser.setName(getString(R.string.anonymous));
        }
        if (!TextUtils.isEmpty(firebaseUser.getEmail())) {
            userMail.setVisibility(View.VISIBLE);
            userMail.setText(firebaseUser.getEmail());
            currentUser.setMail(firebaseUser.getEmail());
        } else {
            userMail.setVisibility(View.GONE);
        }
    }

    private void setupNavigation() {
        mNavController = Navigation.findNavController(this,R.id.nav_host_fragment);
        mToolbar.setTitle(R.string.dashboard);
        setSupportActionBar(mToolbar);

        NavigationUI.setupActionBarWithNavController(this,mNavController,mDrawer);
        NavigationUI.setupWithNavController(mToolbar,mNavController,mDrawer);
        NavigationUI.setupWithNavController(mNavView,mNavController);
        NavigationUI.setupWithNavController(mBottomNav, mNavController);

        mNavController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()){
                case R.id.dashboardFragment:
                    mBottomNav.setVisibility(View.VISIBLE);
                    mToolbar.setTitle(R.string.dashboard);
                    break;
                case R.id.treatmentFragment:
                    mBottomNav.setVisibility(View.VISIBLE);
                    mToolbar.setTitle(R.string.treatment);
                    break;
                case R.id.fertilityFragment:
                    mBottomNav.setVisibility(View.VISIBLE);
                    mToolbar.setTitle(R.string.fertility);
                    break;
                case R.id.doctorFragment:
                    mBottomNav.setVisibility(View.VISIBLE);
                    mToolbar.setTitle(R.string.doctor);
                    break;
                case R.id.doctorDetailsFragment:
                    mBottomNav.setVisibility(View.GONE);
                    mToolbar.setTitle(getString(R.string.doctor_info));
                    break;
                case R.id.painFragment:
                    mBottomNav.setVisibility(View.GONE);
                    mToolbar.setTitle(getString(R.string.my_pain));
                    break;
                case R.id.settingFragment:
                    mBottomNav.setVisibility(View.GONE);
                    mToolbar.setTitle(R.string.settings);
                    break;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return mNavController.navigateUp();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START))
            mDrawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}
