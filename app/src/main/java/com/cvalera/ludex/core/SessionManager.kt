package com.cvalera.ludex.core

import android.content.Context
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val contextProvider: ContextProvider
) {

    companion object {
        private const val PREF_NAME = "user_session"
        private const val KEY_USER_ID = "user_id"
    }

    private val sharedPreferences by lazy {
        contextProvider.getContext()
            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun clearUserId() {
        sharedPreferences.edit().remove(KEY_USER_ID).apply()
    }
}
