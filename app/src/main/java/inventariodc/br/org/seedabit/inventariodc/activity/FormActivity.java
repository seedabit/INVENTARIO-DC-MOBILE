package inventariodc.br.org.seedabit.inventariodc.activity;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import inventariodc.br.org.seedabit.inventariodc.R;
import inventariodc.br.org.seedabit.inventariodc.interfaces.OnProductListener;

public class FormActivity extends AppCompatActivity implements OnProductListener  {

    private TextView txtBarCode;
    private Button btnScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        this.txtBarCode = (TextView) findViewById(R.id.tVCodigoBarra);
        this.btnScanner = (Button) findViewById(R.id.btnScanner);

        this.btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcodeCapture();
            }
        });

    }

    @Override
    public void barcodeCapture() {
        IntentIntegrator intent = new IntentIntegrator(FormActivity.this);
        intent.setBeepEnabled(true);
        intent.initiateScan();
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
    }
}
