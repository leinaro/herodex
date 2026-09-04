package com.leinaro.core

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BaseViewModelTest {

  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  private class TestViewModel : BaseViewModel<String>("initial")

  @Test fun `uiState starts at the default value`() {
    val subject = TestViewModel()

    assertEquals("initial", subject.uiState)
  }

  @Test fun `setValue updates uiState`() = runTest {
    val subject = TestViewModel()

    subject.setValue("updated")

    assertEquals("updated", subject.uiState)
  }
}
