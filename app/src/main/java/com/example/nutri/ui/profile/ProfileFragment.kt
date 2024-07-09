package com.example.nutri.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nutri.R
import com.example.nutri.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Observe ViewModel LiveData and update UI accordingly
        profileViewModel.restrictions.observe(viewLifecycleOwner) {
            updateRestrictions(it)
        }

        profileViewModel.height.observe(viewLifecycleOwner) {
            binding.height.text = "${it} cm"
        }

        profileViewModel.weight.observe(viewLifecycleOwner) {
            binding.weight.text = "${it} kg"
        }

        profileViewModel.sex.observe(viewLifecycleOwner) {
            binding.sex.text = it
        }

        // Set up button click listeners
        binding.buttonEditRestrictions.setOnClickListener {
            enableRestrictionsEditing()
        }

        binding.buttonConfirmRestrictions.setOnClickListener {
            confirmRestrictionsEditing()
        }

        binding.buttonEditInformation.setOnClickListener {
            showEditInformationDialog()
        }

        return root
    }

    // Update the restrictions list UI
    private fun updateRestrictions(restrictions: List<String>) {
        binding.restrictionsLayout.removeAllViews()
        for (restriction in restrictions) {
            val restrictionTextView = TextView(context).apply {
                text = restriction
                textSize = 18f
                setTextColor(ContextCompat.getColor(context, R.color.black))
                setPadding(0, 0, 0, 16)
            }
            binding.restrictionsLayout.addView(restrictionTextView)
        }
    }

    // Enable restrictions editing mode
    private fun enableRestrictionsEditing() {
        // Show confirm button and hide edit button
        binding.buttonEditRestrictions.visibility = View.GONE
        binding.buttonConfirmRestrictions.visibility = View.VISIBLE

        // Clear current restrictions and set up editable fields
        binding.restrictionsLayout.removeAllViews()
        val restrictions = profileViewModel.restrictions.value ?: emptyList()
        for (restriction in restrictions) {
            val restrictionLayout = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
            }
            val restrictionTextView = TextView(context).apply {
                text = restriction
                textSize = 18f
                setTextColor(ContextCompat.getColor(context, R.color.black))
                setPadding(0, 0, 0, 16)
            }
            val removeButton = Button(context).apply {
                text = "Remove"
                textSize = 14f
                setOnClickListener {
                    profileViewModel.removeRestriction(restriction)
                    enableRestrictionsEditing() // Refresh UI to update the list
                }
            }
            restrictionLayout.addView(restrictionTextView)
            restrictionLayout.addView(removeButton)
            binding.restrictionsLayout.addView(restrictionLayout)
        }
        val addButton = Button(context).apply {
            text = "Add Restriction"
            textSize = 14f
            setOnClickListener {
                showAddRestrictionDialog()
            }
        }
        binding.restrictionsLayout.addView(addButton)
    }

    // Confirm the changes to the restrictions list
    private fun confirmRestrictionsEditing() {
        // Hide confirm button and show edit button
        binding.buttonEditRestrictions.visibility = View.VISIBLE
        binding.buttonConfirmRestrictions.visibility = View.GONE

        // Update restrictions list in the ViewModel and update the UI
        updateRestrictions(profileViewModel.restrictions.value ?: emptyList())
    }

    // Show a dialog to add a new restriction
    private fun showAddRestrictionDialog() {
        val editText = EditText(context)
        AlertDialog.Builder(context)
            .setTitle("New Restriction")
            .setView(editText)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                profileViewModel.addRestriction(editText.text.toString())
                enableRestrictionsEditing() // Refresh UI to update the list
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
            .show()
    }

    // Show a dialog to edit user information
    private fun showEditInformationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_info, null)
        val heightEditText = dialogView.findViewById<EditText>(R.id.edit_height)
        val weightEditText = dialogView.findViewById<EditText>(R.id.edit_weight)
        val sexSpinner = dialogView.findViewById<Spinner>(R.id.edit_sex)

        // Set current values in the dialog fields
        heightEditText.setText(profileViewModel.height.value.toString())
        weightEditText.setText(profileViewModel.weight.value.toString())

        // Set up the spinner with options and select the current value
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sex_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sexSpinner.adapter = adapter
        }


        val currentSex = profileViewModel.sex.value
        if (currentSex == "Male") {
            sexSpinner.setSelection(0)
        } else {
            sexSpinner.setSelection(1)
        }

        AlertDialog.Builder(context)
            .setTitle(R.string.heading_information)
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val height = heightEditText.text.toString().toDouble()
                val weight = weightEditText.text.toString().toDouble()
                profileViewModel.setHeight(height)
                profileViewModel.setWeight(weight)
                profileViewModel.setSex(sexSpinner.selectedItem.toString())
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