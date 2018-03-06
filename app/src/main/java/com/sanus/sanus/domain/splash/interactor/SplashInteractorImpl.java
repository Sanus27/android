package com.sanus.sanus.domain.splash.interactor;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanus.sanus.domain.splash.presenter.SplashPresenter;

import java.util.Timer;
import java.util.TimerTask;

public class SplashInteractorImpl implements SplashInteractor {

    private SplashPresenter presenter;
    private static final long SPLASH_SCREEN_DELETE = 1000;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userIdNow;

    public SplashInteractorImpl(SplashPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void init() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {

                    if (userIdNow != null && userIdNow.equals(user.getUid())) {
                        return;
                    }

                    userIdNow = user.getUid();


                    DocumentReference usuarios = db.collection("usuarios").document(userIdNow);
                    usuarios.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            UserEntity complete = documentSnapshot.toObject(UserEntity.class);

                            if (complete.completo == null) {
                                presenter.goCompleteRegister();
                                return;
                            }
                            if (complete.completo.equals("incompleto") || complete.completo.isEmpty()) {
                                presenter.goCompleteRegister();
                                return;
                            }
                            presenter.goMain();
                        }
                    });

                    return;
                }
                presenter.goLogin();

            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, SPLASH_SCREEN_DELETE);
    }
}
