package cz.helu.templateapp.ui.dashboard

import androidx.fragment.app.viewModels
import cz.helu.core.arch.BaseViewModelFragment
import cz.helu.templateapp.R
import cz.helu.templateapp.databinding.FragmentDashboardBinding

class DashboardFragment :
    BaseViewModelFragment<DashboardViewModel, FragmentDashboardBinding>(R.layout.fragment_dashboard) {
    override val viewModel: DashboardViewModel by viewModels()
}
