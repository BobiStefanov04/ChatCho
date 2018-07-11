package com.example.bobi.chatcho;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.example.bobi.chatcho.Mensajes.Mensajeria;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Bobi on 11/03/2018.
 */

public class Registro extends AppCompatActivity {

    private static final String IP_REGISTRAR = "https://annual-formats.000webhostapp.com/Registro_INSERT.php";

    private VolleyRP voley;
    private RequestQueue mRequest;

    private EditText user, password, nombre, apellidos, dia, mes, ano, correo, telefono;
    private Button registro;
    private RadioButton rd_Hombre, rd_Mujer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        voley = VolleyRP.getInstance(this);
        mRequest = voley.getRequestQueue();

        registro = (Button) findViewById(R.id.b_regRegister);

        rd_Hombre = (RadioButton) findViewById(R.id.rd_hombreRegister);
        rd_Mujer = (RadioButton) findViewById(R.id.rd_mujerRegister);

        user = (EditText) findViewById(R.id.et_userRegister);
        password = (EditText) findViewById(R.id.et_passwRegister);
        nombre = (EditText) findViewById(R.id.et_nomRegister);
        apellidos = (EditText) findViewById(R.id.et_apellRegister);
        dia = (EditText) findViewById(R.id.et_diaRegister);
        mes = (EditText) findViewById(R.id.et_mesRegister);
        ano = (EditText) findViewById(R.id.et_anoRegister);
        correo = (EditText) findViewById(R.id.et_correoRegister);
        telefono = (EditText) findViewById(R.id.et_telefRegister);

        rd_Hombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rd_Mujer.setChecked(false);
            }
        });

        rd_Mujer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rd_Hombre.setChecked(false);
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String genero = "";

                if (rd_Hombre.isChecked()) {
                    genero = "Hombre";
                } else {
                    genero = "Mujer";
                }

                registrarWebService(user.getText().toString(),
                        password.getText().toString(),
                        nombre.getText().toString(),
                        apellidos.getText().toString(),
                        dia.getText().toString() + "/" + mes.getText().toString() + "/" + ano.getText().toString(),
                        correo.getText().toString(),
                        telefono.getText().toString(),
                        genero);
            }
        });
    }

    private void registrarWebService(String id, String contrasena, String nombre, String apellidos, String fechaNacimiento, String correo, String numero, String genero) {

        if (!id.isEmpty() &&
                !contrasena.isEmpty() &&
                !nombre.isEmpty() &&
                !apellidos.isEmpty() &&
                !fechaNacimiento.isEmpty() &&
                !correo.isEmpty() &&
                !numero.isEmpty() ) {


            HashMap<String, String> hashMapToken = new HashMap<>();
            hashMapToken.put("id", id.trim());
            hashMapToken.put("nombre", nombre.trim());
            hashMapToken.put("apellidos", apellidos.trim());
            hashMapToken.put("fecha_nac", fechaNacimiento.trim());
            hashMapToken.put("correo", correo.trim());
            hashMapToken.put("telefono", numero.trim());
            hashMapToken.put("genero", genero);
            hashMapToken.put("password", contrasena.trim());

            JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, IP_REGISTRAR, new JSONObject(hashMapToken), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject datos) {
                    try {
                        String resultado = datos.getString("resultado");
                        if (resultado.equalsIgnoreCase("El usuario se registro correctamente")) {
                            Toast.makeText(Registro.this, resultado, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Registro.this, resultado, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(Registro.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registro.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            });
            VolleyRP.addToQueue(solicitud, mRequest, this, voley);
        }else {
            Toast.makeText(Registro.this, "Rellene todos los datos", Toast.LENGTH_SHORT).show();
        }
    }
}

