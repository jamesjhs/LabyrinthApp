package com.jahosi.labyrinth

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppVersionTest {
    @Test
    fun phaseOneVersionStartsAtDocumentedValue() {
        assertEquals(100, BuildConfig.VERSION_CODE)
        assertTrue(BuildConfig.VERSION_NAME.startsWith("0.1.0"))
    }
}
