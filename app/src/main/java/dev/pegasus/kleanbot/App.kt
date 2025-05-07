package dev.pegasus.kleanbot

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

/**
 * Created by: Sohaib Ahmed
 * Date: 5/5/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}