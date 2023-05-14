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
import android.widget.EditText;
import android.widget.Toast;

import sv.edu.utec.tareareposicion.Modelo.DAOusuario;
import sv.edu.utec.tareareposicion.Modelo.Usuario;


public class Registrar extends AppCompatActivity implements View.OnClickListener {
EditText us,pas,nom,ap;
Button reg,can;
DAOusuario dao;
String CHANNEL_ID="NOTIFICACION";
int NOTIFICACION_ID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registrar);
        us=(EditText)findViewById(R.id.username) ;
        pas=(EditText)findViewById(R.id.password);
        nom=(EditText)findViewById(R.id.nomb) ;
        ap=(EditText)findViewById(R.id.ap);
        reg=findViewById(R.id.btnregis);
        can=findViewById(R.id.btncan);

        reg.setOnClickListener(this);
        can.setOnClickListener(this);
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
            case R.id.btnregis:
                Usuario u = new Usuario();
                u.setUsuario(us.getText().toString());
                u.setPassword(pas.getText().toString());
                u.setNombre(nom.getText().toString());
                u.setApellidos(ap.getText().toString());

                if(!u.isNull()){
                    Toast.makeText(this,"ERROR: Campos vacíos",Toast.LENGTH_LONG).show();
                    createNotificacionChanel();
                    createNotificacion(R.drawable.baseline_report_gmailerrorred_24,"Error","Los campos se encuentran vacíos", Color.RED);
                }
                else if (dao.insertUsuario(u)){
                    Toast.makeText(this,"Registro Exitoso!!!",Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(Registrar.this,MainActivity.class);
                    startActivity(i2);
                    finish();
                    createNotificacion(R.drawable.baseline_check_24,"Registro insertado","Los datos fueron ingresados", Color.GREEN);
                }
                else
                {
                    Toast.makeText(this,"Usuario ya registrado",Toast.LENGTH_LONG).show();
                    createNotificacionChanel();
                    createNotificacion(R.drawable.baseline_sms_failed_24,"Error","El usuario ya se encuentra registrado", Color.BLUE);
                }
                break;
            case R.id.btncan:
                Intent i = new Intent(Registrar.this,MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}