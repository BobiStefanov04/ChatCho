package com.example.bobi.chatcho.ActividadUsuarios;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bobi.chatcho.ActividadUsuarios.Amigos.ActivityAmigos;

/**
 * Created by Bobi on 08/07/2018.
 */

public class AdapterUsuarios extends FragmentPagerAdapter {
    public AdapterUsuarios(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0 ){
            return new ActivityAmigos();
        }else if(position == 1){
            return new Fragment_2();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0 ){
            return "Amigos";
        }else if(position == 1){
            return "Solicitudes";
        }

        return null;
    }
}
