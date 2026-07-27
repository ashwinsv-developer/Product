package com.product.data.local.dao

import android.database.Cursor
import androidx.room.*
import com.product.data.local.entity.GenreEntity
import com.product.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Transaction
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    @Query("SELECT * FROM genres WHERE movieId = :movieId")
    suspend fun getGenresForMovie(movieId: Int): List<GenreEntity>

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    // --- Content Provider Support ---

    @Query("SELECT * FROM movies")
    fun getAllMoviesCursor(): Cursor

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieByIdCursor(movieId: Int): Cursor

    @Update
    suspend fun updateMovie(movie: MovieEntity): Int

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Int): Int
    
    @Query("DELETE FROM genres WHERE movieId = :movieId")
    suspend fun deleteGenresForMovie(movieId: Int): Int

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies(): Int
}
