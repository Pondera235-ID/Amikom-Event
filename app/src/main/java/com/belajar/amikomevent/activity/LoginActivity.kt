package com.belajar.amikomevent.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.belajar.amikomevent.R
import com.belajar.amikomevent.appcontroller.AppController
import com.belajar.amikomevent.network.ApiEndPoint.Companion.LOGIN
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class LoginActivity : AppCompatActivity() {

    var pDialog: ProgressDialog? = null
    lateinit var sharedPreferences: SharedPreferences
    var session = false
    var id : String? = null
    var nim : String? = null
    var success : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        sharedPreferences = getSharedPreferences(
            my_shared_preferences, Context.MODE_PRIVATE
        )
        val admin = findViewById(R.id.link_admin) as TextView
        admin.setOnClickListener {
            startActivity(Intent(this,
                LoginAdminActivity::class.java))
        }
        session = sharedPreferences.getBoolean(session_status, false)
        id = sharedPreferences.getString(TAG_ID,  null)
        nim = sharedPreferences.getString(TAG_NIM, null)

        btn_login.setOnClickListener {
            val tv_nim = tv_nim.text.toString()
            val tv_password = tv_password.text.toString()

            if (tv_nim.trim().length > 0 && tv_password.trim().length > 0) {
                if (conManager.activeNetworkInfo != null && conManager.activeNetworkInfo.isAvailable
                    && conManager.activeNetworkInfo.isConnected
                ) {
                    checkLogin(tv_nim, tv_password)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "No Internet Connection",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else { // Prompt user to enter credentials
                Toast.makeText(
                    applicationContext,
                    "Kolom tidak boleh kosong",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkLogin(nim: String, password: String) {
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)
        pDialog!!.setMessage("Logging in ...")
        showDialog()

        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            LOGIN,
            Response.Listener<String> { response ->
                Log.e(
                    TAG,
                    "Login Response: $response"
                )
                hideDialog()
                try {
                    val jObj = JSONObject(response)
                    success = jObj.getInt(TAG_SUCCESS)
                    // Check for error node in json
                    if (success == 1) {
                        val nim_data = jObj.getString(TAG_NIM)
                        val id_data = jObj.getString(TAG_ID)
                        Log.e("Successfully Login!", jObj.toString())
                        Toast.makeText(
                            applicationContext,
                            jObj.getString(TAG_MESSAGE),
                            Toast.LENGTH_LONG
                        ).show()
                        // menyimpan login ke session
                        val editor = sharedPreferences.edit()
                        editor.putBoolean(session_status, true)
                        editor.putString(TAG_ID,id_data)
                        editor.putString(TAG_NIM,nim_data)
                        editor.apply()
                        // Memanggil main activity
                        val intent = Intent(this@LoginActivity, UserActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(
                            applicationContext,
                            jObj.getString(TAG_MESSAGE),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) { // JSON error
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Log.e(
                    TAG,
                    "Login Error: " + error.message
                )
                Toast.makeText(
                    applicationContext,
                    error.message, Toast.LENGTH_LONG
                ).show()
                hideDialog()
            }) {
            override fun getParams(): Map<String, String> { // Posting parameters to login url
                val params: MutableMap<String, String> =
                    HashMap()
                params["nim"] = nim
                params["password"] = password
                return params
            }
        }
        // Adding request to request queue
        AppController.instance!!.addToRequestQueue(strReq,
            tag_json_obj
        )
    }

    private fun showDialog() {
        if (!pDialog!!.isShowing) pDialog!!.show()
    }

    private fun hideDialog() {
        if (pDialog!!.isShowing) pDialog!!.dismiss()
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
        private const val TAG_SUCCESS = "success"
        private const val TAG_MESSAGE = "message"
        const val TAG_NIM = "nim"
        const val TAG_ID = "id_user"
        const val my_shared_preferences = "my_shared_preferences"
        const val session_status = "session_status"
        var tag_json_obj = "json_obj_req"
    }
}
