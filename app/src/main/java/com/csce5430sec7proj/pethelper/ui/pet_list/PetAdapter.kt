package com.csce5430sec7proj.pethelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.csce5430sec7proj.pethelper.R

class PetAdapter(
    private val petList: MutableList<Pet>,
    private val onDeleteClick: (Pet) -> Unit
) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    inner class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val petPhoto: ImageView = itemView.findViewById(R.id.petPhoto)
        val petName: TextView = itemView.findViewById(R.id.petName)
        val deletePet: ImageView = itemView.findViewById(R.id.deletePet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pet, parent, false)
        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = petList[position]
        holder.petName.text = pet.name
        holder.petPhoto.setImageResource(pet.photoResId)
        holder.deletePet.setOnClickListener {
            onDeleteClick(pet)
        }
    }

    override fun getItemCount(): Int = petList.size

    fun moveItemUp(position: Int) {
        if (position > 0) {
            petList.add(position - 1, petList.removeAt(position))
            notifyItemMoved(position, position - 1)
        }
    }

    fun moveItemDown(position: Int) {
        if (position < petList.size - 1) {
            petList.add(position + 1, petList.removeAt(position))
            notifyItemMoved(position, position + 1)
        }
    }
}
