package com.example.myapplication42

import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("gifs/trending")
    suspend fun fetchTrending(
        @Query("api_key") key: String = RetrofitInstance.API_KEY,
        @Query("limit") limit: Int = 20
    ): GiphyResponse

    @GET("gifs/search")
    suspend fun searchImages(
        @Query("q") searchQuery: String,
        @Query("api_key") key: String = RetrofitInstance.API_KEY,
        @Query("limit") limit: Int = 20
    ): GiphyResponse
}

data class GiphyResponse(
    val data: List<GifObject>,
    val pagination: JSONObject,
    val meta: JSONObject
)

data class GifObject(
    val type: String,
    val id: String,
    val images: GifImages
)

data class GifImages(
    val original: GifImage
)

data class GifImage(
    val url: String
)