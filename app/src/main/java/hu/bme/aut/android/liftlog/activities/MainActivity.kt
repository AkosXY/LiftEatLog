package hu.bme.aut.android.liftlog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.liftlog.adapter.LiftingAdapter
import hu.bme.aut.android.liftlog.data.LiftingItem
import hu.bme.aut.android.liftlog.data.LiftingListDatabase
import hu.bme.aut.android.liftlog.databinding.ActivityMainBinding
import hu.bme.aut.android.liftlog.fragments.NewLiftingItemDialogFragment
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), LiftingAdapter.LiftingItemClickListener,
    NewLiftingItemDialogFragment.NewLiftingItemDialogListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var database: LiftingListDatabase
    private lateinit var adapter: LiftingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        database = LiftingListDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            NewLiftingItemDialogFragment().show(
                supportFragmentManager,
                NewLiftingItemDialogFragment.TAG
            )
        }


        initRecyclerView()
    }


    private fun initRecyclerView() {
        adapter = LiftingAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)

        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.liftingItemDao().getAll().reversed()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: LiftingItem) {
        thread {
            database.liftingItemDao().update(item)
            Log.d("MainActivity", "LiftLog update was successful")
        }
    }

    override fun onLiftingItemCreated(newItem: LiftingItem) {

        thread {
            val insertId = database.liftingItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
            loadItemsInBackground()
        }
    }

    override fun onLiftingItemDeleted(newItem: LiftingItem) {
        thread {
            database.liftingItemDao().deleteItem(newItem)

            runOnUiThread {
                adapter.delete(newItem)
            }
        }
    }


}