package com.leinaro.apis.services

import com.leinaro.apis.data.HeroResponse
import retrofit2.http.GET

interface CharactersServices {

  @GET("heroes.json")
  suspend fun fetchAllCharacters(): List<HeroResponse>
}
