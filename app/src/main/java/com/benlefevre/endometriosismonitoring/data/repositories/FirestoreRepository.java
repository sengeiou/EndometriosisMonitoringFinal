package com.benlefevre.endometriosismonitoring.data.repositories;

import com.benlefevre.endometriosismonitoring.models.Action;
import com.benlefevre.endometriosismonitoring.models.Commentary;
import com.benlefevre.endometriosismonitoring.models.FirestorePain;
import com.benlefevre.endometriosismonitoring.models.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

import static com.benlefevre.endometriosismonitoring.utils.Constants.ACTIONS;
import static com.benlefevre.endometriosismonitoring.utils.Constants.COMMENTARY;
import static com.benlefevre.endometriosismonitoring.utils.Constants.FIRESTORE_PAIN;
import static com.benlefevre.endometriosismonitoring.utils.Constants.USERS;

public class FirestoreRepository {

    private static FirestoreRepository sFirestoreRepository;
    private final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    public static FirestoreRepository getInstance(){
        if (sFirestoreRepository == null)
            sFirestoreRepository = new FirestoreRepository();
        return sFirestoreRepository;
    }

//    ------------------------------------CREATE----------------------------------------------------
    public void createFirestorePain(FirestorePain firestorePain){
        mFirestore.collection(FIRESTORE_PAIN).add(firestorePain);
    }

    public void createFirestoreAction(List<Action> actions){
        for (Action action : actions)
        mFirestore.collection(ACTIONS).add(action);
    }

    public void createFirestoreUser(User user){
        mFirestore.collection(USERS).document(user.getId()).set(user);
    }

    public void createFirestoreCommentary(Commentary commentary){
        mFirestore.collection(COMMENTARY).add(commentary);
    }


//    ----------------------------------------READ--------------------------------------------------
    public CollectionReference getAllFirestorePain(){
        return mFirestore.collection(FIRESTORE_PAIN);
    }

    public CollectionReference getAllFirestoreAction(){
        return mFirestore.collection(ACTIONS);
    }

    public Query getCommentariesByDoctorId(String doctorId){
        return mFirestore.collection(COMMENTARY).whereEqualTo("doctorId",doctorId);
    }

}
