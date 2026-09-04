package com.leinaro.domain.mapper

import com.leinaro.data.data.ComicData
import com.leinaro.data.data.HeroData
import com.leinaro.domain.ui_models.CharacterUiModel
import com.leinaro.domain.ui_models.ComicUiModel

internal fun HeroData.toUiModel() = CharacterUiModel(
    id = id,
    name = name,
    description = description,
    thumbnailUrl = thumbnailUrl,
    landscapeUrl = landscapeUrl,
    comics = comics.toUiModel()
)

internal fun ComicData.toUiModel() = ComicUiModel(
    name = name,
    imageUrl = imageUrl
)

internal fun List<ComicData>.toUiModel() = map { it.toUiModel() }
