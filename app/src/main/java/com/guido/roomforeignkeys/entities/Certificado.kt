package com.guido.roomforeignkeys.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO: Implementar foreign key en cascada
@Entity(tableName = "certificados")
data class Certificado (

    @PrimaryKey(autoGenerate = true)
    var id:Long,

    var nombre:String,

    var tipo:String

)