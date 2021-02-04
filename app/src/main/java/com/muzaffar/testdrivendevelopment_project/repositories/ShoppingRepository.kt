package com.muzaffar.testdrivendevelopment_project.repositories

import androidx.lifecycle.LiveData
import com.muzaffar.testdrivendevelopment_project.data.local.ShoppingItem
import com.muzaffar.testdrivendevelopment_project.data.remote.responses.ImageResponse
import com.muzaffar.testdrivendevelopment_project.other.Resource
import retrofit2.Response

interface ShoppingRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>
    fun observeTotalPrice(): LiveData<Float>
    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}