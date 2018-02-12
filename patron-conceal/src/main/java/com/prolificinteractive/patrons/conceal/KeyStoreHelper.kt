package com.prolificinteractive.patrons.conceal

/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.math.BigInteger
import java.security.InvalidAlgorithmParameterException
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.spec.RSAKeyGenParameterSpec
import java.security.spec.RSAKeyGenParameterSpec.F4
import java.util.Calendar
import java.util.GregorianCalendar
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.security.auth.x500.X500Principal

object KeyStoreHelper {

  private const val TAG = "KeyStoreHelper"
  private const val AMOUNT = 30
  private const val KEY_SIZE = 2048

  private val cipher: Cipher
    @Throws(NoSuchPaddingException::class, NoSuchAlgorithmException::class)
    get() = Cipher.getInstance(
        String.format(
            "%s/%s/%s",
            SecurityConstants.TYPE_RSA,
            SecurityConstants.BLOCKING_MODE,
            SecurityConstants.PADDING_TYPE
        ))

  /**
   * Creates a public and private key and stores it using the Android Key
   * Store, so that only this application will be able to access the keys.
   */
  @Throws(NoSuchProviderException::class,
      NoSuchAlgorithmException::class,
      InvalidAlgorithmParameterException::class)
  fun createKeys(context: Context, alias: String) {
    if (!isSigningKey(alias)) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        createKeysM(alias, false)
      } else {
        createKeysJBMR2(context, alias)
      }
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
  @Throws(NoSuchProviderException::class,
      NoSuchAlgorithmException::class,
      InvalidAlgorithmParameterException::class)
  private fun createKeysJBMR2(context: Context, alias: String) {

    val start = GregorianCalendar()
    val end = GregorianCalendar()
    end.add(Calendar.YEAR, AMOUNT)

    val spec = KeyPairGeneratorSpec.Builder(context)
        // You'll use the alias later to retrieve the key. It's a key
        // for the key!
        .setAlias(alias)
        .setSubject(X500Principal("CN=" + alias))
        .setSerialNumber(BigInteger.valueOf(alias.hashCode().toLong()))
        // Date range of validity for the generated pair.
        .setStartDate(start.time)
        .setEndDate(end.time)
        .build()

    val kpGenerator = KeyPairGenerator.getInstance(
        SecurityConstants.TYPE_RSA,
        SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE
    )
    kpGenerator.initialize(spec)
    val kp = kpGenerator.generateKeyPair()
  }

  @TargetApi(Build.VERSION_CODES.M)
  private fun createKeysM(alias: String, requireAuth: Boolean) {
    try {
      val keyPairGenerator = KeyPairGenerator.getInstance(
          KeyProperties.KEY_ALGORITHM_RSA, SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
      val builder = KeyGenParameterSpec.Builder(
          alias,
          KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
      )
      keyPairGenerator.initialize(builder
          .setAlgorithmParameterSpec(RSAKeyGenParameterSpec(KEY_SIZE, F4))
          .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
          .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
          .setDigests(
              KeyProperties.DIGEST_SHA256,
              KeyProperties.DIGEST_SHA384,
              KeyProperties.DIGEST_SHA512
          )
          // Only permit the private key to be used if the user authenticated
          // within the last five minutes.
          .setUserAuthenticationRequired(requireAuth)
          .build()
      )
      val keyPair = keyPairGenerator.generateKeyPair()
    } catch (e: NoSuchProviderException) {
      throw RuntimeException(e)
    } catch (e: NoSuchAlgorithmException) {
      throw RuntimeException(e)
    } catch (e: InvalidAlgorithmParameterException) {
      throw RuntimeException(e)
    }
  }

  fun decrypt(alias: String, cipherText: String): ByteArray {
    try {
      val privateKey = getPrivateKeyEntry(alias)!!.privateKey
      val cipher = cipher
      cipher.init(Cipher.DECRYPT_MODE, privateKey)
      return cipher.doFinal(Base64.decode(cipherText, Base64.NO_WRAP))
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }

  fun deleteKeys(alias: String) {
    try {
      val ks = KeyStore.getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
      ks.deleteEntry(alias)
    } catch (e: KeyStoreException) {
      throw RuntimeException(e)
    }
  }

  fun encrypt(alias: String, plaintext: ByteArray): String {
    try {
      val publicKey = getPrivateKeyEntry(alias)!!.certificate.publicKey
      val cipher = cipher
      cipher.init(Cipher.ENCRYPT_MODE, publicKey)
      return Base64.encodeToString(cipher.doFinal(plaintext), Base64.NO_WRAP)
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }

  private fun getPrivateKeyEntry(alias: String): KeyStore.PrivateKeyEntry? {
    try {
      val ks = KeyStore
          .getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
      ks.load(null)
      val entry = ks.getEntry(alias, null)

      if (entry == null) {
        Log.w(TAG, "No key found under alias: $alias. Exiting signData()...")
        return null
      }

      if (entry !is KeyStore.PrivateKeyEntry) {
        Log.w(TAG, "Not an instance of a PrivateKeyEntry. Exiting signData()...")
        return null
      }
      return entry
    } catch (e: Exception) {
      Log.e(TAG, e.message, e)
      return null
    }
  }

  /**
   * JBMR2+ If Key with the default alias exists, returns true, else false.
   * on pre-JBMR2 returns true always.
   */
  private fun isSigningKey(alias: String): Boolean {
    return try {
      val keyStore = KeyStore.getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
      keyStore.load(null)
      keyStore.containsAlias(alias)
    } catch (e: Exception) {
      Log.e(TAG, e.message, e)
      false
    }
  }

  interface SecurityConstants {
    companion object {
      const val KEYSTORE_PROVIDER_ANDROID_KEYSTORE = "AndroidKeyStore"
      const val TYPE_RSA = "RSA"
      const val PADDING_TYPE = "PKCS1Padding"
      const val BLOCKING_MODE = "NONE"

      val SIGNATURE_SHA256withRSA = "SHA256withRSA"
      val SIGNATURE_SHA512withRSA = "SHA512withRSA"
    }
  }
}