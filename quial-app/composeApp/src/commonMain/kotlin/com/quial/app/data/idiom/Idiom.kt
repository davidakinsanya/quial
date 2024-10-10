package com.quial.app.data.idiom

import kotlinx.serialization.Serializable

/**
 * This class represents the data linked to a specific idiom.
 *
 * @param info the general information behind an idiom.
 * @param meaning generalised meanings of the idiom.
 * @param exampleSentences examples of the idiom in a sentence.
 */
@Serializable
data class Idiom(val info: List<String>,
                 val meaning: List<String>,
                 val exampleSentences: List<String>)