package com.leinaro.characters_list

import android.util.Log
import androidx.paging.Pager
import com.leinaro.domain.ui_models.CharacterUiModel
import com.leinaro.domain.usecases.GetCharactersUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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

  @Test fun `Should log and swallow errors from the characters flow`() = runTest {
    // given
    val pager = mockk<Pager<Int, CharacterUiModel>>(relaxed = true)
    every { pager.flow } returns flow { throw RuntimeException("boom") }
    every { getCharactersUseCase.execute() } returns pager
    mockkStatic(Log::class)
    every { Log.e(any(), any()) } returns 0

    // when
    subject.getCharacters()
    // the ViewModel only builds the flow, it doesn't collect it -- collect it ourselves,
    // same as the UI would via collectAsLazyPagingItems(), to actually trigger .catch { }
    subject.uiState.charactersPager?.collect {}

    // then: collecting doesn't crash, the failure is logged instead
    verify(atLeast = 1) { Log.e(any(), any()) }
  }
}
