package com.leinaro.domain.mapper

import com.leinaro.data.data.ComicData
import com.leinaro.data.data.HeroData
import com.leinaro.domain.ui_models.CharacterUiModel
import com.leinaro.domain.ui_models.ComicUiModel
import org.junit.Assert
import org.junit.Test

internal class HeroDataMapperKtTest {
    @Test
    fun `HeroData to CharacterUiModel`() {
        // given
        val heroData = HeroData(
            id = 1234L,
            name = "Nova Sentinel",
            description = "description",
            thumbnailUrl = "thumbnail_url",
            landscapeUrl = "landscape_url",
            comics = listOf(
                ComicData(
                    name = "Nova Sentinel Vol.1 #1",
                    imageUrl = "comic_image_url",
                )
            ),
        )

        // when
        val subject = heroData.toUiModel()

        // then
        Assert.assertEquals(1234L, subject.id)
        Assert.assertEquals("Nova Sentinel", subject.name)
        Assert.assertEquals("thumbnail_url", subject.thumbnailUrl)
        Assert.assertEquals(1, subject.comics.size)
    }

    @Test
    fun `CharacterUiModel default constructor marker`() {
        // given

        // when
        val subject = CharacterUiModel()

        // then
        Assert.assertEquals(-1, subject.id)
        Assert.assertEquals("", subject.name)
        Assert.assertEquals("", subject.thumbnailUrl)
        Assert.assertEquals("", subject.description)
        Assert.assertEquals("", subject.landscapeUrl)
        Assert.assertEquals(listOf<ComicUiModel>(), subject.comics)
    }

    @Test
    fun `ComicUiModel default constructor marker`() {
        // given

        // when
        val subject = ComicUiModel()

        // then
        Assert.assertEquals("", subject.name)
        Assert.assertEquals("", subject.imageUrl)
    }
}
