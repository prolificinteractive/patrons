package com.prolificinteractive.patrons.conceal

interface ValueEncryption : Encryption {
  fun encryptSet(values: Set<String?>): Set<String?>
  fun decryptSet(values: Set<String?>): Set<String?>
}
