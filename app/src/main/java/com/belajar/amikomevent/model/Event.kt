package com.belajar.amikomevent.model

data class Event (
    val id_event:String?,
    val judul_event:String?,
    val deskripsi_event:String,
    val lokasi_event:String?,
    val tanggal_event: String?,
    val waktu_event:String?,
    val pembuat_event:String?
)