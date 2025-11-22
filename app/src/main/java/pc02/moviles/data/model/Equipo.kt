package pc02.moviles.data.model

import com.google.firebase.firestore.DocumentSnapshot

data class Equipo(
    val id: String = "",
    val nombreEquipo: String = "",
    val anioFundacion: Int = 0,
    val titulosGanados: Int = 0,
    val imagenUrl: String = ""
) {
    companion object {
        fun fromDocument(snapshot: DocumentSnapshot): Equipo {
            return Equipo(
                id = snapshot.id,
                nombreEquipo = snapshot.getString("nombreEquipo") ?: "",
                anioFundacion = snapshot.getLong("anioFund")?.toInt() ?: 0,
                titulosGanados = snapshot.getString("Ntitulos")?.toIntOrNull() ?: 0,
                imagenUrl = snapshot.getString("Urlimagen") ?: ""
            )
        }
    }
}