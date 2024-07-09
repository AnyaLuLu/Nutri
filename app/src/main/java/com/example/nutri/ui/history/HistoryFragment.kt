package com.example.nutri.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.nutri.database.NutriDatabase
import com.example.nutri.databinding.FragmentHistoryBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Instantiate the database with the lifecycleScope
        val database = NutriDatabase.getDatabase(requireContext().applicationContext, lifecycleScope)

        // Create the ViewModel with the database instance
        val historyViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return HistoryViewModel(database) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }).get(HistoryViewModel::class.java)

        // Initialize the charts
        val charts = listOf(
            binding.barChartCalories to "Calories",
            binding.barChartFat to "Fat",
            binding.barChartSugar to "Sugar",
            binding.barChartSodium to "Sodium",
            binding.barChartProtein to "Protein"
        )
        val colors = listOf(
            android.graphics.Color.rgb(107, 143, 201),
            android.graphics.Color.rgb(123, 199, 150),
            android.graphics.Color.rgb(204, 160, 222),
            android.graphics.Color.rgb(230, 228, 154),
            android.graphics.Color.rgb(173, 103, 102)
        )

        charts.forEach { (chart, _) ->
            chart.description.isEnabled = false
            chart.setDrawValueAboveBar(true)

            val xAxis: XAxis = chart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.textColor = android.graphics.Color.WHITE

            val leftAxis: YAxis = chart.axisLeft
            val rightAxis: YAxis = chart.axisRight
            rightAxis.isEnabled = false
            leftAxis.textColor = android.graphics.Color.WHITE

            leftAxis.axisMinimum = 0f
            leftAxis.granularity = 1f
            leftAxis.setDrawLabels(true)
            leftAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
        }

        // Observe the dailyNutrients LiveData and update the charts
        historyViewModel.dailyCalories.observe(viewLifecycleOwner) { dailyNutrients ->
            val nutrientsMap = mapOf(
                binding.barChartCalories to dailyNutrients.mapIndexed { index, dailyNutrient -> BarEntry(index.toFloat(), dailyNutrient.total_calories.toFloat()) },
                binding.barChartFat to dailyNutrients.mapIndexed { index, dailyNutrient -> BarEntry(index.toFloat(), dailyNutrient.total_fat.toFloat()) },
                binding.barChartSugar to dailyNutrients.mapIndexed { index, dailyNutrient -> BarEntry(index.toFloat(), dailyNutrient.total_sugar.toFloat()) },
                binding.barChartSodium to dailyNutrients.mapIndexed { index, dailyNutrient -> BarEntry(index.toFloat(), dailyNutrient.total_sodium.toFloat()) },
                binding.barChartProtein to dailyNutrients.mapIndexed { index, dailyNutrient -> BarEntry(index.toFloat(), dailyNutrient.total_protein.toFloat()) }
            )

            nutrientsMap.forEach { (chart, entries) ->
                val label = charts.first { it.first == chart }.second
                val dataSet = BarDataSet(entries, label)
                dataSet.color = colors[charts.indexOfFirst { it.first == chart }]
                val data = BarData(dataSet)
                chart.data = data
                chart.invalidate() // refresh
            }
        }

        // Fetch data for June
        historyViewModel.fetchDailyCalories("2024-06")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}