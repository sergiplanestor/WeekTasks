package com.splanes.weektasks.data.common.utils

import android.content.res.AssetManager
import com.google.gson.Gson
import java.io.InputStreamReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <reified T> AssetManager.load(file: String): T =
    withContext(Dispatchers.IO) { InputStreamReader(open(file, AssetManager.ACCESS_BUFFER)) }.run {
        Gson().fromJson(this, T::class.java)
    }