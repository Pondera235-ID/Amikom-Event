package com.belajar.amikomevent.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.belajar.amikomevent.R
import com.belajar.amikomevent.activity.LoginActivity.Companion.TAG_ID
import com.belajar.amikomevent.activity.LoginActivity.Companion.TAG_NIM
import com.belajar.amikomevent.adapter.EventAdapter
import com.belajar.amikomevent.model.Event
import com.belajar.amikomevent.network.ApiEndPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var arrayList = ArrayList<Event>()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        supportActionBar?.title = "Event"

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        sharedPreferences = getSharedPreferences(
            LoginActivity.my_shared_preferences, Context.MODE_PRIVATE
        )

        btnLogout.setOnClickListener{
            val editor = sharedPreferences.edit()
            editor.putBoolean(LoginActivity.session_status, false)
            editor.putString(TAG_ID,null)
            editor.putString(TAG_NIM,null)
            editor.apply()
            // Memanggil main activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        mFloatingActionButton.setOnClickListener {
            startActivity(Intent(this, ManageEventActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadAllEvent()
    }


    private fun loadAllEvent() {

        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    arrayList.clear()

                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        loading.dismiss()
                        Toast.makeText(
                            applicationContext,
                            "Event data is empty, Add the data first",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    for (i in 0 until jsonArray?.length()!!) {

                        val jsonObject = jsonArray.optJSONObject(i)
                        arrayList.add(
                            Event(
                                jsonObject.getString("id_event"),
                                jsonObject.getString("judul_event"),
                                jsonObject.getString("deskripsi_event"),
                                jsonObject.getString("lokasi_event"),
                                jsonObject.getString("tanggal_event"),
                                jsonObject.getString("waktu_event"),
                                jsonObject.getString("pembuat_event")
                            )
                        )

                        if (jsonArray.length() - 1 == i) {

                            loading.dismiss()
                            val adapter =
                                EventAdapter(
                                    applicationContext,
                                    arrayList
                                )
                            adapter.notifyDataSetChanged()
                            mRecyclerView.adapter = adapter

                        }

                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}
