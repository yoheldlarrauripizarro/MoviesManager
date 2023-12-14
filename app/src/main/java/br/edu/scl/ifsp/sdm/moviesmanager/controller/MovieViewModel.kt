package br.edu.scl.ifsp.sdm.moviesmanager.controller

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import br.edu.scl.ifsp.sdm.moviesmanager.model.database.MovieListDatabase
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application):ViewModel() {
    private val movieDAOImpl = Room.databaseBuilder(
        application.applicationContext,
        MovieListDatabase::class.java,
        MovieListDatabase.MOVIES_MANAGER_DATABASE
    ).build().getMovieDAO()

    val movieMld = MutableLiveData<List<Movie>>()

    fun insertMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            movieDAOImpl.createMovie(movie)
        }
    }

    fun editMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            movieDAOImpl.updateMovie(movie)
        }
    }
    fun getMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            val movies = movieDAOImpl.retrieveMovies()
            movieMld.postValue(movies)
        }
    }

    fun removeMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            movieDAOImpl.deleteMovie(movie)
        }
    }

    companion object{
        val MovieViewModelFactory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
                MovieViewModel(checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])) as T
        }
    }
}