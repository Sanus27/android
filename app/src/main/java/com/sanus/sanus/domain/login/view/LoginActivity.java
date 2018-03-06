package com.sanus.sanus.domain.login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sanus.sanus.R;
import com.sanus.sanus.domain.account.complete.view.CompleteRegisterActivity;
import com.sanus.sanus.domain.account.create.view.CreateAccountActivity;
import com.sanus.sanus.domain.login.presenter.LoginPresenter;
import com.sanus.sanus.domain.login.presenter.LoginPresenterImpl;
import com.sanus.sanus.domain.main.view.MainActivity;
import com.sanus.sanus.utils.alert.AlertUtils;
import com.sanus.sanus.utils.alert.CallbackAlert;
import com.sanus.sanus.utils.keyboard.KeyboardUtil;

public class LoginActivity extends AppCompatActivity implements LoginView, CallbackAlert {

    private LoginPresenter presenter;
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupVariable();
        setUpView();
    }

    private void setUpView() {

        inputEmail = findViewById(R.id.edCorreo);
        inputPassword = findViewById(R.id.edContraseña);
        btnLogin = findViewById(R.id.btnInicio);
        Button btnSignup = findViewById(R.id.btnCrearCuenta);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickLogin();
            }
        });

        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.validateButtonEnable();
            }
        });

        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.validateButtonEnable();
            }
        });

    }

    private void setupVariable() {
        if (presenter == null) {
            presenter = new LoginPresenterImpl(this);
        }

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyboardUtil.hide(this);
        return true;
    }

    @Override
    public String getEmail() {
        return inputEmail.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return inputPassword.getText().toString().trim();
    }

    @Override
    public void showMessage(int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlertRegister() {
        AlertUtils alertUtils = new AlertUtils(this);
        alertUtils.registerAlert(this, getString(R.string.complete_register));
    }

    @Override
    public void signInWithEmailAndPassword() {
        auth.signInWithEmailAndPassword(getEmail(), getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        presenter.signInWithEmailAndPasswordComplete(task);
                    }
                });
    }

    @Override
    public void enableButton() {
        btnLogin.setEnabled(true);
    }

    @Override
    public void disableButton() {
        btnLogin.setEnabled(false);
    }

    @Override
    public void goMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goCompleteRegister() {
        Intent intent = new Intent(this, CompleteRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public ProgressDialog getLoading() {
        return AlertUtils.getLoading(this);
    }

    @Override
    public void acceptAlert() {
        presenter.acceptAlert();
    }

    @Override
    public void cancelAlert() {
        presenter.cancelAlert();
    }


//    public void irCrearCuentaGoogle(View view) {
//        Toast.makeText(this, "Crea cuenta con google", Toast.LENGTH_SHORT).show();
//    }
//
//    public void irFacebook(View view) {
//        Toast.makeText(this, "Crea cuenta facebook", Toast.LENGTH_SHORT).show();
//    }

}
