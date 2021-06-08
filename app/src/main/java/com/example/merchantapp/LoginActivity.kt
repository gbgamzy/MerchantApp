@file:Suppress("DEPR ECATION")

package com.example.merchantapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.merchantapp.classes.Network
import com.example.merchantapp.classes.DNASnackBar
import com.example.merchantapp.classes.HomeDao
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*

import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var api: Network
    @Inject
    lateinit var db: HomeDao
    private var verificationId: String=""
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    var logging:Boolean=false
    var state:Int=1

    lateinit var pref: SharedPreferences
    lateinit var edit: SharedPreferences.Editor

    var s:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.merchantapp.R.layout.activity_login)
        buttonBack.visibility= View.VISIBLE
        editTextPhone.visibility= View.VISIBLE
        imageButtonLogin.visibility= View.GONE
        editTextOtp.visibility= View.GONE

        pref=this.getSharedPreferences("appSharedPrefs", Context.MODE_PRIVATE)
        edit=pref.edit()
        if(pref.getBoolean("loggedIn",false) ){
            Log.d("logging in ","logging in")
            val intent = Intent(this@LoginActivity, MainActActivity::class.java)
            startActivity(intent, null)
            finish()
        }
        imageButtonLogin.setOnClickListener {

            try{
                verifyCode(editTextOtp.text.toString())
            }
            catch(err:Exception){


                DNASnackBar.show(this,"Some Error Occurred!")
            }

        }
        buttonBack.setOnClickListener{
            s=editTextPhone.text.toString()
            if(s.length!=10){
                DNASnackBar.show(this,"Please Enter valid Number!")
            }
            else{

                val options = PhoneAuthOptions.newBuilder(mAuth)
                    .setPhoneNumber("+91$s") // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this) // Activity (for callback binding)
                    .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            buttonBack.visibility= View.GONE
            editTextPhone.visibility= View.GONE
            imageButtonLogin.visibility= View.VISIBLE
            editTextOtp.visibility= View.VISIBLE}

        }









    }
    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        progressBar2.visibility=View.VISIBLE
        signInWithCredential(credential)




    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult?> {
                    override fun onComplete(task: Task<AuthResult?>) {
                        if (task.isSuccessful()) {
                            var refreshedToken=""

                            try{
                                FirebaseMessaging.getInstance().token.addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        refreshedToken = it.result.toString()
                                        Log.d("REGTOKEN", "SUCCESS" + refreshedToken)
                                        CoroutineScope(Dispatchers.Main).launch {
                                            val p = api.login(
                                                editTextPhone.text.toString(),
                                                refreshedToken
                                            )

                                            Log.d("p", p.body().toString())
                                            if(p.body()?.message=="SUCCESS") {

                                                edit.putBoolean("loggedIn", true)
                                                edit.putString("reg", refreshedToken)
                                                edit.putString(
                                                    "phone",
                                                    editTextPhone.text.toString()
                                                )
                                                edit.apply()
                                                edit.commit()
                                                progressBar2.visibility = View.GONE
                                                val intent =
                                                    Intent(
                                                        this@LoginActivity,
                                                        MainActActivity::class.java
                                                    )

                                                if (state == 1) {
                                                    state = 0
                                                    startActivity(intent, null)
                                                    finish()
                                                    state = 1
                                                }

                                            }
                                            else{
                                                DNASnackBar.show(applicationContext,"You are not an admin")
                                                progressBar2.visibility=View.GONE

                                            }



                                        }

                                    }
                                    if (it.isCanceled) {
                                        Log.d("REGTOKENCANCELLED", it.result.toString())
                                    }
                                }

                            }
                            catch(err:java.lang.Exception){
                                Log.d("REGTOKENERROR", err.toString())
                                progressBar2.visibility=View.GONE
                                DNASnackBar.show(this@LoginActivity,"There seems to be some problem from our end")

                                logging=false

                            }
                        }


                    }
                })
    }
    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(s, forceResendingToken)
            verificationId = s
        }

        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            val code = phoneAuthCredential.smsCode
            if (code != null) {
                editTextOtp.setText(code)


            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            e.message?.let { DNASnackBar.show(this@LoginActivity, it) }
        }
    }




}