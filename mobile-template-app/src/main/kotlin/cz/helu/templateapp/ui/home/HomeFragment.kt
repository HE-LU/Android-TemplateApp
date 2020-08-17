package cz.helu.templateapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import cz.helu.core.arch.BaseViewModelFragment
import cz.helu.templateapp.R
import cz.helu.templateapp.databinding.FragmentHomeBinding

class HomeFragment : BaseViewModelFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        toolbar?.title = getString(R.string.title_home)
    }
}
