package edu.ucne.olylopez_ap2_p1.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.olylopez_ap2_p1.data.local.entities.ServicioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {
    @Upsert()
    suspend fun save(servicio: ServicioEntity)

    @Query("""
        Select *
        From Servicios
        WHERE servicioId=:id
        LIMIT 1
        """)
    suspend fun find(id: Int): ServicioEntity?

    @Delete
    suspend fun delete(servicio: ServicioEntity)

    @Query("SELECT * FROM Servicios")
    fun getAll(): Flow<List<ServicioEntity>>
    @Query("DELETE FROM Servicios WHERE servicioId = :servicioId")
    suspend fun deleteById(servicioId: Int)
}