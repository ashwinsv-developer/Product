package com.product.data.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.product.data.local.AppDatabase
import androidx.room.Room
import com.product.data.local.entity.MovieEntity
import com.product.data.local.entity.GenreEntity
import kotlinx.coroutines.runBlocking

class MovieProvider : ContentProvider() {

    companion object {
        private const val MOVIES = 100
        private const val MOVIE_ID = 101

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES, MOVIES)
            addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIE_ID)
        }
    }

    private lateinit var database: AppDatabase

    override fun onCreate(): Boolean {
        database = Room.databaseBuilder(
            context!!,
            AppDatabase::class.java, "movie_db"
        ).build()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val match = sUriMatcher.match(uri)
        val cursor: Cursor = when (match) {
            MOVIES -> {
                database.movieDao().getAllMoviesCursor()
            }
            MOVIE_ID -> {
                val id = ContentUris.parseId(uri).toInt()
                database.movieDao().getMovieByIdCursor(id)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return when (sUriMatcher.match(uri)) {
            MOVIES -> "vnd.android.cursor.dir/${MovieContract.AUTHORITY}.${MovieContract.PATH_MOVIES}"
            MOVIE_ID -> "vnd.android.cursor.item/${MovieContract.AUTHORITY}.${MovieContract.PATH_MOVIES}"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (values == null) return null

        val match = sUriMatcher.match(uri)
        return when (match) {
            MOVIES -> {
                val movieId = values.getAsInteger(MovieContract.MovieEntry.COLUMN_ID) ?: return null
                
                val movieEntity = MovieEntity(
                    id = movieId,
                    adult = values.getAsBoolean(MovieContract.MovieEntry.COLUMN_ADULT) ?: false,
                    backdropPath = values.getAsString(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH) ?: "",
                    budget = values.getAsInteger(MovieContract.MovieEntry.COLUMN_BUDGET) ?: 0,
                    homepage = values.getAsString(MovieContract.MovieEntry.COLUMN_HOMEPAGE) ?: "",
                    imdbId = values.getAsString(MovieContract.MovieEntry.COLUMN_IMDB_ID) ?: "",
                    originalLanguage = values.getAsString(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE) ?: "",
                    originalTitle = values.getAsString(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE) ?: "",
                    overview = values.getAsString(MovieContract.MovieEntry.COLUMN_OVERVIEW) ?: "",
                    popularity = values.getAsDouble(MovieContract.MovieEntry.COLUMN_POPULARITY) ?: 0.0,
                    posterPath = values.getAsString(MovieContract.MovieEntry.COLUMN_POSTER_PATH) ?: "",
                    releaseDate = values.getAsString(MovieContract.MovieEntry.COLUMN_RELEASE_DATE) ?: "",
                    revenue = values.getAsInteger(MovieContract.MovieEntry.COLUMN_REVENUE) ?: 0,
                    runtime = values.getAsInteger(MovieContract.MovieEntry.COLUMN_RUNTIME) ?: 0,
                    status = values.getAsString(MovieContract.MovieEntry.COLUMN_STATUS) ?: "",
                    tagline = values.getAsString(MovieContract.MovieEntry.COLUMN_TAGLINE) ?: "",
                    title = values.getAsString(MovieContract.MovieEntry.COLUMN_TITLE) ?: "",
                    video = values.getAsBoolean(MovieContract.MovieEntry.COLUMN_VIDEO) ?: false,
                    voteAverage = values.getAsDouble(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE) ?: 0.0,
                    voteCount = values.getAsInteger(MovieContract.MovieEntry.COLUMN_VOTE_COUNT) ?: 0
                )

                // Handle genres if passed as a comma-separated string
                val genresStr = values.getAsString(MovieContract.MovieEntry.COLUMN_GENRES)
                val genreEntities = genresStr?.split(",")?.mapIndexed { index, name ->
                    GenreEntity(id = (movieId * 1000) + index, movieId = movieId, name = name.trim())
                } ?: emptyList()

                runBlocking {
                    database.movieDao().insertMovie(movieEntity)
                    if (genreEntities.isNotEmpty()) {
                        database.movieDao().insertGenres(genreEntities)
                    }
                }

                context?.contentResolver?.notifyChange(uri, null)
                ContentUris.withAppendedId(uri, movieId.toLong())
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val match = sUriMatcher.match(uri)
        return when (match) {
            MOVIES -> {
                val count = runBlocking { database.movieDao().deleteAllMovies() }
                if (count > 0) context?.contentResolver?.notifyChange(uri, null)
                count
            }
            MOVIE_ID -> {
                val id = ContentUris.parseId(uri).toInt()
                val count = runBlocking {
                    database.movieDao().deleteGenresForMovie(id)
                    database.movieDao().deleteMovie(id)
                }
                if (count > 0) context?.contentResolver?.notifyChange(uri, null)
                count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        if (values == null) return 0
        val match = sUriMatcher.match(uri)
        return when (match) {
            MOVIE_ID -> {
                val movieId = ContentUris.parseId(uri).toInt()
                val movieEntity = MovieEntity(
                    id = movieId,
                    adult = values.getAsBoolean(MovieContract.MovieEntry.COLUMN_ADULT) ?: false,
                    backdropPath = values.getAsString(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH) ?: "",
                    budget = values.getAsInteger(MovieContract.MovieEntry.COLUMN_BUDGET) ?: 0,
                    homepage = values.getAsString(MovieContract.MovieEntry.COLUMN_HOMEPAGE) ?: "",
                    imdbId = values.getAsString(MovieContract.MovieEntry.COLUMN_IMDB_ID) ?: "",
                    originalLanguage = values.getAsString(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE) ?: "",
                    originalTitle = values.getAsString(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE) ?: "",
                    overview = values.getAsString(MovieContract.MovieEntry.COLUMN_OVERVIEW) ?: "",
                    popularity = values.getAsDouble(MovieContract.MovieEntry.COLUMN_POPULARITY) ?: 0.0,
                    posterPath = values.getAsString(MovieContract.MovieEntry.COLUMN_POSTER_PATH) ?: "",
                    releaseDate = values.getAsString(MovieContract.MovieEntry.COLUMN_RELEASE_DATE) ?: "",
                    revenue = values.getAsInteger(MovieContract.MovieEntry.COLUMN_REVENUE) ?: 0,
                    runtime = values.getAsInteger(MovieContract.MovieEntry.COLUMN_RUNTIME) ?: 0,
                    status = values.getAsString(MovieContract.MovieEntry.COLUMN_STATUS) ?: "",
                    tagline = values.getAsString(MovieContract.MovieEntry.COLUMN_TAGLINE) ?: "",
                    title = values.getAsString(MovieContract.MovieEntry.COLUMN_TITLE) ?: "",
                    video = values.getAsBoolean(MovieContract.MovieEntry.COLUMN_VIDEO) ?: false,
                    voteAverage = values.getAsDouble(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE) ?: 0.0,
                    voteCount = values.getAsInteger(MovieContract.MovieEntry.COLUMN_VOTE_COUNT) ?: 0
                )
                val count = runBlocking { database.movieDao().updateMovie(movieEntity) }
                if (count > 0) context?.contentResolver?.notifyChange(uri, null)
                count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}
