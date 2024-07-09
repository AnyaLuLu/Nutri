package com.example.nutri.ui.addItem

import android.app.AlertDialog
import android.content.Context
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.*
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.nutri.R
import com.example.nutri.databinding.FragmentAddpresetitemBinding
import com.example.nutri.database.NutriDatabase
import com.example.nutri.database.entity.Ate
import com.example.nutri.database.entity.Food
import com.example.nutri.databinding.FragmentAddcustomitemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class AddPresetItemFragment : Fragment() {

    private var _binding: FragmentAddpresetitemBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private lateinit var searchView: SearchView
    private val binding get() = _binding!!
    private var foodList: List<Food> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddpresetitemBinding.inflate(inflater, container, false)
        val root: View = binding.root

        searchView = binding.searchView

        val database = NutriDatabase.getDatabase(requireContext(), CoroutineScope(SupervisorJob() + Dispatchers.Main))

        fetchFoodItems(database)

        setupSearchView()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAddFoodDialog(foodId: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_food, null)
        val numServingsEditText = dialogView.findViewById<EditText>(R.id.enter_amount)

        // Set current values in the dialog fields
        //heightEditText.setText(profileViewModel.height.value.toString())
        //weightEditText.setText(profileViewModel.weight.value.toString())

        AlertDialog.Builder(context)
            .setTitle(R.string.heading_information)
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->

                val time = OffsetDateTime.now()
                val numServings = numServingsEditText.text.toString().toDouble()

                val ate = Ate(
                    foodId = foodId,
                    time = time,
                    numServings = numServings
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    val database = NutriDatabase.getDatabase(requireContext(), lifecycleScope)
                    database.ateDao().insert(ate)
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
            .show()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = foodList.filter {
                    it.name.contains(newText ?: "", ignoreCase = true)
                }
                generateFoodButtons(filteredList)
                return true
            }
        })
    }


    private fun fetchFoodItems(database: NutriDatabase) {
        lifecycleScope.launch(Dispatchers.IO) {
            println("Items in the database " + database.foodDao().getCount())
        }
        lifecycleScope.launch {
            println("retrieved some food info from the database")
            database.foodDao().getAll().collect { foods ->
                foodList = foods
                generateFoodButtons(foodList)
            }
        }
    }

    private fun generateFoodButtons(foods: List<Food>) {
        binding.selectLayout.removeAllViews()
        for (food in foods) {
            val button = Button(requireContext()).apply {
                text = food.name
                id = food.id
                setOnClickListener {
                    showAddFoodDialog(id)
                }
            }
            binding.selectLayout.addView(button)
        }
    }


}

//TODO @Aaron MAKE THIS USE A MVVM
//TODO @Aaron HIDE THE BOTTOM NAV MENU WHEN YOU ARE ADDING ITEM
//TODO refactor code to look like anya's,