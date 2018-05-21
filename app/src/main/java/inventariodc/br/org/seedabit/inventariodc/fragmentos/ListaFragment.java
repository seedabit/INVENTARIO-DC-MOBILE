package inventariodc.br.org.seedabit.inventariodc.fragmentos;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import inventariodc.br.org.seedabit.inventariodc.R;
import inventariodc.br.org.seedabit.inventariodc.activity.FormActivity;
import inventariodc.br.org.seedabit.inventariodc.adapters.ProdutoAdapter;
import inventariodc.br.org.seedabit.inventariodc.beans.Produto;
import inventariodc.br.org.seedabit.inventariodc.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Button btnVoltar;
    private Button btnCadastrar;
    private List<Produto> mList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista, container, false);
        this.btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        this.btnCadastrar = (Button) view.findViewById(R.id.btnSubmeter);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.rvProdutos);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mList = FormularioFragment.getMList();
        ProdutoAdapter adapter = new ProdutoAdapter(getActivity(), mList, new ProdutoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Produto item) {
                Log.d("CLICK", "ADAPTER");
                sendDataToFormFragment(item);
//                Toast.makeText(getActivity().getApplication().getApplicationContext(),
//                        item.getBarcode(),
//                        Toast.LENGTH_LONG)
//                        .show();
            }
        });
        mRecyclerView.setAdapter(adapter);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FormularioFragment myfragment = new FormularioFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, myfragment);
                fragmentTransaction.commit();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<Produto>> call = Util.addProdutos(mList);
                call.enqueue(new Callback<List<Produto>>() {
                    @Override
                    public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                        Log.d("CallBack", "response is " + response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Produto>> call, Throwable t) {
                        Log.d("CallBack", "Throwable is " + t.getLocalizedMessage());
                    }
                });

                FormularioFragment.limparMList();
                mList.clear();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FormularioFragment myfragment = new FormularioFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, myfragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void sendDataToFormFragment(Produto p){
        FormularioFragment fragment = new FormularioFragment();
        Bundle args = new Bundle();
        args.putString(FormularioFragment.DATA_CHANGE, new Gson().toJson(p));
        fragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
        Log.d("SEND-FINISH", "ENVIADO");
    }

}
