package mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus

import android.content.ContentValues
import android.database.sqlite.SQLiteException
import android.widget.Toast
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.databinding.ActivityMainBinding
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.ui.gallery.GalleryFragment

class Asignacion(este:GalleryFragment) {
    private val este = este
    var id = 0
    var nomEmp = ""
    var areaTrabajo = ""
    var fecha = ""
    var codigoBarras = ""
    private var err = ""


    fun insertar() : Boolean{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            var datos = ContentValues()

            datos.put("NOM_EMPLEADO",nomEmp)
            datos.put("AREA_TRABAJO",areaTrabajo)
            datos.put("FECHA",fecha)
            datos.put("CODIGOBARRAS",codigoBarras)

            val respuesta = tabla.insert("ASIGNACION", null,datos)
            if(respuesta == -1L){ Toast.makeText(este.requireContext(),"NO SE HA PODIDO INSERTAR CORRECTAMENTE",Toast.LENGTH_LONG).show()
                return false
            }else Toast.makeText(este.requireContext(),"SE HA INSERTADO CORRECTAMENTE",Toast.LENGTH_LONG).show()
        }catch (err : SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun mostrarPorNombre(nom:String) : ArrayList<Asignacion>{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        var arreglo = ArrayList<Asignacion>()
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ASIGNACION WHERE NOM_EMPLEADO=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(nom))
            if (cursor.moveToFirst()){
                do {
                    val asig = Asignacion(este)
                    asig.id = cursor.getString(0).toInt()
                    asig.nomEmp = cursor.getString(1)
                    asig.areaTrabajo = cursor.getString(2)
                    asig.fecha = cursor.getString(3)
                    asig.codigoBarras = cursor.getString(4)
                    arreglo.add(asig)
                }while (cursor.moveToNext())
            }

        }catch (err : SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarTodos() : ArrayList<Asignacion>{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        var arreglo = ArrayList<Asignacion>()
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ASIGNACION"

            var cursor = tabla.rawQuery(SQLSELECT,null)
            if (cursor.moveToFirst()){
                do {
                    val asig = Asignacion(este)
                    asig.id = cursor.getString(0).toInt()
                    asig.nomEmp = cursor.getString(1)
                    asig.areaTrabajo = cursor.getString(2)
                    asig.fecha = cursor.getString(3)
                    asig.codigoBarras = cursor.getString(4)
                    arreglo.add(asig)
                }while (cursor.moveToNext())
            }

        }catch (err : SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarPorFecha(fecha:String) : ArrayList<Asignacion>{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        var arreglo = ArrayList<Asignacion>()
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ASIGNACION WHERE FECHA=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(fecha))
            if (cursor.moveToFirst()){
                do {
                    val asig = Asignacion(este)
                    asig.id = cursor.getString(0).toInt()
                    asig.nomEmp = cursor.getString(1)
                    asig.areaTrabajo = cursor.getString(2)
                    asig.fecha = cursor.getString(3)
                    asig.codigoBarras = cursor.getString(4)
                    arreglo.add(asig)
                }while (cursor.moveToNext())
            }

        }catch (err : SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarPorArea(area:String) : ArrayList<Asignacion>{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        var arreglo = ArrayList<Asignacion>()
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ASIGNACION WHERE AREA_TRABAJO=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(area))
            if (cursor.moveToFirst()){
                do {
                    val asig = Asignacion(este)
                    asig.id = cursor.getString(0).toInt()
                    asig.nomEmp = cursor.getString(1)
                    asig.areaTrabajo = cursor.getString(2)
                    asig.fecha = cursor.getString(3)
                    asig.codigoBarras = cursor.getString(4)
                    arreglo.add(asig)
                }while (cursor.moveToNext())
            }

        }catch (err : SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun eliminar(id:String) : Boolean{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase

            val respuesta = tabla.delete("ASIGNACION", "IDASIGNACION=?", arrayOf(id))
            if(respuesta == 0){
                return false
            }

        }catch (err : SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun modificar(id:String) : Boolean{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val datosActualizados = ContentValues()

            datosActualizados.put("NOM_EMPLEADO",nomEmp)
            datosActualizados.put("AREA_TRABAJO",areaTrabajo)
            datosActualizados.put("FECHA",fecha)

            val resultado = tabla.update("ASIGNACION",datosActualizados,"IDASIGNACION=?", arrayOf(id))

            if(resultado==0){
                return false
            }

        }catch (err : SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return true
    }
}