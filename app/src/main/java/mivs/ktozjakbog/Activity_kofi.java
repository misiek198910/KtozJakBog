package mivs.ktozjakbog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_kofi extends AppCompatActivity {

    ImageButton button_back;
    TextView textViewKofi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kofi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_kofi_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener( v -> {
            finish();
            Intent myIntent = new Intent(this, Activity_Main.class);
            this.startActivity(myIntent);
        });

        ImageView btn = findViewById(R.id.kofi_button);
        btn.setOnClickListener(v ->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ko-fi.com/michals"));
            startActivity(browserIntent);
        });

        textViewKofi = findViewById(R.id.textView16);
        textViewKofi.setOnClickListener(v ->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ko-fi.com/michals"));
            startActivity(browserIntent);
        });


        ImageButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener( v -> {
            finish();
        });
    }
}