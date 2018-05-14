package inventariodc.br.org.seedabit.inventariodc.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import inventariodc.br.org.seedabit.inventariodc.R;
import inventariodc.br.org.seedabit.inventariodc.beans.Produto;


public class ProdutoAdapter extends  RecyclerView.Adapter<ProdutoAdapter.MyViewHolder>{

    private List<Produto> mList;
    private LayoutInflater mLayoutInflater;

    public ProdutoAdapter(Context c, List<Produto> l){
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.list_layout, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.barCode.setText(mList.get(position).getTombamento());
        holder.descricao.setText(mList.get(position).getDescrição());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView barCode;
        public TextView descricao;

        public MyViewHolder(View itemView){
            super(itemView);

            barCode = (TextView) itemView.findViewById(R.id.txtCodigoDeBarra);
            descricao = (TextView) itemView.findViewById(R.id.txtDescricao);
        }
    }
}
