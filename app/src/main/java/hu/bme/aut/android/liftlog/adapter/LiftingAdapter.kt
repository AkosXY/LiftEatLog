package hu.bme.aut.android.liftlog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.liftlog.R

import hu.bme.aut.android.liftlog.data.LiftingItem
import hu.bme.aut.android.liftlog.databinding.ItemLiftingListBinding

class LiftingAdapter(private val listener: LiftingItemClickListener) :
    RecyclerView.Adapter<LiftingAdapter.LiftingViewHolder>() {

    private val items = mutableListOf<LiftingItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LiftingViewHolder(
        ItemLiftingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: LiftingViewHolder, position: Int) {
        val liftingItem = items[position]

        holder.binding.ivIcon.setImageResource(getImageResource(liftingItem.exercise))
        holder.binding.tvWeight.text = "${liftingItem.weight} kg"
        holder.binding.tvExercise.text = liftingItem.exercise.name
        holder.binding.tvReps.text = "${liftingItem.repetitions} rep(s)"


        holder.binding.ibRemove.setOnClickListener(){
            listener.onLiftingItemDeleted(liftingItem)
        }
    }

    @DrawableRes()
    private fun getImageResource(category: LiftingItem.Category): Int {
        return when (category) {
            LiftingItem.Category.SQUAT -> R.drawable.squat
            LiftingItem.Category.BENCH -> R.drawable.benchpress
            LiftingItem.Category.DEADLIFT -> R.drawable.deadlift
        }
    }

    override fun getItemCount(): Int = items.size

    interface LiftingItemClickListener {
        fun onItemChanged(item: LiftingItem)
        fun onLiftingItemDeleted(newItem: LiftingItem)
    }

    inner class LiftingViewHolder(val binding: ItemLiftingListBinding) : RecyclerView.ViewHolder(binding.root)



    fun addItem(item: LiftingItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(liftingItems: List<LiftingItem>) {
        items.clear()
        items.addAll(liftingItems)
        notifyDataSetChanged()
    }

    fun delete(item: LiftingItem) {
        items.remove(item)
        notifyDataSetChanged()
    }


}
