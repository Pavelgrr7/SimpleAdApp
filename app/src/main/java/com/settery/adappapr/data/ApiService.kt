package com.settery.adappapr.data

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    // todo endpoint
    @POST("your_endpoint")
    suspend fun getContent(@Body request: ContentRequest): ApiResponse
}

data class ContentRequest(
    val user: String,
    val key: String,
    val tab: String // "tab1" или "tab2"
)