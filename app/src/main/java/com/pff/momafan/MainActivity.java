package com.pff.momafan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pff.momafan.model.pojo.Obra;

public class MainActivity extends AppCompatActivity implements ObrasFragment.NotificadorObra{


    private ObrasFragment obrasFragment;
    private ImageButton cerrarSesionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cerrarSesionButton = findViewById(R.id.cerrar_sesion_button_id);
        cerrarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        //si es que esta logueado con facebook, tengo que desloguearlo
                        LoginManager.getInstance().logOut();
                        Toast.makeText(MainActivity.this, "user de facebook deslogueado", Toast.LENGTH_SHORT).show();

                    }
                        //esto es para desloguearlo de firebase, ya se que entro con facebok o nativo
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(MainActivity.this, "user deslogueado de la app", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        });
        obrasFragment = new ObrasFragment();
        cargarFragment(obrasFragment);
    }

    private void cargarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void notificar(Obra obra) {
        DetalleObraFragment detalleObraFragment = new DetalleObraFragment();
        Bundle unBundle = new Bundle();
        unBundle.putSerializable(DetalleObraFragment.OBRA_CLAVE, obra);
        detalleObraFragment.setArguments(unBundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, detalleObraFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
