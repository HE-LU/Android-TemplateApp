package cz.helu.templateapp.ui.home

import androidx.fragment.app.viewModels
import cz.helu.core.arch.BaseFragmentViewModel
import cz.helu.templateapp.R
import cz.helu.templateapp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragmentViewModel<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {
    override val logTag: String = HomeFragment::class.java.name
    override val viewModel: HomeViewModel by viewModels()
}
