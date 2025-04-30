package com.tbc.bookli.core.data.remote.repository

import com.tbc.bookli.core.domain.Resource
import com.google.firebase.auth.FirebaseAuth
import com.tbc.bookli.core.data.remote.safeFirebaseCall
import com.tbc.bookli.core.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : LoginRepository {

    override suspend fun loginUser(email: String, password: String): Flow<Resource<Unit>> {
        return safeFirebaseCall {
            auth.signInWithEmailAndPassword(email, password).await()
        }
    }
}
