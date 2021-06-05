package br.com.alura.aluraesporte.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal


class ConverterBigDecimal {

    @TypeConverter
    fun toBigDecimal(value: BigDecimal?): String {
        return value?.toString() ?: ""
    }

    @TypeConverter
    fun toString(value: String?): BigDecimal {
        return value?.let { BigDecimal(it) } ?: BigDecimal.ZERO
    }

}
