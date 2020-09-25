package com.guido.roomforeignkeys.entities

import androidx.room.Embedded
import androidx.room.Relation

data class Alumno (

    @Embedded
    var datos: AlumnoDatos,

    @Relation(parentColumn = "id", entityColumn = "id_alumno")
    var cursos: List<Cursos>?

)