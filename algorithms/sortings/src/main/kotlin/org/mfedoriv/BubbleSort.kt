package org.mfedoriv

class BubbleSort : Sort<ArrayList<Int>> {

    override fun sort(array: ArrayList<Int>): ArrayList<Int> {
        for (i in 0 until array.size) {
            for (j in 1 until array.size - i) {
                if (array[j - 1] > array[j]) {
                    array.swap(j - 1, j)
                }
            }
        }
        return array
    }
}