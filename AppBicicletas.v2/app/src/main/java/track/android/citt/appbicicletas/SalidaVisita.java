
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;

import track.android.citt.appbicicletas.model.Alumno;
import track.android.citt.appbicicletas.model.Registro;
import track.android.citt.appbicicletas.model.Visita;

public class SalidaVisita extends AppCompatActivity {

    EditText edtRut, edtNombre, edtCorreo, edtTelefono;
    FirebaseDatabase firebaseDatabase; DatabaseReference databaseReference;
    Button btnGurdar,btnLimpiar;
    ImageButton btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salida_visita);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        edtRut = findViewById(R.id.edtRut); edtNombre = findViewById(R.id.edtNombre);
        edtCorreo = findViewById(R.id.edtCorreo); edtTelefono = findViewById(R.id.edtTelefono);
        btnGurdar = findViewById(R.id.btnGuardar); btnGurdar.setOnClickListener(onClickListener);
        btnLimpiar = findViewById(R.id.btnLimpiar); btnLimpiar.setOnClickListener(onClickListener);
        btnBuscar=findViewById(R.id.btnBuscar);btnBuscar.setOnClickListener(onClickListener);

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
        public void onClick(final View view) {
            switch (view.getId()){
                case R.id.btnBuscar:
                    String rut = edtRut.getText().toString();
                    if (rut.isEmpty()){
                        Snackbar.make(view,"Debe ingresar un R.U.T valido",Snackbar.LENGTH_LONG).show();
                        limpiar();
                    }else{
                        if (rut.length()<=10 && rut.length()>=9){
                            recuperarVisita(rut);

                        }else {
                            Snackbar.make(view,"El R.U.T no es valido",Snackbar.LENGTH_LONG).show();
                            limpiar();
                        }
                    }
                    break;
                case R.id.btnGuardar:
                    final String rutVisita=edtRut.getText().toString();
                    final String nombreVisita = edtNombre.getText().toString();

                    databaseReference.child("visita").child("evento").child(rutVisita).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                Date objDate = new Date();
                                String strDateFormat = "dd-MM-yyyy"; // El formato de fecha está especificado
                                String strSalidaFormat = "hh:mm dd-MM-yyyy";
                                SimpleDateFormat objSalida = new SimpleDateFormat(strSalidaFormat);
                                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
                                Visita visi = dataSnapshot.getValue(Visita.class);
                                Registro reg = new Registro(nombreVisita,visi.getEntrada(),objSalida.format(objDate));

                                databaseReference.child("visita").child(rutVisita).child("evento").setValue(reg);
                            }catch (Exception e){
                                Snackbar.make(view,"Salida registrada correctamente",Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Snackbar.make(view,"Salida registrada correctamente",Snackbar.LENGTH_LONG).show();
                    limpiar();
                case R.id.btnLimpiar:
                    limpiar();
            }
        }
    };

    public void recuperarVisita(String codigo){
        String rut = codigo.replace("-", "").replace(".", "").replace("k", "K");
        databaseReference.child("visita").child(rut).child("evento").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Visita visita = dataSnapshot.getValue(Visita.class);
                try {
                    if (!visita.getNombre().isEmpty()){
                        Snackbar.make(findViewById(R.id.visita_activity), "Se encontro el R.U.T: "+visita.getRut(), Snackbar.LENGTH_LONG).show();
                        edtRut.setText(visita.getRut());
                        edtNombre.setText(visita.getNombre());
                        edtCorreo.setText(visita.getCorreo());
                        edtTelefono.setText(visita.getTelefono());

                        btnGurdar.setEnabled(true);
                        btnLimpiar.setEnabled(true);
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(findViewById(R.id.visita_activity).getWindowToken(), 0);
                    }else{
                        Snackbar.make(findViewById(R.id.visita_activity), "No se encontro", Snackbar.LENGTH_LONG).show();
                    }
                }catch (Exception exepction){
                    Snackbar.make(findViewById(R.id.visita_activity), "No se encontro", Snackbar.LENGTH_LONG).show();
                    limpiar();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(findViewById(R.id.visita_activity), "Codigo no reconocido", Snackbar.LENGTH_LONG).show();
                limpiar();
            }
        });
    }
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
        startActivity(new Intent(SalidaVisita.this, PreSalidaActivity.class));
    }
}
