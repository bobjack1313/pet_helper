package com.csce5430sec7proj.pethelper.ui.pet_list

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.csce5430sec7proj.pethelper.databinding.FragmentAddPetBinding
import java.util.*

class AddPetFragment : Fragment() {

    private var _binding: FragmentAddPetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPetBinding.inflate(inflater, container, false)

        // 处理点击事件选择日期
        binding.petDob.setOnClickListener {
            showDatePicker()
        }

        // 处理保存按钮
        binding.savePetButton.setOnClickListener {
            savePet()
        }

        return binding.root
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.petDob.text = date
            }, year, month, day
        )

        datePickerDialog.show()
    }

    private fun savePet() {
        // 获取输入字段的值
        val name = binding.petName.text.toString()
        val species = binding.petSpecies.text.toString()
        val breed = binding.petBreed.text.toString()
        val dateOfBirth = binding.petDob.text.toString()
        val neutered = binding.petNeutered.isChecked
        val weight = binding.petWeight.text.toString()
        val aggressiveLevel = binding.petAggressiveLevel.value.toInt()

        // 简单的示例，实际情况下可以保存到数据库或发送到服务器
        Toast.makeText(context, "Pet Saved: $name", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
