package sv.edu.utec.tareareposicion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import sv.edu.utec.tareareposicion.Modelo.DAOusuario;
import sv.edu.utec.tareareposicion.Modelo.Usuario;

public class Principal extends AppCompatActivity {
Button btnSalir;
TextView tvNom;
int id=0;
DAOusuario dao;
Usuario u;
String CHANNEL_ID="NOTIFICACION";
int NOTIFICACION_ID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_principal);
        btnSalir=findViewById(R.id.btnCerrar);
        tvNom=findViewById(R.id.tvMs);


        Bundle b = getIntent().getExtras();
        id=b.getInt("Id");
        dao=new DAOusuario(this);
        u=dao.getUsuarioById(id);
        tvNom.setText("Bienvenido "+u.getNombre()+" "+u.getApellidos());
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Principal.this,MainActivity.class);
                startActivity(i);
                createNotificacionChanel();
                createNotificacion(R.drawable.baseline_exit_to_app_24,"Cerrando sesión","", Color.RED);
            }
        });
    }

    private void createNotificacionChanel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name="Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @SuppressLint("MissingPermission")
    private void createNotificacion(int dr,String til,String tex,int col){
        NotificationCompat.Builder builder= new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(dr);
        builder.setContentTitle(til);
        builder.setContentText(tex);
        builder.setColor(col);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long [] {1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID,builder.build());
    }
}