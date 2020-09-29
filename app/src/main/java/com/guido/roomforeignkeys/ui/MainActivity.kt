package com.guido.roomforeignkeys.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.guido.roomforeignkeys.R
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.Cursos

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Enlazo el View Model
        inicializarViewModel()

        //Creo cursos y los inserto
        //crearCursos()

        //Creo alumnos y los inserto
        //crearAlumno()

        //Creo un alumno con cursos y lo inserto
        //crearAlumnoConCursos()
    }

    //Aca adentro esta la funcion de borrar alumno
    private fun inicializarViewModel(){
        viewModel = MainActivityViewModel(application)

        viewModel.getAlumnos().observe(this, {
            if (it != null){
                for (alumno in it){
                    //Esta linea esta para tener un breakpoint en el debug
                    Log.i("ALUMNO",alumno.nombre +" " + alumno.apellido)

                    //Descomentar solo cuando el alumno Guido Perre esta creado
                    //Borro un alumno y todos los objetos relacionados a el
                    //if (alumno.apellido == "Perre")
                    //   viewModel.deleteAlumno(alumno)
                }
            }
        })
    }

    //Creo los datos del alumno
    private fun crearAlumno(){
        val alumnosDatos:ArrayList<Alumno> = ArrayList()

        alumnosDatos.add(Alumno(0L,"Fulanito","Rodriguez",null))
        alumnosDatos.add(Alumno(0L,"Juan","Forh",null))
        alumnosDatos.add(Alumno(0L,"Pedro","Pedron",null))

        for (alumnoDatos in alumnosDatos)
            viewModel.insertAlumno(alumnoDatos)
    }

    //Creo los cursos
    private fun crearCursos(){
        val cursos: ArrayList<Cursos> = ArrayList()
        cursos.add(Cursos(0L,"CSS-HTML","Facil"))
        cursos.add(Cursos(0L,"Android","Medio"))
        cursos.add(Cursos(0L,"Java","Dificil"))

        for (curso in cursos)
            viewModel.insertCurso(curso)
    }

    //Creo un alumno con sus datos y sus objetos correspondientes
    private fun crearAlumnoConCursos(){
        val cursos: ArrayList<Cursos> = ArrayList()
        cursos.add(Cursos(0L,"Kotlin","Dificil"))
        cursos.add(Cursos(0L,"PHP","Medio"))

        val alumno = Alumno(0L,"Guido","Perre",cursos)

        val alumnos:ArrayList<Alumno> = ArrayList()

        alumnos.add(alumno)

        //Simulo la situacion en la que tenga un array de alumnos
        for (alm in alumnos)
            viewModel.insertAlumnoCompleto(alm)
    }

}