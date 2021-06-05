package br.com.alura.aluraesporte.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.alura.aluraesporte.database.AppDatabase
import br.com.alura.aluraesporte.database.dao.PaymentDAO
import br.com.alura.aluraesporte.database.dao.ProductDAO
import br.com.alura.aluraesporte.model.Product
import br.com.alura.aluraesporte.repository.LoginRepository
import br.com.alura.aluraesporte.repository.PaymentRepository
import br.com.alura.aluraesporte.repository.ProductRepository
import br.com.alura.aluraesporte.ui.fragment.DetalhesProdutoFragment
import br.com.alura.aluraesporte.ui.fragment.ListaProdutosFragment
import br.com.alura.aluraesporte.ui.fragment.PagamentoFragment
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ProdutosAdapter
import br.com.alura.aluraesporte.ui.viewmodel.DetalhesProdutoViewModel
import br.com.alura.aluraesporte.ui.viewmodel.LoginViewModel
import br.com.alura.aluraesporte.ui.viewmodel.PagamentoViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ProdutosViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.math.BigDecimal

private const val DATABASE_NAME = "aluraesporte.db"
private const val DATA_BASE_TEST_NAME = "aluraesporte-test.db"

val testeDatabaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DATA_BASE_TEST_NAME
        ).fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(IO).launch {
                        val dao: ProductDAO by inject()
                        dao.save(
                            Product(
                                name = "Bola de futebol",
                                price = BigDecimal("100")
                            ), Product(
                                name = "Camisa",
                                price = BigDecimal("80")
                            ),
                            Product(
                                name = "Chuteira",
                                price = BigDecimal("120")
                            ), Product(
                                name = "Bermuda",
                                price = BigDecimal("60")
                            )
                        )
                    }
                }
            }).build()
    }
}

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}

val daoModule = module {
    single<ProductDAO> { get<AppDatabase>().productDAO() }
    single<PaymentDAO> { get<AppDatabase>().paymentDAO() }
    single<ProductRepository> { ProductRepository(get()) }
    single<PaymentRepository> { PaymentRepository(get()) }
    single<LoginRepository> { LoginRepository(get())  }
    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(get()) }
}

val uiModule = module {
    factory<DetalhesProdutoFragment> { DetalhesProdutoFragment() }
    factory<ListaProdutosFragment> { ListaProdutosFragment() }
    factory<PagamentoFragment> { PagamentoFragment() }
    factory<ProdutosAdapter> { ProdutosAdapter(get()) }
}

val viewModelModule = module {
    viewModel<ProdutosViewModel> { ProdutosViewModel(get()) }
    viewModel<DetalhesProdutoViewModel> { (id: Long) -> DetalhesProdutoViewModel(id, get()) }
    viewModel<PagamentoViewModel> { PagamentoViewModel(get(), get()) }
    viewModel<LoginViewModel> { LoginViewModel(get()) }
}