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

        //Creo alumnos y los inserto
        //crearAlumno()

        //Creo un alumno con cursos y lo inserto
        //crearAlumnoConCursos()
    }

    private fun inicializarViewModel(){
        viewModel = MainActivityViewModel(application)

        viewModel.getAlumnos().observe(this, {
            if (it != null){
                for (alumno in it){
                    //Esta linea esta para tener un breakpoint en el debug
                    Log.i("ALUMNO",alumno.nombre +" " + alumno.apellido)

                    //Descomentar solo cuando el alumno Guido Jorl esta creado
                    //borrarAlumno(alumno)
                }
            }
        })
    }

    //Creo los datos del alumno
    private fun crearAlumno(){
        val alumnosDatos:ArrayList<Alumno> = ArrayList()

        alumnosDatos.add(Alumno(0L,"Guido","Perre",null))
        alumnosDatos.add(Alumno(0L,"Juan","Fulanito",null))
        alumnosDatos.add(Alumno(0L,"Pedro","Pedron",null))

        for (alumnoDatos in alumnosDatos)
            viewModel.insertAlumno(alumnoDatos)
    }

    //Creo un alumno con sus datos y sus objetos correspondientes
    private fun crearAlumnoConCursos(){
        val cursos: ArrayList<Cursos> = ArrayList()
        cursos.add(Cursos(0L,0L,"Java","Dificil"))
        cursos.add(Cursos(0L,0L,"Android","Medio"))

        val alumno = Alumno(0L,"Guido","Jorl",cursos)

        val alumnos:ArrayList<Alumno> = ArrayList()

        alumnos.add(alumno)

        //Simulo la situacion en la que tenga un array de alumnos
        for (alm in alumnos)
            viewModel.insertAlumnoCompleto(alm)
    }

    //Borro un alumno y todos los objetos relacionados a el
    private fun borrarAlumno(alumno:Alumno){
        //Borro al alumno con apellido "Perre"
        if (alumno.apellido == "Jorl")
            viewModel.deleteAlumno(alumno)
    }

}