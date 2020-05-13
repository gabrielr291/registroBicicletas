package track.android.citt.appbicicletas;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Vibrator vibrator;
    ImageButton btnEntrada, btnSalida, btnRegistros;
    TextView txtEntrada, txtSalida, txtRegistros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btnEntrada = findViewById(R.id.btnEntrada);
        btnSalida = findViewById(R.id.btnSalida);
        btnRegistros = findViewById(R.id.btnRegistros);

        txtEntrada = findViewById(R.id.txtEntrada);
        txtSalida = findViewById(R.id.txtSalida);
        txtRegistros = findViewById(R.id.txtRegistros);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                vibrator.vibrate(50);

                startActivity(new Intent(MainActivity.this, PreEntradaActivity.class));
                btnEntrada.setEnabled(false);
                finish();
            }
        });

        btnSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(25);

                startActivity(new Intent(MainActivity.this, PreSalidaActivity.class));
                btnSalida.setEnabled(false);
                finish();
            }
        });

        btnRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(25);

                startActivity(new Intent(MainActivity.this,ListadoRegistros.class));
                btnRegistros.setEnabled(false);
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
    }
}
