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
import edu.ucne.olylopez_ap2_p1.data.remote.UsersApi
import edu.ucne.olylopez_ap2_p1.data.remote.dto.UsersDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesTicketDatabase(@ApplicationContext appContext: Context): ServicioDb =
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
    fun providesServicioApi(moshi: Moshi): UsersApi {
        return Retrofit.Builder()
            .baseUrl("https://gestordomestico.somee.com/api/Tareas")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(UsersApi::class.java)
    }
}