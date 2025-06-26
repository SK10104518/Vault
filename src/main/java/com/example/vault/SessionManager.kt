package com.example.vault

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "BudgetTrackerPrefs"
        private const val USER_ID_KEY = "user_id"
    }

    //Saves the logged-in user's ID to SharedPreferences.
    fun saveSession(userId: Long) {
        prefs.edit() {
            putLong(USER_ID_KEY, userId)
        }
    }

    // Retrieves the logged-in user's ID.
    //@return The user ID if logged in, otherwise -1.

    fun getLoggedInUserId(): Long {
        return prefs.getLong(USER_ID_KEY, -1)
    }

     //Checks if a user is currently logged in.
    fun isLoggedIn(): Boolean {
        return getLoggedInUserId() != -1L
    }

     // Clears the session data, effectively logging the user out.
    fun logout() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}