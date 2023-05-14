package sv.edu.utec.tareareposicion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sv.edu.utec.tareareposicion.Modelo.DAOusuario;
import sv.edu.utec.tareareposicion.Modelo.Usuario;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLog,btnRegistrar;
    EditText user,pass;
    private PendingIntent r;
    String CHANNEL_ID="NOTIFICACION";
    int NOTIFICACION_ID=0;
    DAOusuario dao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        user=(EditText)findViewById(R.id.username) ;
        pass=(EditText)findViewById(R.id.password);
        btnLog=(Button)findViewById(R.id.loginButton);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrarse);

        btnLog.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        dao=new DAOusuario(this);

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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginButton:
                String u=user.getText().toString();
                String p=pass.getText().toString();
                if(u.equals("")&&p.equals("")){
                    Toast.makeText(this,"ERROR: Campos vacíos",Toast.LENGTH_LONG).show();
                }
                else if(dao.login(u,p)==1){
                    Usuario ux=dao.getUsuario(u,p);
                    Toast.makeText(this,"Inicio exitoso",Toast.LENGTH_LONG).show();
                    createNotificacionChanel();
                    createNotificacion(R.drawable.baseline_check_24,"Inicio de sesión correcto","", Color.GREEN);
                    Intent i2 = new Intent(MainActivity.this,Principal.class);
                    i2.putExtra("Id",ux.getId());
                    startActivity(i2);
                }
                else
                {
                    Toast.makeText(this,"ERROR: Usuario y/o contraseña incorrectos",Toast.LENGTH_LONG).show();
                    createNotificacionChanel();
                    createNotificacion(R.drawable.baseline_report_gmailerrorred_24,"Error","Usuario y/o contraseña incorrectos", Color.RED);
                }
                break;
            case R.id.btnRegistrarse:
                Intent i = new Intent(MainActivity.this,Registrar.class);
                startActivity(i);
                break;
        }
    }
}