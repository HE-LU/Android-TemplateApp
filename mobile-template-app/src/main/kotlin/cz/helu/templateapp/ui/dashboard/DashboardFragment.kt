package cz.helu.templateapp.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import cz.helu.core.arch.BaseViewModelFragment
import cz.helu.templateapp.R
import cz.helu.templateapp.databinding.FragmentDashboardBinding

class DashboardFragment :
    BaseViewModelFragment<DashboardViewModel, FragmentDashboardBinding>(R.layout.fragment_dashboard) {
    override val viewModel: DashboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(R.string.title_dashboard)
    }
}
