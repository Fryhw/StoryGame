package com.example.storygame;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;


public class GameActivity extends AppCompatActivity {

    private Button b1,xT, b2,b3,b4;
    private GridLayout gridLayout,cG;
    private TextView swipeView,cT;
    private int counter = 0;
    private boolean test = false;
    private String last ="";

    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741";
    private static final String TAG = "MyActivity";
    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);
    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;
    private AdView adView;
    private FrameLayout adContainerView,adContainerView2;
    private AtomicBoolean initialLayoutComplete = new AtomicBoolean(false);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gridLayout = findViewById(R.id.gridLayout);
        b1 = findViewById(R.id.choice1);
        b2 = findViewById(R.id.choice2);
        b3 = findViewById(R.id.choice3);
        b4 = findViewById(R.id.choice4);
        swipeView = findViewById(R.id.swipeView);
        cG = findViewById(R.id.counterGrid);
        cT = findViewById(R.id.counterText);
        xT = findViewById(R.id.circleButton1);
        ShakeDetector shakeDetector = new ShakeDetector(this);

        String Rv = "Raconte moi un histoire intéractive sous forme texte : -exemple-,puis 4 choix ";
        ChatGPTAPI.chatGPT(Rv, new ChatGPTAPI.ChatGPTListener() {
            @Override
            public void onChatGPTResponse(String response) {

                TextView textView = findViewById(R.id.Text_game);
                System.out.println(response);
                String response2 =response.replace("\\n","SEP");
                int choixIndex = response2.indexOf("SEPSEP");
                String texte = response2.substring(0, choixIndex).trim();
                last+= "suite ="+texte;
                textView.setText(texte);
                String temp = response2.replace(texte,"");
                String[] choixArray = temp.split("SEP");
                if (choixArray.length>2){
                    b1.setText(choixArray[2]);
                    b2.setText(choixArray[3]);
                    b3.setText(choixArray[4]);
                    b4.setText(choixArray[5]);
                }

                gridLayout.setVisibility(View.GONE);
                swipeView.setVisibility(View.VISIBLE);
            }
        });

        swipeView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                gridLayout.setVisibility(View.VISIBLE);
                swipeView.setVisibility(View.GONE);
            }
            public void onSwipeRight(){
                swipeView.setVisibility(View.GONE);
                cG.setVisibility(View.VISIBLE);
                cT.setVisibility(View.VISIBLE);
                xT.setVisibility(View.VISIBLE);
                updateCounterText();
            }
            public void onSwipeTop(){

            }
            public void onSwipeBottom(){

            }
        });
        shakeDetector.setOnShakeListener(() -> {
            System.out.println("BOUMMM");
        });


        b1 = findViewById(R.id.choice1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextFill = findViewById(R.id.Text_game).toString();
                String t1 = b1.getText().toString();
                ChatGPTAPI.chatGPT("Pour rappel"+last+", maintenant on en est là"+TextFill+" et j'ai fait ce choix" +t1, new ChatGPTAPI.ChatGPTListener() {
                    @Override
                    public void onChatGPTResponse(String response) {
                        TextView textView = findViewById(R.id.Text_game);
                        //int choixIndex = response.indexOf("\n\n");
                        //String texte = response.substring(0, choixIndex).trim();
                        textView.setText(response);

                    }
                });
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextFill = findViewById(R.id.Text_game).toString();
                String t4 = b4.getText().toString();
                ChatGPTAPI.chatGPT("Pour rappel"+last+", maintenant on en est là"+TextFill+" et j'ai fait ce choix" +t4, new ChatGPTAPI.ChatGPTListener() {
                    @Override
                    public void onChatGPTResponse(String response) {
                        TextView textView = findViewById(R.id.Text_game);
                        //int choixIndex = response.indexOf("\n\n");
                        //String texte = response.substring(0, choixIndex).trim();
                        textView.setText(response);

                    }
                });
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextFill = findViewById(R.id.Text_game).toString();
                String t3 = b3.getText().toString();
                ChatGPTAPI.chatGPT("Pour rappel"+last+", maintenant on en est là"+TextFill+" et j'ai fait ce choix" +t3, new ChatGPTAPI.ChatGPTListener() {
                    @Override
                    public void onChatGPTResponse(String response) {
                        TextView textView = findViewById(R.id.Text_game);
                        //int choixIndex = response.indexOf("\n\n");
                        //String texte = response.substring(0, choixIndex).trim();
                        textView.setText(response);

                    }
                });
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextFill = findViewById(R.id.Text_game).toString();
                String t2 = b2.getText().toString();
                ChatGPTAPI.chatGPT("Pour rappel"+last+", maintenant on en est là"+TextFill+" et j'ai fait ce choix" +t2, new ChatGPTAPI.ChatGPTListener() {
                    @Override
                    public void onChatGPTResponse(String response) {
                        TextView textView = findViewById(R.id.Text_game);
                        //int choixIndex = response.indexOf("\n\n");
                        //String texte = response.substring(0, choixIndex).trim();
                        textView.setText(response);

                    }
                });
            }
        });
        adContainerView = findViewById(R.id.ad_view_container);
        adContainerView2 = findViewById(R.id.ad_view_container2);

        // Log the Mobile Ads SDK version.
        Log.d(TAG, "Google Mobile Ads SDK Version: " + MobileAds.getVersion());

        googleMobileAdsConsentManager =
                GoogleMobileAdsConsentManager.getInstance(getApplicationContext());
        googleMobileAdsConsentManager.gatherConsent(
                this,
                consentError -> {
                    if (consentError != null) {
                        // Consent not obtained in current session.
                        Log.w(
                                TAG,
                                String.format("%s: %s", consentError.getErrorCode(), consentError.getMessage()));
                    }

                    if (googleMobileAdsConsentManager.canRequestAds()) {
                        initializeMobileAdsSdk();
                    }

                    if (googleMobileAdsConsentManager.isPrivacyOptionsRequired()) {
                        // Regenerate the options menu to include a privacy setting.
                        invalidateOptionsMenu();
                    }
                });

        // This sample attempts to load ads using consent obtained in the previous session.
        if (googleMobileAdsConsentManager.canRequestAds()) {
            initializeMobileAdsSdk();
        }
        adContainerView
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        () -> {
                            if (!initialLayoutComplete.getAndSet(true)
                                    && googleMobileAdsConsentManager.canRequestAds()) {
                                loadBanner();
                            }
                        });

        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345")).build());
    }
    public void incrementCounter(View view) {
        counter++;
        updateCounterText();
    }

    private void updateCounterText() {
        cT.setText(String.valueOf(counter));
    }

