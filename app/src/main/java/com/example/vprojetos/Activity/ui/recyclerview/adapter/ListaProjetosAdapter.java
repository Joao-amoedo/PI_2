package com.example.vprojetos.Activity.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vprojetos.R;
import com.example.vprojetos.model.Projeto;

import java.util.List;

public class ListaProjetosAdapter extends RecyclerView.Adapter<ListaProjetosAdapter.PorjetosViewHolder>{

    private final List<Projeto> projetos;
    private final Context context;
    private OneProjetoListener mOneProjetoListener;

    public ListaProjetosAdapter(Context context, List<Projeto> projetos, OneProjetoListener oneProjetoListener){
        this.projetos = projetos;
        this.context = context;
        this.mOneProjetoListener = oneProjetoListener;
    }

    @NonNull
    @Override
    public PorjetosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_projeto, parent, false);


        return new PorjetosViewHolder(viewCriada, mOneProjetoListener);

    }

    public void mensagem(Object s){
        Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBindViewHolder(@NonNull PorjetosViewHolder holder, int position) {
        Projeto projeto = projetos.get(position);
        holder.vincula(projeto);
    }

    @Override
    public int getItemCount() {
        return projetos.size();

    }

    public void adiciona(Projeto projeto){
        projetos.add(projeto);
        notifyDataSetChanged();
    }

    class PorjetosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView categoria;
        private final TextView descricao;
        private final TextView nota;
        private final TextView titulo;
        OneProjetoListener oneProjetoListener;

        public PorjetosViewHolder(@NonNull View itemView, OneProjetoListener oneProjetoListener) {
            super(itemView);
            categoria = itemView.findViewById(R.id.idTextViewListaProjetoCategoria);
            descricao = itemView.findViewById(R.id.idTextViewListaProjetoDescricao);
            nota = itemView.findViewById(R.id.idTextViewListaProjetoNota);
            titulo = itemView.findViewById(R.id.idTextViewListaProjetoTitulo);
            this.oneProjetoListener = oneProjetoListener;

            itemView.setOnClickListener(this);

        }

        public void vincula(Projeto projeto){
            preencheCampo(projeto);
        }

        private void preencheCampo(Projeto projeto){
            categoria.setText(projeto.getCategoria().toString());
            descricao.setText(projeto.getDescricaoDoProjeto());
            nota.setText(projeto.mediaNotas() + "");
            titulo.setText(projeto.getNome());

        }

        @Override
        public void onClick(View view) {
            oneProjetoListener.onProjetoClick(getAdapterPosition());
        }
    }

    public interface OneProjetoListener{
        void onProjetoClick(int position);
    }

}
