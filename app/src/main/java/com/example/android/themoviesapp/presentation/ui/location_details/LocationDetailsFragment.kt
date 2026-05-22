package com.example.android.themoviesapp.presentation.ui.location_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.themoviesapp.presentation.models.CinemaData
import com.example.android.themoviesapp.presentation.models.LocationData
import com.example.android.themoviesapp.presentation.models.SeatData
import com.example.android.themoviesapp.R
import com.example.android.themoviesapp.databinding.FragmentLocationDetailsBinding
import com.example.android.themoviesapp.presentation.ui.adapters.CinemaSpinnerAdapter
import com.example.android.themoviesapp.presentation.ui.adapters.LocationSpinnerAdapter
import com.example.android.themoviesapp.presentation.ui.adapters.SeatSpinnerAdapter
import com.example.android.themoviesapp.presentation.viewmodel.LocationDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [LocationDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class LocationDetailsFragment : Fragment() {
    private var _binding: FragmentLocationDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LocationDetailsViewModel by viewModels()

    // receives BookingSessionModel from MovieDetailFragment
    private val args: LocationDetailsFragmentArgs by navArgs()

    private lateinit var locationSpinnerAdapter: LocationSpinnerAdapter
    private val locationsList = listOf(
        LocationData("0","Select Location"),
        LocationData("1","Margao"),
        LocationData("2","Panjim"),
        LocationData("3","Porvorim"),
        LocationData("4","Vasco Da Gama")
    )

    private lateinit var cinemaSpinnerAdapter: CinemaSpinnerAdapter
    private var cinemaList = listOf(
        CinemaData("0","Select Cinema"),
        CinemaData("1","Inox, KTC, Margo"),
        CinemaData("2","Inox, Old GMC, Panjim"),
        CinemaData("3","Inox, Mall de Goa, Porvorim"),
        CinemaData("4","PVR, 1933, Vasco")
    )

    private lateinit var seatSpinnerAdapter: SeatSpinnerAdapter
    private var seatList = listOf(
        SeatData("0","Select Seat"),
        SeatData("1","1A"),
        SeatData("2","2A"),
        SeatData("3","3A"),
        SeatData("4","4A"),
        SeatData("5","5A"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        setupDropDowns()
        observeBookingState()
        initOnClickListeners()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LocationDetailsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun setupDropDowns(){
        initLocationSpinner()
        initCinemaSpinner()
        initSeatCinemaSpinner()
    }

    private fun  initLocationSpinner(){

        locationSpinnerAdapter = LocationSpinnerAdapter(
            requireContext(),
            R.layout.spinner_item,
            locationsList
        )
        locationSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.locationSpinner.adapter = locationSpinnerAdapter

        locationSpinnerAdapter.updateList(locationsList)

        binding.locationSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onLocationSelected(locationsList[position])
            }

        }

    }

    private fun  initCinemaSpinner(){

        cinemaSpinnerAdapter = CinemaSpinnerAdapter(
            requireContext(),
            R.layout.spinner_item,
            cinemaList
        )
        cinemaSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.cinemaSpinner.adapter = cinemaSpinnerAdapter

        cinemaSpinnerAdapter.updateList(cinemaList)

        binding.cinemaSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onCinemaSelected(cinemaList[position])
            }

        }

    }

    private fun initSeatCinemaSpinner(){
        seatSpinnerAdapter = SeatSpinnerAdapter(
            requireContext(),
            R.layout.spinner_item,
            seatList
        )
        seatSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.seatSpinner.adapter = seatSpinnerAdapter

        seatSpinnerAdapter.updateList(seatList)

        binding.seatSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onSeatSelected(seatList[position])
            }

        }
    }

    private fun initOnClickListeners(){
        binding.confirmTicketCL.setOnClickListener {
            args.bookingSession?.let {
                viewModel.confirmBooking(it)
            }
        }
    }

    private fun observeBookingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bookingState.collect { state ->
                    when (state) {
                        is BookTicketUiState.Idle    -> showIdle()
                        is BookTicketUiState.Loading -> showLoading()
                        is BookTicketUiState.Success -> navigateToSuccess()
                        is BookTicketUiState.Error   -> showError(state.message)
                    }
                }
            }
        }
    }

    private fun showIdle() {
        binding.progressBar.visibility = View.GONE
        binding.confirmTicketCL.isEnabled = true
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.confirmTicketCL.isEnabled = false   // ← prevent double tap
    }

    private fun navigateToSuccess() {
        Toast.makeText(
            requireContext(),
            "Ticket booked successfully!",
            Toast.LENGTH_SHORT
        ).show()
        findNavController().popBackStack(R.id.moviesListingFragment,false)           // ← go back to movie detail
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.confirmTicketCL.isEnabled = true
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}