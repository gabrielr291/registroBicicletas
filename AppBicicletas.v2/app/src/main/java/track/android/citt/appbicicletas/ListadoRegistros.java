package track.android.citt.appbicicletas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class ListadoRegistros extends AppCompatActivity {
    RecyclerView rwRegistros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_registros);

        rwRegistros=(RecyclerView) findViewById(R.id.rwRegistros);
    }
}
