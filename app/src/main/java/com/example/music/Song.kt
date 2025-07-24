package com.example.music

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val title: String,
    val artist: String,
    val resourceId: Int = R.raw.chu_voi_con,
    val imageRes: Int = R.drawable.chu_voi_con
) : Parcelable
