package com.example.hhru

class Validation {
    fun formatVacancies(vacancies: Int): String {
        return when (vacancies % 10) {
            1 -> "$vacancies вакансия"
            2, 3, 4 -> "$vacancies вакансии"
            else -> "$vacancies вакансий"
        }
    }

    fun formatLookingNumber(lookingNumber: Int): String {
        return when (lookingNumber % 10) {
            1 -> "$lookingNumber человек"
            2, 3, 4 -> "$lookingNumber человека"
            else -> "$lookingNumber человек"
        }
    }
}