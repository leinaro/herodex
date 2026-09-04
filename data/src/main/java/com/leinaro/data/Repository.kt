package com.leinaro.data

import com.leinaro.data.data.HeroData
import kotlinx.coroutines.flow.Flow

interface Repository {
  fun getCharacterDetails(characterId: Long): Flow<HeroData?>
  suspend fun fetchesListsOfCharacters(
    limit: Int,
    offset: Int,
    nameStartsWith: String? = null,
  ): List<HeroData>
}
