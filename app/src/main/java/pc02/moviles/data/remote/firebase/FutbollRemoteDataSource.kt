package pc02.moviles.data.remote.firebase

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import pc02.moviles.data.model.Equipo

object FutbollRemoteDataSource {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getEquipos(): Result<List<Equipo>> {
        return try {
            val snapshot = firestore.collection("futboll").get().await()
            val equipos = snapshot.documents.map { doc ->
                Equipo.fromDocument(doc)
            }
            Result.success(equipos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addEquipo(equipo: Equipo): Result<Unit> {
        return try {
            val data = mapOf(
                "nombreEquipo" to equipo.nombreEquipo,
                "anioFund" to equipo.anioFundacion,
                "Ntitulos" to equipo.titulosGanados.toString(),
                "Urlimagen" to equipo.imagenUrl
            )

            if (equipo.id.isEmpty()) {
                firestore.collection("futboll").add(data).await()
            } else {
                firestore.collection("futboll").document(equipo.id).set(data).await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateEquipo(equipo: Equipo): Result<Unit> {
        return try {
            if (equipo.id.isEmpty()) {
                return Result.failure(IllegalArgumentException("El ID del equipo no puede estar vacío"))
            }

            val updates = mapOf(
                "nombreEquipo" to equipo.nombreEquipo,
                "anioFund" to equipo.anioFundacion,
                "Ntitulos" to equipo.titulosGanados.toString(),
                "Urlimagen" to equipo.imagenUrl
            )

            firestore.collection("futboll")
                .document(equipo.id)
                .update(updates)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteEquipo(id: String): Result<Unit> {
        return try {
            firestore.collection("futboll")
                .document(id)
                .delete()
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
            }
        }
}