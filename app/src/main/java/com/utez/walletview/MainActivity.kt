package com.utez.walletview

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.utez.walletview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Uso de ViewBinding para evitar findViewById
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializamos las dos gráficas requeridas por la Fintech
        configurarGraficaPastel()
        configurarGraficaBarras()
    }

    /**
     * Configuración técnica de la Gráfica de Pastel (PieChart)
     * Muestra la distribución de gastos del usuario.
     */
    private fun configurarGraficaPastel() {
        val entradasGastos = ArrayList<PieEntry>().apply {
            add(PieEntry(6500f, "Renta"))
            add(PieEntry(4200f, "Comida"))
            add(PieEntry(1800f, "Servicios"))
            add(PieEntry(1200f, "Transporte"))
            add(PieEntry(1500f, "Ocio"))
        }

        val dataSet = PieDataSet(entradasGastos, "").apply {
            // Paleta de colores atractiva y legible
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

        val pieData = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter(binding.pieChartGastos))
        }

        binding.pieChartGastos.apply {
            data = pieData
            description.isEnabled = false
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            setUsePercentValues(true)
            setEntryLabelColor(Color.DKGRAY)
            setEntryLabelTextSize(11f)
            centerText = "Gastos\nNoviembre"
            setCenterTextSize(14f)
            // Animación suave de entrada
            animateY(1200, Easing.EaseInOutQuad)
            invalidate() // Refresca la gráfica
        }
    }

    /**
     * Configuración técnica de la Gráfica de Barras (BarChart)
     * Muestra el comparativo mensual de egresos.
     */
    private fun configurarGraficaBarras() {
        val entradasMensuales = ArrayList<BarEntry>().apply {
            add(BarEntry(0f, 14200f)) // Julio
            add(BarEntry(1f, 13800f)) // Agosto
            add(BarEntry(2f, 15500f)) // Septiembre
            add(BarEntry(3f, 12900f)) // Octubre
            add(BarEntry(4f, 15200f)) // Noviembre
        }

        val meses = arrayOf("Jul", "Ago", "Sep", "Oct", "Nov")

        val dataSet = BarDataSet(entradasMensuales, "Monto en $ MXN").apply {
            color = Color.parseColor("#3B82F6") // Azul moderno
            valueTextColor = Color.BLACK
            valueTextSize = 10f
        }

        val barData = BarData(dataSet).apply {
            barWidth = 0.6f
        }

        binding.barChartMensual.apply {
            data = barData
            description.isEnabled = false
            setFitBars(true)

            // Configuración del Eje X (Etiquetas de los meses)
            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(meses)
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                textColor = Color.DKGRAY
            }

            // Configuración del Eje Y
            axisLeft.axisMinimum = 0f
            axisRight.isEnabled = false // Desactivamos eje derecho redundante

            // Animación fluida de crecimiento de barras
            animateY(1000)
            invalidate()
        }
    }
}