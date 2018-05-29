package com.example.ronnycabrera.logingmail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    //crear un cliente api google
    //codigo de respuesta 9001
    private GoogleApiClient googleApiClient;
    private final int CODERC = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignInButton botongoogle = (SignInButton) findViewById(R.id.logeogmail);
        botongoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logeoGmail();
            }
        });
    }

    public void logeoGmail(){
        if(googleApiClient!=null){
            //desconectando
            googleApiClient.disconnect();
        }
        //solicitar correo para iniciar sesion
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        //igual el cliente con el logeo
        googleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();
        //abrir ventana de google
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signIntent,CODERC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODERC){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount acc = result.getSignInAccount();
                String token = acc.getIdToken();
                Log.e("correo: ",acc.getEmail());
                Log.e("nombre: ",acc.getDisplayName());
                Log.e("id: ",acc.getId());
                if(token!=null){
                    Toast.makeText(this,token,Toast.LENGTH_LONG).show();
                }
                Toast.makeText(this,"INGRESO CORRECTO",Toast.LENGTH_LONG).show();

            }
        }
    }
}
