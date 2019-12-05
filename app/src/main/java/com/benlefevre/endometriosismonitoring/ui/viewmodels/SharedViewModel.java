package com.benlefevre.endometriosismonitoring.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.benlefevre.endometriosismonitoring.data.repositories.ActionRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.DoctorRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.FirestoreRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.MoodRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.PainRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.SymptomRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.TemperatureRepository;
import com.benlefevre.endometriosismonitoring.models.Action;
import com.benlefevre.endometriosismonitoring.models.Commentary;
import com.benlefevre.endometriosismonitoring.models.Doctor;
import com.benlefevre.endometriosismonitoring.models.DoctorCommentaryCounter;
import com.benlefevre.endometriosismonitoring.models.FirestorePain;
import com.benlefevre.endometriosismonitoring.models.Mood;
import com.benlefevre.endometriosismonitoring.models.Pain;
import com.benlefevre.endometriosismonitoring.models.Result;
import com.benlefevre.endometriosismonitoring.models.Symptom;
import com.benlefevre.endometriosismonitoring.models.Temperature;
import com.benlefevre.endometriosismonitoring.models.User;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SharedViewModel extends ViewModel {

    private final ActionRepository mActionRepository;
    private final DoctorRepository mDoctorRepository;
    private final FirestoreRepository mFirestoreRepository;
    private final MoodRepository mMoodRepository;
    private final PainRepository mPainRepository;
    private final SymptomRepository mSymptomRepository;
    private final TemperatureRepository mTemperatureRepository;
    private final Executor mExecutor;

    private MutableLiveData<List<FirestorePain>> mFirestorePainLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Action>> mFirestoreActionLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Commentary>> mCommentaryLiveData = new MutableLiveData<>();
    private MutableLiveData<List<DoctorCommentaryCounter>> mCountersLiveData = new MutableLiveData<>();
    private MutableLiveData<User> mCurrentUserLiveData = new MutableLiveData<>();
    private MutableLiveData<Doctor> mCurrentDoctorLiveData = new MutableLiveData<>();


    public SharedViewModel(ActionRepository actionRepository, DoctorRepository doctorRepository,
                           FirestoreRepository firestoreRepository, MoodRepository moodRepository,
                           PainRepository painRepository, SymptomRepository symptomRepository,
                           TemperatureRepository temperatureRepository, Executor executor) {
        mActionRepository = actionRepository;
        mDoctorRepository = doctorRepository;
        mFirestoreRepository = firestoreRepository;
        mMoodRepository = moodRepository;
        mPainRepository = painRepository;
        mSymptomRepository = symptomRepository;
        mTemperatureRepository = temperatureRepository;
        mExecutor = executor;
    }

//    --------------------------------------Room----------------------------------------------------

//    --------------------------------------CREATE--------------------------------------------------
    public long createPain(Pain pain) {
        Callable<Long> insertCallable = () -> mPainRepository.createPain(pain);
        long rowId = 0;

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Long> future = executorService.submit(insertCallable);
        try {
            rowId = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return rowId;
    }

    public void createSymptoms(List<Symptom> symptoms) {
        mExecutor.execute(() -> mSymptomRepository.createSymptoms(symptoms));
    }

    public void createActions(List<Action> actions) {
        mExecutor.execute(() -> mActionRepository.createActions(actions));
    }


    public void createMood(Mood mood) {
        mExecutor.execute(() -> mMoodRepository.createMood(mood));
    }

    public void createTemp(Temperature temperature) {
        mExecutor.execute(() -> mTemperatureRepository.createTemp(temperature));
    }

//    ----------------------------------------READ--------------------------------------------------

    public LiveData<List<Pain>> getAllPain() {
        return mPainRepository.getPain();
    }

    public LiveData<List<Pain>> getPainByPeriod(Date begin, Date end) {
        return mPainRepository.getPainByPeriod(begin, end);
    }

    public LiveData<List<Symptom>> getAllSymptom(){return mSymptomRepository.getAllSymptoms();}

    public LiveData<List<Symptom>> getSymptomByPainId(long painId){return mSymptomRepository.getPainSymptoms(painId);}

    public LiveData<List<Symptom>> getSymptomByPeriod(Date begin, Date end){return mSymptomRepository.getSymptomByPeriod(begin, end);}

    public LiveData<List<Action>> getAllAction(){return mActionRepository.getAllActions();}

    public LiveData<List<Action>> getActionByPainId(long painId){return mActionRepository.getPainActions(painId);}

    public LiveData<List<Action>> getActionsByPeriod(Date begin, Date end){ return mActionRepository.getActionsByPeriod(begin, end);}

    public LiveData<List<Mood>> getAllMood(){return mMoodRepository.getAllMood();}

    public LiveData<Mood> getMoodByPainId(long painId){return mMoodRepository.getPainMood(painId);}

    public LiveData<List<Temperature>> getAllTemperature(){return mTemperatureRepository.getAllTemp();}

    public LiveData<List<Temperature>> getTemperatureByPeriod(Date begin, Date end){return mTemperatureRepository.getTempForPeriod(begin, end);}

//    ----------------------------------------DELETE------------------------------------------------
    public void deleteAllPains() {
        mExecutor.execute(mPainRepository::deleteAllPain);
    }

    public void deleteAllTemp(){
        mExecutor.execute(mTemperatureRepository::deleteAllTemp);
    }

    public void deleteAllSleep(String name){
        mExecutor.execute(() -> mActionRepository.deleteAllSleep(name));
    }

    public void deleteAllActions(String name){
        mExecutor.execute(() -> mActionRepository.deleteAllActions(name));
    }

    public void deleteAllMood(){
        mExecutor.execute(mMoodRepository::deleteAllMood);}

    public void deleteAllSymptom(){
        mExecutor.execute(mSymptomRepository::deleteAllSymptom);}




//    --------------------------------------FireStore-----------------------------------------------

//    ---------------------------------------CREATE-------------------------------------------------
    public void createFirestorePain(FirestorePain firestorePain){mFirestoreRepository.createFirestorePain(firestorePain);}

    public void createFirestoreAction(List<Action> actions){
        mFirestoreRepository.createFirestoreAction(actions);
    }

    public void createFirestoreUser(User user){
        mFirestoreRepository.createFirestoreUser(user);
    }

    public void createFirestoreCommentary(Commentary commentary){mFirestoreRepository.createFirestoreCommentary(commentary);}

    public void createFirestoreDoctorCounter(String doctorId, int rating){
        mFirestoreRepository.updateFirestoreDoctorCommentaryCounter(doctorId,rating)
                .addOnFailureListener(e -> mFirestoreRepository.createFirestoreDoctorCommentaryCounter(doctorId,rating));
    }

//    ----------------------------------------READ--------------------------------------------------

    public LiveData<List<FirestorePain>> getFirestorePain(){
        mFirestoreRepository.getAllFirestorePain().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()){
                List<FirestorePain> painList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    FirestorePain pain = doc.toObject(FirestorePain.class);
                    painList.add(pain);
                }
                mFirestorePainLiveData.setValue(painList);
            }
        });
        return mFirestorePainLiveData;
    }

    public LiveData<List<Action>> getFirestoreAction(){
        mFirestoreRepository.getAllFirestoreAction().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()){
                List<Action> actionList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Action action = doc.toObject(Action.class);
                    actionList.add(action);
                }
                mFirestoreActionLiveData.setValue(actionList);
            }
        });
        return mFirestoreActionLiveData;
    }

    public LiveData<List<Commentary>> getCommentariesByDoctorId(String doctorId){
        List<Commentary> commentaryList = new ArrayList<>();
        mCommentaryLiveData.setValue(commentaryList);
        mFirestoreRepository.getCommentariesByDoctorId(doctorId).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()){
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Commentary commentary = doc.toObject(Commentary.class);
                    commentaryList.add(commentary);
                }
                Comparator<Commentary> compareComment = (Commentary c1, Commentary c2) ->
                        c2.getDate().compareTo(c1.getDate());
                Collections.sort(commentaryList,compareComment);
                mCommentaryLiveData.setValue(commentaryList);
            }
        });
        return mCommentaryLiveData;
    }

    public LiveData<List<DoctorCommentaryCounter>> getDoctorCommentaryCounter(List<String> doctorIdList){
        mFirestoreRepository.getDoctorCommentaryCounter(doctorIdList).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots != null && ! queryDocumentSnapshots.isEmpty()){
                List<DoctorCommentaryCounter> counters = new ArrayList<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    DoctorCommentaryCounter counter = doc.toObject(DoctorCommentaryCounter.class);
                    counters.add(counter);
                }
                mCountersLiveData.setValue(counters);
            }
        });
        return mCountersLiveData;
    }


//    --------------------------------------API-----------------------------------------------------

//    -------------------------------------READ-----------------------------------------------------

    public LiveData<Result> getDoctor(Map<String, String> map){
        return mDoctorRepository.getDoctor(map);
    }


//    ---------------------------PASS DATA BETWEEN FRAGMENT-----------------------------------------

//    --------------------------------------User----------------------------------------------------
    public void setCurrentUser(User user){
        mCurrentUserLiveData.setValue(user);
    }

    public LiveData<User> getCurrentUser(){
        return mCurrentUserLiveData;
    }


//    --------------------------------------Doctor--------------------------------------------------
    public void setSelectedDoctor(Doctor doctor){
        mCurrentDoctorLiveData.setValue(doctor);
    }

    public LiveData<Doctor> getCurrentDoctor(){ return mCurrentDoctorLiveData;}

}
