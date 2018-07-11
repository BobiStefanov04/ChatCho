package com.example.bobi.chatcho.Mensajes;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.bobi.chatcho.R;

import java.util.List;

/**
 * Created by Bobi on 08/12/2017.
 */

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.MensajesViewHolder> {

    private List<MensTexto> mensajesTexto;
    private Context context;

    public MensajesAdapter(List<MensTexto> mensajesTexto, Context contexto) {
        this.mensajesTexto = mensajesTexto;
        this.context = contexto;
    }

    @Override
    public MensajesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_mensajes,parent,false);

        return new MensajesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MensajesViewHolder holder, int position) {

        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) holder.cardView.getLayoutParams();
        FrameLayout.LayoutParams fl = (FrameLayout.LayoutParams) holder.ln_background.getLayoutParams();

        LinearLayout.LayoutParams l1Hora = (LinearLayout.LayoutParams) holder.tvHora.getLayoutParams();
        LinearLayout.LayoutParams l1Mens = (LinearLayout.LayoutParams) holder.tvHora.getLayoutParams();

        if ( mensajesTexto.get(position).getTipoMensaje() == 1 ){ //EMISOR
            holder.ln_background.setBackgroundResource(R.drawable.in_message_bg);
            rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            l1Hora.gravity = Gravity.RIGHT;
            fl.gravity = Gravity.RIGHT;
            l1Mens.gravity = Gravity.RIGHT;
            holder.tvMensaje.setGravity(Gravity.RIGHT);
        }else{
            holder.ln_background.setBackgroundResource(R.drawable.out_message_bg);
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            fl.gravity = Gravity.LEFT;
            l1Mens.gravity = Gravity.LEFT;
            l1Hora.gravity = Gravity.LEFT;
            holder.tvMensaje.setGravity(Gravity.LEFT);
        }

        holder.cardView.setLayoutParams(rl);
        holder.ln_background.setLayoutParams(fl);
        holder.tvHora.setLayoutParams(l1Hora );
        holder.tvMensaje.setLayoutParams(l1Mens);

        holder.tvMensaje.setText(mensajesTexto.get(position).getMensaje());
        holder.tvHora.setText(mensajesTexto.get(position).getHoraMensaje());

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            holder.cardView.getBackground().setAlpha(0);
        }else{
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
        }
    }

    @Override
    public int getItemCount() {
        return mensajesTexto.size();
    }
    static class MensajesViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        LinearLayout ln_background;
        TextView tvMensaje;
        TextView tvHora;

        MensajesViewHolder(View itemView){
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_Mensajes);
            tvMensaje = (TextView) itemView.findViewById(R.id.tv_texto);
            ln_background = (LinearLayout) itemView.findViewById(R.id.backg_mensaje);
            tvHora = (TextView) itemView.findViewById(R.id.tv_hora);
        }
    }

}

