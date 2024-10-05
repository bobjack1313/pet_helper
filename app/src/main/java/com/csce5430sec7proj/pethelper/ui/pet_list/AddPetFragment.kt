package com.csce5430sec7proj.pethelper.ui.pet_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.databinding.FragmentAddPetBinding
import com.csce5430sec7proj.pethelper.ui.Pet
import com.csce5430sec7proj.pethelper.ui.home.PetSharedViewModel

class AddPetFragment : Fragment() {

    private var _binding: FragmentAddPetBinding? = null
    private val binding get() = _binding!!

    // 使用 activityViewModels() 获取共享的 ViewModel 实例
    private val petSharedViewModel: PetSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 设置 Save 按钮点击事件
        binding.savePetButton.setOnClickListener {
            savePetData()
        }

        return root
    }

    private fun savePetData() {
        // 获取用户输入的数据
        val petName = binding.petName.text.toString()
        val petSpecies = binding.petSpecies.text.toString()
        val petBreed = binding.petBreed.text.toString()
        val petWeight = binding.petWeight.text.toString()
        val isNeuteredOrSpayed = binding.petNeutered.isChecked
        val petAggressiveness = binding.petAggressiveLevel.value.toInt()

        // 检查是否所有字段都已填写
        if (petName.isNotEmpty() && petSpecies.isNotEmpty() && petBreed.isNotEmpty() && petWeight.isNotEmpty()) {
            // 创建一个 Pet 数据对象
            val newPet = Pet(
                name = petName,
                type = "$petSpecies / $petBreed", // 组合种类和品种
                age = "Unknown", // 示例值
                description = "Weight: $petWeight kg, Neutered/Spayed: $isNeuteredOrSpayed, Aggressiveness: $petAggressiveness"
            )

            // 将新宠物数据添加到 ViewModel 中
            petSharedViewModel.addPet(newPet)

            // 导航回 HomeFragment
            findNavController().navigate(R.id.action_addPetFragment_to_homeFragment)
        } else {
            // 显示提示信息，告知用户需要填写所有字段
            // 可以用 Toast 或 Snackbar 提示
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
