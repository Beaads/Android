package com.beatriz.androidlistafuncionariosbea.ui.recyclerview.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.beatriz.androidlistafuncionariosbea.ui.activity.ListaFuncionariosActivity;
import com.beatriz.androidlistafuncionariosbea.ui.recyclerview.adapter.ListaFuncionariosAdapter;

public class FuncionarioItemTouchHelperCallback extends ItemTouchHelper.Callback {

    public FuncionarioItemTouchHelperCallback(ListaFuncionariosAdapter adapter) {
        this.adapter = adapter;

    }

    private ListaFuncionariosAdapter adapter;

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacaoDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(0,marcacaoDeDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoDoFuncionarioDeslizado = viewHolder.getAdapterPosition();
        adapter.remove(posicaoDoFuncionarioDeslizado);
    }
}
