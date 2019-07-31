package jp.three_colors.sampleroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import jp.three_colors.sampleroom.data.database.AddressRoomDatabase
import jp.three_colors.sampleroom.data.entity.Address
import jp.three_colors.sampleroom.data.repository.AddressRepository

class AddressViewModel(application: Application): AndroidViewModel(application) {
    private val repository: AddressRepository

    val allAddress: LiveData<List<Address>>
    val targetAddress: LiveData<Address>
    val errorMessage: LiveData<String>
    var zip1: String? = null
    var zip2: String? = null

    init {
        val addressDao = AddressRoomDatabase.getDatabase(application).addressDao()
        repository = AddressRepository(addressDao)
        allAddress = repository.allAddress
        targetAddress = repository.address
        errorMessage = repository.errorMessage
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun getAddress() {
        repository.getAddress(zip1, zip2)
    }
}