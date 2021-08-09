package com.jmachado.placeholdermenu.service

import com.jmachado.placeholdermenu.URL_API
import com.jmachado.placeholdermenu.model.Album
import com.jmachado.placeholdermenu.model.Post
import com.jmachado.placeholdermenu.model.Todos
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {
    @GET("posts")
    suspend fun getPosts(): Response<Array<Post>>

    @GET("albums")
    suspend fun getAlbums(): Response<Array<Album>>

    @GET("todos")
    suspend fun getTodos(): Response<Array<Todos>>

    companion object {

        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }

}