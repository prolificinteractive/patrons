package com.prolificinteractive.patrons.conceal

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import java.util.HashMap

class ConcealSharedPreferences @JvmOverloads constructor(
    context: Context,
    private val delegate: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context),
    private val keyEncryption: KeyEncryption = NoKeyEncryption(),
    private val valueEncryption: ValueEncryption = ConcealEncryption(context)
) : SharedPreferences {

  override fun getAll(): Map<String, *> {
    val all = delegate.all
    val keys = all.keys
    val unencryptedMap = HashMap<String, String>(keys.size)
    for (key in keys) {
      val decryptedKey = keyEncryption.decrypt(key)
      val value = all[key]
      unencryptedMap[decryptedKey] = valueEncryption.decrypt(value.toString())
    }
    return unencryptedMap
  }

  override fun getString(key: String, defValue: String?): String? {
    val v = delegate.getString(keyEncryption.encrypt(key), null)
    return if (v != null) valueEncryption.decrypt(v) else defValue
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  override fun getStringSet(key: String, defValues: Set<String>?): Set<String?>? {
    val stringSet = delegate.getStringSet(keyEncryption.encrypt(key), defValues)
    return if (stringSet != null) valueEncryption.decryptSet(stringSet) else defValues
  }

  override fun getInt(key: String, defValue: Int): Int {
    val encryptedKey = keyEncryption.encrypt(key)
    val v = delegate.getString(encryptedKey, null)
    return if (v != null) {
      val decryptedValue = valueEncryption.decrypt(v)
      decryptedValue.toInt()
    } else {
      defValue
    }
  }

  override fun getLong(key: String, defValue: Long): Long {
    val v = delegate.getString(keyEncryption.encrypt(key), null)
    return if (v != null) valueEncryption.decrypt(v).toLong() else defValue
  }

  override fun getFloat(key: String, defValue: Float): Float {
    val v = delegate.getString(keyEncryption.encrypt(key), null)
    return if (v != null) valueEncryption.decrypt(v).toFloat() else defValue
  }

  override fun getBoolean(key: String, defValue: Boolean): Boolean {
    val v = delegate.getString(keyEncryption.encrypt(key), null)
    return if (v != null) valueEncryption.decrypt(v).toBoolean() else defValue
  }

  override fun contains(key: String) = delegate.contains(keyEncryption.encrypt(key))

  override fun edit(): Editor = Editor(keyEncryption, valueEncryption)

  override fun registerOnSharedPreferenceChangeListener(
      listener: SharedPreferences.OnSharedPreferenceChangeListener) {
    delegate.registerOnSharedPreferenceChangeListener(listener)
  }

  override fun unregisterOnSharedPreferenceChangeListener(
      listener: SharedPreferences.OnSharedPreferenceChangeListener) {
    delegate.unregisterOnSharedPreferenceChangeListener(listener)
  }

  inner class Editor(private val keyEncryption: KeyEncryption,
                     private val valueEncryption: ValueEncryption) : SharedPreferences.Editor {

    private var delegate: SharedPreferences.Editor = this@ConcealSharedPreferences.delegate.edit()

    override fun putBoolean(key: String, value: Boolean): Editor {
      delegate.putString(
          keyEncryption.encrypt(key),
          valueEncryption.encrypt(value.toString())
      )
      return this
    }

    override fun putFloat(key: String, value: Float): Editor {
      delegate.putString(
          keyEncryption.encrypt(key),
          valueEncryption.encrypt(value.toString())
      )
      return this
    }

    override fun putInt(key: String, value: Int): Editor {
      val encryptedKey = keyEncryption.encrypt(key)
      val encryptedValue = valueEncryption.encrypt(value.toString())
      delegate.putString(
          encryptedKey,
          encryptedValue
      )
      return this
    }

    override fun putLong(key: String, value: Long): Editor {
      delegate.putString(keyEncryption.encrypt(key), valueEncryption.encrypt(value.toString()))
      return this
    }

    override fun putString(key: String, value: String?): Editor {
      when (value) {
        null -> delegate.putString(keyEncryption.encrypt(key), null)
        else -> delegate.putString(keyEncryption.encrypt(key), valueEncryption.encrypt(value))
      }
      return this
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun putStringSet(key: String, values: Set<String>?): SharedPreferences.Editor {
      when (values) {
        null -> delegate.putStringSet(keyEncryption.encrypt(key), null)
        else -> delegate.putStringSet(keyEncryption.encrypt(key),
            valueEncryption.encryptSet(values))
      }
      return this
    }

    override fun apply() = delegate.apply()

    override fun clear(): Editor {
      delegate.clear()
      return this
    }

    override fun commit() = delegate.commit()

    override fun remove(s: String): Editor {
      delegate.remove(keyEncryption.encrypt(s))
      return this
    }
  }
}
