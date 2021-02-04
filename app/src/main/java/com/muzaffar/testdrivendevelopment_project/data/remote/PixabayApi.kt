package com.muzaffar.testdrivendevelopment_project.data.remote

import com.muzaffar.testdrivendevelopment_project.BuildConfig
import com.muzaffar.testdrivendevelopment_project.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>

}