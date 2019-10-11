package com.example.top10rss

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: called")
        val downloadData = DownloadData(this, xmlListView)
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
        Log.d(TAG, "onCreate: done")
    }

    companion object {
        private class DownloadData(context: Context, listView: ListView): AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"

            var propContext by Delegates.notNull<Context>()
            var propListView by Delegates.notNull<ListView>()

            init {
                propContext = context
                propListView = listView
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                val parser = Parser()
                parser.parse(result)

                val arrayAdapter = ArrayAdapter<FeedEntry>(propContext, R.layout.list_item, parser.applications)
                propListView.adapter = arrayAdapter
            }

            override fun doInBackground(vararg urls: String?): String {
                Log.d(TAG, "doInBackground: starts with ${urls[0]}")
                val rssFeed = downloadXML(urls[0])
                if(rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground: error downloading")
                }

                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String {
                return URL(urlPath).readText()
            }
        }
    }


}
