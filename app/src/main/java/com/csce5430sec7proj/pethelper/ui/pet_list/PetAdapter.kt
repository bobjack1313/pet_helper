package com.csce5430sec7proj.pethelper.ui.pet_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.csce5430sec7proj.pethelper.R
import com.csce5430sec7proj.pethelper.ui.Pet

class PetAdapter(private var petList: List<Pet>) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    class PetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.pet_name)
        val typeTextView: TextView = view.findViewById(R.id.pet_type)
        val ageTextView: TextView = view.findViewById(R.id.pet_age)
        val descriptionTextView: TextView = view.findViewById(R.id.pet_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pet_item, parent, false)
        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = petList[position]
        holder.nameTextView.text = pet.name
        holder.typeTextView.text = pet.type
        holder.ageTextView.text = "Age: ${pet.age}"
        holder.descriptionTextView.text = pet.description
    }

    override fun getItemCount(): Int = petList.size

    // 更新适配器的数据并刷新
    fun updatePetList(newPetList: List<Pet>) {
        petList = newPetList
        notifyDataSetChanged()
    }
}
