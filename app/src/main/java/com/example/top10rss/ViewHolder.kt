package com.example.top10rss

import android.view.View
import android.widget.TextView

class ViewHolder(v: View) {
    val tvName = v.findViewById<TextView>(R.id.tvName)
    val tvArtist = v.findViewById<TextView>(R.id.tvArtist)
    val tvSummary = v.findViewById<TextView>(R.id.tvSummary)
}