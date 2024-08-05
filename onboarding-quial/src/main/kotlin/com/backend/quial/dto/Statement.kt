package com.backend.quial.dto

import kotlinx.serialization.Serializable

@Serializable
data class Statement(
    val enum: String = OnboardingEnums.STATEMENT.name,
    val header: String,
    val text: String): OnboardingSequence {

    /**
     * This method returns a string object for the type of element in the onboarding sequence.
     *
     * @return a String representing a sequence element.
     */
    override fun getSequenceType(): String {
       return this.enum
    }

    /**
     * This method returns a string object for the header of a Statement object of the onboarding sequence.
     *
     * @return a String representing a header.
     */
    override fun getHeader(): String {
       return this.header
    }

    /**
     * This method returns a string object for the question of a Question object of the onboarding sequence.
     *
     * @return a String representing a question.
     */
    override fun getQuestion(): String? {
        return null
    }

    /**
     * This method returns a string object for the text of a Statement object of the onboarding sequence.
     *
     * @return a String representing text.
     */
    override fun getText(): String {
        return this.text
    }

    /**
     * This method returns a list object for the answer options of a Question object of the onboarding sequence.
     *
     * @return a list object representing a list of answer options.
     */
    override fun getOptions(): List<String>? {
        return null
    }
}
