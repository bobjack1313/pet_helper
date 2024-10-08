package com.csce5430sec7proj.pethelper.ui.pet_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.ui.Pet

class PetAdapter(
    private var petList: List<Pet>,
    private val onItemClick: (Pet) -> Unit // 添加点击事件的 Lambda 表达式
) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    class PetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.pet_name)
        val typeTextView: TextView = view.findViewById(R.id.pet_type)
        val ageTextView: TextView = view.findViewById(R.id.pet_age)
        val descriptionTextView: TextView = view.findViewById(R.id.pet_description)

        // 将 Pet 对象和点击事件绑定到视图
        fun bind(pet: Pet, onItemClick: (Pet) -> Unit) {
            nameTextView.text = pet.name
            typeTextView.text = pet.type
            ageTextView.text = "Age: ${pet.age}"
            descriptionTextView.text = pet.description

            // 为 itemView 设置点击事件，将 pet 对象传递给 onItemClick 函数
            itemView.setOnClickListener {
                onItemClick(pet)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pet_item, parent, false)
        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = petList[position]
        holder.bind(pet, onItemClick) // 传递 pet 和 onItemClick 函数
    }

    override fun getItemCount(): Int = petList.size

    fun updatePetList(newPetList: List<Pet>) {
        petList = newPetList
        notifyDataSetChanged()
    }
}
