package com.prolificinteractive.patrons.conceal

interface Encryption {
  fun encrypt(value: String): String
  fun decrypt(value: String): String
}
