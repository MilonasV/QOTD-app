package com.example.qotd.ui.setup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.qotd.R
import com.example.qotd.ui.home.HomeActivity

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


        setupViewModel = ViewModelProvider(this)[SetupViewModel::class.java]


        if (setupViewModel.isSetupCompleted()){
            // If setup is already completed, redirect to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
        setContentView(R.layout.activity_setup)


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

    private fun showStep3() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Step3Fragment())
            .commit()
    }


    private fun handleNextClick() {

        if (currentStep == 1) {
            val step1Fragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as Step1Fragment
            if (!step1Fragment.validateName()) {
                return
            }
            //Else Save the name from Step 1 and proceed
            step1Fragment.saveName()
        }


        // If we are on step 3, take the user to the home screen and mark setup as completed
        if (currentStep == totalSteps) {
            setupViewModel.markSetupCompleted()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

            // Close the SetupActivity, we don't need it anymore
            finish()

        }
        else {
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
            3 -> showStep3()
        }
    }


    fun getSetupViewModel(): SetupViewModel = setupViewModel
}