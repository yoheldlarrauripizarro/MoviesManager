package br.edu.scl.ifsp.sdm.moviesmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.scl.ifsp.sdm.moviesmanager.R
import br.edu.scl.ifsp.sdm.moviesmanager.controller.MovieViewModel
import br.edu.scl.ifsp.sdm.moviesmanager.databinding.FragmentMainBinding
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie
import br.edu.scl.ifsp.sdm.moviesmanager.view.adapter.MovieAdapter
import br.edu.scl.ifsp.sdm.moviesmanager.view.adapter.OnMovieClickListener


class MainFragment : Fragment(), OnMovieClickListener {
    private lateinit var fmb: FragmentMainBinding
    private val movieList: MutableList<Movie> = mutableListOf()
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
        MovieViewModel.MovieViewModelFactory
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
        }

        return fmb.root
    }
    override fun onMovieClick(position: Int) = navigateToMovieFragment(position, false)

    override fun onRemoveMovieMenuItemClick(position: Int) {
    }

    override fun onEditMovieMeuItemClick(position: Int) = navigateToMovieFragment(position, true)

    override fun onDoneCheckBoxClick(position: Int, checked: Boolean) {
    }
    private fun navigateToMovieFragment(position: Int, editMovie: Boolean) {
        movieList[position].also {
            navController.navigate(
                MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(it, editMovie)
            )
        }
    }
}