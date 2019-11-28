package com.benlefevre.endometriosismonitoring.data.repositories;

import com.benlefevre.endometriosismonitoring.models.Action;
import com.benlefevre.endometriosismonitoring.models.FirestorePain;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FirestoreRepository {

    private final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    public void createFirestorePain(FirestorePain firestorePain){
        mFirestore.collection("FirestorePain").add(firestorePain);
    }

    public void createFirestoreAction(List<Action> actions){
        for (Action action : actions)
        mFirestore.collection("Actions").add(action);
    }
}
