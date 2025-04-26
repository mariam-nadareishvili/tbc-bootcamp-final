package com.example.android_bootcamp.data.remote.repository

import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.data.remote.safeFirebaseCall
import com.example.android_bootcamp.domain.repository.LoginRepository
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
