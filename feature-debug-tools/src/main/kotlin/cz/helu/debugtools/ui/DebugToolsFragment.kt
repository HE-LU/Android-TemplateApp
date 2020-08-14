package cz.helu.debugtools.ui

import androidx.fragment.app.viewModels
import cz.helu.core.arch.BaseViewModelFragment
import cz.helu.debugtools.R
import cz.helu.debugtools.databinding.FragmentDebugToolsBinding

class DebugToolsFragment :
    BaseViewModelFragment<DebugToolsViewModel, FragmentDebugToolsBinding>(R.layout.fragment_debug_tools) {
    override val viewModel: DebugToolsViewModel by viewModels()
}
