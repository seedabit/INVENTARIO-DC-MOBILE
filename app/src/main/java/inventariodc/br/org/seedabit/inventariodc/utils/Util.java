package inventariodc.br.org.seedabit.inventariodc.utils;

import android.util.Log;

import java.util.List;

import inventariodc.br.org.seedabit.inventariodc.beans.Produto;
import inventariodc.br.org.seedabit.inventariodc.service.ProdutoService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Util {

    public static final String BASE_URL = "http://35.231.17.111/";

    public static Call<List<Produto>> addProdutos(List<Produto> produtos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProdutoService service = retrofit.create(ProdutoService.class);
        return service.saveProdutos(produtos);
    }

}
