package com.csce5430sec7proj.pethelper.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csce5430sec7proj.pethelper.databinding.FragmentHomeBinding
import com.csce5430sec7proj.pethelper.ui.Pet
import com.csce5430sec7proj.pethelper.ui.pet_list.PetAdapter

// TODO: Deprecated. Remove.
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val petSharedViewModel: PetSharedViewModel by activityViewModels()
    private lateinit var petAdapter: PetAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 初始化 RecyclerView
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 初始化适配器并设置给 RecyclerView
        petAdapter = PetAdapter(listOf())
        recyclerView.adapter = petAdapter

        // 监听 petList 数据的变化并更新适配器的数据
        petSharedViewModel.petList.observe(viewLifecycleOwner, Observer { petList ->
            petAdapter.updatePetList(petList)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
