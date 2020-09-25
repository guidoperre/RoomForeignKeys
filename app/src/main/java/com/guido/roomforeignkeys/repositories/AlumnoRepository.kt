package com.guido.roomforeignkeys.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.AlumnoDatos
import com.guido.roomforeignkeys.entities.Cursos
import com.guido.roomforeignkeys.room.AppDatabase
import com.guido.roomforeignkeys.room.dao.AlumnoDAO
import kotlinx.coroutines.*
import java.lang.Exception

class AlumnoRepository() {

    private lateinit var dataList: LiveData<List<Alumno>>

    private val viewModelJob = Job()
    private val uiScope =  CoroutineScope(Dispatchers.Main + viewModelJob)


    companion object{
        private lateinit var alumnoDAO: AlumnoDAO
    }

    constructor(application: Application):this(){
        val database: AppDatabase = AppDatabase.getDatabase(application)
        alumnoDAO = database.alumnoDAO()
        dataList = alumnoDAO.getAll()
    }

    fun get(): LiveData<List<Alumno>>{
        return dataList
    }

    fun insertAlumno(alumnoDatos: AlumnoDatos) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                alumnoDAO.insertarAlumnoDatos(alumnoDatos)
            }
        }
    }

    fun insertCurso(cursos: Cursos) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                alumnoDAO.insertarCurso(cursos)
            }
        }
    }

    //Inserta todos los datos de Alumno
    fun insertAlumnoCompleto(alumno:Alumno){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val idAlumno = alumnoDAO.insertarAlumnoDatos(alumno.datos)

                //Inserto los cursos
                val cursosAlumno = alumno.cursos
                if (cursosAlumno != null)
                    for (curso in cursosAlumno){
                        curso.idAlumno = idAlumno
                        try {
                            alumnoDAO.insertarCurso(curso)
                        }catch (e:Exception){
                            Log.e("INSERT ALUMNO COMPLETO", "No se pudo insertar a la base de datos un curso correspondiente al alumno " + alumno.datos.nombre + " " + alumno.datos.apellido)
                        }
                    }

                //Se repitiria lo anterior para cada uno de los objetos a insertar correspondientes
            }
        }
    }

    fun deleteAlumno(alumnoDatos:AlumnoDatos){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                alumnoDAO.borrarAlumno(alumnoDatos)
            }
        }
    }

}