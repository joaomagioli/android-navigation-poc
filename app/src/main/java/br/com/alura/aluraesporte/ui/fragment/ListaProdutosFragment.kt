package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout.VERTICAL
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ProdutosAdapter
import br.com.alura.aluraesporte.ui.viewmodel.LoginViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ProdutosViewModel
import kotlinx.android.synthetic.main.lista_produtos.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ListaProdutosFragment : Fragment() {

    private val viewModel: ProdutosViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private val adapter: ProdutosAdapter by inject()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        getAllProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.lista_produtos,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_product_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_action_log_off) {
            loginViewModel.logoff()
            val directions = ListaProdutosFragmentDirections.actionListaProdutosToLogin()
            navController.navigate(directions)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureRecyclerView() {
        val divisor = DividerItemDecoration(context, VERTICAL)
        lista_produtos_recyclerview.addItemDecoration(divisor)
        adapter.onItemClickListener = { product ->
            val directions = ListaProdutosFragmentDirections.actionListaProdutosToDetalhesProduto(product.id)
            navController.navigate(directions)
        }
        lista_produtos_recyclerview.adapter = adapter
    }

    private fun getAllProducts() {
        viewModel.getAllProducts().observe(this, Observer { foundProducts ->
            foundProducts?.let {
                adapter.atualiza(it)
            }
        })
    }
}
