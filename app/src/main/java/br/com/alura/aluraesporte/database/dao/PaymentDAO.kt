package br.com.alura.aluraesporte.database.dao

import androidx.room.Dao
import androidx.room.Insert
import br.com.alura.aluraesporte.model.Payment

@Dao
interface PaymentDAO {

    @Insert
    fun save(payment: Payment) : Long

}