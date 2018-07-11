package com.example.bobi.chatcho.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;

import com.example.bobi.chatcho.Mensajes.Mensajeria;
import com.example.bobi.chatcho.Preferences;
import com.example.bobi.chatcho.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by Bobi on 20/01/2018.
 */

public class FireBaseServiceMensajes extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String mensaje = remoteMessage.getData().get("mensaje");
        String hora = remoteMessage.getData().get("hora");
        String cabecera = remoteMessage.getData().get("cabecera");
        String cuerpo = remoteMessage.getData().get("cuerpo");
        String receptor = remoteMessage.getData().get("receptor");
        String emisorPHP = remoteMessage.getData().get("emisor");
        String emisor = Preferences.obtenerPreferenceString(this, Preferences.PREFERENCES_USUARIO_LOGIN);


        if ( emisor.equals(receptor)){
            Mensaje(mensaje, hora, emisorPHP );
            showNotification(cabecera, cuerpo);
        }
    }

    private void Mensaje(String mensaje, String hora, String emisor){
        Intent i = new Intent(Mensajeria.MENSAJE);
        i.putExtra("key_mensaje", mensaje);
        i.putExtra("key_hora", hora);
        i.putExtra("key_emisor_PHP", emisor);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }

    private void showNotification(String cabecera, String cuerpo){
        Intent i = new Intent(this,Mensajeria.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        Uri soundNotificacion = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Builder builder = new Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle(cabecera);
        builder.setContentText(cuerpo);
        builder.setSound(soundNotificacion);
        builder.setTicker(cuerpo);
        builder.setSmallIcon(R.drawable.ic_action_emo_evil);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Random random = new Random();

        notificationManager.notify(random.nextInt(), builder.build());

    }
}
