package com.leinaro.data.mapper

import com.leinaro.apis.data.ComicResponse
import com.leinaro.apis.data.HeroResponse
import com.leinaro.data.data.ComicData
import com.leinaro.data.data.HeroData

@JvmName("heroResponseListToDomainModel")
internal fun List<HeroResponse>.toDomainModel(): List<HeroData> =
  this.map { heroResponse -> heroResponse.toDomainModel() }

internal fun HeroResponse.toDomainModel() = HeroData(
  id = this.id,
  name = this.name,
  description = this.description,
  thumbnailUrl = this.thumbnailUrl,
  landscapeUrl = this.landscapeUrl,
  comics = this.comics.toDomainModel(),
)

@JvmName("comicResponseListToDomainModel")
internal fun List<ComicResponse>.toDomainModel(): List<ComicData> =
  this.map { comicResponse -> comicResponse.toDomainModel() }

internal fun ComicResponse.toDomainModel() = ComicData(
  name = this.name,
  imageUrl = this.imageUrl,
)
