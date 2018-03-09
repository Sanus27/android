package com.sanus.sanus.domain.search.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sanus.sanus.R;
import com.sanus.sanus.domain.search.adapter.BusquedaDoctorAdapter;
import com.sanus.sanus.domain.search.data.BusquedaDoctor;
import com.sanus.sanus.domain.search.presenter.SearchPresenter;
import com.sanus.sanus.domain.search.presenter.SearchPresenterImpl;

import java.util.List;

public class SearchFragment extends Fragment implements SearchView{

    public static String IDENTIFIER = "SEARCH_FRAGMENT";

    private SearchPresenter presenter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);
        setUpVariable();
        setUpView(view);

        presenter.init();

        return view;
    }

    private void setUpVariable() {
        if (presenter == null) {
            presenter = new SearchPresenterImpl(this);
        }
    }

    private void setUpView(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        EditText edbuscador = view.findViewById(R.id.edbuscador);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        edbuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.buscador(s.toString());
            }
        });

    }

    @Override
    public void setDataAdapter(List<BusquedaDoctor> busquedaDoctors) {
        BusquedaDoctorAdapter busquedaDoctorAdapteradapter = new BusquedaDoctorAdapter(getContext(), busquedaDoctors);
        recyclerView.setAdapter(busquedaDoctorAdapteradapter);
        busquedaDoctorAdapteradapter.notifyDataSetChanged();
    }
}

