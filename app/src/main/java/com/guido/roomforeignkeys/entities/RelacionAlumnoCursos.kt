package com.guido.roomforeignkeys.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "alumno-cursos",foreignKeys =
[ForeignKey(
    entity = Alumno::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id_alumno"),
    onDelete = ForeignKey.CASCADE
),ForeignKey(
    entity = Cursos::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id_curso"),
    onDelete = ForeignKey.CASCADE
)])
class RelacionAlumnoCursos (

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,

    @ColumnInfo(name = "id_curso")
    var idCurso:Long,

    @ColumnInfo(name = "id_alumno")
    var idAlumno:Long

)