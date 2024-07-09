package com.example.nutri.ui.goals

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nutri.R
import com.example.nutri.databinding.FragmentGoalsBinding

class GoalsFragment : Fragment() {

    private var _binding: FragmentGoalsBinding? = null
    private val binding get() = _binding!!
    private lateinit var goalsViewModel: GoalsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        goalsViewModel = ViewModelProvider(this).get(GoalsViewModel::class.java)

        _binding = FragmentGoalsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Observe ViewModel LiveData and update UI accordingly
        goalsViewModel.calories.observe(viewLifecycleOwner) {
            binding.calories.text = "Under ${it} kcal"
        }

        goalsViewModel.sugar.observe(viewLifecycleOwner) {
            binding.sugar.text = "Under ${it} g"
        }

        goalsViewModel.sodium.observe(viewLifecycleOwner) {
            binding.sodium.text = "Under ${it} mg"
        }

        goalsViewModel.fat.observe(viewLifecycleOwner) {
            binding.fat.text = "Under ${it} g"
        }

        goalsViewModel.protein.observe(viewLifecycleOwner) {
            binding.protein.text = "Over ${it} g"
        }


        binding.buttonEditTargets.setOnClickListener {
            showEditInformationDialog()
        }

        return root
    }


    // Show a dialog to edit targets
    private fun showEditInformationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_goals, null)
        val caloriesEditText = dialogView.findViewById<EditText>(R.id.edit_calories)
        val sugarEditText = dialogView.findViewById<EditText>(R.id.edit_sugar)
        val sodiumEditText = dialogView.findViewById<EditText>(R.id.edit_sodium)
        val fatEditText = dialogView.findViewById<EditText>(R.id.edit_fat)
        val proteinEditText = dialogView.findViewById<EditText>(R.id.edit_protein)

        // Set current values in the dialog fields
        caloriesEditText.setText(goalsViewModel.calories.value.toString())
        sugarEditText.setText(goalsViewModel.sugar.value.toString())
        sodiumEditText.setText(goalsViewModel.sodium.value.toString())
        fatEditText.setText(goalsViewModel.fat.value.toString())
        proteinEditText.setText(goalsViewModel.protein.value.toString())


        AlertDialog.Builder(context)
            .setTitle(R.string.heading_targets)
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val calories = caloriesEditText.text.toString().toDouble()
                val sugar = sugarEditText.text.toString().toDouble()
                val sodium = sodiumEditText.text.toString().toDouble()
                val fat = fatEditText.text.toString().toDouble()
                val protein = proteinEditText.text.toString().toDouble()
                goalsViewModel.setCalories(calories)
                goalsViewModel.setSugar(sugar)
                goalsViewModel.setSodium(sodium)
                goalsViewModel.setFat(fat)
                goalsViewModel.setProtein(protein)

            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}