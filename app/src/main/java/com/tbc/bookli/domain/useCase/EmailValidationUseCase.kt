package com.tbc.bookli.domain.useCase

import javax.inject.Inject

class EmailValidationUseCase @Inject constructor() {
    operator fun invoke(email: String): Boolean {
        return email.isNotBlank() && EMAIL_REGEX.matches(email)
    }

    companion object {
        private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    }
}