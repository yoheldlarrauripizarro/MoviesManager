package br.edu.scl.ifsp.sdm.moviesmanager.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Movie (
    @PrimaryKey
    var name: String,
    var studio: String ="",
    var timeMinDuration: Int = INVALID_TIME,
    var viewed: Int = MOVIE_NOT_VIEWED,
    var rating: Int = NO_RATING,
    var gender: String = "",
):Parcelable{
    companion object {
        const val NO_RATING = 0
        const val INVALID_TIME = -1
        const val MOVIE_NOT_VIEWED = 0
        const val MOVIE_VIEWED = 1
    }
}