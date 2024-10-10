package com.csce5430sec7proj.pethelper.ui.pet_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.csce5430sec7proj.pethelper.databinding.FragmentPetDetailBinding
import com.csce5430sec7proj.pethelper.ui.Pet
import com.csce5430sec7proj.pethelper.ui.home.PetSharedViewModel

class PetDetailFragment : Fragment() {

    private var _binding: FragmentPetDetailBinding? = null
    private val binding get() = _binding!!

    private val petSharedViewModel: PetSharedViewModel by activityViewModels()
    private lateinit var currentPet: Pet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 获取导航传递过来的 Pet 对象
        currentPet = arguments?.getParcelable("pet") ?: return root // 使用 arguments 手动获取 Bundle 传递的 Pet 对象

        // 显示 Pet 详细信息
        binding.petName.setText(currentPet.name)
        binding.petSpecies.setText(currentPet.type.split("/")[0].trim()) // 假设种类和品种是 "species / breed" 格式
        binding.petBreed.setText(currentPet.type.split("/")[1].trim())
        binding.petDob.text = currentPet.age
        binding.petWeight.setText(currentPet.description.split(",")[0].split(":")[1].trim()) // 假设描述是 "Weight: x kg, ..."

        // 设置保存按钮点击事件，更新数据
        binding.savePetButton.setOnClickListener {
            updatePetData()
        }

        return root
    }

    private fun updatePetData() {
        val updatedPet = Pet(
            name = binding.petName.text.toString(),
            type = "${binding.petSpecies.text} / ${binding.petBreed.text}",
            age = binding.petDob.text.toString(),
            description = "Weight: ${binding.petWeight.text} kg, Neutered/Spayed: ${binding.petNeutered.isChecked}, Aggressiveness: ${binding.petAggressiveLevel.value.toInt()}"
        )

        // 更新 ViewModel 中的 Pet 列表
        petSharedViewModel.updatePet(currentPet, updatedPet)

        // 导航回 HomeFragment
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
