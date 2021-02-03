package com.muzaffar.testdrivendevelopment_project.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.muzaffar.testdrivendevelopment_project.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.shoppingDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem =
            ShoppingItem(
                "name",
                45,
                89f,
                "url",
                id = 1
            )
        dao.insertShoppingItem(shoppingItem)
        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem =
            ShoppingItem(
                "name",
                45,
                89f,
                "url",
                id = 1
            )
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        for (i in 1..3) {
            val shoppingItem =
                ShoppingItem(
                    "name + $i",
                    10 * i,
                    10f * i,
                    "url",
                    id = i
                )
            dao.insertShoppingItem(shoppingItem)
        }

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo((10*10)+(20*20)+(30*30))

    }

    @Test
    fun observeTotalItemsInDatabase() = runBlockingTest {
        for (i in 1..3) {
            val shoppingItem =
                ShoppingItem(
                    "name$i",
                    10 * i,
                    10f * i,
                    "url",
                    id = i
                )
            dao.insertShoppingItem(shoppingItem)
        }

        val totalItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(totalItems.size).isEqualTo(3)

    }









}