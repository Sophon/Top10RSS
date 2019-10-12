package com.example.top10rss

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class FeedAdapter(context: Context, val resource: Int, val records: List<FeedEntry>): ArrayAdapter<FeedEntry>(context, resource) {
    private val TAG = "FeedAdapter"

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return records.size
    }
}