package inventariodc.br.org.seedabit.inventariodc.service;

import java.util.List;

import inventariodc.br.org.seedabit.inventariodc.beans.Produto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ProdutoService {

    @Headers("Content-Type: application/json")
    @POST("objects")
    Call<List<Produto>> saveProdutos(@Body List<Produto> produtos);

}
