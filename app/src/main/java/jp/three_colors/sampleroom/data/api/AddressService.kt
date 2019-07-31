package jp.three_colors.sampleroom.data.api

import jp.three_colors.sampleroom.data.entity.ApiAddress
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val API_SERVER_URL = "http://api.thni.net/"
interface AddressService {
    @GET("jzip/X0401/JSON/{zip1}/{zip2}.js")
    fun getAddress(@Path("zip1") zip1: String, @Path("zip2") zip2: String): Call<ApiAddress>
}