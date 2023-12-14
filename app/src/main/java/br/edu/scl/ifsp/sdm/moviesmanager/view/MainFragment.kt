package br.edu.scl.ifsp.sdm.moviesmanager.view

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.scl.ifsp.sdm.moviesmanager.R
import br.edu.scl.ifsp.sdm.moviesmanager.controller.MovieViewModel
import br.edu.scl.ifsp.sdm.moviesmanager.databinding.FragmentMainBinding
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie.Companion.MOVIE_NOT_VIEWED
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie.Companion.MOVIE_VIEWED
import br.edu.scl.ifsp.sdm.moviesmanager.view.adapter.MovieAdapter
import br.edu.scl.ifsp.sdm.moviesmanager.view.adapter.OnMovieClickListener


class MainFragment : Fragment(), OnMovieClickListener {
    private lateinit var fmb: FragmentMainBinding
    private var movieList: MutableList<Movie> = mutableListOf()
    private val moviesAdapter: MovieAdapter by lazy {
        MovieAdapter(movieList, this)
    }
    private val navController: NavController by lazy {
        findNavController()
    }
    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val MOVIE_FRAGMENT_REQUEST_KEY = "MOVIE_FRAGMENT_REQUEST_KEY"
    }

    // ViewModel
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.movieViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(MOVIE_FRAGMENT_REQUEST_KEY) { requestKey, bundle ->
            if (requestKey == MOVIE_FRAGMENT_REQUEST_KEY) {
                val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(EXTRA_MOVIE, Movie::class.java)
                } else {
                    bundle.getParcelable(EXTRA_MOVIE)
                }
                movie?.also { receivedMovie->
                    movieList.indexOfFirst { it.name == receivedMovie.name }.also { position ->
                        if (position != -1) {
                            movieViewModel.editMovie(receivedMovie)
                            movieList[position] = receivedMovie
                            moviesAdapter.notifyItemChanged(position)
                        } else {
                            movieViewModel.insertMovie(receivedMovie)
                            movieList.add(receivedMovie)
                            moviesAdapter.notifyItemInserted(movieList.lastIndex)
                        }
                    }
                }

                // Hiding soft keyboard
                (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    fmb.root.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }

        movieViewModel.movieMld.observe(requireActivity()) { movies ->
            movieList.clear()
            movies.forEachIndexed { index, movie ->
                movieList.add(movie)
                moviesAdapter.notifyItemChanged(index)
            }
        }

        movieViewModel.getMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Movie List"


        fmb = FragmentMainBinding.inflate(inflater, container, false).apply {


            moviesRv.layoutManager = LinearLayoutManager(context)
            moviesRv.adapter = moviesAdapter

            addMovieFab.setOnClickListener {
                navController.navigate(
                    MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(null, editMovie = false)
                )
            }

            orderNameBt.setOnClickListener {
                movieViewModel.orderMoviesByName()
            }

            orderRatingBt.setOnClickListener {
                movieViewModel.orderMoviesByRating()
            }
        }

        return fmb.root
    }
    override fun onMovieClick(position: Int) = navigateToMovieFragment(position, false)

    override fun onRemoveMovieMenuItemClick(position: Int) {
        movieViewModel.removeMovie(movieList[position])
        movieList.removeAt(position)
        moviesAdapter.notifyItemRemoved(position)
    }

    override fun onEditMovieMeuItemClick(position: Int) = navigateToMovieFragment(position, true)

    override fun onDoneCheckBoxClick(position: Int, checked: Boolean) {
        movieList[position].apply {
            viewed = if (checked) MOVIE_VIEWED else MOVIE_NOT_VIEWED
            movieViewModel.editMovie(this)
        }
    }

    private fun navigateToMovieFragment(position: Int, editMovie: Boolean) {
        movieList[position].also {
            navController.navigate(
                MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(it, editMovie)
            )
        }
    }
}