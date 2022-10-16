package com.example.transakcje.ui.outcome_fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.transakcje.MainViewModel
import com.example.transakcje.R
import com.example.transakcje.databinding.OutcomeFragmentBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class OutcomeTransactionFragment : Fragment() {

    private val viewModel by viewModels<OutcomeTransactionViewModel>()
    private val mainVm by  activityViewModels<MainViewModel>()
    private var _binding: OutcomeFragmentBinding? = null
    private val binding get () = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OutcomeFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.outcomePieChart.apply {
            isDrawHoleEnabled = true
            setUsePercentValues(true)
            setEntryLabelTextSize(18f)
            setEntryLabelColor(Color.WHITE)
            centerText = "Outcome"
            setCenterTextSize(24f)
            description.isEnabled = false
            setTransparentCircleAlpha(50)

            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    binding.outcomePieChart.centerText = e?.y.toString() + "PLN"
                    binding.outcomePieChart.invalidate()
                }

                override fun onNothingSelected() {
                    binding.outcomePieChart.centerText = "Outcome"
                    binding.outcomePieChart.invalidate()
                }

            })
        }

        mainVm.getSumOfOutcomeByCategory().observe(viewLifecycleOwner){ transactions ->

            val entries = ArrayList<PieEntry>()
            for (trans in transactions){
                val pieEntry = PieEntry(trans.total, trans.category.name.lowercase())
                entries.add(pieEntry)
            }

            val pieDataSet = PieDataSet(entries, "")
            val colors = listOf(
                Color.parseColor("#9038ff"),
                Color.parseColor("#45197d"),
                Color.parseColor("#E536AB"),
                Color.parseColor("#5C03BC")
            )

            pieDataSet.colors = colors

            val pieData = PieData(pieDataSet)
            pieData.setDrawValues(true)
            pieData.setValueFormatter(PercentFormatter(binding.outcomePieChart))
            pieData.setValueTextSize(12f)
            pieData.setValueTextColor(Color.WHITE)


            binding.outcomePieChart.legend.isEnabled = false
            binding.outcomePieChart.data = pieData
            binding.outcomePieChart.animateY(500, Easing.EaseInOutQuad )
            binding.outcomePieChart.invalidate()

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
