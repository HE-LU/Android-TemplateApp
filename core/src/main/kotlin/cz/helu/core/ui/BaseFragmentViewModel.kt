package cz.helu.core.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import cz.helu.core.ui.ViewModelBinder

abstract class BaseFragmentViewModel<V : BaseViewModel, B : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : BaseFragment(), ViewModelBinder<V, B> {
    private var _binding: B? = null

    // This property is only valid between onCreateView and onDestroyView.
    override val binding: B get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = setupBinding(inflater, layoutResId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        observeBaseEvents()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun observe() {}
}
