package cz.helu.templateapp.ui.dashboard

import androidx.fragment.app.viewModels
import cz.helu.core.arch.BaseFragmentViewModel
import cz.helu.templateapp.R
import cz.helu.templateapp.databinding.FragmentDashboardBinding

class DashboardFragment :
    BaseFragmentViewModel<DashboardViewModel, FragmentDashboardBinding>(R.layout.fragment_dashboard) {
    override val logTag: String = DashboardFragment::class.java.name
    override val viewModel: DashboardViewModel by viewModels()
}
