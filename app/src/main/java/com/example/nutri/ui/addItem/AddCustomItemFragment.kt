package com.example.nutri.ui.addItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.nutri.database.NutriDatabase
import com.example.nutri.database.entity.Food
import com.example.nutri.database.entity.ServingUnit
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.nutri.R
import androidx.lifecycle.lifecycleScope
import com.example.nutri.MainActivity
import com.example.nutri.databinding.FragmentAddcustomitemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class AddCustomItemFragment : Fragment() {

    private lateinit var foodNameEditText: EditText
    private lateinit var servingUnitSpinner: Spinner
    private lateinit var servingSizeEditText: EditText
    private lateinit var caloriesEditText: EditText
    private lateinit var fatEditText: EditText
    private lateinit var sugarEditText: EditText
    private lateinit var sodiumEditText: EditText
    private lateinit var proteinEditText: EditText
    private lateinit var userNotesEditText: EditText
    private lateinit var continueButton: Button


    private var _binding: FragmentAddcustomitemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddcustomitemBinding.inflate(inflater, container, false)
        val root: View = binding.root

        foodNameEditText = binding.etFoodName
        servingUnitSpinner = binding.spinnerServingUnit
        servingSizeEditText = binding.etServingSize
        caloriesEditText = binding.etCalories
        fatEditText = binding.etFat
        sugarEditText = binding.etSugar
        sodiumEditText = binding.etSodium
        proteinEditText = binding.etProtein
        userNotesEditText = binding.etUserNotes
        continueButton = binding.btnContinue

        // Set up the spinner with options and select the current value
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.custom_item_unit_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            servingUnitSpinner.adapter = adapter
        }


        (activity as? MainActivity)?.getBinding()!!.navView.visibility = View.GONE

        continueButton.setOnClickListener {
            val foodName = foodNameEditText.text.toString()
            val servingUnit = ServingUnit.values()[servingUnitSpinner.selectedItemPosition]
            println("dfjasdoifj fjo jfiwej ojf iow joew ijo " + servingUnit)
            val servingSize = servingSizeEditText.text.toString().toDoubleOrNull() ?: 0.0
            val calories = caloriesEditText.text.toString().toDoubleOrNull() ?: 0.0
            val fat = fatEditText.text.toString().toDoubleOrNull() ?: 0.0
            val sugar = sugarEditText.text.toString().toDoubleOrNull() ?: 0.0
            val sodium = sodiumEditText.text.toString().toDoubleOrNull() ?: 0.0
            val protein = proteinEditText.text.toString().toDoubleOrNull() ?: 0.0
            val userNotes = userNotesEditText.text.toString()
            println("FOOD's NAME " + foodName)
            val food = Food(
                name = foodName,
                servingUnit = servingUnit,
                servingSize = servingSize,
                calories = calories,
                fat = fat,
                sugar = sugar,
                sodium = sodium,
                protein = protein,
                nutritionFacts = "Nutrition Facts", // You can update this field if you have nutrition facts data
                userNotes = userNotes,
                thumbnail = "fake.png" // Update this field if you have thumbnail data
            )

            lifecycleScope.launch(Dispatchers.IO) {
                val database = NutriDatabase.getDatabase(requireContext(), lifecycleScope)
                println("inserted " + food.name)
                database.foodDao().insert(food)
                println("Items in the database " + database.foodDao().getCount())
                // Close the activity/fragment after inserting the data (this needs to be done on the main thread)
                launch(Dispatchers.Main) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, AddItemFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.getBinding()!!.navView.visibility = View.VISIBLE
        _binding = null
    }
}
