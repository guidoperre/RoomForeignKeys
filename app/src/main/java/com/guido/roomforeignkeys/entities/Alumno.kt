package com.guido.roomforeignkeys.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "alumno")
data class Alumno (

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,

    var nombre:String,

    var apellido:String,

    @Ignore
    var cursos: List<Cursos>? = null

){
    constructor():this(0L,"","",null)
}