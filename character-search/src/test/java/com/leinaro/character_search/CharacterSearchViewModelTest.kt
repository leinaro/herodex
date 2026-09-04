package com.leinaro.character_search

import androidx.paging.Pager
import com.leinaro.domain.ui_models.CharacterUiModel
import com.leinaro.domain.usecases.GetCharactersUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class CharacterSearchViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @MockK(relaxed = true)
  private lateinit var getCharactersUseCase: GetCharactersUseCase

  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule(testDispatcher)

  private lateinit var subject: CharacterSearchViewModel

  @Before fun setUp() {
    MockKAnnotations.init(this, relaxUnitFun = true)
    subject = CharacterSearchViewModel(testDispatcher, getCharactersUseCase)
  }

  @Test fun `Should get characters`() = runTest {
    // given
    val pager = mockk<Pager<Int, CharacterUiModel>>(relaxed = true)
    every { getCharactersUseCase.execute(any()) } returns pager

    // when
    subject.getCharacters("")

    // then
    verify(exactly = 1) { getCharactersUseCase.execute("") }
  }
}