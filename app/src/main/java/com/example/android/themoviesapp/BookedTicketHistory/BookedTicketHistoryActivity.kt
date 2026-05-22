/*
package com.example.android.themoviesapp.BookedTicketHistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.themoviesapp.data.local.database.MoviesAppDatabase
import com.example.android.themoviesapp.Others.ApiPreferences
import com.example.android.themoviesapp.data.local.entities.BookedTicketHistoryTable
import com.example.android.themoviesapp.databinding.ActivityBookedTicketHistoryBinding
import com.example.android.themoviesapp.presentation.ui.adapters.BookedTicketHistoryAdapter

class BookedTicketHistoryActivity : AppCompatActivity() {

    private lateinit var binding:ActivityBookedTicketHistoryBinding

    private lateinit var bookedTicketHistoryAdapter: BookedTicketHistoryAdapter

    private var bookedTicketsList:ArrayList<BookedTicketHistoryTable> = arrayListOf()

    private lateinit var viewModel: BookedTicketHistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Apply padding to the toolbar specifically to account for the status bar height
        enableEdgeToEdge()
        binding = ActivityBookedTicketHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
      */
/*  ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(0, statusBarHeight, 0, 0)
            insets
        }
*//*

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            // Set the status bar background view height
            binding.statusBarBackground.layoutParams.height = statusBarHeight
            binding.statusBarBackground.requestLayout()

            // No padding on toolbar anymore since statusBarBackground handles the space
            insets
        }
        */
/*  ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
             val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
             view.setPadding(0, insets.top, 0, 0)
             windowInsets
         }*//*


        initializeToolbar()

        initializeRV()

        initializeViewModel()

        initializeObservers()

        getBookedTicketsList()
    }


    private fun initializeToolbar(){
        setSupportActionBar(binding.toolbar)
        binding.navIcon.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initializeRV(){
        binding.bookedTicketsRV.layoutManager = LinearLayoutManager(this)
        bookedTicketHistoryAdapter = BookedTicketHistoryAdapter(this,{})
        binding.bookedTicketsRV.adapter = bookedTicketHistoryAdapter
    }

    */
/*private fun initializeViewModel(){

        val application = requireNotNull(this).application
        val dataSource = MoviesAppDatabase.getInstance(application)
        val apiPreferences = ApiPreferences(application)
        val viewModelFactory = BookedTicketHistoryViewModelFactory(dataSource,apiPreferences)

        viewModel = ViewModelProvider(
            this, viewModelFactory).get(BookedTicketHistoryViewModel::class.java)
    }*//*


   */
/* private fun initializeObservers(){

        viewModel.bookedTicketList.observe(this,{

            if(it.isEmpty()){
                Toast.makeText(this,"You have not booked any tickets",Toast.LENGTH_SHORT).show()
            }else{
                //notify the adapter and display list in reverse order
                    val reversedBookedTicketsList = it.sortedByDescending {it._id  }
                bookedTicketHistoryAdapter.updateList(reversedBookedTicketsList)
            }
        })
    }*//*


    private fun getBookedTicketsList(){

        viewModel.getBookedTicketsList()
    }
}*/
