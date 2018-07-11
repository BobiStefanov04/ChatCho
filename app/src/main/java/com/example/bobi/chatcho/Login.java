package com.example.bobi.chatcho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bobi.chatcho.ActividadUsuarios.ActivityUsuarios;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    private EditText et_user, et_pass ;
    private Button b_login;
    private Button b_register;

    private RadioButton rB_session;
    private VolleyRP voley;
    private RequestQueue mRequest;

    private String USER = "";
    private String PASSWORD = "";

    private boolean isActivatedRadioButton;

    private static final String ip = "http://annual-formats.000webhostapp.com/login_getid.php?usuario=";
    private static final String IP_TOKEN = "https://annual-formats.000webhostapp.com/token_insert_and_update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Preferences.obtenerPreferenceBoolean(this, Preferences.PREFERENCES_ESTAD_BUTTON)){
            IniciarActividadSiguiente();
        }

        voley = VolleyRP.getInstance(this);
        mRequest = voley.getRequestQueue();

        et_user    = (EditText) findViewById(R.id.et_user);
        et_pass    = (EditText) findViewById(R.id.et_password);
        b_login    = (Button) findViewById(R.id.b_login);
        b_register = (Button) findViewById(R.id.b_registrar);

        rB_session             = (RadioButton) findViewById(R.id.rB_session);
        isActivatedRadioButton = rB_session.isChecked();

        rB_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActivatedRadioButton){
                    rB_session.setChecked(false);
                }
                isActivatedRadioButton = rB_session.isChecked();

            }
        });

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarLogin(et_user.getText().toString(), et_pass.getText().toString());
            }
        });

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(Login.this, Registro.class);
                startActivity(i);
            }
        });
    }

    private void comprobarLogin(String user, String pass){
        USER = user;
        PASSWORD = pass;
        solicitudJSON(ip+user);
    }

    public void solicitudJSON(String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                VerificarPassword(datos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,"Error conexion con el sevidor: ",Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest, this, voley);
    }

    public void VerificarPassword(JSONObject datos){
        try {
            String estado = datos.getString("resultado");
            if(estado.equals("ConsultaCorrecta")){
                JSONObject jsonDatos = new JSONObject(datos.getString("datos"));
                String usuario = jsonDatos.getString("usuario");
                String password = jsonDatos.getString("password");
                if (usuario.equals(USER) && password.equals(PASSWORD)){
                    String token = FirebaseInstanceId.getInstance().getToken();
                    if (token != null){
                        if ((""+token.charAt(0)).equalsIgnoreCase("{")){
                            JSONObject js = new JSONObject(token);
                            String tokenRecortado = js.getString("token");
                            subirToken(tokenRecortado);
                        }else{
                            subirToken(token);
                        }

                    }else {
                        Toast.makeText(this, "El token es nulo", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){

        }
    }

    private void subirToken(String token){
        HashMap<String, String> hashMapToken = new HashMap<>();
        hashMapToken.put("id",USER);
        hashMapToken.put("token", token);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_TOKEN ,new JSONObject(hashMapToken),  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                Preferences.savePreferenceBoolean(Login.this, rB_session.isChecked(),Preferences.PREFERENCES_ESTAD_BUTTON);
                Preferences.savePreferenceString(Login.this, USER, Preferences.PREFERENCES_USUARIO_LOGIN);
                try {
                    Toast.makeText(Login.this,datos.getString("resultado"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {}
                IniciarActividadSiguiente();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,"el token no se pudo subir a la bbdd ",Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest, this, voley);
    }

    public void IniciarActividadSiguiente(){
        Intent i = new Intent(Login.this, ActivityUsuarios.class);
        startActivity(i);
        finish();
    }

}
