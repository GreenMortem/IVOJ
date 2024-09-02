package com.example.vojdip.vojat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vojdip.R

class FragmentVoj : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voj, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentVoj()
    }
}