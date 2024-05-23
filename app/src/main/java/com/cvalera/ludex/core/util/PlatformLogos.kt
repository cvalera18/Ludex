package com.cvalera.ludex.core.util

import com.cvalera.ludex.R

object PlatformLogos {
    private const val BASE_URL = "https://images.igdb.com/igdb/image/upload/t_cover_small/"

    val platformLogoMap = mapOf(
        "${BASE_URL}plim.png" to R.drawable.icon_microsoft, // Windows
        "${BASE_URL}plmb.png" to R.drawable.icon_ps1, // PS1
        "${BASE_URL}pl72.png" to R.drawable.icon_ps2, // PS2 Blanco
        "${BASE_URL}tuyy1nrqodtmbqajp4jg.png" to R.drawable.icon_ps3, // PS3 Blanco
        "${BASE_URL}pl6f.png" to R.drawable.icon_ps4_white, // PS4 Blanco
        "${BASE_URL}plha.png" to R.drawable.icon_xbox_360, // Xbox 360
        "${BASE_URL}pl95.png" to R.drawable.icon_xbox_one, // Xbox One
        "${BASE_URL}plfl.png" to R.drawable.icon_xbox_white, // Xbox Series
        "${BASE_URL}plm3.png" to R.drawable.icon_ps5, // PS5
        "${BASE_URL}pll5.png" to R.drawable.icon_macos, // MacOs
        "${BASE_URL}pl92.png" to R.drawable.icon_wii, // Nintendo Wii
        "${BASE_URL}plgu.png" to R.drawable.icon_switch, //Nintendo Switch
        "${BASE_URL}pln3.png" to R.drawable.icon_android, //Android
        "${BASE_URL}pl6w.png" to R.drawable.icon_ios, // IOs
        "${BASE_URL}pl7a.png" to R.drawable.icon_gamecube, // Gamecube
        "${BASE_URL}pl6n.png" to R.drawable.icon_wii_u, // WiiU
        "${BASE_URL}plak.png" to R.drawable.icon_linux, // Linux
        // Añadir más plataformas según sea necesario
    )
}