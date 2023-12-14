package br.edu.scl.ifsp.sdm.moviesmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.scl.ifsp.sdm.moviesmanager.R
import br.edu.scl.ifsp.sdm.moviesmanager.controller.MovieViewModel
import br.edu.scl.ifsp.sdm.moviesmanager.databinding.FragmentMovieDetailsBinding
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie.Companion.MOVIE_NOT_VIEWED
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie.Companion.MOVIE_VIEWED
import br.edu.scl.ifsp.sdm.moviesmanager.view.MainFragment.Companion.EXTRA_MOVIE
import br.edu.scl.ifsp.sdm.moviesmanager.view.MainFragment.Companion.MOVIE_FRAGMENT_REQUEST_KEY


class MovieDetailsFragment : Fragment() {
    private lateinit var ftb: FragmentMovieDetailsBinding
    private val navigationArgs: MovieDetailsFragmentArgs by navArgs()
    private val genderList: MutableList<String> = arrayOf("Romace","Aventura","Terror","Comedia","Ação","Suspense").toMutableList()
    var genreSelected=""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Movie Details"

        ftb = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        ftb.genderSp.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,genderList)

        ftb.genderSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                genreSelected = genderList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                genreSelected = ""
            }
        }
        val receivedMovieDetails = navigationArgs.movie
        receivedMovieDetails?.also { movie ->
            with(ftb) {
                nameEt.setText(movie.name)
                studioEt.setText(movie.studio)
                timeMinDurationEt.setText(movie.timeMinDuration.toString())
                watchedCb.isChecked = movie.viewed == MOVIE_VIEWED
                ratingRb.rating = movie.rating.toFloat()
                genderSp.setSelection(genderList.indexOf(movie.gender))
                navigationArgs.editMovie.also { editMovie ->
                    nameEt.isEnabled = editMovie
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
                            (ratingRb.rating*2).toInt(),
                            genreSelected
                        )
                    )
                })
                findNavController().navigateUp()
            }
        }

        ftb.addGenreBt.setOnClickListener {
            val newGenre = ftb.newGenreEt.text.toString()
            if (newGenre.isNotEmpty() && !genderList.contains(newGenre)) {
                genderList.add(newGenre)
                updateGenderSpinner()
            }
            ftb.newGenreEt.text.clear()
        }

        return ftb.root
    }

    private fun updateGenderSpinner() {
        ftb.genderSp.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, genderList)
    }
}