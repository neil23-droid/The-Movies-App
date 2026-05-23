package com.example.android.themoviesapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.themoviesapp.Others.POSTER_BASE_URL
import com.example.android.themoviesapp.R
import com.example.android.themoviesapp.databinding.BookedTicketHistoryItemBinding
import com.example.android.themoviesapp.presentation.models.BookingHistoryUiModel

class BookingHistoryAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var bookedTicketsList: List<BookingHistoryUiModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false
        val itemBinding = BookedTicketHistoryItemBinding.inflate(
            inflater,
            parent,
            shouldAttachToParentImmediately
        )

        return BookedTicketViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BookedTicketViewHolder).bindData(bookedTicketsList.get(position))
    }

    override fun getItemCount(): Int {
        return bookedTicketsList.size
    }

    fun updateList(data: List<BookingHistoryUiModel>) {
        bookedTicketsList = data
        notifyDataSetChanged()
    }

    class BookedTicketViewHolder(private val itemBinding: BookedTicketHistoryItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun bindData(data: BookingHistoryUiModel) {

            //display image
            Glide
                .with(itemView.context)
                .load("${POSTER_BASE_URL}${data?.posterPath ?: ""}")
                .placeholder(R.drawable.empty_poster)
                .error(R.drawable.empty_poster)
                .into(itemBinding.moviePosterIV)

            itemBinding.movieNameTxt.text = "${data.originalTitle ?: ""}"

            itemBinding.movieReleaseDateTxt.text = "${data.releaseDate}"

            itemBinding.adultsOnlyTxt.text = if (data.adult) {
                "Adult"
            } else {
                "Non-Adult"
            }
            itemBinding.locationValue.text = "${data?.selectedLocation ?: ""}"

            itemBinding.cinemaValue.text = "${data?.selectedCinema ?: ""}"

            itemBinding?.seatValue?.text = "${data?.selectedSeat ?: ""}"

        }
    }
}