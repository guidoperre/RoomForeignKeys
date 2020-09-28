package com.guido.roomforeignkeys.room.dao

import androidx.room.*
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.Cursos
import com.guido.roomforeignkeys.entities.RelacionAlumnoCursos

@Dao
interface AlumnoDAO {

    @Query("SELECT * FROM alumno")
    fun getAlumno(): List<Alumno>

    @Query("SELECT * FROM cursos WHERE id=:idCurso")
    fun getCurso(idCurso:Long): List<Cursos>

    @Insert
    fun insertarAlumno(alumno: Alumno):Long

    @Insert
    fun insertarCurso(cursos: Cursos):Long

    @Insert
    fun insertarAlumnoCurso(alumnoCursos: RelacionAlumnoCursos):Long

    @Delete
    @Transaction
    fun borrarAlumno(alumno: Alumno)

}