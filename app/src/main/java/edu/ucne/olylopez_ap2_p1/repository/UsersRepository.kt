package edu.ucne.olylopez_ap2_p1.repository

import edu.ucne.olylopez_ap2_p1.data.remote.UsersApi
import edu.ucne.olylopez_ap2_p1.data.remote.dto.UsersDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val usersApi: UsersApi
) {
    suspend fun getUsers(): Flow<Resource<List<UsersDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = usersApi.getUsers()
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}
