package cz.helu.templateapp.ui.notifications

import androidx.fragment.app.viewModels
import cz.helu.core.arch.BaseViewModelFragment
import cz.helu.templateapp.R
import cz.helu.templateapp.databinding.FragmentNotificationsBinding

class NotificationsFragment :
    BaseViewModelFragment<NotificationsViewModel, FragmentNotificationsBinding>(R.layout.fragment_notifications) {
    override val logTag: String = NotificationsFragment::class.java.name
    override val viewModel: NotificationsViewModel by viewModels()
}
