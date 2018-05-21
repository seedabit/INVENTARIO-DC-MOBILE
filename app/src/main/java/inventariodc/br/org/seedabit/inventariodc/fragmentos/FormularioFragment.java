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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import inventariodc.br.org.seedabit.inventariodc.R;
import inventariodc.br.org.seedabit.inventariodc.activity.FormActivity;
import inventariodc.br.org.seedabit.inventariodc.beans.Produto;
import inventariodc.br.org.seedabit.inventariodc.interfaces.OnProductListener;


public class FormularioFragment extends Fragment  implements OnProductListener {
    public final static String DATA_CHANGE = "DATA";
    private static Produto produtoForm;

    private static boolean isManterLocal = false;
    private static boolean isManterResp = false;

    private TextView txtBarCode;
    private EditText edtDescricao;
    private EditText edtLocalizacao;
    private EditText edtResponsavel;
    private EditText edtObservacao;
    private CheckBox chk_resp;
    private CheckBox chk_local;
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
        this.chk_local = (CheckBox) view.findViewById(R.id.chk_localizacao);
        this.chk_resp = (CheckBox) view.findViewById(R.id.chk_resp);

        this.chk_local.setChecked(isManterLocal);
        this.chk_resp.setChecked(isManterResp);

        setInitialValues();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        chk_resp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isManterResp = !isManterResp;
            }
        });

        chk_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isManterLocal = !isManterLocal;
            }
        });

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
                String tombamento = txtBarCode.getText().toString();
                if(!tombamento.equals("")){
                    if(!(edtResponsavel.getText().toString().trim().equals("") &&
                            edtLocalizacao.getText().toString().trim().equals("") &&
                            edtDescricao.getText().toString().trim().equals("") &&
                            edtObservacao.getText().toString().trim().equals(""))) {

                        if (rgStatus.getCheckedRadioButtonId() == R.id.rbFuncionando ||
                                rgStatus.getCheckedRadioButtonId() == R.id.rbParado ||
                                rgStatus.getCheckedRadioButtonId() == R.id.rbQuebrado) {

                            String responsavel = edtResponsavel.getText().toString();
                            String localizacao = edtLocalizacao.getText().toString();
                            String descricao = edtDescricao.getText().toString();
                            String obs = edtObservacao.getText().toString();
                            String status = "";

                            switch (rgStatus.getCheckedRadioButtonId()) {
                                case R.id.rbFuncionando:
                                    status = "Utilizado";
                                    break;
                                case R.id.rbParado:
                                    status = "Parado";
                                    break;
                                case R.id.rbQuebrado:
                                    status = "Quebrado";
                                    break;
                                default:
                                    status = "";
                                    break;
                            }
                            if (!status.equals("")) {
//                            String tombParcial = "098123";
                                Produto produto = new Produto(tombamento, descricao, responsavel, localizacao, status, obs);
                                addToList(produto);
                                limparCampos();
                                Toast.makeText(getActivity(), "Adicionado!", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(getActivity(), "ERRO! \n TENTE NOVAMENTE!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Preencha os campos obrigatórios!", Toast.LENGTH_LONG).show();
                        }
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
        if(!isManterLocal){
            edtLocalizacao.setText("");
        }
        if(!isManterResp){
            edtResponsavel.setText("");
        }
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

    private void addToList(Produto produto){
        if(mList.contains(produto)){
            mList.set(mList.indexOf(produto), produto);
        }else{
            mList.add(produto);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("onStart", "Metodo OnStart");
        Bundle args = getArguments();
        if(args != null){
            Log.d("onStart", "Tem dados");
            String produtoFormString = args.getString(FormularioFragment.DATA_CHANGE);
            produtoForm = new Gson().fromJson(produtoFormString, Produto.class);
            Log.d("objeto", produtoForm.toString());
            setInitialValues();
        }
    }

    private void setInitialValues(){
        if(produtoForm != null){
            Log.d("objeto-init", produtoForm.toString());
            this.txtBarCode.setText(produtoForm.getBarcode());
            this.edtDescricao.setText(produtoForm.getDescription());
            this.edtLocalizacao.setText(produtoForm.getLocation());
            this.edtResponsavel.setText(produtoForm.getResponsable());
            this.edtObservacao.setText(produtoForm.getObservation());
            int id = -1;
            switch (produtoForm.getStatus()) {
                case "Utilizado":
                    id = R.id.rbFuncionando;
                    break;
                case "Parado":
                    id = R.id.rbParado;
                    break;
                case "Quebrado":
                    id = R.id.rbQuebrado;
                    break;
                default:
                    break;
            }
            this.rgStatus.check(id);
            testeCarregamento();
        }
    }

    private void testeCarregamento(){
        while(this.txtBarCode.getText().toString().equals("") && produtoForm != null){
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            setInitialValues();
        }
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void limparMList(){
        mList.clear();
    }



}
