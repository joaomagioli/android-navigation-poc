package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.ProductRepository

class DetalhesProdutoViewModel(
    productId: Long,
    productRepository: ProductRepository
) : ViewModel() {

    val foundProduct = productRepository.getById(productId)

}