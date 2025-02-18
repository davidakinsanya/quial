package com.backend.quial.dao.intrface

import com.backend.quial.dto.Idiom

interface Topics {

    fun pushTopic(topic: String): Boolean

    fun getTopics(): List<String>

    fun getIdiomsByTopic(topic: String): List<Idiom>
}