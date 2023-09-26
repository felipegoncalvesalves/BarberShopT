package com.example.barbershopt

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.barbershopt.databinding.ActivityMainBinding
import com.example.barbershopt.view.Home
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btLogin.setOnClickListener {

            // Obter os valores inseridos nos campos de nome e telefone
            val nome = binding.editNome.text.toString()
            val telefono = binding.editSenha.text.toString()

            // Realizar verificações nos dados inseridos
             when{
                nome.isEmpty() ->{
                     mensagem(it,"Por favor, añade un nombre válido!")
                 }telefono.isEmpty() -> {
                     mensagem(it,"Por favor, ponga su numero de telefono!!")
                 }telefono.length <=8 ->{
                    mensagem(it, "Minimo 9 numeros")
                 }else -> {

                 // Navegar para a tela Home enviando nome e telefone como extras
                     navegarParaHome(nome,telefono)
                 }
            }
        }

    }
    // Função para exibir uma mensagem de Snackbar com fundo vermelho
    private fun mensagem(view: View, mensagem:String){
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#FF0000"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }
    // Função para navegar para a tela Home e enviar nome e telefone como extras
    private fun navegarParaHome(nome: String, telefono: String){
        val intent = Intent(this, Home::class.java)
        intent.putExtra("nome",nome)
        intent.putExtra("telefono", telefono)
        startActivity(intent)
    }
}