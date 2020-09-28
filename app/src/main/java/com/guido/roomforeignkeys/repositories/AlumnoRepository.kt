package com.guido.roomforeignkeys.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.Cursos
import com.guido.roomforeignkeys.room.AppDatabase
import com.guido.roomforeignkeys.room.dao.AlumnoDAO
import kotlinx.coroutines.*

class AlumnoRepository() {

    private val viewModelJob = Job()
    private val uiScope =  CoroutineScope(Dispatchers.Main + viewModelJob)


    companion object{
        private lateinit var alumnoDAO: AlumnoDAO
        private var dataList: MutableLiveData<List<Alumno>> = MutableLiveData()
    }

    constructor(application: Application):this(){
        val database: AppDatabase = AppDatabase.getDatabase(application)
        alumnoDAO = database.alumnoDAO()
        getAlumno()
    }

    fun get(): MutableLiveData<List<Alumno>>{
        return dataList
    }

    private fun getAlumno(){
        uiScope.launch {
            val response = withContext(Dispatchers.IO) {
                return@withContext alumnoDAO.getAlumnoCompleto()
            }
            dataList.value = response
        }
    }

    fun insertAlumno(alumno: Alumno) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                alumnoDAO.insertarAlumno(alumno)
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
                alumnoDAO.insertarAlumnoCompleto(alumno)
            }
        }
    }

    fun deleteAlumno(alumno:Alumno){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                alumnoDAO.borrarAlumno(alumno)
            }
        }
    }

}