package com.guido.roomforeignkeys.room.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.Cursos
import java.lang.Exception

@Dao
interface AlumnoDAO {

    @Query("SELECT * FROM alumno")
    fun getAlumno(): List<Alumno>

    @Query("SELECT * FROM cursos WHERE id_alumno=:idAlumno")
    fun getCurso(idAlumno:Long): List<Cursos>

    @Transaction
    fun getAlumnoCompleto(): List<Alumno>? {
        var alumnos:List<Alumno>? = null

        try {
            alumnos = getAlumno()
        }catch (e: Exception){
            Log.e("GET ALUMNO COMPLETO", "No se pudo traer de la base de datos")
        }

        if (alumnos != null)
            for (alumno in alumnos)
                try {
                    alumno.cursos = getCurso(alumno.id)
                }catch (e:Exception){
                    Log.e("GET ALUMNO COMPLETO", "No se pudieron traer de la base de datos los cursos del alumno " + alumno.nombre + " " + alumno.apellido)
                }

        return alumnos
    }

    @Insert
    fun insertarAlumno(alumno: Alumno):Long

    @Insert
    fun insertarCurso(cursos: Cursos):Long

    //Se hace una Transaction para que en caso de no poder insertar algun datos, que no se inserte ninguno
    @Transaction
    fun insertarAlumnoCompleto(alumno:Alumno){
        var idAlumno: Long? = null

        //Inserto los datos del alumno
        try {
            idAlumno = insertarAlumno(alumno)
        }catch (e: Exception){
            Log.e("INSERT ALUMNO COMPLETO", "No se pudo insertar a la base de datos el alumno " + alumno.nombre + " " + alumno.apellido)
        }

        //Inserto los cursos
        val cursosAlumno = alumno.cursos
        if (cursosAlumno != null && idAlumno != null)
            for (curso in cursosAlumno){
                curso.idAlumno = idAlumno
                try {
                    insertarCurso(curso)
                }catch (e: Exception){
                    Log.e("INSERT ALUMNO COMPLETO", "No se pudo insertar a la base de datos un curso correspondiente al alumno " + alumno.nombre + " " + alumno.apellido)
                }
            }

        //Se repitiria lo anterior para cada uno de los objetos a insertar correspondientes
    }

    @Delete
    @Transaction
    fun borrarAlumno(alumno: Alumno)

}