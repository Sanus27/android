package com.sanus.sanus.domain.account.complete.view;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.sanus.sanus.R;
import com.sanus.sanus.domain.account.complete.presenter.CompleteRegisterPresenter;
import com.sanus.sanus.domain.account.complete.presenter.CompleteRegisterPresenterImpl;
import com.sanus.sanus.domain.main.view.MainActivity;
import com.sanus.sanus.utils.alert.AlertUtils;
import com.sanus.sanus.utils.glide.GlideApp;
import com.sanus.sanus.utils.keyboard.KeyboardUtil;

import java.util.ArrayList;


public class CompleteRegisterActivity extends AppCompatActivity implements CompleteRegisterView {
    private CompleteRegisterPresenter presenter;

    private EditText nombre, apellido;
    private Button masculino, femenino, save;
    private Spinner spinnerEdad;
    private ImageView imgavatar;
    private String edadPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completa_registro);

        setUpVariable();
        setUpView();
        presenter.setUpSpinner();

    }

    private void setUpView() {

        nombre = findViewById(R.id.edNombre);
        apellido = findViewById(R.id.edApellido);
        masculino = findViewById(R.id.btnMasculino);
        femenino = findViewById(R.id.btnFemenino);
        spinnerEdad = findViewById(R.id.spinnerEdad);
        save = findViewById(R.id.btnGuardar);
        RelativeLayout rlHeader = findViewById(R.id.rlHeader);
        imgavatar = findViewById(R.id.imgProfile);
        ImageView imgCamara = findViewById(R.id.imgCamera);

        masculino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickGenderMale();
            }
        });

        femenino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickGenderFemale();
            }
        });

        imgCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickImgCamera();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickSaveData();
            }
        });

        rlHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });

        nombre.addTextChangedListener(new TextWatcher() {
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

        apellido.addTextChangedListener(new TextWatcher() {
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

    private void hideKeyboard() {
        KeyboardUtil.hide(this);
    }

    private void setUpVariable() {

        if (presenter == null) {
            presenter = new CompleteRegisterPresenterImpl(this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data, getApplicationContext());
    }

    @Override
    public void setImageAvatar(Bitmap bitmap) {
         imgavatar.setImageBitmap(bitmap);
    }

    @Override
    public void selectMale() {
        masculino.setBackgroundColor(getResources().getColor(R.color.black));
        masculino.setTextColor(getResources().getColor(R.color.white));
        femenino.setBackgroundColor(getResources().getColor(R.color.white));
        femenino.setTextColor(getResources().getColor(R.color.black));

    }

    @Override
    public void selectFemale() {
        femenino.setBackgroundColor(getResources().getColor(R.color.black));
        femenino.setTextColor(getResources().getColor(R.color.white));
        masculino.setBackgroundColor(getResources().getColor(R.color.white));
        masculino.setTextColor(getResources().getColor(R.color.black));

    }

    @Override
    public void showFileChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select"), requestCode);
    }

    @Override
    public void setListSpinner(ArrayList<String> ageList) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, ageList);
        adapter.setDropDownViewResource(R.layout.list_item_spinner);
        spinnerEdad.setAdapter(adapter);
        spinnerEdad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edadPosition = spinnerEdad.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    @Override
    public String getName() {
        return nombre.getText().toString().trim();
    }

    @Override
    public String getLastName() {
        return apellido.getText().toString().trim();
    }

    @Override
    public String getEdadPosition() {
        return edadPosition;
    }

    @Override
    public void enableButton() {
        save.setEnabled(true);
    }

    @Override
    public void disableButton() {
        save.setEnabled(false);
    }

    @Override
    public void showMessage(int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void goMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public ContentResolver getContentResolve() {
        return getContentResolver();
    }

    @Override
    public void showImage(Bitmap bitmap) {
        if (bitmap!=null){
            GlideApp.with(this).load(bitmap).apply(RequestOptions.circleCropTransform()).into(imgavatar);

        }
    }

    @Override
    public ProgressDialog getLoading() {
        return AlertUtils.getLoading(this);
    }
}
