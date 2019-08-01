package jp.three_colors.sampleroom.data.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import jp.three_colors.sampleroom.data.api.API_SERVER_URL
import jp.three_colors.sampleroom.data.api.AddressService
import jp.three_colors.sampleroom.data.dao.AddressDao
import jp.three_colors.sampleroom.data.entity.Address
import jp.three_colors.sampleroom.data.entity.ApiAddress
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddressRepository(private val addressDao: AddressDao) {
    val allAddress: LiveData<List<Address>> = addressDao.getAllAddress()
    private val _address: MutableLiveData<Address> = MutableLiveData()
    private var _errorMessage: MutableLiveData<String> = MutableLiveData()
    val address: LiveData<Address> = _address
    val errorMessage: LiveData<String> = _errorMessage


    private val addressService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(AddressService::class.java)
    }

    fun insert(address: Address) {
        InsertAsyncTask(addressDao).execute(address)
    }

    fun deleteAll() {
        DeleteAllAsyncTask(addressDao).execute()
    }

    fun getAddress(zip1: String?, zip2: String?) {
        _errorMessage.postValue(null)
        if( zip1.isNullOrBlank() || zip2.isNullOrBlank() ){
            _errorMessage.postValue("郵便番号を指定してください")
        } else {
            val asyncTask = GetAddressAsyncTask(addressDao)
            asyncTask.delegate = this
            asyncTask.execute(zip1, zip2)
        }
    }

    fun asyncFinished(address: Address) {
        _address.postValue(address)
    }

    fun getAddressFromNetwork(zip1: String, zip2: String) {
        addressService.getAddress(zip1, zip2).enqueue(object : Callback<ApiAddress> {
            override fun onResponse(call: Call<ApiAddress>, response: Response<ApiAddress>) {
                if( response.isSuccessful ) {
                    val apiAddress = response.body()!!
                    val address = Address(zip1=zip1, zip2=zip2, apiAddress = apiAddress)
                    insert(address)
                    _address.postValue(address)
                } else {
                    _errorMessage.postValue("対象の住所が見つかりませんでした")
                }
            }

            override fun onFailure(call: Call<ApiAddress>, t: Throwable) {
                _errorMessage.postValue("ネットワーク通信でエラーが発生しました")
            }
        })
    }

    class InsertAsyncTask(addressDao: AddressDao): AsyncTask<Address, Void, Void>() {
        private val asyncTaskDao: AddressDao = addressDao
        override fun doInBackground(vararg params: Address): Void? {
            asyncTaskDao.insert(params[0])
            return null
        }
    }

    class DeleteAllAsyncTask(addressDao: AddressDao): AsyncTask<Void, Void, Void>() {
        private val asyncTaskDao: AddressDao = addressDao
        override fun doInBackground(vararg params: Void?): Void? {
            asyncTaskDao.deleteAll()
            return null
        }
    }

    class GetAddressAsyncTask(addressDao: AddressDao): AsyncTask<String, Void, Void>() {
        private val asyncTaskDao: AddressDao = addressDao
        lateinit var delegate: AddressRepository

        override fun doInBackground(vararg params: String): Void? {
            val addressList = asyncTaskDao.getAddress(params[0], params[1])

            if(addressList.size > 0) {
                delegate.asyncFinished(addressList[0])
            } else {
                delegate.getAddressFromNetwork(params[0], params[1])
            }

            return null
        }
    }
}

