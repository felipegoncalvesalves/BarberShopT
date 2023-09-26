package com.example.barbershopt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.barbershopt.databinding.ServicosItemBinding
import com.example.barbershopt.model.Servicos
// Classe do adaptador que gerencia a exibição dos itens de serviço em um RecyclerView

class ServicosAdapter (private val  context: Context,
    private val listaServicos: MutableList<Servicos>):
    RecyclerView.Adapter<ServicosAdapter.ServicosViewHolder>() {

    // Método chamado quando um novo ViewHolder é criado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicosViewHolder {
        // Inflando o layout do item de serviço a partir do XML usando o contexto
        val itemLista = ServicosItemBinding.inflate(LayoutInflater.from(context), parent, false)

        // Retorna um ViewHolder que contém a exibição do itemLista
        return ServicosViewHolder(itemLista)
    }
    // Método que retorna a quantidade de itens na lista
        override fun getItemCount() = listaServicos.size


    // Método chamado para associar dados a uma exibição de item de serviço
        override fun onBindViewHolder(holder: ServicosViewHolder, position: Int) {

        // Define a imagem e o texto do item com base nos dados da lista
            holder.imgServicos.setImageResource(listaServicos[position].img!!)
            holder.txtServicos.text = listaServicos[position].nome
        }

    // Classe interna que define um ViewHolder para os itens de serviço
        inner class ServicosViewHolder(binding: ServicosItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        // Referências às visualizações dentro do layout do item de serviço
            val imgServicos = binding.imgServico
            val txtServicos = binding.txtServico
        }
    }