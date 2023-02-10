package com.staynight.calculator

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.staynight.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val adapter = CalculationHistoryAdapter {
        (binding?.rvHistory?.layoutManager as? LinearLayoutManager)?.scrollToPosition(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initRecyclerView()
        setupListeners()
    }

    private fun setupListeners() {
        onClearButtonClick()
        onAddButtonsClick()
        onRemoveButtonClick()
        onRemoveNumberButtonClick()
        onDivideButtonClick()
        onMultiplyButtonClick()
        onAddButtonClick()
        onEqualsButtonClick()
    }

    private fun initRecyclerView() {
        (binding?.rvHistory?.layoutManager as? LinearLayoutManager)?.reverseLayout = true
        binding?.rvHistory?.adapter = adapter
    }

    private fun onAddButtonsClick() {
        binding?.btnNumber1?.setOnClickListener {
            onAddButtonClick((it as Button).text.toString())
        }
        binding?.btnNumber2?.setOnClickListener {
            onAddButtonClick((it as Button).text.toString())
        }
        binding?.btnNumber3?.setOnClickListener {
            onAddButtonClick((it as Button).text.toString())
        }
        binding?.btnNumber4?.setOnClickListener {
            onAddButtonClick((it as Button).text.toString())
        }
        binding?.btnNumber5?.setOnClickListener {
            onAddButtonClick((it as Button).text.toString())
        }
        binding?.btnNumber6?.setOnClickListener {
            onAddButtonClick((it as Button).text.toString())
        }
        binding?.btnNumber7?.setOnClickListener {
            onAddButtonClick((it as Button).text.toString())
        }
        binding?.btnNumber8?.setOnClickListener {
            onAddButtonClick((it as Button).text.toString())
        }
        binding?.btnNumber9?.setOnClickListener {
            onAddButtonClick((it as Button).text.toString())
        }
        binding?.btnNumber0?.setOnClickListener {
            onAddButtonClick((it as Button).text.toString())
        }
        binding?.btnComma?.setOnClickListener {
            onAddButtonClick((it as Button).text.toString())
        }
    }

    private fun onEqualsButtonClick() {
        binding?.btnEquals?.setOnClickListener {
            var currentText = binding?.tvCalculation?.text
            if (!currentText.isNullOrEmpty()) {
                if (!(currentText.last() == '+' || currentText.last() == '-' || currentText.last() == '/' || currentText.last() == '*')) {
                    val numbersAndActions = currentText.split(" ")
                    onActionButtonClick("=")
                    var calculationResult: Double? = numbersAndActions[0].toDouble()
                    numbersAndActions.forEachIndexed { index, numberOrAction ->
                        when (numberOrAction) {
                            "+" -> {
                                calculationResult =
                                    calculationResult?.plus(numbersAndActions[index + 1].toDouble())
                            }
                            "-" -> {
                                calculationResult =
                                    calculationResult?.minus(numbersAndActions[index + 1].toDouble())
                            }
                            "*" -> {
                                calculationResult =
                                    calculationResult?.times(numbersAndActions[index + 1].toDouble())
                            }
                            "/" -> {
                                if (numbersAndActions[index + 1] != "0")
                                    calculationResult =
                                        calculationResult?.div(numbersAndActions[index + 1].toDouble())
                                else {
                                    calculationResult = null
                                    return@forEachIndexed
                                }
                            }
                            else -> {

                            }
                        }
                    }
                    currentText = binding?.tvCalculation?.text
                    binding?.tvCalculation?.text = ""
                    if (calculationResult == null)
                        addCalculationResultToRecyclerView("$currentText NaN")
                    else
                        addCalculationResultToRecyclerView("$currentText $calculationResult")
                }
            }
        }
    }

    private fun addCalculationResultToRecyclerView(result: String) {
        adapter.addCalculationToHistory(result)
    }

    private fun onAddButtonClick() {
        binding?.btnAdd?.setOnClickListener {
            onActionButtonClick("+")
        }
    }

    private fun onRemoveButtonClick() {
        binding?.btnRemove?.setOnClickListener {
            onActionButtonClick("-")
        }
    }

    private fun onDivideButtonClick() {
        binding?.btnDivision?.setOnClickListener {
            onActionButtonClick("/")
        }
    }

    private fun onMultiplyButtonClick() {
        binding?.btnMultiplication?.setOnClickListener {
            onActionButtonClick("*")
        }
    }

    private fun onActionButtonClick(action: String) {
        var currentText = binding?.tvCalculation?.text
        if (!currentText.isNullOrEmpty()) {
            when (currentText.trim().last()) {
                '+', '-', '*', '/' -> {
                    removeLastChar()
                    currentText = binding?.tvCalculation?.text
                    binding?.tvCalculation?.text = "$currentText $action"
                }
                '=' -> {}
                else -> {
                    binding?.tvCalculation?.text = "$currentText $action"
                }
            }
        }
    }

    private fun onRemoveNumberButtonClick() {
        binding?.btnRemoveNumber?.setOnClickListener {
            removeLastChar()
        }
    }

    private fun removeLastChar() {
        val currentText = binding?.tvCalculation?.text
        if (currentText != null && currentText.isNotEmpty()) {
            if ((currentText.getOrNull(currentText.lastIndex - 1) == ' ')) {
                binding?.tvCalculation?.text =
                    currentText.slice(0 until currentText.lastIndex - 1)
            } else
                binding?.tvCalculation?.text =
                    currentText.slice(0 until currentText.lastIndex)
        }
    }

    private fun onClearButtonClick() {
        binding?.btnClear?.setOnClickListener {
            binding?.tvCalculation?.text = ""
        }
    }

    private fun onAddButtonClick(symbol: String) {
        val currentText = binding?.tvCalculation?.text ?: ""
        if (currentText.isNotEmpty()) when (currentText.last()) {
            '+', '-', '*', '/' -> {
                if (symbol != ",") binding?.tvCalculation?.text = "$currentText $symbol"
            }
            ',' -> {
                if (symbol != ",") binding?.tvCalculation?.text = "$currentText$symbol"
            }
            else -> {
                if (symbol == "," && currentText.split(" ").lastOrNull()
                        ?.contains(",") != true
                ) binding?.tvCalculation?.text = "$currentText$symbol"
                else if (symbol != ",") binding?.tvCalculation?.text = "$currentText$symbol"
            }
        }
        else if (symbol != ",") binding?.tvCalculation?.text = "$currentText$symbol"

    }
}