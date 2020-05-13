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
import android.widget.Toast;

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
import java.util.UUID;

import track.android.citt.appbicicletas.model.Alumno;
import track.android.citt.appbicicletas.model.Evento;
import track.android.citt.appbicicletas.model.Registro;

public class SalidaAlumno extends AppCompatActivity {

    EditText edtRut, edtNombre, edtCorreo, edtCarrera;
    ImageButton btnEscaner, btnBuscar;
    FirebaseDatabase firebaseDatabase; DatabaseReference databaseReference;
    Button btnGurdar,btnLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_alumno);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnBuscar = findViewById(R.id.btnBuscar); btnBuscar.setOnClickListener(onClickListener);
        btnEscaner = findViewById(R.id.btnEscaner); btnEscaner.setOnClickListener(onClickListener);
        edtRut = findViewById(R.id.edtRut); edtNombre = findViewById(R.id.edtNombre);
        edtCorreo = findViewById(R.id.edtCorreo); edtCarrera = findViewById(R.id.edtCarrera);
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
                case R.id.btnEscaner:
                    new IntentIntegrator(SalidaAlumno.this).initiateScan();
                    break;
                case R.id.btnBuscar:
                    String rut = edtRut.getText().toString();
                    if (rut.isEmpty()){
                        Snackbar.make(view,"Debe ingresar un R.U.T valido",Snackbar.LENGTH_LONG).show();
                        limpiar();
                    }else{
                        if (rut.length()<=10 && rut.length()>=9){
                            recuperarAlumno(rut);

                        }else {
                            Snackbar.make(view,"El R.U.T no es valido",Snackbar.LENGTH_LONG).show();
                            limpiar();
                        }
                    }
                    break;
                case R.id.btnGuardar:
                    Evento eve;
                    final String rutAlumno=edtRut.getText().toString();
                    final String nombreAlumno = edtNombre.getText().toString();

                    databaseReference.child("alumno").child(rutAlumno).child("evento").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                Date objDate = new Date();
                                String strDateFormat = "dd-MM-yyyy"; // El formato de fecha está especificado
                                String strSalidaFormat = "hh:mm dd-MM-yyyy";
                                SimpleDateFormat objSalida = new SimpleDateFormat(strSalidaFormat);
                                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
                                Evento eve = dataSnapshot.getValue(Evento.class);
                                Registro reg = new Registro(nombreAlumno,eve.getEntrada(),objSalida.format(objDate));

                                databaseReference.child("registro").child(objSDF.format(objDate)).child(rutAlumno).child(UUID.randomUUID().toString()).setValue(reg);
                                databaseReference.child("alumno").child((rutAlumno)).child("evento").removeValue();
                            }catch (Exception e){
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

    private void limpiar() {
        edtRut.setText("");
        edtNombre.setText("");
        edtCarrera.setText("");
        edtCorreo.setText("");
        btnGurdar.setEnabled(false);
        btnLimpiar.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents() != null){
                recuperarAlumno(result.getContents());
            }else {
                Snackbar.make(findViewById(R.id.alumno_activity), "Cancelado", Snackbar.LENGTH_SHORT).show();
                limpiar();
            }
        }
    }

    public void recuperarAlumno(String codigo){
        String rut = codigo.replace("-", "").replace(".", "").replace("k", "K");
        databaseReference.child("alumno").child(rut).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Alumno alumno = dataSnapshot.getValue(Alumno.class);
                try {
                    if (!alumno.getNombre().isEmpty()){
                        Snackbar.make(findViewById(R.id.alumno_activity), "Se encontro el R.U.T: "+alumno.getRut(), Snackbar.LENGTH_LONG).show();
                        edtRut.setText(alumno.getRut());
                        edtNombre.setText(alumno.getNombre());
                        edtCorreo.setText(alumno.getCorreo());
                        edtCarrera.setText(alumno.getCarrera());

                        btnGurdar.setEnabled(true);
                        btnLimpiar.setEnabled(true);
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(findViewById(R.id.alumno_activity).getWindowToken(), 0);
                    }else{
                        Snackbar.make(findViewById(R.id.alumno_activity), "No se encontro", Snackbar.LENGTH_LONG).show();
                    }
                }catch (Exception exepction){
                    Snackbar.make(findViewById(R.id.alumno_activity), "No se encontro", Snackbar.LENGTH_LONG).show();
                    limpiar();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(findViewById(R.id.alumno_activity), "Codigo no reconocido", Snackbar.LENGTH_LONG).show();
                limpiar();
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
        startActivity(new Intent(SalidaAlumno.this, PreSalidaActivity.class));
    }
}
