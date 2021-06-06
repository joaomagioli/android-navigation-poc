package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatToBrazilianCurrency
import br.com.alura.aluraesporte.ui.viewmodel.DetalhesProdutoViewModel
import kotlinx.android.synthetic.main.detalhes_produto.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsProductFragment : BaseFragment() {

    private val navArgs by navArgs<DetalhesProdutoFragmentArgs>()
    private val productId by lazy {
        navArgs.produtoId
    }
    private val viewModel: DetalhesProdutoViewModel by viewModel { parametersOf(productId) }
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.detalhes_produto,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProduct()
        setBuyButton()
    }

    private fun setBuyButton() {
        detalhes_produto_botao_comprar.setOnClickListener {
            viewModel.foundProduct.value?.let {
                val directions = DetalhesProdutoFragmentDirections.actionDetalhesProdutoToPagamento(productId)
                navController.navigate(directions)
            }
        }
    }

    private fun getProduct() {
        viewModel.foundProduct.observe(this, Observer {
            it?.let { product ->
                detalhes_produto_nome.text = product.name
                detalhes_produto_preco.text = product.price.formatToBrazilianCurrency()
            }
        })
    }
}