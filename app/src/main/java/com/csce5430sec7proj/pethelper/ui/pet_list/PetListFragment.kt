package com.csce5430sec7proj.pethelper.ui.pet_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.databinding.FragmentPetListBinding
import com.csce5430sec7proj.pethelper.ui.Pet
import com.csce5430sec7proj.pethelper.ui.home.PetSharedViewModel

// TODO: Deprecated. Remove.
class PetListFragment : Fragment() {

    private var _binding: FragmentPetListBinding? = null
    private val binding get() = _binding!!

    
    private val petSharedViewModel: PetSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        
        binding.addPetButton.setOnClickListener {
            findNavController().navigate(R.id.action_petListFragment_to_addPetFragment)
        }

        

        return root
    }

   
    private fun addNewPet(name: String, type: String) {
        val newPet = Pet(name, type)
        petSharedViewModel.addPet(newPet)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
