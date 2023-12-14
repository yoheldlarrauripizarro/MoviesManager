package br.edu.scl.ifsp.sdm.moviesmanager.controller

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import br.edu.scl.ifsp.sdm.moviesmanager.model.database.MovieListDatabase
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie

class MovieViewModel(application: Application):ViewModel() {
    private val taskDAOImpl = Room.databaseBuilder(
        application.applicationContext,
        MovieListDatabase::class.java,
        MovieListDatabase.MOVIES_MANAGER_DATABASE
    ).build().getMovieDAO()

    val taskMld = MutableLiveData<List<Movie>>()

    fun insertMovie(movie: Movie){

    }

    fun editMovie(movie: Movie){

    }
    fun getMovies(){

    }

    fun removeMovie(movie: Movie){

    }

    companion object{

    }
}