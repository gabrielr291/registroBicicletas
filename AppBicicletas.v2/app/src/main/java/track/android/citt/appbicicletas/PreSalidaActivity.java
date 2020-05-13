package track.android.citt.appbicicletas;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PreSalidaActivity extends AppCompatActivity {

    Vibrator vibrator;
    Button btnAlumno,btnVisita;
    ImageButton imgBtnAlumno,imgBtnVisita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_salida);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnAlumno = findViewById(R.id.btnAlumno);
        btnVisita=findViewById(R.id.btnVisita);
        imgBtnAlumno = findViewById(R.id.imgBtnAlumno);
        imgBtnVisita=findViewById(R.id.imgBtnVisita);


        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(50);
                startActivity(new Intent(PreSalidaActivity.this, SalidaAlumno.class));
                btnAlumno.setEnabled(false);
                imgBtnAlumno.setEnabled(false);
                finish();
            }
        });

        imgBtnAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(50);
                startActivity(new Intent(PreSalidaActivity.this, SalidaAlumno.class));
                btnAlumno.setEnabled(false);
                imgBtnAlumno.setEnabled(false);
                finish();
            }
        });
        btnVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(50);
                startActivity(new Intent(PreSalidaActivity.this, SalidaVisita.class));
                btnVisita.setEnabled(false);
                imgBtnVisita.setEnabled(false);
                finish();
            }
        });

        imgBtnVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(50);
                startActivity(new Intent(PreSalidaActivity.this, SalidaVisita.class));
                btnVisita.setEnabled(false);
                imgBtnVisita.setEnabled(false);
                finish();
            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PreSalidaActivity.this, MainActivity.class));
    }
}
