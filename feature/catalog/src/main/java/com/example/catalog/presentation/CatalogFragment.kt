package com.example.catalog.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cart_api.CartDepsProvider
import com.example.catalog.R
import com.example.catalog.di.DaggerCatalogFragmentComponent
import com.example.core.di.NavigationDepsProvider
import com.example.core.getApplication
import com.example.core.observe
import com.example.core.ui.ProductItemView
import com.example.product_api.ProductDepsProvider
import javax.inject.Inject

class CatalogFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: CatalogViewModel.Factory

    private val viewModel: CatalogViewModel by viewModels { viewModelFactory }

    private var listView: LinearLayout? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerCatalogFragmentComponent.factory().create(
            navigationDepsProvider = (getApplication() as NavigationDepsProvider.Holder).getNavigationDepsProvider(),
            cartDepsProvider = (getApplication() as CartDepsProvider.Holder).getCartDepsProvider(),
            productDepsProvider = (getApplication() as ProductDepsProvider.Holder).getProductDepsProvider()
        ).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.fragment_catalog_list)

        viewModel.productsState.observe(this) {
            updateProducts(it)
        }
    }

    private fun updateProducts(stateList: List<CatalogViewModel.ProductState>) {
        val listViewValue = listView ?: return
        listViewValue.removeAllViews()
        for (state in stateList) {
            val view = ProductItemView(requireContext())
            listViewValue.addView(view)
            view.bind(state.productItemState)
            view.setOnClickListener {
                viewModel.onProductClick(findNavController(), state.product)
            }
            view.plusView.setOnClickListener {
                viewModel.onProductPlusClick(state.product)
            }
            view.minusView.setOnClickListener {
                viewModel.onProductMinusClick(state.product)
            }
        }
    }
}