package mivs.ktozjakbog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class Activity_Splash extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    private AppOpenAd appOpenAd = null;
    private boolean isShowingAd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicjalizacja Mobile Ads SDK.
        MobileAds.initialize(this, initializationStatus -> {
            Log.d(TAG, "Mobile Ads SDK initialized.");
            loadAd();
        });
    }

    /**
     * Ładuje reklamę typu App Open Ad.
     */
    private void loadAd() {
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                this,
                BuildConfig.AD_START_UNIT_ID,
                request,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd ad) {
                        // Reklama została pomyślnie załadowana.
                        appOpenAd = ad;
                        Log.i(TAG, "App Open Ad loaded.");
                        // Po załadowaniu, od razu próbujemy ją wyświetlić.
                        showAdIfAvailable();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Błąd ładowania reklamy.
                        Log.d(TAG, "App Open Ad failed to load: " + loadAdError.getMessage());
                        appOpenAd = null;
                        // Niezależnie od błędu, przechodzimy do głównej aplikacji.
                        navigateToMainApp();
                    }
                });
    }

    /**
     * Wyświetla reklamę, jeśli została załadowana i nie jest już wyświetlana.
     */
    private void showAdIfAvailable() {
        if (isShowingAd) {
            Log.d(TAG, "The app open ad is already showing.");
            return;
        }

        if (appOpenAd == null) {
            Log.d(TAG, "The app open ad is not ready yet.");
            navigateToMainApp();
            return;
        }

        // Ustawiamy callbacki, aby wiedzieć, co dzieje się z reklamą.
        appOpenAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Wywoływane, gdy użytkownik zamknie reklamę.
                        Log.d(TAG, "Ad was dismissed.");
                        appOpenAd = null;
                        isShowingAd = false;
                        navigateToMainApp();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        // Błąd podczas próby wyświetlenia.
                        Log.d(TAG, "Ad failed to show: " + adError.getMessage());
                        appOpenAd = null;
                        isShowingAd = false;
                        navigateToMainApp();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Reklama została pomyślnie wyświetlona.
                        Log.d(TAG, "Ad showed fullscreen content.");
                    }
                });

        isShowingAd = true;
        appOpenAd.show(this);
    }

    /**
     * Metoda odpowiedzialna za przejście do głównej aktywności aplikacji.
     */
    private void navigateToMainApp() {
        if (!isFinishing()) {
            Intent intent = new Intent(Activity_Splash.this, Activity_Main.class);
            startActivity(intent);
            finish();
        }
    }
}