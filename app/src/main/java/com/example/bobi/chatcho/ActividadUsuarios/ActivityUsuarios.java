package com.example.bobi.chatcho.ActividadUsuarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bobi.chatcho.ActividadUsuarios.Amigos.ActivityAmigos;
import com.example.bobi.chatcho.Login;
import com.example.bobi.chatcho.Preferences;
import com.example.bobi.chatcho.R;

/**
 * Created by Bobi on 04/07/2018.
 */

public class ActivityUsuarios extends AppCompatActivity {


    private TabLayout tableLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        tableLayout = (TabLayout) findViewById(R.id.tabLayout_usuarios);
        viewPager = (ViewPager) findViewById(R.id.viewPager_usuarios);
        tableLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new AdapterUsuarios(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0 ){
                    Toast.makeText(ActivityUsuarios.this, "estoy en el fragment 1", Toast.LENGTH_SHORT).show();
                }else if(position == 1){
                    Toast.makeText(ActivityUsuarios.this, "estoy en el fragment 2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_amigos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == R.id.it_cerrarSessionMenu){
            Preferences.savePreferenceBoolean(ActivityUsuarios.this, false, Preferences.PREFERENCES_ESTAD_BUTTON);
            Intent i  = new Intent(ActivityUsuarios.this, Login.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
