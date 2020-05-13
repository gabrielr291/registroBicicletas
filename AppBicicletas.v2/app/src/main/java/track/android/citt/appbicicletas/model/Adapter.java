package track.android.citt.appbicicletas.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.RegistrosViewHolder>{
    @NonNull
    @Override
    public RegistrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listado_registros,parent, false);
        RegistrosViewHolder holder=new RegistrosViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RegistrosViewHolder registrosViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class RegistrosViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombre;
        public RegistrosViewHolder(View itemView) {
            super(itemView);
            txtNombre=(TextView) itemView.findViewById(R.id.txtNombre);
        }
    }
}
