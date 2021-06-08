package com.example.merchantapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.merchantapp.FoodListActivity
import com.example.merchantapp.LoginActivity
import com.example.merchantapp.R
import com.example.merchantapp.classes.Network
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogOutFragment : Fragment() {
    @Inject
    lateinit var api: Network

    lateinit var pref: SharedPreferences
    lateinit var edit: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref= activity?.getSharedPreferences("appSharedPrefs", Context.MODE_PRIVATE)!!
        edit =pref.edit()
        Log.d("pref", pref.getBoolean("loggedIn", false).toString())

        CoroutineScope(Dispatchers.Main).launch {
            pref.getString("phone","786")?.let { Log.d("phone", it) }

            Log.d("logout",
                pref.getString("phone","")?.let { api.logout() }?.body().toString())

        }

        edit.putBoolean("loggedIn",false)
        edit.putString("phone","")
        edit.apply()
        edit.commit()
        val intent =Intent(context, LoginActivity::class.java)

        context?.let { ContextCompat.startActivity(it, intent, null) }
        activity?.finish()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_out, container, false)
    }


}