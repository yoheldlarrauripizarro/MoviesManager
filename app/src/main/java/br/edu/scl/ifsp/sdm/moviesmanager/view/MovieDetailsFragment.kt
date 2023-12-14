package br.edu.scl.ifsp.sdm.moviesmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.edu.scl.ifsp.sdm.moviesmanager.databinding.FragmentMovieDetailsBinding
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie.Companion.MOVIE_NOT_VIEWED
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie.Companion.MOVIE_VIEWED
import br.edu.scl.ifsp.sdm.moviesmanager.view.MainFragment.Companion.EXTRA_MOVIE
import br.edu.scl.ifsp.sdm.moviesmanager.view.MainFragment.Companion.MOVIE_FRAGMENT_REQUEST_KEY


class MovieDetailsFragment : Fragment() {
    private lateinit var ftb: FragmentMovieDetailsBinding
    private val navigationArgs: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Movie Details"

        ftb = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        val receivedMovieDetails = navigationArgs.movie
        receivedMovieDetails?.also { movie ->
            with(ftb) {
                nameEt.setText(movie.name)
                studioEt.setText(movie.studio)
                timeMinDurationEt.setText(movie.timeMinDuration.toString())
                watchedCb.isChecked = movie.viewed == MOVIE_VIEWED
                ratingRb.rating = movie.rating.toFloat()
                //gender
                navigationArgs.editMovie.also { editMovie ->
                    nameEt.isEnabled = editMovie
                    watchedCb.isEnabled = editMovie
                    saveBt.visibility = if (editMovie) VISIBLE else GONE
                }
            }

        }
        ftb.run {
            saveBt.setOnClickListener {
                setFragmentResult(MOVIE_FRAGMENT_REQUEST_KEY, Bundle().apply {
                    putParcelable(
                        EXTRA_MOVIE, Movie(
                            nameEt.text.toString(),
                            studioEt.text.toString(),
                            timeMinDurationEt.text.toString().toInt(),
                            if (watchedCb.isChecked) MOVIE_VIEWED else MOVIE_NOT_VIEWED,
                            ratingRb.numStars.toInt(),
                            //implementar depois spinner
                            "Scary"
                        )
                    )
                })
                findNavController().navigateUp()
            }
        }
        return ftb.root
    }
}