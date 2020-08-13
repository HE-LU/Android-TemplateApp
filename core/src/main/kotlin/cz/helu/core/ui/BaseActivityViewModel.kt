package cz.helu.core.arch

//import androidx.annotation.LayoutRes
//import androidx.databinding.ViewDataBinding
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.ViewModelProvider
//import cz.helu.core.ui.ViewModelBinder
//
//abstract class BaseActivityViewModel<V : BaseViewModel, B : ViewDataBinding>(
//    @LayoutRes layoutResId: Int,
//    navigation: NavigationType = NavigationType.NONE,
//    statusBarStyle: StatusBarStyle = StatusBarStyle.WHITE,
//    navigationBarStyle: NavigationBarStyle = NavigationBarStyle.WHITE,
//    ignoreAccentTheme: Boolean = false
//) : BaseActivity(layoutResId, navigation, statusBarStyle, navigationBarStyle, ignoreAccentTheme),
//    ViewModelBinder<V, B> {
//    private var _binding: B? = null
//
//    // This property is only valid between onCreateView and onDestroyView.
//    override val binding: B get() = _binding!!
//    override fun getViewLifecycleOwner(): LifecycleOwner = this
//
//    @Inject
//    lateinit var defaultViewModelFactory: InjectingSavedStateViewModelFactory
//
//    /**
//     * This method androidx uses for `by viewModels` method.
//     * We can set out injecting factory here and therefore don't touch it again
//     */
//    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
//        defaultViewModelFactory.create(this, getExtras())
//
//    override fun setupView() {
//        _binding = setupBinding(layoutInflater, layoutResId)
//        setContentView(binding.root)
//
//        observeBaseEvents()
//        observe()
//
//        setupToolbar() // Invoke toolbar settings after observe implementation setup all required data
//    }
//
//    override fun onDestroy() {
//        _binding = null
//        super.onDestroy()
//    }
//
//    override fun observe() {}
//}