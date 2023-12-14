package br.edu.scl.ifsp.sdm.moviesmanager.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.scl.ifsp.sdm.moviesmanager.model.dao.MovieDAO
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie

@Database(entities = [Movie::class], version=1)
abstract class MovieListDatabase:RoomDatabase() {
    companion object{
        const val MOVIES_MANAGER_DATABASE="moviesManagerDatabase"
    }
    abstract fun getMovieDAO(): MovieDAO
}