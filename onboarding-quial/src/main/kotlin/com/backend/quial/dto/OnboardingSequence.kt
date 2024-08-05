package com.backend.quial.dto

import kotlinx.serialization.Serializable

/**
 * This interface bundles the different elements used in an onboarding sequence.
 *
 * @author David.
 */
@Serializable
sealed interface OnboardingSequence {

    /**
     * This method returns a string object for the type of element in the onboarding sequence.
     *
     * @return a String representing a sequence element.
     */
    fun getSequenceType(): String

    /**
     * This method returns a string object for the header of a Statement object of the onboarding sequence.
     *
     * @return a String representing a header.
     */
    fun getHeader(): String?

    /**
     * This method returns a string object for the question of a Question object of the onboarding sequence.
     *
     * @return a String representing a question.
     */
    fun getQuestion(): String?

    /**
     * This method returns a string object for the text of a Statement object of the onboarding sequence.
     *
     * @return a String representing text.
     */
    fun getText(): String?

    /**
     * This method returns a list object for the answer options of a Question object of the onboarding sequence.
     *
     * @return a list object representing a list of answer options.
     */
    fun getOptions(): List<String>?
}