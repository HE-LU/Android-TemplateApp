package cz.helu.templateapp.ui.dashboard

import androidx.fragment.app.viewModels
import cz.helu.core.arch.BaseViewModelFragment
import cz.helu.templateapp.R
import cz.helu.templateapp.databinding.FragmentDashboardBinding

class DashboardFragment :
    BaseViewModelFragment<DashboardViewModel, FragmentDashboardBinding>(R.layout.fragment_dashboard) {
    override val logTag: String = DashboardFragment::class.java.name
    override val viewModel: DashboardViewModel by viewModels()
}
