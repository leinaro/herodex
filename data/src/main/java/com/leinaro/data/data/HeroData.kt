package com.leinaro.data.data

data class HeroData(
  val id: Long,
  val name: String,
  val description: String,
  val thumbnailUrl: String,
  val landscapeUrl: String,
  var comics: List<ComicData> = listOf(),
)

data class ComicData(
  val name: String,
  val imageUrl: String,
)
