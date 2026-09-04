package com.leinaro.apis.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// HeroDex's own mock API: a static JSON file hosted on GitHub Pages, no auth required.
private const val HERO_API_BASE_URL = "https://leinaro.github.io/herodex/api/"

@InstallIn(SingletonComponent::class)
@Module()
object NetworkModule {
  @Provides
  @Singleton
  fun providesRetrofit(
    client: OkHttpClient
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(HERO_API_BASE_URL)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return interceptor
  }

  @Provides
  @Singleton
  fun providesChuckerInterceptor(
    @ApplicationContext context: Context,
  ): ChuckerInterceptor {
    return ChuckerInterceptor.Builder(context).build()
  }

  @Provides
  @Singleton
  fun providesOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    chuckerInterceptor: ChuckerInterceptor,
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(httpLoggingInterceptor)
      .addInterceptor(chuckerInterceptor)
      .build()
  }
}
