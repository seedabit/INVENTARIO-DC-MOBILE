package inventariodc.br.org.seedabit.inventariodc.activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Camera;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import inventariodc.br.org.seedabit.inventariodc.R;
import inventariodc.br.org.seedabit.inventariodc.fragmentos.FormularioFragment;
import inventariodc.br.org.seedabit.inventariodc.interfaces.OnProductListener;

public class FormActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        FragmentManager fragmentManager = getFragmentManager ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();

        FormularioFragment myfragment  = new FormularioFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, myfragment);
        fragmentTransaction.commit();

    }


}
