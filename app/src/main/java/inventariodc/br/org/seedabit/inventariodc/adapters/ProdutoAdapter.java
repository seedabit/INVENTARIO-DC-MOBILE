package inventariodc.br.org.seedabit.inventariodc.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.util.List;

import inventariodc.br.org.seedabit.inventariodc.R;
import inventariodc.br.org.seedabit.inventariodc.beans.Produto;


public class ProdutoAdapter extends  RecyclerView.Adapter<ProdutoAdapter.MyViewHolder>{

    private List<Produto> mList;
    private LayoutInflater mLayoutInflater;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Produto item);
    }

    public ProdutoAdapter(Context c, List<Produto> l, OnItemClickListener listener){
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listener = listener;
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
        holder.bind(mList.get(position), listener);
    }

    private Produto getProdctByPosition(int pos){
        return this.mList.get(pos);
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

        public void bind(final Produto item, final OnItemClickListener listener){
            barCode.setText(item.getBarcode());
            descricao.setText(item.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }

    }
}
