package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Payment
import br.com.alura.aluraesporte.repository.PaymentRepository
import br.com.alura.aluraesporte.repository.ProductRepository

class PagamentoViewModel(
    private val paymentRepository: PaymentRepository,
    private val produtodRepository: ProductRepository) : ViewModel() {

    fun salva(payment: Payment) = paymentRepository.save(payment)
    fun buscaProdutoPorId(id: Long) = produtodRepository.getById(id)

}