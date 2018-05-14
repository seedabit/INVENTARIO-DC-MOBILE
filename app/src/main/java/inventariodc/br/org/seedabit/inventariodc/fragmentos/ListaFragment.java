package inventariodc.br.org.seedabit.inventariodc.fragmentos;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import inventariodc.br.org.seedabit.inventariodc.R;
import inventariodc.br.org.seedabit.inventariodc.adapters.ProdutoAdapter;
import inventariodc.br.org.seedabit.inventariodc.beans.Produto;


public class ListaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Button btnVoltar;
    private Button btnCadastrar;
    private List<Produto> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        ProdutoAdapter adapter = new ProdutoAdapter(getActivity(), mList);
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
                Toast.makeText(getActivity(), "Cadastrado!", Toast.LENGTH_LONG).show();
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

}