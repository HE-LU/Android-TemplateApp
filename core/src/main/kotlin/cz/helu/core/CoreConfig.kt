package cz.helu.core

private const val FLAVOR_PRODUCTION = "production"
private const val FLAVOR_STAGING = "staging"
private const val FLAVOR_DEVELOP = "develop"

private const val BUILD_TYPE_DEBUG = "debug"
private const val BUILD_TYPE_RELEASE = "release"

object CoreConfig {
    const val PRODUCTION_FLAVOR = BuildConfig.FLAVOR == FLAVOR_PRODUCTION
    const val STAGING_FLAVOR = BuildConfig.FLAVOR == FLAVOR_STAGING
    const val DEVELOP_FLAVOR = BuildConfig.FLAVOR == FLAVOR_DEVELOP

    const val DEBUG_BUILD_TYPE = BuildConfig.BUILD_TYPE == BUILD_TYPE_DEBUG
    const val RELEASE_BUILD_TYPE = BuildConfig.BUILD_TYPE == BUILD_TYPE_RELEASE

    const val APP_TAG = "TemplateApp"

    object Url {
        const val TERMS_OF_SERVICE = "https://www.helu.cz"
        const val PRIVACY_POLICY = "https://www.helu.cz"
    }
}
