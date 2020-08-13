package cz.helu.core.ktools

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// Simple SharedPreferences delegates - supports getter/setter
// Note: When not specified explicitly, the name of the property is used as a preference key
// Example: `val userName by sharedPrefs().string()`
fun SharedPreferencesProvider.int(def: Int = 0, key: String? = null): ReadWriteProperty<Any?, Int> =
    delegatePrimitive(def, key, SharedPreferences::getInt, Editor::putInt)

fun SharedPreferencesProvider.long(def: Long = 0, key: String? = null): ReadWriteProperty<Any?, Long> =
    delegatePrimitive(def, key, SharedPreferences::getLong, Editor::putLong)

fun SharedPreferencesProvider.float(def: Float = 0f, key: String? = null): ReadWriteProperty<Any?, Float> =
    delegatePrimitive(def, key, SharedPreferences::getFloat, Editor::putFloat)

fun SharedPreferencesProvider.boolean(def: Boolean = false, key: String? = null): ReadWriteProperty<Any?, Boolean> =
    delegatePrimitive(def, key, SharedPreferences::getBoolean, Editor::putBoolean)

fun SharedPreferencesProvider.stringSet(
    def: Set<String> = emptySet(),
    key: String? = null
): ReadWriteProperty<Any?, Set<String>?> = delegate(def, key, SharedPreferences::getStringSet, Editor::putStringSet)

fun SharedPreferencesProvider.string(def: String? = null, key: String? = null): ReadWriteProperty<Any?, String?> =
    delegate(def, key, SharedPreferences::getString, Editor::putString)

// LiveData SharedPreferences delegates - provides LiveData access to prefs with sync across app on changes
// Note: When not specified explicitly, the name of the property is used as a preference key
// Example: `val userName by sharedPrefs().stringLiveData()`
fun SharedPreferencesProvider.intLiveData(def: Int, key: String? = null): ReadOnlyProperty<Any?, MutableLiveData<Int>> =
    liveDataDelegatePrimitive(def, key, SharedPreferences::getInt, SharedPreferences.Editor::putInt)

fun SharedPreferencesProvider.longLiveData(
    def: Long,
    key: String? = null
): ReadOnlyProperty<Any?, MutableLiveData<Long>> =
    liveDataDelegatePrimitive(def, key, SharedPreferences::getLong, SharedPreferences.Editor::putLong)

fun SharedPreferencesProvider.floatLiveData(
    def: Float,
    key: String? = null
): ReadOnlyProperty<Any?, MutableLiveData<Float>> =
    liveDataDelegatePrimitive(def, key, SharedPreferences::getFloat, SharedPreferences.Editor::putFloat)

fun SharedPreferencesProvider.booleanLiveData(
    def: Boolean,
    key: String? = null
): ReadOnlyProperty<Any?, MutableLiveData<Boolean>> =
    liveDataDelegatePrimitive(def, key, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean)

fun SharedPreferencesProvider.stringLiveData(
    def: String? = null,
    key: String? = null
): ReadOnlyProperty<Any?, MutableLiveData<String?>> =
    liveDataDelegate(def, key, SharedPreferences::getString, SharedPreferences.Editor::putString)

fun SharedPreferencesProvider.stringSetLiveData(
    def: Set<String>? = null,
    key: String? = null
): ReadOnlyProperty<Any?, MutableLiveData<Set<String>?>> =
    liveDataDelegate(def, key, SharedPreferences::getStringSet, SharedPreferences.Editor::putStringSet)

// Option to observe preferences using Kotlin Flow

fun SharedPreferencesProvider.intFlow(def: Int = 0, key: String) = flowPrimitive(def, key, SharedPreferences::getInt)
fun SharedPreferencesProvider.longFlow(def: Long = 0, key: String) = flowPrimitive(def, key, SharedPreferences::getLong)
fun SharedPreferencesProvider.floatFlow(def: Float = 0f, key: String) =
    flowPrimitive(def, key, SharedPreferences::getFloat)

fun SharedPreferencesProvider.booleanFlow(def: Boolean = false, key: String) =
    flowPrimitive(def, key, SharedPreferences::getBoolean)

fun SharedPreferencesProvider.stringFlow(def: String? = null, key: String) =
    flowPrimitive(def, key, SharedPreferences::getString)

// -- internal

