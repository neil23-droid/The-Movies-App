package com.example.android.themoviesapp.presentation.ui.movies

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.themoviesapp.presentation.viewmodel.MoviesListingViewModel

import com.example.android.themoviesapp.databinding.FragmentMoviesListingBinding
import com.example.android.themoviesapp.presentation.models.MoviesListUiModel
import com.example.android.themoviesapp.presentation.common.UiState
import com.example.android.themoviesapp.presentation.ui.adapters.UpComingMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [MoviesListingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class MoviesListingFragment : Fragment() {

    private var _binding: FragmentMoviesListingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesListingViewModel by viewModels()  // ← Hilt injection

    private val moviesAdapter by lazy {
        UpComingMoviesAdapter { selectedMovie ->
            navigateToDetail(selectedMovie)               // ← passes movieId directly
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeUiState()
        viewModel.loadMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null                             // ← prevents memory leak
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoviesListingFragment().apply {
            }
    }



    /*
    *
    * initialize upcoming movies recycler view*/
    private fun setupRecyclerView() {
        binding.upComingMoviesRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter
        }
    }


    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> showLoading()
                        is UiState.Empty   -> showEmpty()
                        is UiState.Error   -> showError(state.message)
                        is UiState.Success -> showMovies(state.data)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.materialProgressBar.visibility = View.VISIBLE
        binding.upComingMoviesRV.visibility = View.GONE
        /* binding.tvEmpty.visibility = View.GONE*/
    }

    private fun showEmpty() {
        binding.materialProgressBar.visibility = View.GONE
        binding.upComingMoviesRV.visibility = View.GONE
        /* binding.tvEmpty.visibility = View.VISIBLE
         binding.tvEmpty.text = "No movies available"*/
    }

    private fun showError(message: String) {
        binding.materialProgressBar.visibility = View.GONE
        binding.upComingMoviesRV.visibility = View.GONE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showMovies(movies: List<MoviesListUiModel>) {
        binding.materialProgressBar.visibility = View.GONE
        binding.upComingMoviesRV.visibility = View.VISIBLE
        moviesAdapter.updateList(movies)
    }

    private fun navigateToDetail(selectedMovie: MoviesListUiModel) {
        val action = MoviesListingFragmentDirections
            .actionMoviesListingFragmentToMovieDetailsFragment(
                movieId = selectedMovie.movieId,
                movieName = selectedMovie.originalTitle
            )
        findNavController().navigate(action)
    }

}