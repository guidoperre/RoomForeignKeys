package com.guido.roomforeignkeys.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.AlumnoDatos
import com.guido.roomforeignkeys.entities.Cursos

@Dao
interface AlumnoDAO {

    @Query("SELECT * FROM alumno")
    @Transaction
    fun getAll(): LiveData<List<Alumno>>

    @Insert
    @Transaction
    fun insertarAlumnoDatos(alumnoDatos: AlumnoDatos)

    @Insert
    @Transaction
    fun insertarCurso(cursos: Cursos)

}