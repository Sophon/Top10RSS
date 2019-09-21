package com.example.top10rss

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private inner class DownloadData: AsyncTask<String, Void, String>() {
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg params: String?): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
