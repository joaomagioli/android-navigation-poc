package br.com.alura.aluraesporte.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatToBrazilianCurrency
import br.com.alura.aluraesporte.model.Product
import kotlinx.android.synthetic.main.item_produto.view.*

class ProductsAdapter(
    private val context: Context,
    private val products: MutableList<Product> = mutableListOf(),
    var onItemClickListener: (product: Product) -> Unit = {}
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_produto,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun update(newProducts: List<Product>) {
        notifyItemRangeRemoved(0, products.size)
        products.clear()
        products.addAll(newProducts)
        notifyItemRangeInserted(0, products.size)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var product: Product
        private val name by lazy { itemView.item_produto_nome }
        private val price by lazy { itemView.item_produto_preco }

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    onItemClickListener(product)
                }
            }
        }

        fun bind(product: Product) {
            this.product = product
            name.text = product.name
            price.text = product.price.formatToBrazilianCurrency()
        }

    }

}
