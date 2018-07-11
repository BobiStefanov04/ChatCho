package com.example.bobi.chatcho.Mensajes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bobi.chatcho.Login;
import com.example.bobi.chatcho.Preferences;
import com.example.bobi.chatcho.R;
import com.example.bobi.chatcho.Registro;
import com.example.bobi.chatcho.Services.FireBaseId;
import com.example.bobi.chatcho.VolleyRP;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bobi on 22/11/2017.
 */

public class Mensajeria extends AppCompatActivity{

    public static final String MENSAJE = "MENSAJE";

    private BroadcastReceiver bR;

    private RecyclerView rv;
    private Button bEnviarMensaje, bCerrarSession;
    private List<MensTexto> mensajes;
    private EditText etEscribirMens;
    private MensajesAdapter adapter;
    private String MENSAJE_ENVIAR = "";
    private String EMISOR = "";
    private String RECEPTOR = "";

    private VolleyRP voley;
    private RequestQueue mRequest;

    private static final String IP_MENSAJE = "https://annual-formats.000webhostapp.com/Enviar_Mensajes.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajeria);

        mensajes = new ArrayList<>();

        voley = VolleyRP.getInstance(this);
        mRequest = voley.getRequestQueue();

        EMISOR = Preferences.obtenerPreferenceString(this, Preferences.PREFERENCES_USUARIO_LOGIN);

        Intent i = new Intent();
        Bundle bun = i.getExtras();
        if ( bun != null ){
            RECEPTOR = bun.getString("key_receptor");
        }

        Toolbar toolb = (Toolbar) findViewById(R.id.toolbar);
        bEnviarMensaje = (Button) findViewById(R.id.bEnviarMensaje);
        etEscribirMens = (EditText) findViewById(R.id.et_escribirMens);


        rv = (RecyclerView) findViewById(R.id.rvMensajes);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);

        adapter = new MensajesAdapter(mensajes,this);
        rv.setAdapter(adapter);

        bEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = etEscribirMens.getText().toString().trim();
                //RECEPTOR = etReceptor.getText().toString();
                if (!mensaje.isEmpty() && !RECEPTOR.isEmpty()){
                    MENSAJE_ENVIAR = mensaje;
                   // RECEPTOR = etReceptor.getText().toString();
                    MandarMensaje();
                    CreateMensaje(mensaje, "00:00",1);
                    etEscribirMens.setText("");
                }

            }
        });

        toolb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setScrollbarChat();

        bR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String mensaje = intent.getStringExtra("key_mensaje");
                String hora = intent.getStringExtra("key_hora");
                String parametrosHora[] = hora.split(",");
                String emisor = intent.getStringExtra("key_emisor_PHP");
                if (emisor.equals(RECEPTOR)) {
                    CreateMensaje(mensaje, parametrosHora[0], 2);
                }

            }
        };
    }

    private void MandarMensaje(){
        HashMap<String, String> hashMapToken = new HashMap<>();
        hashMapToken.put("emisor",EMISOR);
        hashMapToken.put("receptor", RECEPTOR);
        hashMapToken.put("mensaje", MENSAJE_ENVIAR);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_MENSAJE ,new JSONObject(hashMapToken),  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    Toast.makeText(Mensajeria.this,datos.getString("resultado"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mensajeria.this,"Ocurrió un error",Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest, this, voley);
    }


    public void CreateMensaje(String mensaje, String hora, int tipoDeMensaje){
        MensTexto m = new MensTexto();
        m.setId("0");
        m.setMensaje(mensaje);
        m.setHoraMensaje(hora);
        m.setTipoMensaje(tipoDeMensaje);
        mensajes.add(m);
        adapter.notifyDataSetChanged();
        setScrollbarChat();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(bR, new IntentFilter(MENSAJE));
    }

    public void setScrollbarChat(){
        rv.scrollToPosition(adapter.getItemCount() - 1);
    }
}
