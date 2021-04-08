package com.belajar.amikomevent.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.belajar.amikomevent.activity.ManageEventActivity
import com.belajar.amikomevent.R
import com.belajar.amikomevent.model.Event
import kotlinx.android.synthetic.main.item_event.view.*

class EventAdapter (private val context: Context, private val eventList : ArrayList<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): EventViewHolder {
return EventViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_event,
        parent,
        false
    )
)
    }

    override fun getItemCount(): Int = eventList.size

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.view.tv_title.text = eventList.get(position).judul_event
        holder.view.tv_date.text = eventList.get(position).tanggal_event
        holder.view.tv_place.text = eventList.get(position).lokasi_event
        holder.view.tv_time.text = eventList.get(position).waktu_event

        holder.view.rvEvent.setOnClickListener{
            val i = Intent(context, ManageEventActivity::class.java)
            i.putExtra("editmode", "1")
            i.putExtra("id_event", eventList.get(position).id_event)
            i.putExtra("judul_event", eventList.get(position).judul_event)
            i.putExtra("deskripsi_event", eventList.get(position).deskripsi_event)
            i.putExtra("lokasi_event", eventList.get(position).lokasi_event)
            i.putExtra("tanggal_event", eventList.get(position).tanggal_event)
            i.putExtra("waktu_event", eventList.get(position).waktu_event)
            i.putExtra("pembuat_event", eventList.get(position).pembuat_event)
            context.startActivity(i)
        }
    }

    class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}