package com.guido.roomforeignkeys.room.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.AlumnoDatos
import com.guido.roomforeignkeys.entities.Cursos
import com.guido.roomforeignkeys.repositories.AlumnoRepository
import java.lang.Exception

@Dao
interface AlumnoDAO {

    @Query("SELECT * FROM alumno")
    @Transaction
    fun getAll(): LiveData<List<Alumno>>

    @Insert
    fun insertarAlumnoDatos(alumnoDatos: AlumnoDatos):Long

    @Insert
    fun insertarCurso(cursos: Cursos):Long

    //Se hace una Transaction para que en caso de no poder insertar algun datos, que no se inserte ninguno
    @Transaction
    fun insertarAlumnoCompleto(alumno:Alumno){
        var idAlumno: Long? = null

        //Inserto los datos del alumno
        try {
            idAlumno = insertarAlumnoDatos(alumno.datos)
        }catch (e: Exception){
            Log.e("INSERT ALUMNO COMPLETO", "No se pudo insertar a la base de datos el alumno " + alumno.datos.nombre + " " + alumno.datos.apellido)
        }

        //Inserto los cursos
        val cursosAlumno = alumno.cursos
        if (cursosAlumno != null && idAlumno != null)
            for (curso in cursosAlumno){
                curso.idAlumno = idAlumno
                try {
                    insertarCurso(curso)
                }catch (e: Exception){
                    Log.e("INSERT ALUMNO COMPLETO", "No se pudo insertar a la base de datos un curso correspondiente al alumno " + alumno.datos.nombre + " " + alumno.datos.apellido)
                }
            }

        //Se repitiria lo anterior para cada uno de los objetos a insertar correspondientes
    }

    @Delete
    @Transaction
    fun borrarAlumno(alumnoDatos: AlumnoDatos)

}