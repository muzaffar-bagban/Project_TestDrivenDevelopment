package com.muzaffar.testdrivendevelopment_project.repositories

import androidx.lifecycle.LiveData
import com.muzaffar.testdrivendevelopment_project.data.local.ShoppingDao
import com.muzaffar.testdrivendevelopment_project.data.local.ShoppingItem
import com.muzaffar.testdrivendevelopment_project.data.remote.PixabayApi
import com.muzaffar.testdrivendevelopment_project.data.remote.responses.ImageResponse
import com.muzaffar.testdrivendevelopment_project.other.Resource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayApi: PixabayApi
): ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayApi.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("An unknown error occurred", null)
            } else {
                return Resource.error("Couldn't fetch the data. try again", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }


}