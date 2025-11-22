package pc02.moviles.data.repository

import pc02.moviles.data.model.Equipo
import pc02.moviles.data.remote.firebase.FutbollRemoteDataSource

class EquipoRepository(
    private val remoteDataSource: FutbollRemoteDataSource = FutbollRemoteDataSource
) {

    suspend fun obtenerEquipos(): Result<List<Equipo>> =
        remoteDataSource.getEquipos()

    suspend fun registrarEquipo(equipo: Equipo): Result<Unit> =
        remoteDataSource.addEquipo(equipo)

    suspend fun actualizarEquipo(equipo: Equipo): Result<Unit> =
        remoteDataSource.updateEquipo(equipo)

    suspend fun eliminarEquipo(id: String): Result<Unit> =
        remoteDataSource.deleteEquipo(id)
}

