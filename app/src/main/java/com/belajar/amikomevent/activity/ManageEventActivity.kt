package com.belajar.amikomevent.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.belajar.amikomevent.R
import com.belajar.amikomevent.network.ApiEndPoint
import kotlinx.android.synthetic.main.activity_manage_event.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ManageEventActivity : AppCompatActivity() {

    lateinit var i: Intent

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_event)

        supportActionBar?.title = "Manage Event"

        i = intent
        if (i.hasExtra("editmode")) {
            if (i.getStringExtra("editmode").equals("1")) {
                onEditMode()
            }
        }
        btnCreate.setOnClickListener {
            create()
        }
        btnUpdate.setOnClickListener {
            update()
        }

        val tvDate: TextView = findViewById(R.id.tanggal_event)
        var calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            val myFormat = "dd MMM yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            tvDate.text = sdf.format(calendar.time)
        }

        val tvTime: TextView = findViewById(R.id.waktu_event)
        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            tvTime.text = SimpleDateFormat("HH:mm").format(cal.time)
        }

        tvTime.setOnClickListener {
            TimePickerDialog(this, timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MONTH), true

            ).show()
        }

        tvDate.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Hapus data ini ?")
                .setPositiveButton("HAPUS", { dialogInterface, i ->
                    delete()
                })
                .setNegativeButton("BATAL", { dialogInterface, i ->
                    dialogInterface.dismiss()
                }).show()
        }
    }

    private fun onEditMode() {

        id_event.setText(i.getStringExtra("id_event"))
        judul_event.setText(i.getStringExtra("judul_event"))
        deskripsi_event.setText(i.getStringExtra("deskripsi_event"))
        lokasi_event.setText(i.getStringExtra("lokasi_event"))
        tanggal_event.setText(i.getStringExtra("tanggal_event"))
        waktu_event.setText(i.getStringExtra("waktu_event"))
        pembuat_event.setText(i.getStringExtra("pembuat_event"))

        btnCreate.visibility = View.GONE
        changeData.visibility = View.VISIBLE

    }

    private fun create() {

        val loading = ProgressDialog(this)
        loading.setMessage("Menambahkan data...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.CREATE)
            .addBodyParameter("id_event", id_event.text.toString())
            .addBodyParameter("judul_event", judul_event.text.toString())
            .addBodyParameter("deskripsi_event", deskripsi_event.text.toString())
            .addBodyParameter("lokasi_event", lokasi_event.text.toString())
            .addBodyParameter("tanggal_event", tanggal_event.text.toString())
            .addBodyParameter("waktu_event", waktu_event.text.toString())
            .addBodyParameter("pembuat_event", pembuat_event.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(
                        applicationContext,
                        response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@ManageEventActivity.finish()
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

    private fun update() {

        val loading = ProgressDialog(this)
        loading.setMessage("Mengubah data...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.UPDATE)
            .addBodyParameter("id_event", id_event.text.toString())
            .addBodyParameter("judul_event", judul_event.text.toString())
            .addBodyParameter("deskripsi_event", deskripsi_event.text.toString())
            .addBodyParameter("lokasi_event", lokasi_event.text.toString())
            .addBodyParameter("tanggal_event", tanggal_event.text.toString())
            .addBodyParameter("waktu_event", waktu_event.text.toString())
            .addBodyParameter("pembuat_event", pembuat_event.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(
                        applicationContext,
                        response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@ManageEventActivity.finish()
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

    private fun delete() {

        val loading = ProgressDialog(this)
        loading.setMessage("Menghapus data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.DELETE + "?id_event=" + id_event.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(
                        applicationContext,
                        response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@ManageEventActivity.finish()
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
