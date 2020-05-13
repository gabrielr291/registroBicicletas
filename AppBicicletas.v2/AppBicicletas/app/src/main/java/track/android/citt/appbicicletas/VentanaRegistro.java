/*package track.android.citt.appbicicletas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import track.android.vistabicicletas.Clases.Alumno;
import track.android.vistabicicletas.Clases.Registro;

public class VentanaRegistro extends AppCompatActivity {

    Button btnescaner; //crear variables
    EditText txtRut;
    EditText txtNombre;
    EditText txtLLegada;
    EditText txtSalida;
    EditText txtComentario;

    SimpleDateFormat dateFormat=new SimpleDateFormat("'fecha 'dd-MM 'hrs 'HH:mm");
    String fechaAhora=dateFormat.format(new Date());
    Button btnGuardar;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference; //instanciar firebase y referenciarla
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_registro);

        iniciarFirebase();//debemos crear la funcion de conexion a la base de datos ya creada

        btnGuardar=(Button)findViewById(R.id.btnGuardar);
        btnescaner= (Button) findViewById(R.id.btnEscaner);
        txtRut=(EditText) findViewById(R.id.txtRut);
        txtNombre=(EditText) findViewById(R.id.txtNombre);
        txtLLegada=(EditText) findViewById(R.id.txtLLegada);
        txtSalida=(EditText)findViewById(R.id.txtSalida);
        txtComentario=(EditText) findViewById(R.id.txtComentario);//se buscan los botones en la ventana registro.xml

        btnescaner.setOnClickListener(onClickListener);//primero debemos crear la funcion onClickListener para luego utilizarla
        btnGuardar.setOnClickListener(guardar);
    }
    public void iniciarFirebase(){//metodo para conectarse a firebase
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }

    public void getDatos(final String codigo){
        String rut=codigo.replace("-","").replace(".","");
        databaseReference.child("alumno").child(rut).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Alumno alumno= dataSnapshot.getValue(Alumno.class);
                Toast.makeText(VentanaRegistro.this,alumno.getNombre(),Toast.LENGTH_SHORT).show();
                txtRut.setText(alumno.getRut());
                txtNombre.setText(alumno.getNombre());
                txtLLegada.setText(fechaAhora);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private View.OnClickListener onClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnEscaner:
                    new IntentIntegrator(VentanaRegistro.this).initiateScan();
                    break;
            }
        }
    };
    private View.OnClickListener guardar= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String rutAlumno=txtRut.getText().toString();
            String nombreAlumno=txtNombre.getText().toString();
            String horaLLegada=txtLLegada.getText().toString();
            String horaSalida=txtSalida.getText().toString();
            String comentario=txtComentario.getText().toString();

            Registro regi=new Registro(rutAlumno,nombreAlumno,horaLLegada,horaSalida,comentario);

            databaseReference.child("registro Bicicletas").child(rutAlumno+horaLLegada).setValue(regi);
            Toast.makeText(getApplicationContext(),
                    "Alumno Registrado correctamente",Toast.LENGTH_SHORT).show();

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents() != null){
                getDatos(result.getContents());
            }else {
                Toast.makeText(this,"cancelado",Toast.LENGTH_LONG).show();
            }
        }
    }
}*/
