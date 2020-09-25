package com.guido.roomforeignkeys.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.guido.roomforeignkeys.R
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.AlumnoDatos
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
        crearAlumnoConCursos()
    }

    private fun inicializarViewModel(){
        viewModel = MainActivityViewModel(application)

        viewModel.getAlumnos().observe(this, {
            if (it != null){
                for (alumno in it){
                    //Esta linea esta mas que nada para tener un breakpoint en el debug
                    Log.i("ALUMNO",alumno.datos.nombre +" " + alumno.datos.apellido)

                    //Descomentar solo cuando el alumno Guido Perre esta creado
                    borrarAlumno(alumno.datos)
                }
            }
        })
    }

    //Creo los datos del alumno
    private fun crearAlumno(){
        val alumnosDatos:ArrayList<AlumnoDatos> = ArrayList()

        alumnosDatos.add(AlumnoDatos(0L,"Guido","Perre"))
        alumnosDatos.add(AlumnoDatos(0L,"Juan","Fulanito"))
        alumnosDatos.add(AlumnoDatos(0L,"Pedro","Pedron"))

        for (alumnoDatos in alumnosDatos)
            viewModel.insertAlumno(alumnoDatos)
    }

    //Creo un alumno con sus datos y sus objetos correspondientes
    private fun crearAlumnoConCursos(){
        val alumnos:ArrayList<Alumno> = ArrayList()

        val alumnoDatos = AlumnoDatos(0L,"Jorge","Fulanito")
        val cursos: ArrayList<Cursos> = ArrayList()
        cursos.add(Cursos(0L,0L,"Java","Dificil"))
        cursos.add(Cursos(0L,0L,"Android","Medio"))

       alumnos.add(Alumno(alumnoDatos,cursos))

        for (alumno in alumnos)
            viewModel.insertAlumnoCompleto(alumno)
    }

    //Borro un alumno y todos los objetos relacionados a el
    private fun borrarAlumno(alumnoDatos:AlumnoDatos){
        //Borro al alumno con apellido "Perre"
        if (alumnoDatos.apellido == "Perre")
            viewModel.deleteAlumno(alumnoDatos)
    }

}