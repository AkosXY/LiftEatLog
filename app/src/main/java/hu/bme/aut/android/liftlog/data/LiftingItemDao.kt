package hu.bme.aut.android.liftlog.data

import androidx.room.*

@Dao
interface LiftingItemDao {
    @Query("SELECT * FROM liftingitem")
    fun getAll(): List<LiftingItem>

    @Insert
    fun insert(liftingItems: LiftingItem): Long

    @Update
    fun update(liftingItem: LiftingItem)

    @Delete
    fun deleteItem(liftingItem: LiftingItem)
}
