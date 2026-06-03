package mivs.ktozjakbog;

import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Activity_Nowenna extends AppCompatActivity {
    private FrameLayout adContainerView;
    private AdView adView;
    Button btn1, btn2;
    ImageButton btn_plus, btn_minus;
    TextView textView_texts;
    ScrollView scroolView1;
    String texts;
    int currentDay = 0;
    int text_size = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nowenna);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_nowenna), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MobileAds.initialize(this, initializationStatus -> {});

        adContainerView = findViewById(R.id.ad_view_container);
        loadAdaptiveBanner();

        btn1 = findViewById(R.id.button1_nowenna);
        btn2 = findViewById(R.id.button2_nowenna);
        btn_plus = findViewById(R.id.button_text_plus_novenna);
        btn_minus = findViewById(R.id.button_text_minus_novenna);
        scroolView1 = findViewById(R.id.scrollView1);
        textView_texts = findViewById(R.id.textView_texts_nowenna);

        btn1.setOnClickListener(v ->{
            if (currentDay == 29) { btn2.setEnabled(true); btn2.setAlpha(1f); }

            if (currentDay > 0) {
                currentDay -= 1;
                updadeContent();
            }

            if (currentDay == 0) { btn1.setEnabled(false); btn1.setAlpha(0.3f); }
        });

        btn2.setOnClickListener(v ->{
            if (currentDay < 30) {
                currentDay += 1;
                updadeContent();
            }

            if (currentDay > 0) { btn1.setEnabled(true); btn1.setAlpha(1f); }
            if (currentDay == 29) { btn2.setEnabled(false); btn2.setAlpha(0.3f); }
        });

        btn_plus.setOnClickListener(v ->{
            text_size ++;

            if (text_size == 24) { btn_plus.setEnabled(false); btn_minus.setEnabled(true); btn_plus.setAlpha(0.5f); }
            else { btn_plus.setEnabled(true); btn_minus.setEnabled(true); btn_minus.setAlpha(1f); }

            textView_texts.setTextSize(text_size);
        });

        btn_minus.setOnClickListener(v ->{
            text_size --;

            if (text_size == 12) { btn_minus.setEnabled(false); btn_plus.setEnabled(true); btn_minus.setAlpha(0.5f); }
            else { btn_minus.setEnabled(true); btn_plus.setEnabled(true); btn_plus.setAlpha(1f); }

            textView_texts.setTextSize(text_size);
        });

        updadeContent();
        btn1.setEnabled(false);
        btn1.setAlpha(0.3f);
    }

    public void updadeContent() {

        int dayNumber = currentDay + 1;
        texts = LoadData("Dzien_" + dayNumber);

        textView_texts.setText(Html.fromHtml(texts , Html.FROM_HTML_MODE_COMPACT));
        scroolView1.smoothScrollTo(0,0);
    }

    /** @noinspection CallToPrintStackTrace*/
    public String LoadData(String inFile) {
        String tContents;
        String partEN = "_en.txt";
        String partPL = "_pl.txt";
        String file2;

        Class_Prefs pref = new Class_Prefs();
        int locale = pref.LoadPrefInt(getApplicationContext(), "language_data");

        if (locale == 0) {
            file2 = inFile + partPL;
        } else {
            file2 = inFile + partEN;
        }

        try (InputStream stream = getAssets().open(file2)) {
            int size = stream.available();
            byte[] buffer = new byte[size];
            int bytesRead = stream.read(buffer);
            if (bytesRead != size) {
                throw new IOException("Could not read the entire file.");
            }
            tContents = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            tContents = "";
        }

        return tContents;
    }

    public void button_back_Clicked(View view) {
        finish();
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