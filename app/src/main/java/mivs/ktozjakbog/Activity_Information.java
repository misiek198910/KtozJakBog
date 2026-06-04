package mivs.ktozjakbog;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Activity_Information extends AppCompatActivity {

    LinearLayout bottom_toolbar;
    ImageButton button_back;
    private FrameLayout adContainerView;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MobileAds.initialize(this, initializationStatus -> {});

        adContainerView = findViewById(R.id.ad_view_container);
        loadAdaptiveBanner();

        button_back = findViewById(R.id.button_back);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        button_back.setOnClickListener( v -> finish());
    }

    private void loadAdaptiveBanner() {
        adView = new AdView(this);
        adView.setAdUnitId(BuildConfig.AD_BANNER_ID);

        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Pobranie parametrów wyświetlacza w celu określenia szerokości okna reklamy
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Zwrócenie zoptymalizowanego, adaptacyjnego rozmiaru bannera
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}