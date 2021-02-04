package com.muzaffar.testdrivendevelopment_project.di

import android.content.Context
import androidx.room.Room
import com.muzaffar.testdrivendevelopment_project.data.local.ShoppingDao
import com.muzaffar.testdrivendevelopment_project.data.local.ShoppingItemDatabase
import com.muzaffar.testdrivendevelopment_project.data.remote.PixabayApi
import com.muzaffar.testdrivendevelopment_project.other.Constants.BASE_URL
import com.muzaffar.testdrivendevelopment_project.other.Constants.DATABASE_NAME
import com.muzaffar.testdrivendevelopment_project.repositories.DefaultShoppingRepository
import com.muzaffar.testdrivendevelopment_project.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayApi
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository

    @Singleton
    @Provides
    fun providesShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayApi::class.java)
    }

}