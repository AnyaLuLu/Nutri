package com.example.nutri.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.nutri.database.NutriDatabase
import com.example.nutri.database.entity.Ate
import com.example.nutri.database.entity.User
import com.example.nutri.database.repository.NutriRepository
import com.example.nutri.databinding.FragmentHomeBinding
import com.example.nutri.ui.home.HomeViewModel
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize UserViewModel
        val application = requireNotNull(this.activity).application

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
