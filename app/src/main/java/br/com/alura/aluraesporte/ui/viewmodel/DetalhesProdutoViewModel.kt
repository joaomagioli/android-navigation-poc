package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.ProductRepository

class DetalhesProdutoViewModel(
    produtoId: Long,
    repository: ProductRepository
) : ViewModel() {

    val produtoEncontrado = repository.getById(produtoId)

}