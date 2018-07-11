package com.example.bobi.chatcho;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Bobi on 17/06/2018.
 */

public class Preferences {

    public static final String STRING_PREFERENCES = "bobi.chatcho";
    public static final String PREFERENCES_ESTAD_BUTTON = "estado.button.session";
    public static final String PREFERENCES_USUARIO_LOGIN = "usuario.login";

    public static void savePreferenceBoolean(Context c, boolean b, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putBoolean(key, b).apply();
    }

    public static void savePreferenceString(Context c, String str, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putString(key, str).apply();
    }

    public static boolean obtenerPreferenceBoolean(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        return preferences.getBoolean(key, false); //si nunca se ha guardado nada en esta key retornará false
    }

    public static String obtenerPreferenceString(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        return preferences.getString(key, ""); //si nunca se ha guardado nada en esta key retornará cadena vacia
    }
}
