package com.example.top10rss

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FeedAdapter(
    context: Context, private val resource: Int, private val records: List<FeedEntry>
): ArrayAdapter<FeedEntry>(context, resource) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if(convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val currentRecord = records[position]

        viewHolder.tvName.text = currentRecord.name
        viewHolder.tvArtist.text = currentRecord.artist
        viewHolder.tvSummary.text = currentRecord.summary

        return view
    }

    override fun getCount(): Int {
        return records.size
    }
}