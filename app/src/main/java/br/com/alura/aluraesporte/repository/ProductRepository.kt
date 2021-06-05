package br.com.alura.aluraesporte.repository

import androidx.lifecycle.LiveData
import br.com.alura.aluraesporte.database.dao.ProductDAO
import br.com.alura.aluraesporte.model.Product

class ProductRepository(private val dao: ProductDAO) {

    fun buscaTodos(): LiveData<List<Product>> = dao.getAll()

    fun buscaPorId(id: Long): LiveData<Product> = dao.getById(id)

}
