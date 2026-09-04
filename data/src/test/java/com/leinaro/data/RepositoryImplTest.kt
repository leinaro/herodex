package com.leinaro.data

import app.cash.turbine.test
import com.leinaro.apis.data.HeroResponse
import com.leinaro.apis.services.CharactersServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {

  @MockK(relaxed = true)
  private lateinit var charactersServices: CharactersServices

  private lateinit var subject: Repository

  private val heroes = listOf(
    HeroResponse(
      id = 1L,
      name = "Nova Sentinel",
      description = "description",
      thumbnailUrl = "thumbnail_1",
      landscapeUrl = "landscape_1",
    ),
    HeroResponse(
      id = 2L,
      name = "Iron Falcon",
      description = "description",
      thumbnailUrl = "thumbnail_2",
      landscapeUrl = "landscape_2",
    ),
  )

  @Before fun setUp() {
    MockKAnnotations.init(this, relaxUnitFun = true)
    subject = RepositoryImpl(charactersServices)
  }

  @Test fun `Should get character successfully`() = runBlocking {
    // given
    coEvery { charactersServices.fetchAllCharacters() } returns heroes

    // when / then
    subject.getCharacterDetails(1L).test {
      val character = awaitItem()
      assert(character?.id == 1L)
      assert(character?.name == "Nova Sentinel")
      awaitComplete()
    }
    coVerify(exactly = 1) { charactersServices.fetchAllCharacters() }
  }

  @Test fun `Should return null when character is not found`() = runBlocking {
    // given
    coEvery { charactersServices.fetchAllCharacters() } returns heroes

    // when / then
    subject.getCharacterDetails(999L).test {
      assert(awaitItem() == null)
      awaitComplete()
    }
  }

  @Test fun `Should return a list of heroes`() = runBlocking {
    // given
    coEvery { charactersServices.fetchAllCharacters() } returns heroes

    // when
    val result = subject.fetchesListsOfCharacters(limit = 10, offset = 0)

    // then
    coVerify(exactly = 1) { charactersServices.fetchAllCharacters() }
    assert(result.size == 2)
    assert(result.first().id == 1L)
  }

  @Test fun `Should filter heroes by name`() = runBlocking {
    // given
    coEvery { charactersServices.fetchAllCharacters() } returns heroes

    // when
    val result = subject.fetchesListsOfCharacters(limit = 10, offset = 0, nameStartsWith = "Iron")

    // then
    assert(result.size == 1)
    assert(result.first().name == "Iron Falcon")
  }

  @Test fun `Should only fetch the roster once and cache it`() = runBlocking {
    // given
    coEvery { charactersServices.fetchAllCharacters() } returns heroes

    // when
    subject.fetchesListsOfCharacters(limit = 10, offset = 0)
    subject.fetchesListsOfCharacters(limit = 10, offset = 0)
    subject.getCharacterDetails(1L).test { awaitItem(); awaitComplete() }

    // then
    coVerify(exactly = 1) { charactersServices.fetchAllCharacters() }
  }

  @Test fun `Should return an empty list when no character was found`() = runBlocking {
    // given
    coEvery { charactersServices.fetchAllCharacters() } returns emptyList()

    // when
    val result = subject.fetchesListsOfCharacters(limit = 10, offset = 0)

    // then
    assert(result.isEmpty())
  }
}
