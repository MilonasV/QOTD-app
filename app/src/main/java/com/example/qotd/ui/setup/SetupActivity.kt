package com.example.qotd.ui.setup

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.qotd.R

class SetupActivity : AppCompatActivity() {

    private lateinit var setupViewModel: SetupViewModel

    private var currentStep = 1
    private val totalSteps = 3

    // UI References
    private lateinit var progressBar: ProgressBar
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        setupViewModel = ViewModelProvider(this)[SetupViewModel::class.java]

        progressBar = findViewById(R.id.progress_bar)
        previousButton = findViewById(R.id.btn_previous)
        nextButton = findViewById(R.id.btn_next)
        progressBar.max = totalSteps
        progressBar.progress = currentStep


        nextButton.setOnClickListener { handleNextClick() }
        previousButton.setOnClickListener { handlePreviousClick() }

        if (currentStep == 1) {
            previousButton.visibility = View.INVISIBLE
        }

        if (currentStep == totalSteps) {
            nextButton.text = getString(R.string.btn_finish)
        } else {
            nextButton.text = getString(R.string.btn_next)
        }

        showStep1()
    }

    private fun showStep1() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Step1Fragment())
            .commit()
    }

    private fun showStep2() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Step2Fragment())
            .commit()
    }


    private fun handleNextClick() {
        if (currentStep < totalSteps) {

            currentStep++
            updateUI()
            showCurrentStep()
        }
    }

    private fun handlePreviousClick() {
        if (currentStep > 1) {
            currentStep--
            updateUI()
            showCurrentStep()
        }
    }


    private fun updateUI() {
        progressBar.progress = currentStep

        previousButton.visibility = if (currentStep == 1) View.INVISIBLE else View.VISIBLE

        nextButton.text = if (currentStep == totalSteps) {
            getString(R.string.btn_finish)
        } else {
            getString(R.string.btn_next)
        }
    }


    private fun showCurrentStep() {
        when (currentStep) {
            1 -> showStep1()
            2 -> showStep2()
            // 3 -> showStep3()
        }
    }


    fun getSetupViewModel(): SetupViewModel = setupViewModel
}