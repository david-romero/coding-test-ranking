package com.idealista.domain

data class Description(val content: String) {

    val keyWordsOccurrences = countKeyWordsOccurrences()

    val words = countWords()

    fun isPresent() = !content.isBlank()

    private fun countKeyWordsOccurrences(): Int {
        return keyWords.filter {
            content.toLowerCase().contains(it)
        }.count()
    }

    private fun countWords() = content.split(" ").size

    companion object {

        fun empty() = Description("")

        private val keyWords = arrayOf("luminoso", "nuevo", "céntrico", "reformado", "ático")

    }

}