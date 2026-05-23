package com.example.android.themoviesapp.presentation.ui.ticket_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.themoviesapp.databinding.FragmentBookingHistoryBinding
import com.example.android.themoviesapp.presentation.common.UiState
import com.example.android.themoviesapp.presentation.models.BookingHistoryUiModel
import com.example.android.themoviesapp.presentation.ui.adapters.BookingHistoryAdapter
import com.example.android.themoviesapp.presentation.viewmodel.BookingHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookingHistoryFragment : Fragment() {

    private var _binding: FragmentBookingHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingHistoryViewModel by viewModels()

    private val bookingHistoryAdapter by lazy { BookingHistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeUiState()
        viewModel.loadBookedTickets()
    }

    private fun setupRecyclerView() {
        binding.bookedTicketsRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookingHistoryAdapter
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bookingHistoryUiState.collect { state ->
                when (state) {
                    is UiState.Loading -> showLoading()
                    is UiState.Empty   -> showEmpty()
                    is UiState.Error   -> showError(state.message)
                    is UiState.Success -> showTickets(state.data)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility    = View.VISIBLE
        binding.bookedTicketsRV.visibility = View.GONE
        binding.tvEmpty.visibility        = View.GONE
    }

    private fun showEmpty() {
        binding.progressBar.visibility    = View.GONE
        binding.bookedTicketsRV.visibility = View.GONE
        binding.tvEmpty.visibility        = View.VISIBLE
        binding.tvEmpty.text              = "You have not booked any tickets"
    }

    private fun showError(message: String) {
        binding.progressBar.visibility    = View.GONE
        binding.bookedTicketsRV.visibility = View.GONE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showTickets(bookedTickets: List<BookingHistoryUiModel>) {
        binding.progressBar.visibility    = View.GONE
        binding.bookedTicketsRV.visibility = View.VISIBLE
        bookingHistoryAdapter.updateList(bookedTickets)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null                             // ← prevents memory leak
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookingHistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}