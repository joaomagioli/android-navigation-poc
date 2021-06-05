package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Payment
import br.com.alura.aluraesporte.repository.PaymentRepository
import br.com.alura.aluraesporte.repository.ProductRepository

class PagamentoViewModel(
    private val paymentRepository: PaymentRepository,
    private val productRepository: ProductRepository) : ViewModel() {

    fun save(payment: Payment) = paymentRepository.save(payment)
    fun getProductById(id: Long) = productRepository.getById(id)

}