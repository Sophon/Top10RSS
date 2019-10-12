package com.example.top10rss

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception

class Parser {
    private val TAG = "Parser"

    val records = ArrayList<FeedEntry>()

    fun parse(xmlData: String): Boolean {
        Log.d(TAG, "parse called with $xmlData")
        var status = true
        var inEntry = false
        var textValue = ""
        var gotImage = false

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = FeedEntry()

            while(eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = xpp.name?.toLowerCase()
                when(eventType) {
                    XmlPullParser.START_TAG -> {
                        Log.d(TAG, "parse: starting tag for $tagName")
                        if(tagName == "entry") {
                            inEntry = true
                        } else if(tagName == "image" && inEntry) {
                            val imageRes = xpp.getAttributeValue(null, "height")
                            if(imageRes.isEmpty()) gotImage = imageRes == "53"
                        }
                    }

                    XmlPullParser.TEXT -> textValue = xpp.text

                    XmlPullParser.END_TAG -> {
                        Log.d(TAG, "parse: ending tag for $tagName")
                        if(inEntry) {
                            when(tagName) {
                                "entry" -> {
                                    records.add(currentRecord)
                                    inEntry = false
                                    currentRecord = FeedEntry()
                                }
                                "name" -> currentRecord.name = textValue
                                "artist" -> currentRecord.artist = textValue
                                "release date" -> currentRecord.releaseDate = textValue
                                "summary" -> currentRecord.summary = textValue
                                "image" -> if(gotImage) currentRecord.imageURL = textValue
                            }
                        }
                    }
                }

                eventType = xpp.next()
            }

            for(app in records) {
                Log.d(TAG, "*****")
                Log.d(TAG, app.toString())
            }
        } catch(e: Exception) {
            e.printStackTrace()
            status = false
        }

        return status
    }
}