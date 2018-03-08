package com.sanus.sanus.domain.main.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sanus.sanus.domain.configuration.view.AjustesFragment;
import com.sanus.sanus.domain.main.presenter.MainPresenter;
import com.sanus.sanus.domain.main.presenter.MainPresenterImpl;
import com.sanus.sanus.domain.search.view.SearchFragment;
import com.sanus.sanus.domain.chat.view.ChatFragment;
import com.sanus.sanus.domain.citas.view.CitasFragment;
import com.sanus.sanus.domain.foro.view.ForoFragment;
import com.sanus.sanus.R;

public class MainActivity extends AppCompatActivity implements MainView {

    private BottomNavigationViewEx navigation;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpVariable();
        setUpView();
        showSerchFragment();
    }

    private void setUpVariable() {
        if (presenter == null) {
            presenter = new MainPresenterImpl(this);
        }
    }

    private void setUpView() {

        navigation = findViewById(R.id.navigation);
        navigation.enableAnimation(true);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);

        setUpBottomNavigationBar();
        
    }

    private void setUpBottomNavigationBar() {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_busqueda:
                        showSerchFragment();
                        return true;
                    case R.id.navigation_citas:
                        showCitasFragment();
                        return true;
                    case R.id.navigation_chat:
                        showChatFragment();
                        return true;
                    case R.id.navigation_foro:
                        showForoFragment();
                        return true;
                    case R.id.navigation_ajustes:
                        showConfigFragment();
                        return true;
                }
                return true;
            }
        });
    }


    private void showCitasFragment() {

        CitasFragment citasFragment = (CitasFragment) getSupportFragmentManager().findFragmentByTag(CitasFragment.IDENTIFIER);
        if(citasFragment == null){
            citasFragment = new CitasFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, citasFragment, CitasFragment.IDENTIFIER)
                .commit();
    }

    private void showSerchFragment() {

        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag(SearchFragment.IDENTIFIER);
        if(searchFragment == null){
            searchFragment = new SearchFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, searchFragment, SearchFragment.IDENTIFIER)
                .commit();
    }

    private void showChatFragment() {

        ChatFragment chatFragment = (ChatFragment) getSupportFragmentManager().findFragmentByTag(ChatFragment.IDENTIFIER);
        if(chatFragment == null){
            chatFragment = new ChatFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, chatFragment, ChatFragment.IDENTIFIER)
                .commit();

    }

    private void showForoFragment() {

        ForoFragment foroFragment = (ForoFragment) getSupportFragmentManager().findFragmentByTag(ForoFragment.IDENTIFIER);
        if(foroFragment == null){
            foroFragment = new ForoFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, foroFragment, ForoFragment.IDENTIFIER)
                .commit();
    }

    private void showConfigFragment() {

        AjustesFragment ajustesFragment = (AjustesFragment) getSupportFragmentManager().findFragmentByTag(AjustesFragment.IDENTIFIER);
        if(ajustesFragment == null){
            ajustesFragment = new AjustesFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, ajustesFragment, AjustesFragment.IDENTIFIER)
                .commit();
    }

}
