package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatToBrazilianCurrency
import br.com.alura.aluraesporte.model.Payment
import br.com.alura.aluraesporte.model.Product
import br.com.alura.aluraesporte.ui.viewmodel.PagamentoViewModel
import kotlinx.android.synthetic.main.pagamento.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val FAILED_TO_CREATE_PAYMENT = "Failed to create payment"
private const val SUCCESSFUL_PURCHASE = "Successful purchase"

class PaymentFragment : BaseFragment() {

    private val navArgs by navArgs<PagamentoFragmentArgs>()
    private val productId by lazy {
        navArgs.produtoId
    }
    private val viewModel: PagamentoViewModel by viewModel()
    private lateinit var chosenProduct: Product
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
        setConfirmPaymentButton()
        getProduct()
    }

    private fun getProduct() {
        viewModel.getProductById(productId).observe(this, Observer {
            it?.let { foundProduct ->
                chosenProduct = foundProduct
                pagamento_preco.text = foundProduct.price
                    .formatToBrazilianCurrency()
            }
        })
    }

    private fun setConfirmPaymentButton() {
        pagamento_botao_confirma_pagamento.setOnClickListener {
            createPayment()?.let(this::save) ?: Toast.makeText(
                context,
                FAILED_TO_CREATE_PAYMENT,
                Toast.LENGTH_LONG
            ).show()
            val directions = PagamentoFragmentDirections.actionPagamentoToListaProdutos()
            navController.navigate(directions)
        }
    }

    private fun save(payment: Payment) {
        if (::chosenProduct.isInitialized) {
            viewModel.save(payment)
                .observe(this, Observer {
                    it?.dado?.let {
                        Toast.makeText(context,
                            SUCCESSFUL_PURCHASE,
                            Toast.LENGTH_LONG)
                            .show()
                    }
                })
        }
    }

    private fun createPayment(): Payment? {
        val cardNumber = pagamento_numero_cartao
            .editText?.text.toString()
        val expirationDate = pagamento_data_validade
            .editText?.text.toString()
        val cvc = pagamento_cvc
            .editText?.text.toString()
        return generatePayment(cardNumber, expirationDate, cvc)
    }

    private fun generatePayment(
        cardNumber: String,
        expirationDate: String,
        cvc: String
    ): Payment? = try {
        Payment(
            cardNumber = cardNumber.toInt(),
            expirationDate = expirationDate,
            cvc = cvc.toInt(),
            productId = productId,
            price = chosenProduct.price
        )
    } catch (e: NumberFormatException) {
        null
    }
}