package com.example.android_bootcamp.data.remote.repository

import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.data.remote.safeFirebaseCall
import com.example.android_bootcamp.domain.repository.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
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