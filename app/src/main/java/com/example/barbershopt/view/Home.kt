package com.example.barbershopt.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.barbershopt.R
import com.example.barbershopt.adapter.ServicosAdapter
import com.example.barbershopt.databinding.ActivityHomeBinding
import com.example.barbershopt.model.Servicos

class Home : AppCompatActivity() {
    //Import do xlm
    private lateinit var binding :ActivityHomeBinding
    private lateinit var servicosAdapter: ServicosAdapter
    private val listaServicos: MutableList<Servicos> = mutableListOf()

    //Inflando o layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Escondendo a barra do actionbar
        supportActionBar?.hide()

        //Obtendo os valores digitados do EditText
        val nome = intent.extras?.getString("nome")
        val telefono = intent.extras?.getString("telefono")

        //Colocando o nome do cliente junto com o Bem vindo
        binding.txtUsername.text = "¡Bienvenido(a)!, $nome,"

        //Trazendo o recycleView com o id do layoute configurando como lista em duas colunas
        val recyclerViewServicos = binding.rvServicios
        recyclerViewServicos.layoutManager = GridLayoutManager(this,2)

        //Exibindo os itens
        servicosAdapter = ServicosAdapter(this, listaServicos)

        //Configurando o RecyclerView para ter um tamanho fixo.
        recyclerViewServicos.setHasFixedSize(true)

        //Definindo o adaptador servicosAdapter para o RecyclerView
        recyclerViewServicos.adapter = servicosAdapter

        //Chamando a função getServicos() para preencher a lista de serviços
        getServicos()

        //Dando açao para o bottom de agendamento e guardando os valores de nome e telefone na intent
        binding.btCita.setOnClickListener{
            val intent = Intent (this, Agendamento::class.java)
            intent.putExtra("nome", nome)
            intent.putExtra("telefono", telefono)
            startActivity(intent)
        }
    }
        //Preenchendo a lista de servisos com as imagens e o texto
    private fun getServicos(){

        val servico1 = Servicos(R.drawable.img1, "Corte de pelo")
        listaServicos.add(servico1)
        val servico2 = Servicos(R.drawable.img2, "Corte de barba")
        listaServicos.add(servico2)
        val servico3 = Servicos(R.drawable.img3, "Corte pelo niño")
        listaServicos.add(servico3)
        val servico4 = Servicos(R.drawable.img4, "Lavado de pelo")
        listaServicos.add(servico4)
    }
}