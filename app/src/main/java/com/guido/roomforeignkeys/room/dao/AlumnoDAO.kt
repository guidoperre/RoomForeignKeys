package com.guido.roomforeignkeys.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.AlumnoDatos
import com.guido.roomforeignkeys.entities.Cursos

@Dao
interface AlumnoDAO {

    @Query("SELECT * FROM alumno")
    @Transaction
    fun getAll(): LiveData<List<Alumno>>

    @Insert
    fun insertarAlumnoDatos(alumnoDatos: AlumnoDatos):Long

    @Insert
    fun insertarCurso(cursos: Cursos):Long

    @Delete
    @Transaction
    fun borrarAlumno(alumnoDatos: AlumnoDatos)

}