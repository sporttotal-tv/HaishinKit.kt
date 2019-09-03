package com.haishinkit.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class PreferenceFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_preference, container, false)
    }

    companion object {
        fun newInstance(): PreferenceFragment {
            return PreferenceFragment()
        }
    }
}

