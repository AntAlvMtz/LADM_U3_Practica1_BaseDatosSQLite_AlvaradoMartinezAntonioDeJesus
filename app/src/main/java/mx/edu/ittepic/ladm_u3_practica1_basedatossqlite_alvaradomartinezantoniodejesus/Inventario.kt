package mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus

import android.content.ContentValues
import android.database.sqlite.SQLiteException
import android.widget.Toast
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.ui.home.HomeFragment

class Inventario(este:HomeFragment) {
    private val este = este
    var codigoBarras = ""
    var tipoEquipo = ""
    var caracteristicas = ""
    var fechaCompra = ""
    private var err = ""

    fun insertar() : Boolean{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            var datos = ContentValues()

            datos.put("CODIGOBARRAS",codigoBarras)
            datos.put("TIPOEQUIPO",tipoEquipo)
            datos.put("CARACTERISTICAS",caracteristicas)
            datos.put("FECHACOMPRA",fechaCompra)

            val respuesta = tabla.insert("INVENTARIO", null,datos)
            if(respuesta == -1L) return false
        }catch (err : SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun mostrarPorTipo(tipo:String) : ArrayList<Inventario>{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        var arreglo = ArrayList<Inventario>()
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM INVENTARIO WHERE TIPOEQUIPO=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(tipo))
            if (cursor.moveToFirst()){
                do {
                    val inventario = Inventario(este)
                    inventario.codigoBarras = cursor.getString(0)
                    inventario.tipoEquipo = cursor.getString(1)
                    inventario.caracteristicas = cursor.getString(2)
                    inventario.fechaCompra = cursor.getString(3)
                    arreglo.add(inventario)
                }while (cursor.moveToNext())
            }

        }catch (err : SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarPorCodigo(codigo:String) : Inventario{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        var inv = Inventario(este)
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM INVENTARIO WHERE CODIGOBARRAS=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(codigo))
            if (cursor.moveToFirst()){
                    inv.codigoBarras = cursor.getString(0)
                    inv.tipoEquipo = cursor.getString(1)
                    inv.caracteristicas = cursor.getString(2)
                    inv.fechaCompra = cursor.getString(3)
            }

        }catch (err : SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return inv
    }


    fun mostrarPorCaracteristicas(caracteristicas:String) : ArrayList<Inventario>{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        var arreglo = ArrayList<Inventario>()
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM INVENTARIO WHERE CARACTERISTICAS=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(caracteristicas))
            if (cursor.moveToFirst()){
                do {
                    val inventario = Inventario(este)
                    inventario.codigoBarras = cursor.getString(0)
                    inventario.tipoEquipo = cursor.getString(1)
                    inventario.caracteristicas = cursor.getString(2)
                    inventario.fechaCompra = cursor.getString(3)
                    arreglo.add(inventario)
                }while (cursor.moveToNext())
            }

        }catch (err : SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarPorRango(fechaInicio:String,fechaFin:String) : ArrayList<Inventario>{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        var arreglo = ArrayList<Inventario>()
        try {
            val tabla = baseDatos.readableDatabase
            val SQLSELECT = "SELECT * FROM INVENTARIO WHERE FECHACOMPRA BETWEEN ? and ?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(fechaInicio,fechaFin))
            if (cursor.moveToFirst()){
                do {
                    val inventario = Inventario(este)
                    inventario.codigoBarras = cursor.getString(0)
                    inventario.tipoEquipo = cursor.getString(1)
                    inventario.caracteristicas = cursor.getString(2)
                    inventario.fechaCompra = cursor.getString(3)
                    arreglo.add(inventario)
                }while (cursor.moveToNext())
            }

        }catch (err : SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun eliminar(codBar:String) : Boolean{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase

            val respuesta = tabla.delete("INVENTARIO", "CODIGOBARRAS=?", arrayOf(codBar))
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

    fun modificar(codBar:String) : Boolean{
        val baseDatos = AsignacionEquipoComputoBD(este.requireContext(), "asignacionequipocomputo", null, 1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val datosActualizados = ContentValues()

            datosActualizados.put("TIPOEQUIPO",tipoEquipo)
            datosActualizados.put("CARACTERISTICAS",caracteristicas)
            datosActualizados.put("FECHACOMPRA",fechaCompra)

            val resultado = tabla.update("INVENTARIO",datosActualizados,"CODIGOBARRAS=?", arrayOf(codBar))

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