public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        MenuItem moreMenu = menu.findItem(R.id.action_more);
        moreMenu.setVisible(googleMobileAdsConsentManager.isPrivacyOptionsRequired());
        return true;
        }
public boolean onOptionsItemSelected(MenuItem item) {
        View menuItemView = findViewById(item.getItemId());
        PopupMenu popup = new PopupMenu(this, menuItemView);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(
        popupMenuItem -> {
        if (popupMenuItem.getItemId() == R.id.privacy_settings) {
        // Handle changes to user consent.
        googleMobileAdsConsentManager.showPrivacyOptionsForm(
        this,
        formError -> {
        if (formError != null) {
        Toast.makeText(this, formError.getMessage(), Toast.LENGTH_SHORT).show();
        }
        });
        return true;
        }
        return false;
        });
        return super.onOptionsItemSelected(item);
        }


private void loadBanner() {
        // Create a new ad view.
        AdView adView1 = new AdView(this);
        adView1.setAdUnitId(AD_UNIT_ID);
        adView1.setAdSize(getAdSize());

        // Add the first ad view to the first container.
        adContainerView.removeAllViews();
        adContainerView.addView(adView1);

        // Start loading the ad in the background.
        AdRequest adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);

        // Create a new ad view for the second container.
        AdView adView2 = new AdView(this);
        adView2.setAdUnitId(AD_UNIT_ID); // Change the ad unit ID if necessary.
        adView2.setAdSize(getAdSize()); // You may need to adjust the ad size.

        // Add the second ad view to the second container.
        adContainerView2.removeAllViews();
        adContainerView2.addView(adView2);

        AdRequest adRequest2 = new AdRequest.Builder().build();
        adView2.loadAd(adRequest2);
        }

private void initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
        return;
        }

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(
        this,
        new OnInitializationCompleteListener() {
public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        // Load an ad.
        if (initialLayoutComplete.get()) {
        loadBanner();
        }
        }

private AdSize getAdSize() {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;
        float adWidthPixels=0;
        if (test != true){
        adWidthPixels = adContainerView.getWidth();
        test = true;
        }
        else {
        adWidthPixels = adContainerView2.getWidth();
        }


        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
        adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
        }
}