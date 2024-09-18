package com.backend.quial.dto

import kotlinx.serialization.Serializable

@Serializable
data class IdiomList(private val list: List<Idiom>)
