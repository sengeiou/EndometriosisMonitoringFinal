package com.benlefevre.endometriosismonitoring.ui.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.injection.Injection;
import com.benlefevre.endometriosismonitoring.injection.ViewModelFactory;
import com.benlefevre.endometriosismonitoring.models.User;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import static com.benlefevre.endometriosismonitoring.utils.Constants.RC_SIGN_IN;

public class LoginActivity extends AppCompatActivity {

    private SharedViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            AuthUI.getInstance().signOut(this);
        }
        configureViewModel();
        createSignInIntent();
    }

    //    Gets viewmodel to handle the user save in Firestore
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(SharedViewModel.class);
    }

    private void createSignInIntent() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.AnonymousBuilder().build()
        );

//        Creates AuthMethodPickerLayout to customise the Login Ui
        AuthMethodPickerLayout authMethodPickerLayout = new AuthMethodPickerLayout.Builder(R.layout.activity_login)
                .setEmailButtonId(R.id.login_mail_btn)
                .setGoogleButtonId(R.id.login_google_btn)
                .setFacebookButtonId(R.id.login_facebook_btn)
                .setAnonymousButtonId(R.id.login_anonymous)
                .build();

//        Setup signIn Intent with the previous AuthMethod && a custom theme for mail provider
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .enableAnonymousUsersAutoUpgrade()
                .setAuthMethodPickerLayout(authMethodPickerLayout)
                .setTheme(R.style.LoginTheme)
                .build(), RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    //    If Auth process is Ok, the user is save in Firestore and the MainActivity is launched
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                createUserInFirestoreAfterSignIn();
                if (idpResponse != null && idpResponse.getProviderType() != null) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            } else {
                if (idpResponse == null)
                    Toast.makeText(this, R.string.cancel_auth,Toast.LENGTH_SHORT).show();
                else if (idpResponse.getError() != null &&
                        idpResponse.getError().getErrorCode() == ErrorCodes.NO_NETWORK)
                    Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
                else if (idpResponse.getError() != null &&
                        idpResponse.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR)
                    Toast.makeText(this, R.string.unknown_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * If user is logged and he isn't logged with anonymous provider then we record User in Firestore
     */
    private void createUserInFirestoreAfterSignIn() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null && !FirebaseAuth.getInstance().getCurrentUser().isAnonymous()) {
            String id = null, name = null, mail = null, photoUrl = null;
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (!TextUtils.isEmpty(firebaseUser.getDisplayName()))
                name = firebaseUser.getDisplayName();
            if (!TextUtils.isEmpty(firebaseUser.getUid()))
                id = firebaseUser.getUid();
            if (!TextUtils.isEmpty(firebaseUser.getEmail()))
                mail = firebaseUser.getEmail();
            if (firebaseUser.getPhotoUrl() != null && !TextUtils.isEmpty(firebaseUser.getPhotoUrl().toString()))
                photoUrl = firebaseUser.getPhotoUrl().toString();
            User user = new User(id, name, mail, photoUrl);
            mViewModel.createFirestoreUser(user);
        }
    }
}
