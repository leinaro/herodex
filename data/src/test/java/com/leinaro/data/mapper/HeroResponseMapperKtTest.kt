package com.leinaro.data.mapper

import com.leinaro.apis.data.ComicResponse
import com.leinaro.apis.data.HeroResponse
import org.junit.Assert
import org.junit.Test

class HeroResponseMapperKtTest {

  @Test
  fun `ComicResponse to ComicData`() {
    // given
    val comicResponse = ComicResponse(
      id = 1234,
      name = "Nova Sentinel Vol.1 #1",
      imageUrl = "https://leinaro.github.io/herodex/api/comics/1234.jpg",
    )

    // when
    val subject = comicResponse.toDomainModel()

    // then
    Assert.assertEquals("Nova Sentinel Vol.1 #1", subject.name)
    Assert.assertEquals("https://leinaro.github.io/herodex/api/comics/1234.jpg", subject.imageUrl)
  }

  @Test
  fun `HeroResponse to HeroData`() {
    // given
    val heroResponse = HeroResponse(
      id = 1234,
      name = "Nova Sentinel",
      description = "A guardian forged from a dying star.",
      thumbnailUrl = "https://api.dicebear.com/9.x/bottts/svg?seed=nova-sentinel",
      landscapeUrl = "https://api.dicebear.com/9.x/bottts/svg?seed=nova-sentinel-wide",
      comics = listOf(
        ComicResponse(id = 1, name = "Nova Sentinel Vol.1 #1", imageUrl = "https://example.com/1.jpg")
      ),
    )

    // when
    val subject = heroResponse.toDomainModel()

    // then
    Assert.assertEquals(1234, subject.id)
    Assert.assertEquals("Nova Sentinel", subject.name)
    Assert.assertEquals("A guardian forged from a dying star.", subject.description)
    Assert.assertEquals("https://api.dicebear.com/9.x/bottts/svg?seed=nova-sentinel", subject.thumbnailUrl)
    Assert.assertEquals("https://api.dicebear.com/9.x/bottts/svg?seed=nova-sentinel-wide", subject.landscapeUrl)
    Assert.assertEquals(1, subject.comics.size)
  }

  @Test
  fun `List of HeroResponse to List of HeroData`() {
    // given
    val heroResponseList = listOf(
      HeroResponse(
        id = 1234,
        name = "Nova Sentinel",
        description = "description",
        thumbnailUrl = "thumbnail_url",
        landscapeUrl = "landscape_url",
      )
    )

    // when
    val subject = heroResponseList.toDomainModel()

    // then
    Assert.assertEquals(1234, subject.first().id)
    Assert.assertEquals("Nova Sentinel", subject.first().name)
    Assert.assertEquals("description", subject.first().description)
    Assert.assertEquals("thumbnail_url", subject.first().thumbnailUrl)
    Assert.assertEquals("landscape_url", subject.first().landscapeUrl)
  }
}
