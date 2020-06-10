package com.example.vprojetos.Activity.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vprojetos.R;
import com.example.vprojetos.model.Projeto;

import java.util.ArrayList;
import java.util.List;

public class ListaProjetosAdapter extends RecyclerView.Adapter<ListaProjetosAdapter.PorjetosViewHolder> implements Filterable {

    private  List<Projeto> projetos;
    private  List<Projeto> projetosFull;
    private  Context context;
    private OneProjetoListener mOneProjetoListener;

    public ListaProjetosAdapter(Context context, List<Projeto> projetos, OneProjetoListener oneProjetoListener){
        this.projetos = projetos;
        this.context = context;
        this.mOneProjetoListener = oneProjetoListener;
        projetosFull = new ArrayList<Projeto>(projetos);
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

    @Override
    public Filter getFilter() {
        return exempleFilter;
    }

    private Filter exempleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Projeto> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(projetosFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Projeto projeto: projetosFull) {
                    if(projeto.getNome().toLowerCase().contains(filterPattern)){
                        filteredList.add(projeto);
                    }

                }
            }

            FilterResults results= new FilterResults();
            results.values = filteredList;
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            projetos = (List<Projeto>) filterResults.values;
            //projetos.clear();
            //projetos.addAll((List)  filterResults.values);

            notifyDataSetChanged();
        }
    };

    class PorjetosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView categoria;
        private final TextView nota;
        private final TextView titulo;
        OneProjetoListener oneProjetoListener;
        private final TextView arrecadado;
        private final TextView doadores;

        public PorjetosViewHolder(@NonNull View itemView, OneProjetoListener oneProjetoListener) {
            super(itemView);
            categoria = itemView.findViewById(R.id.idTextViewItemProjetoCategoria);
            nota = itemView.findViewById(R.id.idTextViewItemProjetoNota);
            titulo = itemView.findViewById(R.id.idTextViewListaProjetoTitulo);
            arrecadado = itemView.findViewById(R.id.idTextViewItemProjetoArrecadado);
            doadores = itemView.findViewById(R.id.idTextViewItemProjetoDoadores);
            this.oneProjetoListener = oneProjetoListener;

            itemView.setOnClickListener(this);

        }

        public void vincula(Projeto projeto){
            preencheCampo(projeto);
        }

        private void preencheCampo(Projeto projeto){
            categoria.setText(projeto.getCategoria().toString());
            nota.setText(projeto.mediaNotas() + "");
            titulo.setText(projeto.getNome());

            double porcentagem = projeto.getDinheiroArrecadado() / projeto.getDinheiroAlvo();
            arrecadado.setText(porcentagem + "%");
            doadores.setText(projeto.getUsuariosDoacoes().size() + "");



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
