package com.example.bobi.chatcho.ActividadUsuarios.Amigos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bobi.chatcho.Mensajes.Mensajeria;
import com.example.bobi.chatcho.R;

import java.util.List;

/**
 * Created by Bobi on 20/06/2018.
 */

public class AmigosAdapter extends RecyclerView.Adapter<AmigosAdapter.HolderAmigos> {

    private List<Amigo> amigos;
    private Context context;

    public AmigosAdapter(List<Amigo> listaAmigos, Context context){
        this.amigos = listaAmigos;
        this.context = context;
    }

    @Override
    public HolderAmigos onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_amigos,parent,false);

        return new AmigosAdapter.HolderAmigos(v);
    }

    @Override
    public void onBindViewHolder(HolderAmigos holder,final int position) {
        holder.imageView.setImageResource((amigos.get(position)).getFotoAmigo());
        holder.nombre.setText((amigos.get(position)).getNombre());
        holder.mensaje.setText((amigos.get(position)).getMensaje());
        holder.hora.setText((amigos.get(position)).getHora());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Mensajeria.class);
                i.putExtra("key_receptor", amigos.get(position).getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return amigos.size();
    }

    static class HolderAmigos extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView imageView;
        TextView nombre;
        TextView mensaje;
        TextView hora;

        public HolderAmigos(View itemView) {
            super(itemView);
            cardView  = (CardView)  itemView.findViewById(R.id.cv_Amigos);
            imageView = (ImageView) itemView.findViewById(R.id.iv_fotoPerfAmigos);
            nombre    = (TextView)  itemView.findViewById(R.id.tv_nomUserAmigo);
            mensaje   = (TextView)  itemView.findViewById(R.id.tv_mensajeAmigos);
            hora      = (TextView)  itemView.findViewById(R.id.horaAmigos);

        }
    }
}
