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
    //private lateinit var setupViewModel: ViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step1, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setupViewModel = ViewModelProvider(requireActivity())[SetupViewModel::class.java]


        nameEditText = view.findViewById(R.id.et_name)


    }


    fun getName(): String {
        return nameEditText.text.toString().trim()
    }

    fun isNameValid(): Boolean {
        val name = getName()
        return name.isNotEmpty() && name.length >= 3
    }

}