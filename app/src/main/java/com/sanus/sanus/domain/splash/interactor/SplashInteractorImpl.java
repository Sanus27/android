package com.sanus.sanus.domain.splash.interactor;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanus.sanus.data.repository.firebase.entity.user.UserEntity;
import com.sanus.sanus.domain.splash.presenter.SplashPresenter;

public class SplashInteractorImpl implements SplashInteractor {

    private SplashPresenter presenter;
    private static final long SPLASH_SCREEN_DELETE = 1000;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = this.getClass().getSimpleName();

    public SplashInteractorImpl(SplashPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void init() {

        //     TimerTask timerTask = new TimerTask() {
        //  @Override
        //public void run() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userIdNow = user.getUid();
            DocumentReference usuarios = db.collection("usuarios").document(userIdNow);
            usuarios.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            presenter.goMain();
                            Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        } else {
                            presenter.showAlertRegister();
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

            return;
        }
        presenter.goLogin();

    }
    // };

    //    Timer timer = new Timer();
    //  timer.schedule(timerTask, SPLASH_SCREEN_DELETE);
    // }

    @Override
    public void acceptAlert() {
        presenter.goCompleteRegister();
    }

    @Override
    public void cancelAlert() {

        FirebaseAuth.getInstance().signOut();
        presenter.goLogin();

    }
}
