package com.leinaro.data

import com.leinaro.apis.services.CharactersServices
import com.leinaro.data.data.HeroData
import com.leinaro.data.mapper.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * HeroDex's mock API is a single static JSON file with the full roster (see /docs/api),
 * so pagination and name filtering happen here, client-side, over a response fetched once
 * and cached in memory for the lifetime of the process.
 */
@Singleton
class RepositoryImpl @Inject constructor(
  private val charactersServices: CharactersServices,
) : Repository {

  private var cachedHeroes: List<HeroData>? = null

  private suspend fun heroes(): List<HeroData> =
    cachedHeroes ?: charactersServices.fetchAllCharacters().toDomainModel().also {
      cachedHeroes = it
    }

  override fun getCharacterDetails(characterId: Long): Flow<HeroData?> = flow {
    val hero = heroes().firstOrNull { it.id == characterId }
    emit(hero)
  }

  override suspend fun fetchesListsOfCharacters(
    limit: Int,
    offset: Int,
    nameStartsWith: String?,
  ): List<HeroData> {
    return heroes()
      .filter { nameStartsWith.isNullOrBlank() || it.name.startsWith(nameStartsWith, ignoreCase = true) }
      .drop(offset)
      .take(limit)
  }
}
