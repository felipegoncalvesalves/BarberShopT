package com.example.barbershopt.view

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import com.example.barbershopt.databinding.ActivityAgendamentoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class Agendamento : AppCompatActivity() {

    private lateinit var binding: ActivityAgendamentoBinding
    private val calendar: Calendar = Calendar.getInstance()
    private var data: String = ""
    private var hora: String = ""
    private var barbeiroSelecionado: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Ocultando a barra de açao
        supportActionBar?.hide()

        // Extrair informações passadas da atividade anterior
        val nome = intent.extras?.getString("nome").toString()
        val telefono = intent.extras?.getString("telefono").toString()

        // Configurar o DatePicker para selecionar a data
        val datePicker = binding.dataPicker
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_YEAR, dayOfMonth)

            var dia = dayOfMonth.toString()
            val mes: String

            if (dayOfMonth < 10) {
                dia = "0$dayOfMonth"
            }
            if (monthOfYear < 10) {
                mes = "" + (monthOfYear + 1)
            } else {
                mes = (monthOfYear + 1).toString()
            }
            // Formatando  a data
            data = "$dia / $mes / $year"
        }
        // Configurando o TimePicker para usar o formato de 24 horas
        binding.timePicker.setIs24HourView(true)
        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minuteOfHour ->
            var adjustedHour = hourOfDay
            var adjustedMinute = minuteOfHour

            // Arredonda os minutos para a hora mais próxima de meia em meia hora
            if (minuteOfHour < 30) {
                adjustedMinute = 0
            }else if (minuteOfHour > 30) {
                adjustedMinute =30
            }
            else {
                adjustedMinute = 30
                adjustedHour++
            }
            // Formate a hora e os minutos em uma única string
            val hourString = if (adjustedHour < 10) "0$adjustedHour" else adjustedHour.toString()
            val minuteString = if (adjustedMinute < 10) "0$adjustedMinute" else adjustedMinute.toString()

             hora = "$hourString:$minuteString"
            }

            // Configurando a seleção de barbeiros,(para nao poder selecionar mais de um)
        binding.barbeiro1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                barbeiroSelecionado = 1
                binding.barbeiro2.isChecked = false
                binding.barbeiro3.isChecked = false
            }
        }

        binding.barbeiro2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                barbeiroSelecionado = 2
                binding.barbeiro1.isChecked = false
                binding.barbeiro3.isChecked = false
            }
        }

        binding.barbeiro3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                barbeiroSelecionado = 3
                binding.barbeiro1.isChecked = false
                binding.barbeiro2.isChecked = false
            }
        }

        // Configurando o botão de agendamento
        binding.btCita.setOnClickListener {

            val barbeiro1 = binding.barbeiro1
            val barbeiro2 = binding.barbeiro2
            val barbeiro3 = binding.barbeiro3

            when{
                hora.isEmpty() -> {
                    mensagen(it,"Elija la hora!", "#FF0000")
                }
                hora < "8:00" && hora > "19:00" -> {
                    mensagen(it,"Barbearia cerrada - horario de funcionamiento es de 08:00 hasta 19:00!", "#FF0000")
                }
                data.isEmpty() -> {
                    mensagen(it,"Elija una fecha!", "#FF0000")
                }
                barbeiro1.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                salvarAgendameno(it, nome, "Felipe", "$telefono", data, hora)
                }
                barbeiro2.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendameno(it, nome, "Flavio", "$telefono", data, hora)
                }
                barbeiro3.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendameno(it, nome, "Marcos", "$telefono", data, hora )

                }
                else -> {
                    mensagen(it,"Elija un Barbero!", "#FF0000")
                }
            }

        }
    }
    // Função para exibir uma mensagem de Snackbar
    private fun  mensagen(view: View, mensagem: String, cor: String){
        val  snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(cor))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    // Função para salvar o agendamento no Firebase Firestore
    private fun salvarAgendameno(view: View, cliente:String, barbeiro:String, telefono: String, data: String, hora:String){

        val db = FirebaseFirestore.getInstance()

        val dadosUsuario = hashMapOf(
            "cliente" to cliente,
            "barbeiro" to barbeiro,
            "data" to data,
            "telefono" to telefono,
            "hora" to hora)

        db.collection("Agendamento").document(cliente).set(dadosUsuario).addOnCompleteListener {
            mensagen (view,"Cita confirmada!","#FF03DAC5")
        }.addOnFailureListener {
            mensagen(view,"Error en el  servidor!","#FF0000")
        }
    }
}