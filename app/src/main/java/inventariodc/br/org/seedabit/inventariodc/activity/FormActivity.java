package inventariodc.br.org.seedabit.inventariodc.activity;

import android.content.Intent;
import android.graphics.Camera;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import inventariodc.br.org.seedabit.inventariodc.R;
import inventariodc.br.org.seedabit.inventariodc.interfaces.OnProductListener;

public class FormActivity extends AppCompatActivity implements OnProductListener  {

    private TextView txtBarCode;
    private EditText edtDescricao;
    private EditText edtLocalizacao;
    private EditText edtResponsavel;
    private EditText edtObservacao;
    private Button btnScanner;
    private Button btnLimpar;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        this.txtBarCode = (TextView) findViewById(R.id.tVCodigoBarra);
        this.edtDescricao = (EditText) findViewById(R.id.edtDescricao);
        this.edtLocalizacao = (EditText) findViewById(R.id.edtLocalizacao);
        this.edtResponsavel = (EditText) findViewById(R.id.edtResponsavel);
        this.edtObservacao = (EditText) findViewById(R.id.edtObs);
        this.btnScanner = (Button) findViewById(R.id.btnScanner);
        this.btnLimpar = (Button) findViewById(R.id.btnLimpar);
        this.btnCadastrar = (Button) findViewById(R.id.btnSubmeter);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
                txtBarCode.setText("");
                edtDescricao.setText("");
                edtLocalizacao.setText("");
                edtResponsavel.setText("");
                edtObservacao.setText("");
            }
        });

    }

    @Override
    public void barcodeCapture() {
        IntentIntegrator integrator = new IntentIntegrator(FormActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Escanear Número de Tombamento do Item");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
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
}
