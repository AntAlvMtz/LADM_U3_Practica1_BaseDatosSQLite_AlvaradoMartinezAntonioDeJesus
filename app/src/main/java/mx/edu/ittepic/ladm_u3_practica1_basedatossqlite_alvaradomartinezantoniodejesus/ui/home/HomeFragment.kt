package mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.Inventario
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    val opciones = listOf("Tipo de Consulta","Por Tipo de Equipo", "Por Características", "Por Rango de Fechas")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.op.adapter = ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_item,opciones)

        binding.op.onItemSelectedListener = object:
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when(position){
                    1 -> if(binding.tipoequipo.text.isNotEmpty()){
                        consultarPorTipo(binding.tipoequipo.text.toString())
                        }else{Toast.makeText(requireContext(),"Necesita ingresar un tipo",Toast.LENGTH_LONG).show()}
                    2 ->if(binding.caracteristicas.text.isNotEmpty()){
                        consultarPorCaracteristicas(binding.caracteristicas.text.toString())
                        }else{Toast.makeText(requireContext(),"Necesita ingresar la caracteristica a buscar",Toast.LENGTH_LONG).show()}
                    3 ->if(binding.fecha.text.isNotEmpty() && binding.fechafin.text.isNotEmpty()){
                        consultarPorRango(binding.fecha.text.toString(),binding.fechafin.text.toString())
                        }else{Toast.makeText(requireContext(),"Necesita ingresar un rango de fechas",Toast.LENGTH_LONG).show()}
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.lista.setOnItemClickListener { parent, view, position, l ->
            binding.eliminar.isEnabled = true
            binding.modificar.isEnabled = true
            binding.codigobarras.isEnabled = false
            var a = parent.getItemAtPosition(position)
            var x = a.toString().split("\n")
            binding.codigobarras.setText(x[0])
            binding.tipoequipo.setText(x[1])
            binding.caracteristicas.setText(x[2])
            binding.fecha.setText(x[3])
        }

        binding.insertar.setOnClickListener {
            insertar()
        }

        binding.eliminar.setOnClickListener {
            eliminar()
        }

        binding.modificar.setOnClickListener {
            modificar()
        }


        return root
    }

    private fun modificar() {
        var inv =Inventario(this)
        inv.tipoEquipo = binding.tipoequipo.text.toString()
        inv.caracteristicas = binding.caracteristicas.text.toString()
        inv.fechaCompra = binding.fecha.text.toString()
        val resultado = inv.modificar(binding.codigobarras.text.toString())
        if(binding.tipoequipo.text.isNotEmpty() && binding.caracteristicas.text.isNotEmpty() && binding.fecha.text.isNotEmpty()){
            if(resultado){
                Toast.makeText(requireContext(),"SE HA MODIFICADO CORRECTAMENTE",Toast.LENGTH_LONG).show()
                var listaInventario = Inventario(this).mostrarTodos()
                var inventario = ArrayList<String>()
                if (listaInventario.size == 0){
                    inventario.add("NO HAY ELEMENTOS POR MOSTRAR")

                }else{
                    (0..(listaInventario.size-1)).forEach {
                        val inv = listaInventario.get(it)
                        inventario.add(inv.codigoBarras+"\n"+inv.tipoEquipo+"\n"+inv.caracteristicas+"\n"+inv.fechaCompra)
                    }
                }
                binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,inventario)
            }else{
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO MODIFICAR")
                    .show()
            }
        }else{
            Toast.makeText(requireContext(),"INGRESE LOS DATOS A MODIFICAR",Toast.LENGTH_LONG).show()
        }
        binding.codigobarras.isEnabled = true
    }

    private fun eliminar() {
        var inv = Inventario(this)
        val resultado = inv.eliminar(binding.codigobarras.text.toString())
        if (resultado){
            Toast.makeText(requireContext(),"SE HA ELIMINADO CORRECTAMENTE",Toast.LENGTH_LONG).show()
            var listaInventario = Inventario(this).mostrarTodos()
            var inventario = ArrayList<String>()
            if (listaInventario.size == 0){
                inventario.add("NO HAY ELEMENTOS POR MOSTRAR")

            }else{
                (0..(listaInventario.size-1)).forEach {
                    val inv = listaInventario.get(it)
                    inventario.add(inv.codigoBarras+"\n"+inv.tipoEquipo+"\n"+inv.caracteristicas+"\n"+inv.fechaCompra)
                }
            }
            binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,inventario)
        }else{
            AlertDialog.Builder(requireContext())
                .setTitle("ERROR")
                .setMessage("NO SE PUDO ELIMINAR")
                .show()
        }
        binding.codigobarras.isEnabled = true
    }

    private fun consultarPorRango(fechaIn:String,fechaFin:String) {
        var listaInventario = Inventario(this).mostrarPorRango(fechaIn,fechaFin)
        var inventario = ArrayList<String>()
        if (listaInventario.size == 0){
            inventario.add("NO HAY ELEMENTOS POR MOSTRAR")

        }else{
            (0..(listaInventario.size-1)).forEach {
                val inv = listaInventario.get(it)
                inventario.add(inv.codigoBarras+"\n"+inv.tipoEquipo+"\n"+inv.caracteristicas+"\n"+inv.fechaCompra)
            }
        }
        binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,inventario)
    }

    private fun consultarPorCaracteristicas(carac:String) {
        var listaInventario = Inventario(this).mostrarPorCaracteristicas(carac)
        var inventario = ArrayList<String>()
        if (listaInventario.size == 0){
            inventario.add("NO HAY ELEMENTOS POR MOSTRAR")

        }else{
            (0..(listaInventario.size-1)).forEach {
                val inv = listaInventario.get(it)
                inventario.add(inv.codigoBarras+"\n"+inv.tipoEquipo+"\n"+inv.caracteristicas+"\n"+inv.fechaCompra)
            }
        }
        binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,inventario)
    }

    private fun consultarPorTipo(tipo:String) {
        var listaInventario = Inventario(this).mostrarPorTipo(tipo)
        var inventario = ArrayList<String>()
        if (listaInventario.size == 0){
            inventario.add("NO HAY ELEMENTOS POR MOSTRAR")

        }else{
            (0..(listaInventario.size-1)).forEach {
                val inv = listaInventario.get(it)
                inventario.add(inv.codigoBarras+"\n"+inv.tipoEquipo+"\n"+inv.caracteristicas+"\n"+inv.fechaCompra)
            }
        }
        binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,inventario)
    }


    private fun insertar() {
        var inv = Inventario(this)
        inv.codigoBarras = binding.codigobarras.text.toString()
        inv.tipoEquipo = binding.tipoequipo.text.toString()
        inv.caracteristicas = binding.caracteristicas.text.toString()
        inv.fechaCompra = binding.fecha.text.toString()
        if (binding.codigobarras.text.isNotEmpty() && binding.tipoequipo.text.isNotEmpty() && binding.caracteristicas.text.isNotEmpty()
            && binding.fecha.text.isNotEmpty()){
            val resultado = inv.insertar()
            if(resultado){
                Toast.makeText(requireContext(),"SE HA INSERTADO CORRECTAMENTE!", Toast.LENGTH_LONG)
                    .show()
                binding.codigobarras.text.clear()
                binding.tipoequipo.text.clear()
                binding.caracteristicas.text.clear()
                binding.fecha.text.clear()
            }else{
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO INSERTAR")
                    .show()
            }
        }else{
            Toast.makeText(requireContext(), "DEBE LLENAR CORRECTAMENTE TODOS LOS DATOS",Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}