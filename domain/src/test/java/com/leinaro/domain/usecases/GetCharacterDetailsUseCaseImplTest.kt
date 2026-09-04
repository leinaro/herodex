package com.leinaro.domain.usecases

import app.cash.turbine.test
import com.leinaro.data.Repository
import com.leinaro.data.data.HeroData
import com.leinaro.domain.mapper.toUiModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class GetCharacterDetailsUseCaseImplTest {
    @MockK(relaxed = true)
    private lateinit var repository: Repository

    private lateinit var subject: GetCharacterDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        subject = GetCharacterDetailsUseCaseImpl(repository)
    }

    @Test
    fun `Should get character details successfully`() = runBlocking {
        // given
        val heroData = mockk<HeroData>(relaxed = true)
        every { repository.getCharacterDetails(1234L) } returns flow {
            emit(heroData)
        }

        // when / then
        subject.execute(1234L).test {
            val characterUiModel = awaitItem()
            assertEquals(heroData.toUiModel(), characterUiModel)
            awaitComplete()
        }
        verify(exactly = 1) { repository.getCharacterDetails(1234L) }
    }
}
