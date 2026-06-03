package mivs.ktozjakbog;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Activity_Apps extends AppCompatActivity {

    ImageButton button_back;
    private FrameLayout adContainerView;
    private AdView adView;
    ConstraintLayout main;
    LinearLayout panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9,
    panel10, panel11, panel12, panel13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_apps);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MobileAds.initialize(this, initializationStatus -> {});

        adContainerView = findViewById(R.id.ad_view_container);
        loadAdaptiveBanner();

        main = findViewById((R.id.main));
        panel1 = findViewById(R.id.panel1);
        panel2 = findViewById(R.id.panel2);
        panel3 = findViewById(R.id.panel3);
        panel4 = findViewById(R.id.panel4);
        panel5 = findViewById(R.id.panel5);
        panel6 = findViewById(R.id.panel6);
        panel7 = findViewById(R.id.panel7);
        panel8 = findViewById(R.id.panel8);
        panel9 = findViewById(R.id.panel9);
        panel10 = findViewById(R.id.panel10);
        panel11 = findViewById(R.id.panel11);
        panel12 = findViewById(R.id.panel12);
        panel13 = findViewById(R.id.panel13);

        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener( v -> finish());

        panel1.setOnClickListener(v ->
                OpenStore("mivs.objawienia"));
        panel2.setOnClickListener(v ->
                OpenStore("pakiet.rachuneksumienia"));
        panel3.setOnClickListener(v ->
                OpenStore("pakiet.rachuneksumienia_plus"));
        panel4.setOnClickListener(v ->
                OpenStore("mivs.niewolnik_maryi"));
        panel5.setOnClickListener(v ->
                OpenStore("mivs.niewolnik_maryi_plus"));
        panel6.setOnClickListener(v ->
                OpenStore("kalkulator.cnc"));
        panel7.setOnClickListener(v ->
                OpenStore("kalkulator.cnc.plus"));
        panel8.setOnClickListener(v ->
                OpenStore("mivs.m_j_r_aniec"));
        panel9.setOnClickListener(v ->
                OpenStore("mivs.m_j_r_aniec_plus"));
        panel10.setOnClickListener(v ->
                OpenStore("mivs.kalendarz_liturgiczny"));
        panel11.setOnClickListener(v ->
                OpenStore("mivs.kalendarz_liturgiczny_plus"));
        panel12.setOnClickListener(v ->
                OpenStore("droga_krzyzowa.droga_krzyzowa"));
        panel13.setOnClickListener(v ->
                OpenStore("droga_krzyzowa.droga_krzyzowa_plus"));
    }
    public void OpenStore(String packageName)
    {
        try
        {
            startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("market://details?id=" + packageName)));
        }
        catch (Exception e) //Jeżeli sklep jest niedostepny
        {
            startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
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