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
import java.util.Calendar

class Agendamento : AppCompatActivity() {

    private lateinit var binding: ActivityAgendamentoBinding
    private val calendar: Calendar = Calendar.getInstance()
    private var data: String = ""
    private var hora: String = ""
    private var barbeiroSelecionado: Int =
        0 // 0: Nenhum, 1: Barbeiro 1, 2: Barbeiro 2, 3: Barbeiro 3


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()
        val nome = intent.extras?.getString("nome").toString()

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
            data = "$dia / $mes / $year"
        }
        // Configurar o TimePicker para usar o formato de 24 horas
        binding.timePicker.setIs24HourView(true)
        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val minuto: String
            if (minute < 10){
                minuto = "0$minute"
            }else {
                minuto = minute.toString()
            }
            hora = "$hourOfDay:$minuto"
            }


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
                    mensagen(it,"Cita confirmada! ", "#FF03DAC5")
                }
                barbeiro2.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    mensagen(it,"Cita confirmada! ", "#FF03DAC5")
                }
                barbeiro3.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    mensagen(it,"Cita confirmada! ", "#FF03DAC5")
                }
                barbeiro1.isChecked && barbeiro2.isChecked && barbeiro3.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    mensagen(it,"Solo puedes elegir un Barbero! ", "#FF0000")
                }
                else -> {
                    mensagen(it,"Elija un Barbero!", "#FF0000")
                }
            }

        }
    }
    private fun  mensagen(view: View, mensagem: String, cor: String){
        val  snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(cor))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun salvarAgendameno(view: View, cliente:String, barbeiro:String, data: String, hora:String){


    }
}