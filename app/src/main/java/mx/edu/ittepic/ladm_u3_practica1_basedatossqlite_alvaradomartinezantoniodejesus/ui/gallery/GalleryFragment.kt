package mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.ui.gallery

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.Asignacion
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.Inventario
import mx.edu.ittepic.ladm_u3_practica1_basedatossqlite_alvaradomartinezantoniodejesus.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

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

        binding.insertar.setOnClickListener {
            insertar()
        }

        binding.consultar.setOnClickListener {
            consultar()
        }

        return root
    }

    private fun consultar() {
        /*var listaAsignacion = Asignacion(this).mostrarTodos()
        var asignacion = ArrayList<String>()
        if (listaAsignacion.size == 0){
            asignacion.add("NO HAY ELEMENTOS POR MOSTRAR")

        }else{
            (0..(listaAsignacion.size-1)).forEach {
                val asig = listaAsignacion.get(it)
                asignacion.add(asig.id.toString()+"\n"+asig.nomEmp"\n"+asig.areaTrabajo+"\n"+asig.fecha+"\n"+asig.codigoBarras)
            }
        }
        binding.lista.adapter = ArrayAdapter<String>(requireContext(),
            R.layout.simple_list_item_1,asignacion)*/
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