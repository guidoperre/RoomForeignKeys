package com.guido.roomforeignkeys.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.Cursos
import com.guido.roomforeignkeys.entities.RelacionAlumnoCursos
import com.guido.roomforeignkeys.room.AppDatabase
import com.guido.roomforeignkeys.room.dao.AlumnoDAO
import kotlinx.coroutines.*
import java.lang.Exception

class AlumnoRepository(){

    private val viewModelJob = Job()
    private val uiScope =  CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var database: AppDatabase


    companion object{
        private lateinit var alumnoDAO: AlumnoDAO
        private var dataList: MutableLiveData<List<Alumno>> = MutableLiveData()
    }

    constructor(application: Application):this(){
        database = AppDatabase.getDatabase(application)
        alumnoDAO = database.alumnoDAO()
        getAlumno()
    }

    fun get(): MutableLiveData<List<Alumno>>{
        return dataList
    }

    //Traigo todos los datos del alumno
    private fun getAlumno(){
        uiScope.launch {
            val response = withContext(Dispatchers.IO) {
                var alumnos:List<Alumno>? = null

                //Se hace una Transaction para que en caso de no poder traer algun dato, que no se traiga ninguno
                database.runInTransaction(Runnable {
                    var aux:List<Alumno>? = null
                    try {
                        aux = alumnoDAO.getAlumno()
                    }catch (e: Exception){
                        Log.e("GET ALUMNO COMPLETO", "No se pudo traer de la base de datos")
                    }

                    if (aux != null)
                        for (alumno in aux)
                            try {
                                alumno.cursos = alumnoDAO.getCurso(alumno.id)
                            }catch (e:Exception){
                                Log.e("GET ALUMNO COMPLETO", "No se pudieron traer de la base de datos los cursos del alumno " + alumno.nombre + " " + alumno.apellido)
                            }

                    alumnos = aux
                })

                return@withContext alumnos
            }

            dataList.value = response
        }
    }

    //Inserto los datos del alumno
    fun insertAlumno(alumno: Alumno) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                alumnoDAO.insertarAlumno(alumno)
            }
        }
    }

    //Inserto los datos del curso
    fun insertarCurso(curso:Cursos){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                alumnoDAO.insertarCurso(curso)
            }
        }
    }

    //Inserta todos los datos de Alumno
    fun insertAlumnoCompleto(alumno:Alumno){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                //Se hace una Transaction para que en caso de no poder insertar algun datos, que no se inserte ninguno
                database.runInTransaction(Runnable {
                    //Inserto los datos del alumno
                    var idAlumno: Long? = null
                    try {
                        idAlumno = alumnoDAO.insertarAlumno(alumno)
                    }catch (e: Exception){
                        Log.e("INSERT ALUMNO COMPLETO", "No se pudo insertar a la base de datos el alumno " + alumno.nombre + " " + alumno.apellido)
                    }

                    //Checkeo si existen los cursos e inserto
                    val idCursos: ArrayList<Long> = ArrayList()
                    try {
                        val cursosAlumno = alumno.cursos
                        if (cursosAlumno != null)
                            for (curso in cursosAlumno)
                                if (alumnoDAO.getCurso(curso.id).isEmpty())
                                    idCursos.add(alumnoDAO.insertarCurso(curso))
                                else
                                    idCursos.add(curso.id)
                    }catch (e: Exception){
                        Log.e("INSERT ALUMNO COMPLETO", "No se pudo insertar a la base de datos el curso " + alumno.nombre + " " + alumno.apellido)
                    }

                    //Inserto la relacion entre el alumno y el curso
                    if (idAlumno != null && idCursos.isNotEmpty()){
                        try {
                            for(idCurso in idCursos)
                                alumnoDAO.insertarAlumnoCurso(RelacionAlumnoCursos(0L,idCurso,idAlumno))
                        }catch (e: Exception){
                            Log.e("INSERT ALUMNO COMPLETO", "No se pudo insertar a la base de datos el curso " + alumno.nombre + " " + alumno.apellido)
                        }
                    }

                    //Se repitiria lo anterior para cada uno de los objetos a insertar correspondientes
                })
            }
        }
    }

    //Borra al alumno y sus tablas relacionadas
    fun deleteAlumno(alumno:Alumno){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                //Se hace una Transaction para que en caso de no poder insertar algun datos, que no se inserte ninguno
                database.runInTransaction(Runnable {
                    //Borro los datos del alumno
                    try {
                        alumnoDAO.borrarAlumno(alumno.id)
                    }catch (e: Exception){
                        Log.e("INSERT ALUMNO COMPLETO", "No se pudo borrar de la base de datos al alumno " + alumno.nombre + " " + alumno.apellido)
                    }

                    //Checkeo si existe alguna relacion y la borro
                    try {
                        alumnoDAO.borrarRelacionAlumnoCurso(alumno.id)
                    }catch (e: Exception){
                        Log.e("INSERT ALUMNO COMPLETO", "No se pudo borrar de la base de datos la relacion del alumno " + alumno.nombre + " " + alumno.apellido)
                    }

                    //Se repitiria lo anterior para cada uno de los objetos a insertar correspondientes
                })
            }
        }
    }

}