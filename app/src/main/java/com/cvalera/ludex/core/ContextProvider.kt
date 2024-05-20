package com.cvalera.ludex.core

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getContext(): Context = context
}