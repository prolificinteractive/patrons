package com.prolificinteractive.sample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.facebook.android.crypto.keychain.AndroidConceal;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.prolificinteractive.patrons.Preference;
import com.prolificinteractive.patrons.StringPreference;
import com.prolificinteractive.patrons.conceal.ConcealIntPreference;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  private static final Random random = new Random(50);

  private Preference<Integer> randomPreference;
  private Preference<String> encryptedPreference;

  @Override protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    final Crypto crypto = AndroidConceal
        .get()
        .createDefaultCrypto(new SharedPrefsBackedKeyChain(
            this,
            CryptoConfig.KEY_256
        ));

    randomPreference = new ConcealIntPreference(crypto, prefs, "random");
    encryptedPreference = new StringPreference(prefs, "random");

    final View save = findViewById(R.id.save);
    final View extract = findViewById(R.id.extract);
    final TextView display = (TextView) findViewById(R.id.display);
    final TextView encrypted = (TextView) findViewById(R.id.encrypted);

    save.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {

        randomPreference.set(random.nextInt());
      }
    });

    extract.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {
        display.setText(String.valueOf(randomPreference.get()));
        encrypted.setText(String.valueOf(encryptedPreference.get()));
      }
    });
  }
}
