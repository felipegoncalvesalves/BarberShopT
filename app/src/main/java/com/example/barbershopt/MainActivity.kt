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
            val nome = binding.editNome.text.toString()
            val senha = binding.editSenha.text.toString()
             when{
                nome.isEmpty() ->{
                     mensagem(it,"Por favor, añade un nombre válido!")
                 }senha.isEmpty() -> {
                     mensagem(it,"Por favor, ponga su numero de telefone!!")
                 }senha.length <=9 ->{
                    mensagem(it, "Minimo 9 numeros")
                 }else -> {
                     navegarParaHome(nome)
                 }
            }
        }

    }
    private fun mensagem(view: View, mensagem:String){
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#FF0000"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun navegarParaHome(nome: String){
        val intent = Intent(this, Home::class.java)
        intent.putExtra("nome",nome)
        startActivity(intent)
    }
}