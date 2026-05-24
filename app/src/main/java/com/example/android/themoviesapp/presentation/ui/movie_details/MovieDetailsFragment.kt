package com.example.android.themoviesapp.presentation.ui.movie_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.themoviesapp.Others.MOVIE_TRAILER_LINK
import com.example.android.themoviesapp.databinding.FragmentMovieDetailsBinding
import com.example.android.themoviesapp.presentation.models.BookingSessionModel
import com.example.android.themoviesapp.presentation.models.MovieDetailsUiModel
import com.example.android.themoviesapp.presentation.models.MoviePostersUiModel
import com.example.android.themoviesapp.presentation.common.UiState
import com.example.android.themoviesapp.presentation.ui.adapters.MoviePosterViewPagerAdapter
import com.example.android.themoviesapp.presentation.viewmodel.MovieDetailsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    private lateinit var viewPagerAdapter: MoviePosterViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        initializeViewPager()
        initializeOnClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMovieDetail()
        observePosters()
        observeMovieTrailers()
        viewModel.loadMovieDetail(args.movieId)
        viewModel.loadMoviePosters(args.movieId)
    }

    // Section 1 — observes movie detail independently
    private fun observeMovieDetail() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieDetailsUiState.collect { movieDetailUiState  ->
                when (movieDetailUiState) {
                    is UiState.Loading -> showDetailLoading()
                    is UiState.Empty   -> showDetailEmpty()
                    is UiState.Error   -> showDetailError(movieDetailUiState.message)
                    is UiState.Success -> showMovieDetail(movieDetailUiState.data)
                }
            }
        }
    }

    // movie detail section
    private fun showDetailLoading() {
        binding.materialPorgressBar.visibility = View.VISIBLE
    }

    private fun showDetailEmpty() {
        binding.materialPorgressBar.visibility = View.GONE
        Toast.makeText(requireContext(), "No movie details available", Toast.LENGTH_SHORT).show()
    }

    private fun showDetailError(message: String) {
        binding.materialPorgressBar.visibility = View.GONE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showMovieDetail(movie: MovieDetailsUiModel) {
        binding.materialPorgressBar.visibility = View.GONE
        binding.apply {
            movieTitle.text       = movie.title
            overviewValue.text    = movie.overview
            dateValue.text = movie.releaseDate
            popularityRating.rating = movie.voteAverage
            genresValues.text      = movie.genres
        }
    }

    // Section 2 — observes posters independently
    private fun observePosters() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moviePostersUiState.collect { state ->
                when (state) {
                    is UiState.Loading -> showPostersLoading()
                    is UiState.Empty   -> showPostersEmpty()
                    is UiState.Error   -> hidePostersSection()
                    is UiState.Success -> showPosters(state.data)
                }
            }
        }
    }

    // posters section
    private fun showPostersLoading() {
        //binding.materialPorgressBar.visibility = View.VISIBLE
        //binding.postersRecyclerView.visibility = View.GONE
    }

    private fun showPostersEmpty() {
        //binding.materialPorgressBar.visibility = View.GONE
        //binding.postersSection.visibility = View.GONE  // ← hide entire section
    }

    private fun hidePostersSection() {
        //binding.postersProgressBar.visibility = View.GONE
        //binding.postersSection.visibility = View.GONE  // ← hide on error too
    }

    private fun showPosters(posters: List<MoviePostersUiModel>) {
       /* binding.postersProgressBar.visibility = View.GONE
        binding.postersRecyclerView.visibility = View.VISIBLE
        postersAdapter.submitList(posters)*/
        if (posters.size > 5) {
            viewPagerAdapter.updateList(posters.take(5))
        } else {
            viewPagerAdapter.updateList(posters)
        }
    }

    fun observeMovieTrailers(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieTrailerUiState.collect { state ->
                when (state) {
                    is UiState.Loading->{

                    }
                    is UiState.Success ->{
                        if (state.data.size >= 1) {
                            val urlKey = state.data.get(0)?.key ?: ""
                            openVideoUrl(urlKey)
                        }
                    }
                    is UiState.Error->
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                            .show()
                    else -> {
                        Toast.makeText(requireContext(), "Unable to load movie trailer", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }



    private fun initializeOnClickListeners(){
        binding.viewTrailerButton.setOnClickListener {
            viewModel.getMovieTrailers(args.movieId)
        }

        binding.bookMovieTicketCL.setOnClickListener {
            val bookingSession = viewModel.getBookingSession()
            if (bookingSession != null) {
                navigateToLocationDetails(bookingSession)
            }
        }
    }

    private fun navigateToLocationDetails(bookingSession: BookingSessionModel) {
        val action = MovieDetailsFragmentDirections
            .actionMovieDetailsFragmentToLocationDetailsFragment(
                bookingSession = bookingSession)
        findNavController().navigate(action)
    }

    private fun initializeViewPager(){
        viewPagerAdapter = MoviePosterViewPagerAdapter()
        binding.movieImagesViewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.swipeableImagesTabLayout,binding.movieImagesViewPager,object :
            TabLayoutMediator.TabConfigurationStrategy {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

            }
        }).attach()
    }

    private fun openVideoUrl(key:String){
        val url = "${MOVIE_TRAILER_LINK}${key}"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}