package com.sanus.sanus.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.sanus.sanus.Fragments.AjustesFragment;
import com.sanus.sanus.Fragments.BusquedaFragment;
import com.sanus.sanus.Fragments.ChatFragment;
import com.sanus.sanus.Fragments.CitasFragment;
import com.sanus.sanus.Fragments.ForoFragment;
import com.sanus.sanus.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_busqueda:
                    setFragment(0);
                    return true;
                case R.id.navigation_citas:
                    setFragment(1);
                    return true;
                case R.id.navigation_chat:
                    setFragment(2);
                    return true;
                case R.id.navigation_foro:
                    setFragment(3);
                    return true;
                case R.id.navigation_ajustes:
                    setFragment(4);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setFragment(0);
    }

    public void setFragment (int pos) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (pos) {
            case 0:
                BusquedaFragment busquedaFragment = new BusquedaFragment();
                transaction.replace(R.id.fragment, busquedaFragment);
                transaction.commit();
                break;
            case 1:
                CitasFragment citasFragment = new CitasFragment();
                transaction.replace(R.id.fragment, citasFragment);
                transaction.commit();
                break;
            case 2:
                ChatFragment chatFragment = new ChatFragment();
                transaction.replace(R.id.fragment, chatFragment);
                transaction.commit();
                break;
            case 3:
                ForoFragment foroFragment = new ForoFragment();
                transaction.replace(R.id.fragment, foroFragment);
                transaction.commit();
                break;
            case 4:
                AjustesFragment ajustesFragment = new AjustesFragment();
                transaction.replace(R.id.fragment, ajustesFragment);
                transaction.commit();
                break;

        }
    }

}
