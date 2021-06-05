package br.com.alura.aluraesporte.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.aluraesporte.model.Product

@Dao
interface ProductDAO {

    @Query("SELECT * FROM Product")
    fun getAll(): LiveData<List<Product>>

    @Insert
    fun save(vararg product: Product)

    @Query("SELECT * FROM Product WHERE id = :id")
    fun getById(id: Long): LiveData<Product>

}