private inline fun <T> SharedPreferencesProvider.delegate(
    defaultValue: T?,
    key: String? = null,
    crossinline getter: SharedPreferences.(String, T?) -> T?,
    crossinline setter: Editor.(String, T?) -> Editor
) =
    object : ReadWriteProperty<Any?, T?> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T? =
            prefs.getter(
                key
                    ?: property.name,
                defaultValue
            )

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) =
            prefs.edit().setter(
                key
                    ?: property.name,
                value
            ).apply()
    }

private inline fun <T> SharedPreferencesProvider.delegatePrimitive(
    defaultValue: T,
    key: String? = null,
    crossinline getter: SharedPreferences.(String, T) -> T,
    crossinline setter: Editor.(String, T) -> Editor
) =
    object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T =
            prefs.getter(
                key
                    ?: property.name,
                defaultValue
            )!!

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
            prefs.edit().setter(
                key
                    ?: property.name,
                value
            ).apply()
    }

private inline fun <T> SharedPreferencesProvider.liveDataDelegate(
    defaultValue: T? = null,
    key: String? = null,
    crossinline getter: SharedPreferences.(String, T?) -> T?,
    crossinline setter: Editor.(String, T?) -> Editor
): ReadOnlyProperty<Any?, MutableLiveData<T?>> =
    object :
        MutableLiveData<T?>(),
        ReadOnlyProperty<Any?, MutableLiveData<T?>>,
        SharedPreferences.OnSharedPreferenceChangeListener {
        var originalProperty: KProperty<*>? = null
        lateinit var prefKey: String

        override fun getValue(thisRef: Any?, property: KProperty<*>): MutableLiveData<T?> {
            originalProperty = property
            prefKey = key ?: originalProperty!!.name
            return this
        }

        override fun getValue(): T? {

            val value = prefs.getter(prefKey, defaultValue)
            return super.getValue()
                ?: value
                ?: defaultValue
        }

        override fun setValue(value: T?) {
            super.setValue(value)
            prefs.edit().setter(prefKey, value).apply()
        }

        override fun onActive() {
            super.onActive()
            value = prefs.getter(prefKey, defaultValue)
            prefs.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, changedKey: String) {
            if (changedKey == prefKey) {
                value = sharedPreferences.getter(changedKey, defaultValue)
            }
        }

        override fun onInactive() {
            super.onInactive()
            prefs.unregisterOnSharedPreferenceChangeListener(this)
        }
    }

private inline fun <T> SharedPreferencesProvider.liveDataDelegatePrimitive(
    defaultValue: T,
    key: String? = null,
    crossinline getter: SharedPreferences.(String, T) -> T,
    crossinline setter: Editor.(String, T) -> Editor
): ReadOnlyProperty<Any?, MutableLiveData<T>> =
    object :
        MutableLiveData<T>(),
        ReadOnlyProperty<Any?, MutableLiveData<T>>,
        SharedPreferences.OnSharedPreferenceChangeListener {
        var originalProperty: KProperty<*>? = null
        lateinit var prefKey: String

        override fun getValue(thisRef: Any?, property: KProperty<*>): MutableLiveData<T> {
            originalProperty = property
            prefKey = key ?: originalProperty!!.name
            return this
        }

        override fun getValue(): T {

            val value = prefs.getter(prefKey, defaultValue)
            return super.getValue()
                ?: value
                ?: defaultValue
        }

        override fun setValue(value: T) {
            super.setValue(value)
            prefs.edit().setter(prefKey, value).apply()
        }

        override fun onActive() {
            super.onActive()
            value = prefs.getter(prefKey, defaultValue)
            prefs.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, changedKey: String) {
            if (changedKey == prefKey) {
                value = sharedPreferences.getter(changedKey, defaultValue)
            }
        }

        override fun onInactive() {
            super.onInactive()
            prefs.unregisterOnSharedPreferenceChangeListener(this)
        }
    }

@OptIn(ExperimentalCoroutinesApi::class)
private inline fun <T> SharedPreferencesProvider.flowPrimitive(
    defaultValue: T,
    key: String,
    crossinline getter: SharedPreferences.(String, T) -> T
) = channelFlow {
    offer(prefs.getter(key, defaultValue))

    val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, k ->
        if (key == k) {
            offer(prefs.getter(key, defaultValue))
        }
    }

    prefs.registerOnSharedPreferenceChangeListener(listener)
    awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
}

class SharedPreferencesProvider(val prefs: SharedPreferences) {

    fun clear() {
        prefs.edit().clear().apply()
    }
}
