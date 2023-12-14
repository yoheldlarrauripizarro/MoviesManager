package br.edu.scl.ifsp.sdm.moviesmanager.view.adapter


interface OnMovieClickListener {
    fun onMovieClick(position: Int)
    fun onRemoveMovieMenuItemClick(position: Int)
    fun onEditMovieMeuItemClick(position: Int)
    fun onDoneCheckBoxClick(position: Int, checked: Boolean)
}