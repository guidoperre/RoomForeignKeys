package com.guido.roomforeignkeys.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        //Las dos funciones que siguen estan solamente para insertar datos en la base

        //Creo alumnos y los inserto
        //crearAlumno()

        //Creo cursos y los inserto
        //crearCursos()
    }

    private fun inicializarViewModel(){
        viewModel = MainActivityViewModel(application)

        viewModel.getAlumnos().observe(this, Observer{
            if (it != null){
                for (alumno in it){
                    //Esta linea esta mas que nada para tener un breakpoint el debug
                    Toast.makeText(this,alumno.datos.nombre,Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    //Ejecutar primero que cursos siempre
    private fun crearAlumno(){
        val alumnosDatos:ArrayList<AlumnoDatos> = ArrayList()

        alumnosDatos.add(AlumnoDatos(0L,"Guido","Perre"))
        alumnosDatos.add(AlumnoDatos(0L,"Juan","Fulanito"))
        alumnosDatos.add(AlumnoDatos(0L,"Pedro","Pedron"))

        for (alumnoDatos in alumnosDatos)
            viewModel.insertAlumno(alumnoDatos)
    }

    //No ejecutar hasta que ya esten los alumnos creados o posiblemente no funcionara
    private fun crearCursos(){
        val cursos:ArrayList<Cursos> = ArrayList()

        cursos.add(Cursos(0L,1L,"HTML","Facil"))
        cursos.add(Cursos(0L,2L,"Android","Medio"))
        cursos.add(Cursos(0L,3L,"Java","Dificil"))

        for (curso in cursos)
            viewModel.insertCurso(curso)
    }

}