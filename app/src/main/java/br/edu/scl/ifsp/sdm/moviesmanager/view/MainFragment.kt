package br.edu.scl.ifsp.sdm.moviesmanager.view

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
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