package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatParaMoedaBrasileira
import br.com.alura.aluraesporte.model.Payment
import br.com.alura.aluraesporte.model.Product
import br.com.alura.aluraesporte.ui.viewmodel.PagamentoViewModel
import kotlinx.android.synthetic.main.pagamento.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val FALHA_AO_CRIAR_PAGAMENTO = "Falha ao criar pagamento"
private const val COMPRA_REALIZADA_SUCESSO = "Compra realizada com sucesso"

class PagamentoFragment : Fragment() {

    private val navArgs by navArgs<PagamentoFragmentArgs>()
    private val produtoId by lazy {
        navArgs.produtoId
    }
    private val viewModel: PagamentoViewModel by viewModel()
    private lateinit var productEscolhido: Product
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.pagamento,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraBotaoConfirmaPagamento()
        buscaProduto()
    }

    private fun buscaProduto() {
        viewModel.buscaProdutoPorId(produtoId).observe(this, Observer {
            it?.let { produtoEncontrado ->
                productEscolhido = produtoEncontrado
                pagamento_preco.text = produtoEncontrado.price
                    .formatParaMoedaBrasileira()
            }
        })
    }

    private fun configuraBotaoConfirmaPagamento() {
        pagamento_botao_confirma_pagamento.setOnClickListener {
            criaPagamento()?.let(this::salva) ?: Toast.makeText(
                context,
                FALHA_AO_CRIAR_PAGAMENTO,
                Toast.LENGTH_LONG
            ).show()
            val directions = PagamentoFragmentDirections.actionPagamentoToListaProdutos()
            navController.navigate(directions)
        }
    }

    private fun salva(payment: Payment) {
        if (::productEscolhido.isInitialized) {
            viewModel.salva(payment)
                .observe(this, Observer {
                    it?.dado?.let {
                        Toast.makeText(context,
                            COMPRA_REALIZADA_SUCESSO,
                            Toast.LENGTH_LONG)
                            .show()
                    }
                })
        }
    }

    private fun criaPagamento(): Payment? {
        val numeroCartao = pagamento_numero_cartao
            .editText?.text.toString()
        val dataValidade = pagamento_data_validade
            .editText?.text.toString()
        val cvc = pagamento_cvc
            .editText?.text.toString()
        return geraPagamento(numeroCartao, dataValidade, cvc)
    }

    private fun geraPagamento(
        numeroCartao: String,
        dataValidade: String,
        cvc: String
    ): Payment? = try {
        Payment(
            cardNumber = numeroCartao.toInt(),
            expirationDate = dataValidade,
            cvc = cvc.toInt(),
            productId = produtoId,
            price = productEscolhido.price
        )
    } catch (e: NumberFormatException) {
        null
    }
}