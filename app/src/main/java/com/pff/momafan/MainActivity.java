package com.pff.momafan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;
import com.pff.momafan.model.pojo.Obra;

public class MainActivity extends AppCompatActivity implements ObrasFragment.NotificadorObra {

    private ObrasFragment obrasFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obrasFragment = new ObrasFragment();
        cargarFragment(obrasFragment);
    }

    private void cargarFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void notificar(Obra obra){

        DetalleObraFragment detalleObraFragment = new DetalleObraFragment();
        Bundle unBundle = new Bundle();
        unBundle.putSerializable(DetalleObraFragment.OBRA_CLAVE,obra);
        detalleObraFragment.setArguments(unBundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detalleObraFragment);
        fragmentTransaction.addToBackStack(detalleObraFragment.getClass().getName());
        fragmentTransaction.commit();

    }


}

