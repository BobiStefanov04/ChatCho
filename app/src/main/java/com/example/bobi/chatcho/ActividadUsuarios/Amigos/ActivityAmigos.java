package com.example.bobi.chatcho.ActividadUsuarios.Amigos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bobi.chatcho.Login;
import com.example.bobi.chatcho.Preferences;
import com.example.bobi.chatcho.R;
import com.example.bobi.chatcho.VolleyRP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bobi on 20/06/2018.
 */

public class ActivityAmigos extends Fragment {

    private List<Amigo> listaAmigos;
    private AmigosAdapter adaptador;
    private RecyclerView rv;

    private VolleyRP voley;
    private RequestQueue mRequest;

    private static final String URL_GET_ALL_USUARIOS = "https://annual-formats.000webhostapp.com/Amigos_GETALL.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);
        setTitle("Amigos");

        voley = VolleyRP.getInstance(this);
        mRequest = voley.getRequestQueue();

        listaAmigos = new ArrayList<Amigo>();

        rv = (RecyclerView) findViewById(R.id.rv_Amigos);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);

        adaptador = new AmigosAdapter(listaAmigos, getContext());
        rv.setAdapter(adaptador);

        solicitudJSON();

    }





    public void agregarAmigo(int fotoPerfil, String nombre, String ultimoMensaje, String hora, String id){
        Amigo friend = new Amigo();
        friend.setId(id);
        friend.setFotoAmigo(fotoPerfil);
        friend.setNombre(nombre);
        friend.setMensaje(ultimoMensaje);
        friend.setHora(hora);
        listaAmigos.add(friend);
        adaptador.notifyDataSetChanged();
    }

    public void solicitudJSON(){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL_GET_ALL_USUARIOS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String todosDatosUsuarios = datos.getString("resultado");
                    String usersWithToken     = datos.getString("UsuariosConToken");
                    JSONArray jsonArray = new JSONArray(todosDatosUsuarios);
                    JSONArray jsonUserToken = new JSONArray(usersWithToken);
                    String myUser = Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCES_USUARIO_LOGIN);
                    for (int i = 0; jsonArray.length() > i; i++){
                        JSONObject js = jsonArray.getJSONObject(i);
                        if (!myUser.equals(js.getString("id"))) {
                            for (int k = 0; k< jsonUserToken.length();k++){
                                JSONObject userWToken = jsonUserToken.getJSONObject(k);
                                if(js.getString("id").equals(userWToken.getString("id"))){
                                    agregarAmigo(R.drawable.ic_account_circle, js.getString("nombre"), "que pasa cabron!", "12:23 ", js.getString("id"));
                                }
                            }

                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(),"Error al descomponer el JSON",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error conexion con el sevidor: ",Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest, getContext(), voley);
    }
}
