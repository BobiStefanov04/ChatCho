package com.example.bobi.chatcho.ActividadUsuarios;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bobi.chatcho.R;

/**
 * Created by Bobi on 08/07/2018.
 */

public class Fragment_2  extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_registro,container,false);
        return view;
    }
}
