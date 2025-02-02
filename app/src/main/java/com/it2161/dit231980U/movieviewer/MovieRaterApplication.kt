package com.it2161.dit231980U.movieviewer


import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.it2161.dit231980U.movieviewer.data.UserProfile
import java.io.File

class MovieRaterApplication : Application() {
    companion object{
        lateinit var instance : MovieRaterApplication
            private set
    }

    var userProfile: UserProfile? = null
        get() = field
        set(value) {
            if (value != null) {
                field = value
                saveProfileToFile(applicationContext)
            }

        }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Loads Profile and Movie details
        loadData(applicationContext)
    }


    private fun saveProfileToFile(context: Context) {

        if (context != null) {
            val gson = Gson()
            val jsonString = gson.toJson(userProfile)
            val file = File(context.filesDir, "profile.dat")
            file.writeText(jsonString)
        }
    }

    private fun loadProfileFromFile(context: Context) {

        val file = File(context.filesDir, "profile.dat")
        userProfile =
            try {
                val jsonString = file.readText()
                val gson = Gson()
                val type = object :
                    TypeToken<UserProfile>() {}.type // TypeToken reflects the updated Movie class
                gson.fromJson(jsonString, type)
            } catch (e: Exception) {
                null
            }
    }

    private fun loadData(context: Context) {
        loadProfileFromFile(context)
    }



}