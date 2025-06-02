package com.example.qotd.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.qotd.R

class Step1Fragment : Fragment() {


    private lateinit var nameEditText: EditText
    private lateinit var setupViewModel: SetupViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel = (requireActivity() as SetupActivity).getSetupViewModel()

        return inflater.inflate(R.layout.fragment_step1, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText = view.findViewById(R.id.et_name)
        loadName()

    }


    fun saveName() {
        val name = getName()
        if (name.isEmpty()) {
            nameEditText.error = getString(R.string.error_name_required)
        } else {
            // Save the name to ViewModel or wherever needed
            setupViewModel.updateUserName(name)
        }


    }

    fun getName(): String {
        return nameEditText.text.toString().trim()
    }

    fun loadName() {
        // Load the name from ViewModel or wherever it is stored
        val name = setupViewModel.loadUserName()
        nameEditText.setText(name)
    }


    fun validateName(): Boolean {
        val name = getName()
        return if (name.isEmpty() || name.length < 3) {
            nameEditText.error = getString(R.string.error_name_required)
            false
        } else {
            true
        }
    }







}