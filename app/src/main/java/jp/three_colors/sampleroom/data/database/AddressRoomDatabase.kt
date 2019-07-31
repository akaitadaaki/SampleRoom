package jp.three_colors.sampleroom.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jp.three_colors.sampleroom.data.dao.AddressDao
import jp.three_colors.sampleroom.data.entity.Address

@Database(entities = [Address::class], version = 1)
abstract class AddressRoomDatabase: RoomDatabase() {

    abstract fun addressDao(): AddressDao

    companion object {
        @Volatile
        private var INSTANCE: AddressRoomDatabase? = null

        fun getDatabase(context: Context): AddressRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AddressRoomDatabase::class.java,
                    "Word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}