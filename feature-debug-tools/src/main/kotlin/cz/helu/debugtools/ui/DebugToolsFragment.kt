package cz.helu.debugtools.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import cz.helu.core.arch.BaseViewModelFragment
import cz.helu.debugtools.R
import cz.helu.debugtools.databinding.FragmentDebugToolsBinding

class DebugToolsFragment :
    BaseViewModelFragment<DebugToolsViewModel, FragmentDebugToolsBinding>(R.layout.fragment_debug_tools) {
    override val viewModel: DebugToolsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(titleId = R.string.title_debug_tools, homeAsUpEnabled = true)
    }
}
