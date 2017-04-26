package com.prolificinteractive.patrons.conceal

class NoKeyEncryption : KeyEncryption {
  override fun encrypt(value: String) = value
  override fun decrypt(value: String) = value
}