package cz.helu.templateapp.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import cz.helu.core.arch.BaseViewModelFragment
import cz.helu.templateapp.R
import cz.helu.templateapp.databinding.FragmentNotificationsBinding

class NotificationsFragment :
    BaseViewModelFragment<NotificationsViewModel, FragmentNotificationsBinding>(R.layout.fragment_notifications) {
    override val viewModel: NotificationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        toolbar?.title = getString(R.string.title_notifications)
    }
}
