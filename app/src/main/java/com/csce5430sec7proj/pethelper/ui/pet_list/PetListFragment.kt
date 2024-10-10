package com.csce5430sec7proj.pethelper.ui.pet_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.databinding.FragmentPetListBinding

// TODO: Deprecated. Remove.
class PetListFragment : Fragment() {

    private var _binding: FragmentPetListBinding? = null

    
    private val binding get() = _binding!!

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
