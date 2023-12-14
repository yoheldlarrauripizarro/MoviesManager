package br.edu.scl.ifsp.sdm.moviesmanager.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie
@Dao
interface MovieDAO {
    companion object {
        const val MOVIE_TABLE = "MOVIE"
    }
    @Insert
    fun createMovie(movie:Movie)
    @Query("SELECT * FROM $MOVIE_TABLE")
    fun retrieveMovies():List<Movie>
    @Query("SELECT * FROM $MOVIE_TABLE ORDER BY RATING")
    fun retrieveMoviesOrderByRating():List<Movie>
    @Query("SELECT * FROM $MOVIE_TABLE ORDER BY NAME")
    fun retrieveMoviesOrderByName():List<Movie>
    @Update
    fun updateMovie(movie: Movie)
    @Delete
    fun deleteMovie(movie: Movie)
}