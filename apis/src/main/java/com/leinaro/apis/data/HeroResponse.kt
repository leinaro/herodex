package com.leinaro.apis.data

/**
 * Shape returned by HeroDex's own mock API (a static JSON file, see /docs/api in the repo
 * root, served over GitHub Pages). No auth, no query params: the client fetches the full
 * roster once and paginates/filters locally.
 */
data class HeroResponse(
  val id: Long,
  val name: String,
  val description: String,
  val thumbnailUrl: String,
  val landscapeUrl: String,
  val comics: List<ComicResponse> = emptyList(),
)

data class ComicResponse(
  val id: Long,
  val name: String,
  val imageUrl: String,
)
