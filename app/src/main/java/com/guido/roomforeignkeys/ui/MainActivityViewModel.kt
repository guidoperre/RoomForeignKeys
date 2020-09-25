package com.guido.roomforeignkeys.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.AlumnoDatos
import com.guido.roomforeignkeys.entities.Cursos
import com.guido.roomforeignkeys.repositories.AlumnoRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var dataList: LiveData<List<Alumno>>

    private var alumnoRepository: AlumnoRepository = AlumnoRepository(application)

    init {
        dataList = alumnoRepository.get()
    }

    fun getAlumnos():LiveData<List<Alumno>>{
        return dataList
    }

    fun insertAlumno(alumnoDatos: AlumnoDatos){
        alumnoRepository.insertAlumno(alumnoDatos)
    }

    fun insertCurso(curso: Cursos){
        alumnoRepository.insertCurso(curso)
    }

}