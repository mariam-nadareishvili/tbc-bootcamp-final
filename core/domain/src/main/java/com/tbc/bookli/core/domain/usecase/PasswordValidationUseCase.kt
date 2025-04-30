package com.tbc.bookli.core.domain.usecase

import javax.inject.Inject

class PasswordValidationUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }
}