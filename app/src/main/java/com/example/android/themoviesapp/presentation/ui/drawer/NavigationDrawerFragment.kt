package com.example.android.themoviesapp.presentation.ui.drawer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.themoviesapp.databinding.FragmentNavigationDrawerBinding
import com.example.android.themoviesapp.presentation.models.DrawerItem
import com.example.android.themoviesapp.presentation.ui.adapters.DrawerAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [NavigationDrawerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NavigationDrawerFragment : Fragment() {

    interface FragmentNavigationDrawerListener {
        fun onDrawerMenuClicked(item: DrawerItem.MenuItem)
    }

    private var listener: FragmentNavigationDrawerListener? = null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentNavigationDrawerBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentNavigationDrawerListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = _binding?: FragmentNavigationDrawerBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return _binding?.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRecyclerView() {
        val menuList = listOf<DrawerItem>(
            DrawerItem.Header,
            DrawerItem.MenuItem.BookedTicketHistory
        )

        // 4. Access views directly via 'binding'
        binding.drawerRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = DrawerAdapter(items = menuList, onItemClick = {
                listener?.onDrawerMenuClicked(it)
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NavigationDrawerFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}