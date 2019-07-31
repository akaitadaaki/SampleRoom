package jp.three_colors.sampleroom.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import jp.three_colors.sampleroom.data.entity.Address

@Dao
interface AddressDao {

    @Insert
    fun insert(address: Address)

    @Query("DELETE FROM address_table")
    fun deleteAll()

    @Query("select * from address_table")
    fun getAllAddress(): LiveData<List<Address>>

    @Query("select * from address_table where zip1 = :zip1 and zip2 = :zip2 limit 1")
    fun getAddress(zip1: String, zip2: String): List<Address>
}
