package com.example.top10rss

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private var downloadData: DownloadData? = null

    private var feedURL: String = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
    private var feedLimit = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadFeed(feedURL.format(feedLimit))
    }

    private fun downloadFeed(feedURL: String) {
        Log.d(TAG, "downloadFeed: starting AsyncTask")
        downloadData = DownloadData(this, xmlListView)
        downloadData?.execute(feedURL)
        Log.d(TAG, "downloadFeed: done")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.mnuFree -> feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
            R.id.mnuPaid -> feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml"
            R.id.mnuSongs -> feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"
            R.id.mnu10, R.id.mnu25 -> {
                if(!item.isChecked) {
                    item.isChecked = true
                    feedLimit = 35 - feedLimit
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }

        downloadFeed(feedURL.format(feedLimit))
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData?.cancel(true)
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

                val feedAdapter = FeedAdapter(propContext, R.layout.list_record, parser.records)
                propListView.adapter = feedAdapter
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
