package com.guido.roomforeignkeys.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "cursos",foreignKeys = [ForeignKey(
    entity = AlumnoDatos::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id_alumno"),
    onDelete = ForeignKey.CASCADE
)])
data class Cursos (

    @PrimaryKey(autoGenerate = true)
    var id:Long,

    @ColumnInfo(name = "id_alumno")
    var idAlumno:Long,

    @ColumnInfo(name = "nombre_curso")
    var nombreCurso:String,

    @ColumnInfo(name = "dificultad_curso")
    var dificultadCurso:String

)