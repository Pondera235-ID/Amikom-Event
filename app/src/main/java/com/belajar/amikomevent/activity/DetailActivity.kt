package com.belajar.amikomevent.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.belajar.amikomevent.R
import com.belajar.amikomevent.model.Event
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    var eventList = ArrayList<Event>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val i = intent

        tv_detail_title.setText(i.getStringExtra("judul_event"))
        tv_detail_deskripsi.setText(i.getStringExtra("deskripsi_event"))
        tv_detail_tanggal.setText(i.getStringExtra("tanggal_event"))
        tv_detail_waktu.setText(i.getStringExtra("waktu_event"))
        tv_detail_pembuat.setText(i.getStringExtra("pembuat_event"))
    }
}
