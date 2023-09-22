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
    private lateinit var binding :ActivityHomeBinding
    private lateinit var servicosAdapter: ServicosAdapter
    private val listaServicos: MutableList<Servicos> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val nome = intent.extras?.getString("nome")

        binding.txtUsername.text = "¡Bienvenido(a)!, $nome"
        val recyclerViewServicos = binding.rvServicios
        recyclerViewServicos.layoutManager = GridLayoutManager(this,2)
        servicosAdapter = ServicosAdapter(this, listaServicos)
        recyclerViewServicos.setHasFixedSize(true)
        recyclerViewServicos.adapter = servicosAdapter
        getServicos()

        binding.btCita.setOnClickListener{
            val intent = Intent (this, Agendamento::class.java)
            intent.putExtra("nome", nome)
            startActivity(intent)
        }
    }

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