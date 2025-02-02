package com.it2161.dit231980U.movieviewer

//import com.it2161.dit99999x.movieviewer.data.Comments
//import com.it2161.dit99999x.movieviewer.data.MovieItem
//import com.it2161.dit99999x.movieviewer.data.UserProfile
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.it2161.dit231980U.movieviewer.data.UserProfile
import com.it2161.dit231980U.movieviewer.data.mvBeneathTheSurface
import com.it2161.dit231980U.movieviewer.data.mvCityOfShadowsData
import com.it2161.dit231980U.movieviewer.data.mvEchosOfEternityData
import com.it2161.dit231980U.movieviewer.data.mvIntoTheUnknownData
import com.it2161.dit231980U.movieviewer.data.mvLostInTimeData
import com.it2161.dit231980U.movieviewer.data.mvShadowsOfthePastData
import com.it2161.dit231980U.movieviewer.data.mvTheLastFrontierData
import com.it2161.dit231980U.movieviewer.data.mvTheSilentStormData
import jsonData
import org.json.JSONArray
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

    //Gets image vector.
    fun getImgVector(fileName: String): Bitmap {

        val dataValue = when (fileName) {

            "IntoTheUnknown" -> mvIntoTheUnknownData
            "EchosOfEternity" -> mvEchosOfEternityData
            "LostInTime" -> mvLostInTimeData
            "ShadowsOfThePast" -> mvShadowsOfthePastData
            "BeneathTheSurface" -> mvBeneathTheSurface
            "LastFrontier" -> mvTheLastFrontierData
            "CityOfShadows" -> mvCityOfShadowsData
            "SilentStorm" -> mvTheSilentStormData
            else -> ""

        }

        val imageBytes = Base64.decode(
            dataValue,
            Base64.DEFAULT
        )
        val imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        return imageBitmap
    }


}