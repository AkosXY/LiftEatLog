package hu.bme.aut.android.liftlog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LiftingItem::class], version = 1)
@TypeConverters(value = [LiftingItem.Category::class])
abstract class LiftingListDatabase : RoomDatabase() {
    abstract fun liftingItemDao(): LiftingItemDao

    companion object {
        fun getDatabase(applicationContext: Context): LiftingListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                LiftingListDatabase::class.java,
                "liftLog"
            ).build();
        }
    }
}
