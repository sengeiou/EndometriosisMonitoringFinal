package com.benlefevre.endometriosismonitoring.data.repositories;

import com.benlefevre.endometriosismonitoring.models.Action;
import com.benlefevre.endometriosismonitoring.models.FirestorePain;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.benlefevre.endometriosismonitoring.utils.Constants.ACTIONS;
import static com.benlefevre.endometriosismonitoring.utils.Constants.FIRESTORE_PAIN;

public class FirestoreRepository {

    private static FirestoreRepository sFirestoreRepository;
    private final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    public static FirestoreRepository getInstance(){
        if (sFirestoreRepository == null)
            sFirestoreRepository = new FirestoreRepository();
        return sFirestoreRepository;
    }

    public void createFirestorePain(FirestorePain firestorePain){
        mFirestore.collection(FIRESTORE_PAIN).add(firestorePain);
    }

    public void createFirestoreAction(List<Action> actions){
        for (Action action : actions)
        mFirestore.collection(ACTIONS).add(action);
    }

    public CollectionReference getAllFirestorePain(){
        return mFirestore.collection(FIRESTORE_PAIN);
    }

    public CollectionReference getAllFirestoreAction(){
        return mFirestore.collection(ACTIONS);
    }
}
