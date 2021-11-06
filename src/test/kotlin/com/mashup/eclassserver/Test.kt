package com.mashup.eclassserver

import org.junit.jupiter.api.Test

class Test {
    @Test
    fun test() {
        val stickerRepoList = listOf<Long>(1, 2, 3)
        val attachedList = listOf<Long>(1, 2, 3, 4)
        val filterList = attachedList.filterNot {
            it in stickerRepoList
        }.count()

        println(filterList)
    }
}