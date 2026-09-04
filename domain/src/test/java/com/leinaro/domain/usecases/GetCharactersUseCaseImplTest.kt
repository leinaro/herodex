package com.leinaro.domain.usecases

import com.leinaro.domain.CharactersSource
import com.leinaro.domain.CharactersSourceFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class GetCharactersUseCaseImplTest {

  @MockK
  private lateinit var charactersSourceFactory: CharactersSourceFactory

  private lateinit var useCase: GetCharactersUseCase

  @Before fun setUp() {
    MockKAnnotations.init(this, relaxUnitFun = true)
    useCase = GetCharactersUseCaseImpl(charactersSourceFactory)
  }

  @Test fun `should return element when execute use case`() = runBlocking {
    // given
    val charactersSource = mockk<CharactersSource>(relaxed = true)
    every { charactersSourceFactory.createCharactersSource() } returns charactersSource

    // when
    val subject = useCase.execute()


        // then
      //  verify (exactly = 1){ charactersSourceFactory.createCharactersSource() }
    }
}
