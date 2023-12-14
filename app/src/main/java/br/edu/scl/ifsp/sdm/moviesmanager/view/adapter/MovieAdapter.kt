package br.edu.scl.ifsp.sdm.moviesmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.scl.ifsp.sdm.moviesmanager.databinding.TileMovieBinding
import br.edu.scl.ifsp.sdm.moviesmanager.model.entity.Movie

class MovieAdapter (
    private val movieList: List<Movie>,
    private val onMovieClickListener: OnMovieClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieTileViewHolder>() {
    inner class MovieTileViewHolder(tileMovieBinding:TileMovieBinding):
        RecyclerView.ViewHolder(tileMovieBinding.root){

        init{

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TileMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            .run{MovieTileViewHolder(this)}

    override fun onBindViewHolder(holder: MovieTileViewHolder, position: Int) {
    }

    override fun getItemCount() = movieList.size
}