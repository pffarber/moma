package com.pff.momafan;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pff.momafan.controller.ObraController;
import com.pff.momafan.model.pojo.Obra;
import com.pff.momafan.utils.ResultListener;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ObrasFragment extends Fragment implements AdapterObra.NotificadorObraCelda {
    private RecyclerView recyclerView;
    private NotificadorObra notificadorObra;
    private AdapterObra adapter;
    private ObraController obraController;


    public ObrasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_obras, container, false);
        recyclerView = view.findViewById(R.id.recycler_id);
        obraController = new ObraController();
        adapter = new AdapterObra(this);
        obtenerObras();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        notificadorObra = (NotificadorObra) context;
    }


    private void obtenerObras() {
        obraController.obtenerObras(new ResultListener<List<Obra>>() {
            @Override
            public void finish(List<Obra> obras) {
                adapter.agregarObras(obras);
            }
        });
    }

    @Override
    public void notificarObraClickeada(Obra obra) {
        notificadorObra.notificar(obra);
    }

    public interface NotificadorObra{
        public void notificar (Obra obra);
    }

}
