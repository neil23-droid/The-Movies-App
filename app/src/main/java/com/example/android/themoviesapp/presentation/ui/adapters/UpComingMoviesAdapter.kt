package com.example.android.themoviesapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.themoviesapp.Others.POSTER_BASE_URL
import com.example.android.themoviesapp.Others.getDate2
import com.example.android.themoviesapp.R
import com.example.android.themoviesapp.databinding.MoviesListItemBinding
import com.example.android.themoviesapp.presentation.models.MoviesListUiModel

class UpComingMoviesAdapter(
    private val onMovieClick:(MoviesListUiModel)->Unit={}
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var upComingMoviesList:List<MoviesListUiModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false
        val itemBinding = MoviesListItemBinding.inflate(inflater, parent, shouldAttachToParentImmediately)

        return UpComingMovieViewHolder(itemBinding, onMovieClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UpComingMovieViewHolder).bindData(upComingMoviesList.get(position))
    }

    override fun getItemCount(): Int {
        return upComingMoviesList.size
    }

    fun updateList(data:List<MoviesListUiModel>){
        upComingMoviesList = data
        notifyDataSetChanged()
    }

    class UpComingMovieViewHolder(
        private val binding: MoviesListItemBinding,
        private val onMovieClick: (MoviesListUiModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(movie: MoviesListUiModel) {
            Glide.with(binding.root)
                .load("$POSTER_BASE_URL${movie.posterPath.orEmpty()}")
                .placeholder(R.drawable.empty_poster)
                .error(R.drawable.empty_poster)
                .into(binding.moviePosterIV)

            binding.movieNameTxt.text = movie.originalTitle
            binding.movieReleaseDateTxt.text = movie.releaseDate
            binding.adultsOnlyTxt.text = if (movie.isAdultContentVisible) "Adult" else "Non-Adult"
            binding.root.setOnClickListener { onMovieClick(movie) }
        }
    }
}