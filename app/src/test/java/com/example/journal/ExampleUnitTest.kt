package com.example.journal

import com.example.journal.speech.ui.matchForEnglish
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun testMatch(){
       matchForEnglish("I will make my dog walk tomorrow people are going")
    }
}