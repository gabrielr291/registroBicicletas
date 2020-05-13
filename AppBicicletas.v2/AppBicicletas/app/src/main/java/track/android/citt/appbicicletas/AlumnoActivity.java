
package track.android.citt.appbicicletas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import track.android.citt.appbicicletas.model.Alumno;
import track.android.citt.appbicicletas.model.Registro;

public class AlumnoActivity extends AppCompatActivity {

    EditText edtRut, edtNombre, edtCorreo, edtCarrera;
    ImageButton btnEscaner, btnBuscar;
    Button btnGuardar;//decidir si cambiar a imageButton
    FirebaseDatabase firebaseDatabase; DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnBuscar = findViewById(R.id.btnBuscar); btnBuscar.setOnClickListener(onClickListener);
        btnEscaner = findViewById(R.id.btnEscaner); btnEscaner.setOnClickListener(onClickListener);
        edtRut = findViewById(R.id.edtRut); edtNombre = findViewById(R.id.edtNombre);
        edtCorreo = findViewById(R.id.edtCorreo); edtCarrera = findViewById(R.id.edtCarrera);
        btnGuardar= findViewById(R.id.btnGuardar);btnGuardar.setOnClickListener(onClickListener);//aca nada se ve afectado

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
                    new IntentIntegrator(AlumnoActivity.this).initiateScan();
                    btnGuardar.setEnabled(true);
                    break;
                case R.id.btnBuscar:
                    String rut = edtRut.getText().toString();
                    if (rut.isEmpty()){
                        Snackbar.make(view,"Debe ingresar un R.U.T valido",Snackbar.LENGTH_LONG).show();
                    }else{
                        if (rut.length()<=10 && rut.length()>=9){
                            recuperarAlumno(rut);
                            btnGuardar.setEnabled(true);
                        }else {
                            Snackbar.make(view,"El R.U.T no es valido",Snackbar.LENGTH_LONG).show();
                        }
                    }
                    break;
                case R.id.btnGuardar:
                    String rutAlumno=edtRut.getText().toString();
                    String nombreAlumno=edtNombre.getText().toString();
                    //String horaLLegada=txtLLegada.getText().toString();
                    //String horaSalida=txtSalida.getText().toString();
                    //String comentario=txtComentario.getText().toString();
                    if (rutAlumno.isEmpty()){
                        Snackbar.make(view,"Debe ingresar un R.U.T valido",Snackbar.LENGTH_LONG).show();
                    }else {
                        if (rutAlumno.length() <= 10 && rutAlumno.length() >= 9&& nombreAlumno.length()>1) {
                            Registro regi=new Registro(rutAlumno,nombreAlumno,"","","");

                            databaseReference.child("registro").child(rutAlumno).setValue(regi);
                            Snackbar.make(view,
                                    "Alumno Registrado correctamente",Snackbar.LENGTH_LONG).show();
                            edtRut.setText("");
                            edtNombre.setText("");
                            edtCorreo.setText("");
                            edtCarrera.setText("");
                            btnGuardar.setEnabled(false);
                        } else {
                            Snackbar.make(view, "El R.U.T no es valido", Snackbar.LENGTH_LONG).show();
                        }
                    }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents() != null){
                recuperarAlumno(result.getContents());
            }else {
                Snackbar.make(findViewById(R.id.alumno_activity), "Cancelado", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    public void recuperarAlumno(String codigo){
        String rut = codigo.replace("-", "").replace(".", "");
        databaseReference.child("alumno").child(rut).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Alumno alumno = dataSnapshot.getValue(Alumno.class);
                    if (!alumno.getNombre().isEmpty()){
                        Snackbar.make(findViewById(R.id.alumno_activity), "Se encontro el R.U.T: "+alumno.getRut(), Snackbar.LENGTH_LONG).show();
                        edtRut.setText(alumno.getRut());
                        edtNombre.setText(alumno.getNombre());
                        edtCorreo.setText(alumno.getCorreo());
                        edtCarrera.setText(alumno.getCarrera());
                    }else{
                        Snackbar.make(findViewById(R.id.alumno_activity), "No se encontro", Snackbar.LENGTH_LONG).show();
                    }
                }catch (Exception ex){Snackbar.make(findViewById(R.id.alumno_activity),"No se encontro",Snackbar.LENGTH_LONG).show();}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        startActivity(new Intent(AlumnoActivity.this, PreEntradaActivity.class));
    }
}