package br.edu.scl.ifsp.sdm.moviesmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.edu.scl.ifsp.sdm.moviesmanager.R
import br.edu.scl.ifsp.sdm.moviesmanager.databinding.TileMovieBinding
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie.Companion.MOVIE_VIEWED

class MovieAdapter (
    private val movieList: List<Movie>,
    private val onMovieClickListener: OnMovieClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieTileViewHolder>() {
    inner class MovieTileViewHolder(tileMovieBinding:TileMovieBinding):
        RecyclerView.ViewHolder(tileMovieBinding.root){
        val nameTv: TextView = tileMovieBinding.nameTv
        val donecb: CheckBox = tileMovieBinding.doneCb
        val genderTv: TextView = tileMovieBinding.genderTv

        init{
            tileMovieBinding.apply {
                root.run {
                    setOnCreateContextMenuListener { menu, _, _ ->
                        (onMovieClickListener as? Fragment)?.activity?.menuInflater?.inflate(
                            R.menu.context_menu_movie,
                            menu
                        )
                        menu?.findItem(R.id.removeMovieMi)?.setOnMenuItemClickListener {
                            onMovieClickListener.onRemoveMovieMenuItemClick(adapterPosition)
                            true
                        }
                        menu?.findItem(R.id.editMovieMi)?.setOnMenuItemClickListener {
                            onMovieClickListener.onEditMovieMeuItemClick(adapterPosition)
                            true
                        }
                    }
                    setOnClickListener {
                        onMovieClickListener.onMovieClick(adapterPosition)
                    }
                }
                doneCb.run {
                    setOnClickListener {
                        onMovieClickListener.onDoneCheckBoxClick(adapterPosition, isChecked)
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TileMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            .run{MovieTileViewHolder(this)}

    override fun onBindViewHolder(holder: MovieTileViewHolder, position: Int) {
        movieList[position].let { movie ->
            with(holder) {
                nameTv.text = movie.name
                donecb.isChecked= movie.viewed == MOVIE_VIEWED
                genderTv.text = movie.gender
            }
        }
    }

    override fun getItemCount() = movieList.size
}