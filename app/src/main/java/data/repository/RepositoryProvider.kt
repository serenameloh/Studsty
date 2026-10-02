package data.repository

import android.content.Context

object RepositoryProvider {

    private var repository: StudstyRepository? = null

    fun getRepository(context: Context): StudstyRepository {
        return repository ?: synchronized(this) {
            repository ?: StudstyRepository(
                context.applicationContext
            ).also {
                repository = it
            }
        }
    }
}