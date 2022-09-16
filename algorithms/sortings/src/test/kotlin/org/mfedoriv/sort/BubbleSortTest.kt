package org.mfedoriv.sort

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BubbleSortTest {

    private val sorting = BubbleSort()

    @Test
    fun `Sorting a sorted list (best case)`() {
        val array = arrayListOf<Int>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        val sortedArray = arrayListOf<Int>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        sorting.sort(array)
        assertEquals(array, sortedArray)
    }

    @Test
    fun `Sorting a sorted in descending order list (worst case)`() {
        val array = arrayListOf<Int>(9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
        val sortedArray = arrayListOf<Int>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        sorting.sort(array)
        assertEquals(array, sortedArray)
    }

    @Test
    fun `Sorting an unsorted list (worst case)`() {
        val array = arrayListOf<Int>(6, 3, 7, 1, 5, 8, 9, 2, 0, 4)
        val sortedArray = arrayListOf<Int>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        sorting.sort(array)
        assertEquals(array, sortedArray)
    }

}