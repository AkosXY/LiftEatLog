package hu.bme.aut.android.liftlog.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.liftlog.R
import hu.bme.aut.android.liftlog.data.LiftingItem
import hu.bme.aut.android.liftlog.databinding.DialogNewLiftingItemBinding

class NewLiftingItemDialogFragment : DialogFragment() {
    interface NewLiftingItemDialogListener {
        fun onLiftingItemCreated(newItem: LiftingItem)
    }

    private lateinit var listener: NewLiftingItemDialogListener

    private lateinit var binding: DialogNewLiftingItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewLiftingItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewLiftingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewLiftingItemBinding.inflate(LayoutInflater.from(context))
        binding.spExercise.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_lifting_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onLiftingItemCreated(getLiftingItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.etWeight.text.isNotEmpty()

    private fun getLiftingItem() = LiftingItem(
        weight = binding.etWeight.text.toString(),
        repetitions = binding.etReps.text.toString().toIntOrNull() ?: 1,
        exercise = LiftingItem.Category.getByOrdinal(binding.spExercise.selectedItemPosition)
            ?: LiftingItem.Category.DEADLIFT
    )


    companion object {
        const val TAG = "NewLiftingItemDialogFragment"
    }
}
