package ar.edu.unlam.mobile.scaffolding.evolution

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ScaffoldingApplication :
    Application(),
    ImageLoaderFactory { // prueba de cacheo de imágenes para agilizar los tiempos de carga entre pantallas
    override fun newImageLoader(): ImageLoader =
        ImageLoader
            .Builder(this)
            .diskCache {
                DiskCache
                    .Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02) // Caché hasta 2% del tamaño del almacenamiento
                    .build()
            }.build() // eliminar esto si no se notan cambios relevantes en el renderizado de imagenes.
}
