package com.leinaro.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ResultTest {

  @Test fun `Success carries its data and Success status`() {
    val subject = Result.Success("hero")

    assertEquals("hero", subject.data)
    assertEquals(ApiStatus.Success, subject.status)
    assertNull(subject.message)
  }

  @Test fun `Error carries its exception, message and Error status`() {
    val exception = IllegalStateException("boom")
    val subject = Result.Error(exception, "boom")

    assertEquals(exception, subject.exception)
    assertEquals("boom", subject.message)
    assertEquals(ApiStatus.Error, subject.status)
    assertNull(subject.data)
  }

  @Test fun `Loading carries its isLoading flag and Loading status`() {
    val subject = Result.Loading<String>(isLoading = true)

    assertEquals(true, subject.isLoading)
    assertEquals(ApiStatus.Loading, subject.status)
    assertNull(subject.data)
    assertNull(subject.message)
  }
}
