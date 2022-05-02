package mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.insertar.setOnClickListener {
            insertar()
        }

        binding.consultar.setOnClickListener {
            consultar()
        }

        return root
    }

    private fun consultar() {
        /*var listaInventario = Inventario(this).mostrarTodos()
        var inventario = ArrayList<String>()
        if (listaInventario.size == 0){
            inventario.add("NO HAY ELEMENTOS POR MOSTRAR")

        }else{
            (0..(listaInventario.size-1)).forEach {
                val inv = listaInventario.get(it)
                inventario.add(inv.codigoBarras+"\n"+inv.tipoEquipo+"\n"+inv.caracteristicas+"\n"+inv.fechaCompra)
            }
        }
        binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,inventario)*/

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