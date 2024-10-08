package com.csce5430sec7proj.pethelper.ui.home

import android.os.Bundle
import com.csce5430sec7proj.pethelper.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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

        // 初始化空的适配器，并添加点击事件
        petAdapter = PetAdapter(listOf()) { pet ->
            // 使用 Bundle 传递 Pet 对象
            val bundle = Bundle().apply {
                putParcelable("pet", pet)
            }
            findNavController().navigate(R.id.petDetailFragment, bundle)
        }
        recyclerView.adapter = petAdapter

        // 监听 petList 数据的变化并更新适配器
        petSharedViewModel.petList.observe(viewLifecycleOwner, Observer { petList ->
            petAdapter = PetAdapter(petList) { pet ->
                // 使用 Bundle 传递 Pet 对象
                val bundle = Bundle().apply {
                    putParcelable("pet", pet)
                }
                findNavController().navigate(R.id.petDetailFragment, bundle)
            }
            recyclerView.adapter = petAdapter
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
