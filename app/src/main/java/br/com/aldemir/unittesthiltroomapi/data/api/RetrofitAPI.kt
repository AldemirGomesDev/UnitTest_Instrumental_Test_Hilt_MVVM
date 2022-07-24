package br.com.aldemir.unittesthiltroomapi.data.api

import br.com.aldemir.unittesthiltroomapi.model.ImageResponse
import br.com.aldemir.unittesthiltroomapi.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey : String = API_KEY
    ) : Response<ImageResponse>
}