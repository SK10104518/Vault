package com.example.vault.ui.main // Use your actual package name

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vault.SessionManager
import com.example.vault.data.entities.Category
import com.example.vault.data.entities.Expense
import com.example.vault.databinding.FragmentAddExpenseBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddExpenseFragment : Fragment() {

    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    // Use activityViewModels to share the ViewModel between fragments
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var sessionManager: SessionManager

    // Properties to store user selections
    private var selectedDate = Calendar.getInstance()
    private var photoUri: Uri? = null

    // Launcher for the image picker
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            photoUri = it
            binding.imageViewPhotoPreview.setImageURI(it)
            binding.imageViewPhotoPreview.isVisible = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // These function calls will now work correctly
        setupCategorySpinner()
        setupDatePicker()

        binding.buttonAddPhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.buttonSave.setOnClickListener {
            saveExpense()
        }
    }

    private fun setupCategorySpinner() {
        // This will now work because 'allCategories' exists in the ViewModel
        viewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            // The compiler now knows 'categories' is a List<Category>
            val categoryNames = categories.map { it.name } // 'it' is now resolved
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNames)

            // 'setDropDownViewResource' is now resolved because 'adapter' is a valid ArrayAdapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter
        }
    }

    private fun setupDatePicker() {
        updateDateButtonText()
        binding.buttonDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateDateButtonText()
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateDateButtonText() {
        val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        binding.buttonDate.text = format.format(selectedDate.time)
    }

    private fun saveExpense() {
        val amountStr = binding.editTextAmount.text.toString()
        val description = binding.editTextDescription.text.toString()
        val selectedCategoryName = binding.spinnerCategory.selectedItem as? String

        if (amountStr.isBlank() || description.isBlank() || selectedCategoryName == null) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        // This whole block now works because 'allCategories' is resolved
        viewModel.allCategories.value?.find { it.name == selectedCategoryName }?.let { category ->
            // 'it' and 'category' types are known, so 'category.id' is resolved
            val newExpense = Expense(
                userId = sessionManager.getLoggedInUserId(),
                categoryId = category.id,
                description = description,
                amount = amount,
                date = selectedDate.timeInMillis,
                photoPath = photoUri?.toString()
            )

            viewModel.insertExpense(newExpense)
            Toast.makeText(context, "Expense Saved!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}