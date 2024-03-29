package br.pro.nigri.projetoblocoandroid.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.pro.nigri.projetoblocoandroid.CotacoesActivity
import br.pro.nigri.projetoblocoandroid.R
import br.pro.nigri.projetoblocoandroid.ViewModel.LoginViewModel
import br.pro.nigri.projetoblocoandroid.ViewModelFactory
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        txtBtnCadastrar.setOnClickListener{
            findNavController().navigate(R.id.cadastroFragment)
        }

        btnAcessarLogin.setOnClickListener {


            var email = txtLogin.text.toString()
            var senha = txtSenha.text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty()){
                progressBarLogin.visibility = View.VISIBLE
                viewModelFactory = ViewModelFactory()
                activity?.let {
                    viewModel =
                        ViewModelProvider(it, viewModelFactory) // MainActivity
                            .get(LoginViewModel::class.java)
                }

                var result = viewModel.logar(email, senha)

                result.addOnSuccessListener {
                    startActivity(
                        Intent(activity, CotacoesActivity::class.java)
                    )
                    progressBarLogin.visibility = View.GONE
                }
                result.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }else{
                Toast.makeText(
                    requireContext(),
                    "Todos os campos devem ser preenchidos",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        txtBtnEsqueciSenha.setOnClickListener {
            findNavController().navigate(R.id.redefinirSenhaFragment)
        }



    }
}