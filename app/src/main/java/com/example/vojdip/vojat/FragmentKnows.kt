package com.example.vojdip.vojat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vojdip.R
import com.example.vojdip.databinding.FragmentVojBinding

class FragmentKnows : Fragment() {

    lateinit var bindingFragmentVoj: FragmentVojBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragmentVoj = FragmentVojBinding.inflate(inflater)
        return bindingFragmentVoj.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentKnows()


    }
}