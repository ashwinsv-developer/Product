package com.product.data.provider

import android.net.Uri
import android.provider.BaseColumns

object MovieContract {
    const val AUTHORITY = "com.product.movieprovider"
    val BASE_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")
    const val PATH_MOVIES = "movies"

    object MovieEntry : BaseColumns {
        val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build()
        const val TABLE_NAME = "movies"

        const val COLUMN_ID = "movie_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_OVERVIEW = "overview"
        const val COLUMN_POSTER_PATH = "poster_path"
        const val COLUMN_BACKDROP_PATH = "backdrop_path"
        const val COLUMN_RELEASE_DATE = "release_date"
        const val COLUMN_VOTE_AVERAGE = "vote_average"
        const val COLUMN_VOTE_COUNT = "vote_count"
        const val COLUMN_POPULARITY = "popularity"
        const val COLUMN_ADULT = "adult"
        const val COLUMN_BUDGET = "budget"
        const val COLUMN_REVENUE = "revenue"
        const val COLUMN_RUNTIME = "runtime"
        const val COLUMN_STATUS = "status"
        const val COLUMN_TAGLINE = "tagline"
        const val COLUMN_HOMEPAGE = "homepage"
        const val COLUMN_IMDB_ID = "imdb_id"
        const val COLUMN_ORIGINAL_LANGUAGE = "original_language"
        const val COLUMN_ORIGINAL_TITLE = "original_title"
        const val COLUMN_VIDEO = "video"
        
        // Genres will be handled separately or as a joined string in simple cases, 
        // but for a provider we might want a separate table or a simple string.
        // Let's keep it simple for now and store genres as a comma separated string in the provider 
        // OR provide a separate URI for genres.
        const val COLUMN_GENRES = "genres" // JSON string or comma separated
    }
}
