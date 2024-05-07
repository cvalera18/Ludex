package com.cvalera.ludex.data.network

//import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor() {

    val auth: FirebaseAuth get() = FirebaseAuth.getInstance()
//    val db = Firebase.firestore

}