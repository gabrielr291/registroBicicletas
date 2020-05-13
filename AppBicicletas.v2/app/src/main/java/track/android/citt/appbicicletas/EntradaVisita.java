
package track.android.citt.appbicicletas;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.FirebaseApp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import track.android.citt.appbicicletas.model.Visita;

public class EntradaVisita extends AppCompatActivity {

    EditText edtRut, edtNombre, edtCorreo, edtTelefono;
    FirebaseDatabase firebaseDatabase; DatabaseReference databaseReference;
    Button btnGurdar,btnLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_visita);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        edtRut = findViewById(R.id.edtRut); edtNombre = findViewById(R.id.edtNombre);
        edtCorreo = findViewById(R.id.edtCorreo); edtTelefono = findViewById(R.id.edtTelefono);
        btnGurdar = findViewById(R.id.btnGuardar); btnGurdar.setOnClickListener(onClickListener);
        btnLimpiar = findViewById(R.id.btnLimpiar); btnLimpiar.setOnClickListener(onClickListener);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); getSupportActionBar().setDisplayShowHomeEnabled(true);
        iniciarFirebase();
    }

    public void iniciarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }

    private View.OnClickListener onClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnGuardar:
                    String rutVisita=edtRut.getText().toString();
                    String nombreVisita=edtNombre.getText().toString();
                    String correoVisita=edtCorreo.getText().toString();
                    String telefonoVisita=edtTelefono.getText().toString();
                    Date objDate = new Date();
                    String strDateFormat = "hh:mm dd-MM-yyyy"; // El formato de fecha está especificado
                    SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
                    if (rutVisita.isEmpty()){
                        Snackbar.make(view,"El R.U.T no puede estar vacio",Snackbar.LENGTH_LONG).show();
                    }else{
                        if (nombreVisita.isEmpty()) {
                            Snackbar.make(view, "El Nombre no puede estar vacio", Snackbar.LENGTH_LONG).show();
                        }else{
                            Visita visi = new Visita(rutVisita,nombreVisita,correoVisita,telefonoVisita,objSDF.format(objDate),"");
                            databaseReference.child("visita").child(rutVisita).child("evento").setValue(visi);
                            Snackbar.make(view,"Entrada registrada correctamente",Snackbar.LENGTH_LONG).show();
                            limpiar();
                        }
                    }
                case R.id.btnLimpiar:
                    limpiar();
            }
        }
    };

    private void limpiar() {
        edtRut.setText("");
        edtNombre.setText("");
        edtTelefono.setText("");
        edtCorreo.setText("");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EntradaVisita.this, PreEntradaActivity.class));
    }
}
