package inventariodc.br.org.seedabit.inventariodc.fragmentos;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import inventariodc.br.org.seedabit.inventariodc.R;
import inventariodc.br.org.seedabit.inventariodc.activity.FormActivity;
import inventariodc.br.org.seedabit.inventariodc.beans.Produto;
import inventariodc.br.org.seedabit.inventariodc.interfaces.OnProductListener;


public class FormularioFragment extends Fragment  implements OnProductListener {

    private TextView txtBarCode;
    private EditText edtDescricao;
    private EditText edtLocalizacao;
    private EditText edtResponsavel;
    private EditText edtObservacao;
    private Button btnScanner;
    private Button btnLimpar;
    private Button btnCadastrar;
    private Button btnVerLista;
    private RadioGroup rgStatus;

    private static List<Produto> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_formulario, container, false);

        this.txtBarCode = (TextView) view.findViewById(R.id.tVCodigoBarra);
        this.edtDescricao = (EditText) view.findViewById(R.id.edtDescricao);
        this.edtLocalizacao = (EditText) view.findViewById(R.id.edtLocalizacao);
        this.edtResponsavel = (EditText) view.findViewById(R.id.edtResponsavel);
        this.edtObservacao = (EditText) view.findViewById(R.id.edtObs);
        this.btnScanner = (Button) view.findViewById(R.id.btnScanner);
        this.btnLimpar = (Button) view.findViewById(R.id.btnLimpar);
        this.btnCadastrar = (Button) view.findViewById(R.id.btnCadastrar);
        this.btnVerLista =  (Button) view.findViewById(R.id.btnVerLista);
        this.rgStatus = (RadioGroup) view.findViewById(R.id.rgStatus);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //click do botao para scanear o codigo de barras
        this.btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcodeCapture();
            }
        });

        //click do botao para limpar os campos do formulario
        this.btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparCampos();
            }
        });

        this.btnVerLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mList.isEmpty())
                    Toast.makeText(getActivity(), "Lista Vazia", Toast.LENGTH_LONG).show();
                else {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    ListaFragment myfragment = new ListaFragment();
                    fragmentTransaction.replace(R.id.fragmentContainer, myfragment);
                    fragmentTransaction.commit();
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(txtBarCode.getText().toString().trim().equals("") &&
                        edtDescricao.getText().toString().trim().equals("") &&
                        edtLocalizacao.getText().toString().trim().equals("") &&
                        edtResponsavel.getText().toString().trim().equals(""))) {

                    if(rgStatus.getCheckedRadioButtonId() == R.id.rbFuncionando ||
                            rgStatus.getCheckedRadioButtonId() == R.id.rbParado ||
                            rgStatus.getCheckedRadioButtonId() == R.id.rbQuebrado) {
                        String responsavel = edtResponsavel.getText().toString();
                        String localizacao = edtLocalizacao.getText().toString();
                        String tombamento = txtBarCode.getText().toString();
                        String descricao = edtDescricao.getText().toString();
                        String obs = edtDescricao.getText().toString();
                        short status = 0;

                        switch (rgStatus.getCheckedRadioButtonId()){
                            case R.id.rbFuncionando:
                                status = 1;
                                break;
                            case R.id.rbParado:
                                status = 2;
                                break;
                            case R.id.rbQuebrado:
                                status = 3;
                                break;
                            default:
                                break;
                        }
                        if(status != 0) {
//                            String tombParcial = "098123";
                            Produto produto = new Produto(responsavel, localizacao, tombamento, descricao, obs, status);
                            mList.add(produto);
                            limparCampos();
                            Toast.makeText(getActivity(), "Adicionado!", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getActivity(), "ERRO! \n TENTE NOVAMENTE!", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getActivity(), "Preencha os campos obrigatórios!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getActivity(), "Preencha os campos obrigatórios!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    public void limparCampos(){
        txtBarCode.setText("");
        edtDescricao.setText("");
        edtLocalizacao.setText("");
        edtResponsavel.setText("");
        edtObservacao.setText("");
        rgStatus.clearCheck();
    }

    @Override
    public void barcodeCapture() {
        Activity activity = getActivity();
        IntentIntegrator integrator = new IntentIntegrator(activity).forFragment(this);
        integrator.setOrientationLocked(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Escanear Número de Tombamento do Item");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            String barCode = result.getContents();
            if(barCode != null && !"".equals(barCode)){
                this.txtBarCode.setText(barCode);
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static List<Produto> getMList(){
        return mList;
    }

    public static void limparMList(){
        mList.clear();
    }



}