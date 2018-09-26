package phani.recast.com.views.fragments


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import phani.recast.com.R


/**
 * A simple [Fragment] subclass.
 *
 */
class ProductsFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    companion object {
        fun newInstance(): ProductsFragment = ProductsFragment()
    }

}
