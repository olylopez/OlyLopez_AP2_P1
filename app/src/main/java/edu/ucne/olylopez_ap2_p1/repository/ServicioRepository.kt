package edu.ucne.olylopez_ap2_p1.repository

import edu.ucne.olylopez_ap2_p1.data.local.dao.ServicioDao
import edu.ucne.olylopez_ap2_p1.data.local.entities.ServicioEntity

class ServicioRepository(private val servicioDao: ServicioDao) {
    suspend fun saveServicio(servicio: ServicioEntity) = servicioDao.save(servicio)
    fun getServicio() = servicioDao.getAll()
    suspend fun getServicio(id: Int) = servicioDao.find(id)
    suspend fun deleteById(servicioId: Int) = servicioDao.deleteById(servicioId)
}