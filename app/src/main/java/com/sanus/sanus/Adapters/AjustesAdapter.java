package com.sanus.sanus.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sanus.sanus.Data.AjustesData;

import java.util.List;

/**
 * Created by Mireya on 23/02/2018.
 */

public class AjustesAdapter{
    Context context;
    List<AjustesData> ajustesDataList;

    public AjustesAdapter(Context context, List<AjustesData> ajustesDataList){
        this.context = context;
        this.ajustesDataList = ajustesDataList;
    }


}
