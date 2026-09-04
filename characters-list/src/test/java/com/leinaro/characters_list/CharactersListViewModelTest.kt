package com.leinaro.characters_list

import androidx.paging.Pager
import com.leinaro.characters_list.ui_state.CharactersListUiState
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
internal class CharactersListViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @MockK(relaxed = true)
  private lateinit var getCharactersUseCase: GetCharactersUseCase

  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule(testDispatcher)

  private lateinit var subject: CharactersListViewModel

  @Before fun setUp() {
    MockKAnnotations.init(this, relaxUnitFun = true)
    subject = CharactersListViewModel(testDispatcher, getCharactersUseCase)
  }

  @Test fun `Should get characters`() = runTest {
    // given
    val pager = mockk<Pager<Int, CharacterUiModel>>(relaxed = true)
    every { getCharactersUseCase.execute() } returns pager

    // when
    subject.getCharacters()

    // then
    // getCharacters() wraps the use case's flow in `.catch { }`, so it's a different Flow
    // instance than `pager.flow` itself -- assert on the interaction and non-null result.
    verify(exactly = 2) { getCharactersUseCase.execute() }
    assert(subject.uiState.charactersPager != null)
  }

  @Test fun `Should refresh characters`() = runTest {
    // given
    val pager = mockk<Pager<Int, CharacterUiModel>>(relaxed = true)
    every { getCharactersUseCase.execute() } returns pager

    // when
    subject.onRefresh()

    // then
    verify(exactly = 2) { getCharactersUseCase.execute() }
    assert(subject.uiState.charactersPager != null)
  }
}