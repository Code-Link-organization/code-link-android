package com.ieee.codelink.utils

import com.ieee.codelink.common.DateProvider
import com.ieee.codelink.common.getCurrentUtcDateTime
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.getTimeDifference
import com.ieee.codelink.common.toggle
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.text.SimpleDateFormat
import java.util.TimeZone

class UtilsClassTest {

    lateinit var dateProvider: DateProvider

    @Before
    fun setUp() {
        val fixedDateStr = "2023-09-16T12:00:00Z"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val fixedDate = dateFormat.parse(fixedDateStr)
        val dateProviderMock = mock(DateProvider::class.java)
        `when`(dateProviderMock.getCurrentDate()).thenReturn(fixedDate)
        dateProvider = dateProviderMock
    }

    @Test
    fun testGetCurrentUtcDateTime() {
        val expectedDateStr = "2023-09-16T12:00:00Z"

        val result = getCurrentUtcDateTime(dateProvider)

        assertEquals(expectedDateStr, result)
    }


    @Test
    fun testTimeDifference() {

        val testCases = listOf(
            Pair("2023-09-16T12:00:00.000000Z", "now"),
            Pair("2023-09-15T12:00:00.000000Z", "1 d"),
            Pair("2023-09-09T12:00:00.000000Z", "1 w"),
            Pair("2023-08-16T12:00:00.000000Z", "1 months"),
            Pair("2022-09-16T12:00:00.000000Z", "1 y"),
            Pair("2023-09-16T11:30:00.000000Z", "30 m")
        )


        for ((dateStr, expected) in testCases) {
            val actual = getTimeDifference(dateStr, dateProvider)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun testToggle() {
        // Test 1: Toggle true to false
        val trueValue = true
        val toggledTrue = trueValue.toggle()
        assertEquals(false, toggledTrue)

        // Test 2: Toggle false to true
        val falseValue = false
        val toggledFalse = falseValue.toggle()
        assertEquals(true, toggledFalse)
    }

    @Test
    fun testGetImageForGlideWithUrl() {
        val url = "image.jpg"
        val baseUrl = "https://example.com/"
        val expected = "https://example.com/image.jpg"

        val result = getImageForGlide(url, baseUrl)

        assertEquals(expected, result)
    }

    @Test
    fun testGetImageForGlideWithNullUrl() {
        val url: String? = null
        val baseUrl = "https://example.com/"

        val result = getImageForGlide(url, baseUrl)

        assertEquals(null, result)
    }

}