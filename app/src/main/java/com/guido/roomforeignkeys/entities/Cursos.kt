package com.guido.roomforeignkeys.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cursos")
data class Cursos (

    @PrimaryKey(autoGenerate = true)
    var id:Long,

    var nombre:String,

    var dificultad:String

)