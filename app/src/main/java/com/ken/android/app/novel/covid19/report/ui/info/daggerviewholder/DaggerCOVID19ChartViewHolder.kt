package com.ken.android.app.novel.covid19.report.ui.info.daggerviewholder

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.WidgetCountryChartBinding
import com.ken.android.app.novel.covid19.report.di.recyclerview.viewholder.DaggerBaseViewHolder
import com.ken.android.app.novel.covid19.report.di.recyclerview.viewholder.DaggerViewHolderFactory
import com.ken.android.app.novel.covid19.report.ui.info.data.COVID19ChartData

class DaggerCOVID19ChartViewHolder (private var binding : WidgetCountryChartBinding) : DaggerBaseViewHolder(binding.root) {
    override fun setData(itemData: Any) {
        if(itemData is COVID19ChartData){

            val deathsBarDataSet = BarDataSet(itemData.getDeathsBarEntries(), binding.root.context.getString(
                R.string.chart_for_deaths))
            deathsBarDataSet.color = Color.RED
            val caseLineDataSet = LineDataSet(itemData.getCasesEntries(), binding.root.context.getString(
                R.string.chart_for_cases))
            caseLineDataSet.color = Color.BLUE
            caseLineDataSet.setCircleColor(Color.BLUE)

            val lineData = LineData(caseLineDataSet)

            val barData = BarData(deathsBarDataSet)
            barData.setValueTextColor(Color.RED)
            val combinedData = CombinedData()
            combinedData.setData(lineData)
            combinedData.setData(barData)


            val xAxis = binding.countryBarChart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = itemData
            xAxis.setDrawGridLines(false)
            xAxis.textSize = 10f
            xAxis.labelRotationAngle = -45f
            xAxis.labelCount = itemData.getDeathsBarEntries().size

            xAxis.axisMinimum = -barData.barWidth /2
            xAxis.axisMaximum = itemData.getDeathsBarEntries().size-barData.barWidth /2

            val axisRight = binding.countryBarChart.axisRight
            axisRight.setDrawAxisLine(false)
            axisRight.setDrawGridLines(false)
            axisRight.setDrawLabels(false)
            axisRight.isEnabled = false

            val axisLeft = binding.countryBarChart.axisLeft
            axisLeft.isEnabled = false


            binding.countryBarChart.data = combinedData


            binding.countryBarChart.description = null
            binding.countryBarChart.isHighlightPerTapEnabled = false

            binding.countryBarChart.isHighlightFullBarEnabled = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.countryBarChart.defaultFocusHighlightEnabled = false
            }

            binding.countryBarChart.setScaleEnabled(false)
            binding.countryBarChart.invalidate()
        }
    }

    open class Factory : DaggerViewHolderFactory(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaggerBaseViewHolder {
            val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val widgetCountryChartBinding = DataBindingUtil.inflate<WidgetCountryChartBinding>(inflater, R.layout.widget_country_chart, parent, false)
            return DaggerCOVID19ChartViewHolder(
                widgetCountryChartBinding
            )

        }

    }
}