package com.example.top10rss

class FeedEntry {
    var name = ""
    var artist = ""
    var releaseDate =""
    var summary = ""
    var imageURL = ""

    override fun toString(): String {
        return """
            name = $name
            artist = $artist
            release date = $releaseDate
        """.trimIndent()
    }
}