package com.tbc.bookli.core.data.remote.repository

import com.google.firebase.auth.FirebaseAuth
import com.tbc.bookli.core.data.remote.safeFirebaseCall
import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class RegisterRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : RegisterRepository {

    override suspend fun registerUser(email: String, password: String): Flow<Resource<Unit>> {
        return safeFirebaseCall {
            auth.createUserWithEmailAndPassword(email, password).await()
        }

    }
}