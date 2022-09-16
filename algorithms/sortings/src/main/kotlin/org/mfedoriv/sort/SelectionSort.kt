package org.mfedoriv.sort

import org.mfedoriv.swap

class SelectionSort : Sort<ArrayList<Int>>{

    override fun sort(array: ArrayList<Int>) {
        for (i in 0 until array.size) {
            var minIdx = i
            for (j in i + 1 until array.size) {
                if (array[j] < array[minIdx]) {
                    minIdx = j
                }
            }
            array.swap(minIdx, i)
        }
    }
}