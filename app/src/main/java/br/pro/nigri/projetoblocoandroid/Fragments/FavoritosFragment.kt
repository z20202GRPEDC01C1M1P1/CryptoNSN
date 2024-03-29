package br.pro.nigri.projetoblocoandroid.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.pro.nigri.projetoblocoandroid.Adapter.CotacaoAdapter
import br.pro.nigri.projetoblocoandroid.Model.MoedaModel
import br.pro.nigri.projetoblocoandroid.R
import br.pro.nigri.projetoblocoandroid.ViewModel.ListCotacoesViewModel
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedaViewModel
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedasFavoritasCRUDViewModel
import br.pro.nigri.projetoblocoandroid.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favoritos.*
import kotlinx.android.synthetic.main.fragment_lista_moedas.*


class FavoritosFragment : Fragment() {

    private lateinit var listCotacoesViewModel: ListCotacoesViewModel
    private lateinit var moedasFavoritasCRUDViewModel: MoedasFavoritasCRUDViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarListaFavoritos.visibility = View.VISIBLE
        configurarRecyclerView()

        viewModelFactory = ViewModelFactory()
        activity?.let {
            moedasFavoritasCRUDViewModel =
                ViewModelProvider(it, viewModelFactory)
                    .get(MoedasFavoritasCRUDViewModel::class.java)
        }

        listCotacoesViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(ListCotacoesViewModel::class.java)

        moedasFavoritasCRUDViewModel.getFavListByUser(requireContext(), listCotacoesViewModel)

        listCotacoesViewModel.listaFav.observe(viewLifecycleOwner, Observer {lista->
            if (lista != null){
                val adapter = lista_favoritos.adapter

                if (adapter is CotacaoAdapter){
                    adapter.atualizarDados(lista)
                    progressBarListaFavoritos.visibility = View.GONE
                }
            }
            else
            {
                Toast.makeText(requireContext(),"Nenhuma Moeda Encontrada", Toast.LENGTH_LONG).show()
            }

        })



    }

    private fun configurarRecyclerView() {
        lista_favoritos.layoutManager =
            LinearLayoutManager(activity)
        lista_favoritos.adapter = CotacaoAdapter() {

            moedasFavoritasCRUDViewModel.getCryptoDetails(it,"brl",requireContext())

            findNavController().navigate(R.id.moedaDetailsFragment)
        }
    }


}