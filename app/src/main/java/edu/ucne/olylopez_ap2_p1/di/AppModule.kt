package edu.ucne.olylopez_ap2_p1.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.olylopez_ap2_p1.data.local.database.ServicioDb
import edu.ucne.olylopez_ap2_p1.data.remote.TareasApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesServicioDatabase(@ApplicationContext appContext: Context): ServicioDb =
        Room.databaseBuilder(
            appContext,
            ServicioDb::class.java,
            "Servicio.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesServicioDao(db: ServicioDb) = db.servicioDao()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesTareaApi(moshi: Moshi): TareasApi {
        return Retrofit.Builder()
            .baseUrl("https://gestordomestico.somee.com/api/Tareas")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TareasApi::class.java)
    }
}