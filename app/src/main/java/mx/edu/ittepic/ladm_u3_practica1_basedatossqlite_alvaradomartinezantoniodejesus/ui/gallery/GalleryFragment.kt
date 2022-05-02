package mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.ui.gallery

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.Asignacion
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.Inventario
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.databinding.FragmentGalleryBinding
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.ui.home.HomeFragment

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    val frag = HomeFragment()

    val opciones = listOf("Tipo de Consulta","Por Empleado", "Por Area de Trabajo", "Por Fecha")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.op.adapter = ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_item,opciones)

        binding.op.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when(position){
                    1 -> if(binding.nomempleado.text.isNotEmpty())
                            consultarPorEmp(binding.nomempleado.text.toString())
                        else Toast.makeText(requireContext(),"Necesita ingresar un empleado",Toast.LENGTH_LONG).show()
                    2 -> if(binding.areatrab.text.isNotEmpty())
                            consultarPorArea(binding.areatrab.text.toString())
                        else Toast.makeText(requireContext(),"Necesita ingresar un área de trabajo",Toast.LENGTH_LONG).show()
                    3 -> if(binding.fecha.text.isNotEmpty())
                            consultarPorFecha(binding.fecha.text.toString())
                        else Toast.makeText(requireContext(),"Necesita ingresar una fecha",Toast.LENGTH_LONG).show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.lista.setOnItemClickListener { parent, view, position, l ->
            binding.eliminar.isEnabled = true
            binding.modificar.isEnabled = true
            var a = parent.getItemAtPosition(position)
            var x = a.toString().split("\n")
            binding.id.setText(x[0])
            binding.nomempleado.setText(x[1])
            binding.areatrab.setText(x[2])
            binding.fecha.setText(x[3])
            binding.codigobarras.setText(x[4])
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
        var asig = Asignacion(this)
        asig.nomEmp = binding.nomempleado.text.toString()
        asig.areaTrabajo = binding.areatrab.text.toString()
        asig.fecha = binding.fecha.text.toString()
        asig.codigoBarras = binding.codigobarras.text.toString()
        val resultado = asig.modificar(binding.id.text.toString())
        if(binding.nomempleado.text.isNotEmpty() && binding.areatrab.text.isNotEmpty() && binding.fecha.text.isNotEmpty() && binding.codigobarras.text.isNotEmpty()){
            if(resultado){
                Toast.makeText(requireContext(),"SE HA MODIFICADO CORRECTAMENTE",Toast.LENGTH_LONG).show()
                var listaInventario = Inventario(this).mostrarPorCodigo(binding.codigobarras.text.toString())
                var inventario = ArrayList<String>()
                if (listaInventario.equals("")){
                    inventario.add("NO HAY ELEMENTOS POR MOSTRAR")

                }else{
                    val inv = listaInventario
                    inventario.add(inv.codigoBarras+"\n"+inv.tipoEquipo+"\n"+inv.caracteristicas+"\n"+inv.fechaCompra)
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
    }

    private fun eliminar() {
        var asig = Asignacion(this)
        val resultado = asig.eliminar(binding.id.text.toString())
        if (resultado){
            Toast.makeText(requireContext(),"SE HA ELIMINADO CORRECTAMENTE",Toast.LENGTH_LONG).show()
            var listaAsignacion = Asignacion(this).mostrarTodos()
            var asignacion = ArrayList<String>()
            if (listaAsignacion.size == 0){
                asignacion.add("NO HAY ELEMENTOS POR MOSTRAR")

            }else{
                (0..(listaAsignacion.size-1)).forEach {
                    val asig = listaAsignacion.get(it)
                    asignacion.add(asig.id.toString()+"\n"+asig.nomEmp+"\n"+asig.areaTrabajo+"\n"+asig.fecha+"\n"+asig.codigoBarras)
                }
            }
            binding.lista.adapter = ArrayAdapter<String>(requireContext(),
                R.layout.simple_list_item_1,asignacion)
        }else{
            AlertDialog.Builder(requireContext())
                .setTitle("ERROR")
                .setMessage("NO SE PUDO ELIMINAR")
                .show()
        }

    }

    private fun consultarPorFecha(fecha:String) {
        var listaAsignacion = Asignacion(this).mostrarPorFecha(fecha)
        var asignacion = ArrayList<String>()
        if (listaAsignacion.size == 0){
            asignacion.add("NO HAY ELEMENTOS POR MOSTRAR")

        }else{
            (0..(listaAsignacion.size-1)).forEach {
                val asig = listaAsignacion.get(it)
                asignacion.add(asig.id.toString()+"\n"+asig.nomEmp+"\n"+asig.areaTrabajo+"\n"+asig.fecha+"\n"+asig.codigoBarras)
            }
        }
        binding.lista.adapter = ArrayAdapter<String>(requireContext(),
            R.layout.simple_list_item_1,asignacion)
    }

    private fun consultarPorArea(area:String) {
        var listaAsignacion = Asignacion(this).mostrarPorArea(area)
        var asignacion = ArrayList<String>()
        if (listaAsignacion.size == 0){
            asignacion.add("NO HAY ELEMENTOS POR MOSTRAR")

        }else{
            (0..(listaAsignacion.size-1)).forEach {
                val asig = listaAsignacion.get(it)
                asignacion.add(asig.id.toString()+"\n"+asig.nomEmp+"\n"+asig.areaTrabajo+"\n"+asig.fecha+"\n"+asig.codigoBarras)
            }
        }
        binding.lista.adapter = ArrayAdapter<String>(requireContext(),
            R.layout.simple_list_item_1,asignacion)
    }

    private fun consultarPorEmp(emp:String) {
        var listaAsignacion = Asignacion(this).mostrarPorNombre(emp)
        var asignacion = ArrayList<String>()
        if (listaAsignacion.size == 0){
            asignacion.add("NO HAY ELEMENTOS POR MOSTRAR")

        }else{
            (0..(listaAsignacion.size-1)).forEach {
                val asig = listaAsignacion.get(it)
                asignacion.add(asig.id.toString()+"\n"+asig.nomEmp+"\n"+asig.areaTrabajo+"\n"+asig.fecha+"\n"+asig.codigoBarras)
            }
        }
        binding.lista.adapter = ArrayAdapter<String>(requireContext(),
            R.layout.simple_list_item_1,asignacion)
    }


    private fun insertar() {
        var asig = Asignacion(this)
        asig.nomEmp = binding.nomempleado.text.toString()
        asig.areaTrabajo = binding.areatrab.text.toString()
        asig.fecha = binding.fecha.text.toString()
        asig.codigoBarras = binding.codigobarras.text.toString()
        if (binding.codigobarras.text.isNotEmpty() && binding.areatrab.text.isNotEmpty() && binding.nomempleado.text.isNotEmpty()
            && binding.fecha.text.isNotEmpty()){
            val resultado = asig.insertar()
            if(resultado){
                Toast.makeText(requireContext(),"SE HA INSERTADO CORRECTAMENTE!", Toast.LENGTH_LONG)
                    .show()
                binding.codigobarras.text.clear()
                binding.areatrab.text.clear()
                binding.nomempleado.text.clear()
                binding.fecha.text.clear()
            }else{
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO INSERTAR")
                    .show()
            }
        }else{
            Toast.makeText(requireContext(), "DEBE LLENAR CORRECTAMENTE TODOS LOS DATOS", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}