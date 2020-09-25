package com.guido.roomforeignkeys.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.AlumnoDatos
import com.guido.roomforeignkeys.entities.Cursos
import com.guido.roomforeignkeys.room.AppDatabase
import com.guido.roomforeignkeys.room.dao.AlumnoDAO
import kotlinx.coroutines.*

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

}