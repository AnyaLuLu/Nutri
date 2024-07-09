package com.example.nutri.ui.addItem


import android.content.Intent // NEW
import android.os.Bundle
import android.util.Log // NEW
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button // NEW
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity // NEW
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nutri.BarcodeScannerActivity // NEW
import com.example.nutri.R
import com.example.nutri.databinding.FragmentAdditemBinding



class AddItemFragment : Fragment() {

    private var _binding: FragmentAdditemBinding? = null
    private val binding get() = _binding!!

    // START NEW
    companion object {
        private const val BARCODE_REQUEST_CODE = 1
        private const val TAG = "AddItemFragment"
    }
    // END

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val addItemViewModel =
            ViewModelProvider(this).get(AddItemViewModel::class.java)

        _binding = FragmentAdditemBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAddItem
        addItemViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.presetItemButton.setOnClickListener {
            addPresetItem()
        }

        binding.customItemButton.setOnClickListener {
            addCustomItem()
        }

        val scanButton: Button = binding.barcodeItemButton
        scanButton.setOnClickListener {
            val intent = Intent(activity, BarcodeScannerActivity::class.java)
            startActivityForResult(intent, BARCODE_REQUEST_CODE)
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BARCODE_REQUEST_CODE) {
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                val barcode = data.getStringExtra("barcode")
                Log.d(TAG, "Scanned barcode: $barcode")
            } else {
                Log.e(TAG, "Failed to scan barcode")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addPresetItem() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, AddPresetItemFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun addCustomItem() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, AddCustomItemFragment())
            .addToBackStack(null)
            .commit()
    }
}