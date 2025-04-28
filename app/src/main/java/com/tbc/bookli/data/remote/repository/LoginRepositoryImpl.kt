package com.tbc.bookli.data.remote.repository

import com.tbc.bookli.common.Resource
import com.tbc.bookli.data.remote.safeFirebaseCall
import com.tbc.bookli.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
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
