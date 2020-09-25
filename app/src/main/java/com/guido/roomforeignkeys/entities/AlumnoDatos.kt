package com.guido.roomforeignkeys.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alumno")
data class AlumnoDatos (

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,

    var nombre:String,

    var apellido:String

)