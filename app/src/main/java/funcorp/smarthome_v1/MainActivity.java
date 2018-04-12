package funcorp.smarthome_v1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    //SeekBar sekbar
    Firebase bacadata, bacadata1, bacadata2, bacadata3;
    String terasa, pintua,jendelaa,kondisia, kamaramandi, ruangatamu, sekbara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        final ImageView pintu = (ImageView) findViewById(R.id.pintu);
        final ImageView jendela = (ImageView) findViewById(R.id.jendela);
        Firebase.setAndroidContext(this);
        bacadata = new Firebase("https://rumah-otomatis.firebaseio.com/kendali");


        bacadata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pintua = dataSnapshot.child("pintu").getValue().toString();
                jendelaa = dataSnapshot.child("jendela").getValue().toString();
                kondisia=dataSnapshot.child("kondisi").getValue().toString();
                String t, t2, d, d2, kondisi, r2, k, k2;
                //teras
                t = "mati";
                t2 = "hidup";
                d = "mati";
                d2 = "hidup";
                kondisi="bahaya";

                if (pintua.equals(t)) {
                    pintu.setImageResource(R.mipmap.ic_lock_outline_black_24dp);
                }
                if (pintua.equals(t2)) {
                    pintu.setImageResource(R.mipmap.ic_lock_open_black_24dp);
                }
                if (jendelaa.equals(d)) {
                    jendela.setImageResource(R.mipmap.ic_lock_outline_black_24dp);
                }
                if (jendelaa.equals(d2)) {
                    jendela.setImageResource(R.mipmap.ic_lock_open_black_24dp);
                }

                if (kondisia.equals(kondisi)){
                    notifkondisi();
                }

                if (pintua.equals(t2)){
                    notippintu();
                }
                if (jendelaa.equals(d2)){
                    notifjendela();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        pintu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String a = "mati";
                    if (pintua.equals(a)) {
                        bacadata.child("pintu").setValue("hidup");
                    } else {
                        bacadata.child("pintu").setValue("mati");
                    }
                } catch (Exception e) {

                }
            }
        });
        jendela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String a = "mati";
                    if (jendelaa.equals(a)) {
                        bacadata.child("jendela").setValue("hidup");
                    } else {
                        bacadata.child("jendela").setValue("mati");
                    }
                } catch (Exception e) {

                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void notippintu() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.setAction("alkohol");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_lock_open_black_24dp)
                .setContentTitle("Peringatan")
                .setContentText("Pintu Rumah anda terbuka")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationBuilder.build());
    }
    public void notifjendela() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.setAction("alkohol");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_lock_open_black_24dp)
                .setContentTitle("Peringatan")
                .setContentText("Jendala anda terbuka")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(2, notificationBuilder.build());
    }
    public void notifkondisi() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.setAction("alkohol");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_lock_open_black_24dp)
                .setContentTitle("Peringatan")
                .setContentText("Rumah anda dicoba di bobol")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(3, notificationBuilder.build());
    }

}
