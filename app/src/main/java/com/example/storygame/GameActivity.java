package com.example.storygame;
import static android.view.View.GONE;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.storygame.model.LoadData;
import com.example.storygame.model.SaveData;
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
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


public class GameActivity extends AppCompatActivity {
    private static int state = 1;
    private Button b1, b2, b3, b4;
    private Button xT;
    private GridLayout gridLayout, cG;
    private TextView indication, cT;
    private int counter = 0;
    private boolean test = false;
    private String last = "";
    private LinearLayout swipe ;
    private CustomScrollView myScrollView;

    private SaveData saveData;

    private Chronometer chronometer;

    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741";
    private static final String TAG = "MyActivity";
    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);
    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;
    private AdView adView;
    private FrameLayout adContainerView, adContainerView2;
    private AtomicBoolean initialLayoutComplete = new AtomicBoolean(false);

    protected void onPause  () {
        super.onPause();



        LoadData.loadSave(MainActivity.getAppContext(),"save.txt");

        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        saveData.setChronoValue(elapsedMillis);

        Log.d("LOL", String.valueOf(chronometer.getBase()));
        saveData.writeFileOnInternalStorage(MainActivity.getAppContext(),"save.txt",saveData);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        myScrollView = (CustomScrollView) findViewById(R.id.myScroll);
        myScrollView.setEnableScrolling(false); // disable scrolling
        indication = findViewById(R.id.indication);

        saveData = new SaveData();
        gridLayout = findViewById(R.id.gridLayout);



        chronometer = findViewById(R.id.chrono);






        b1 = findViewById(R.id.choice1);
        b2 = findViewById(R.id.choice2);
        b3 = findViewById(R.id.choice3);
        b4 = findViewById(R.id.choice4);
        swipe  = findViewById(R.id.swipeLayout);
        TextView chap = findViewById(R.id.chapitre);
        TextView textView = findViewById(R.id.Text_game);
        cG = findViewById(R.id.counterGrid);
        cT = findViewById(R.id.counterText);
        xT = findViewById(R.id.circleButton1);
        ShakeDetector shakeDetector = new ShakeDetector(this);






        String Rules = "L'histoire doit se dérouler en 5 cgapitre, qui sont chacun lié par 4 possibilité de choix, à chaque requete tu dois continuer l'histoire en fonction du choix précedent, jusqu'à ce que ce soit le dernier chapitre";
        String structure = "Choix 1 Choix 2 Choix 3 Choix 4";
        String structure = "Texte Choix 1 Choix 2 Choix 3 Choix 4";



        String Rv = "Raconte moi une histoire intéractive en appliquant ces règles :"+Rules+"Pour information tu es au Chapitre ="+state+"Mais il ne faut pas l'écrire"+"Suit la structure ="+structure;

        if (LoadData.alreadySaved(MainActivity.getAppContext(),"save.txt")){
            saveData = LoadData.loadSave(MainActivity.getAppContext(),"save.txt");
            chronometer.setBase( SystemClock.elapsedRealtime() - saveData.getChronoValue());
            String savedScenario = saveData.getScenario();
            String RvSave = "Affiche juste l'histoire suivante" + savedScenario + " en respectant ces règles"  + Rules + "Pour information tu es au Chapitre ="+state+"Mais il ne faut pas l'écrire"+"Suit la structure ="+structure;
            Rv = RvSave;
        }



        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        ChatGPTAPI.chatGPT(Rv, new ChatGPTAPI.ChatGPTListener() {
            @Override
            public void onChatGPTResponse(String response) {


                System.out.println("Start reply " + response);
                String response2 = response.replace("\\n", "SEP");
                int choixIndex = response2.indexOf("SEPSEP");
                String texte = response2.substring(0, choixIndex).trim();
                last += "suite =" + texte;
                textView.setText(texte);
                chap.setText("Chapitre "+state);
                fillAll(response2, b1, b2, b3, b4, textView);
            }
        });

        swipe.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                setRandomFontColor(textView);
            }

            public void onSwipeRight() {
                textView.setTextColor(Color.WHITE);
            }

            public void onSwipeTop() {
                cG.setVisibility(View.VISIBLE);
                cT.setVisibility(View.VISIBLE);
                xT.setVisibility(View.VISIBLE);
                updateCounterText();
                int temp = 10-counter;
                indication.setText("clique "+temp+" fois pour chargé les choix");


            }

            public void onSwipeBottom() {
                cG.setVisibility(GONE);
                cT.setVisibility(GONE);
                xT.setVisibility(GONE);
                indication.setText("swipe vers le haut pour afficher la zone de clique");
            }
        });
        shakeDetector.setOnShakeListener(() -> {
            RelativeLayout rl = findViewById(R.id.relativeLayout);
            setRandomBackgroundColor(rl);
            });

        b1 = findViewById(R.id.choice1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextFill = findViewById(R.id.Text_game).toString();
                String t1 = b1.getText().toString();
                saveData.addChoices(t1);
                saveData.writeFileOnInternalStorage(MainActivity.getAppContext(),"save.json",saveData);

                String text0 = "Ecris suite de l'hisoire interactive avec les règles :"+Rules+" pour rappel" + last + ", maintenant on en est là" + TextFill + " et j'ai fait ce choix" + t1.substring(3, t1.length() - 1) +"Pour information tu es au Chapitre ="+state+"Mais il ne faut pas l'écrire"+"Suit la structure ="+structure;
                ChatGPTAPI.chatGPT(text0, new ChatGPTAPI.ChatGPTListener() {
                    @Override
                    public void onChatGPTResponse(String response) {
                        TextView textView = findViewById(R.id.Text_game);

                        chap.setText("Chapitre "+state);
                        fillAll(response, b1, b2, b3, b4, textView);

                    }
                });
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextFill = findViewById(R.id.Text_game).toString();
                String t4 = b4.getText().toString();
                ChatGPTAPI.chatGPT(" Raconte moi la suite de l'hisoire : pour rappel" + last + ", maintenant on en est là" + TextFill + " et j'ai fait ce choix" + t4.substring(3, t4.length() - 1) + " raconte moi ce qu'il se passe,sous forme texte : -exemple-,puis 4 choix", new ChatGPTAPI.ChatGPTListener() {
                    @Override
                    public void onChatGPTResponse(String response) {
                        TextView textView = findViewById(R.id.Text_game);

                        chap.setText("Chapitre "+state);
                        fillAll(response, b1, b2, b3, b4, textView);

                    }
                });
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextFill = findViewById(R.id.Text_game).toString();
                String t3 = b3.getText().toString();
                ChatGPTAPI.chatGPT("Raconte moi la suite de l'hisoire : pour rappel" + last + ", maintenant on en est là" + TextFill + " et j'ai fait ce choix" + t3.substring(3, t3.length() - 1) + " raconte moi ce qu'il se passe,sous forme texte : -exemple-,puis 4 choix", new ChatGPTAPI.ChatGPTListener() {
                    @Override
                    public void onChatGPTResponse(String response) {
                            TextView textView = findViewById(R.id.Text_game);
                            chap.setText("Chapitre "+state);
                            fillAll(response, b1, b2, b3, b4, textView);

                        }
                });
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextFill = findViewById(R.id.Text_game).toString();
                String t2 = b2.getText().toString();
                ChatGPTAPI.chatGPT("Raconte moi la suite de l'hisoire : pour rappel" + last + ", maintenant on en est là" + TextFill + " et j'ai fait ce choix" + t2.substring(3, t2.length() - 1) + " raconte moi ce qu'il se passe,sous forme texte : -exemple-,puis 4 choix", new ChatGPTAPI.ChatGPTListener() {
                    @Override
                    public void onChatGPTResponse(String response) {
                        TextView textView = findViewById(R.id.Text_game);
                        chap.setText("Chapitre "+state);
                        fillAll(response, b1, b2, b3, b4, textView);

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
        int temp = 11-counter;
        indication.setText("clique "+temp+" fois pour chargé les choix");
        updateCounterText();
    }

    private void updateCounterText() {
        if (counter > 9){
            cG.setVisibility(GONE);
            cT.setVisibility(GONE);
            xT.setVisibility(GONE);
            gridLayout.setVisibility(View.VISIBLE);
            myScrollView.setEnableScrolling(true);
            indication.setVisibility(GONE);
        }
        else{
            cT.setText(String.valueOf(counter));
        }

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
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                    }
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
        float adWidthPixels = 0;
        if (test != true) {
            adWidthPixels = adContainerView.getWidth();
            test = true;
        } else {
            adWidthPixels = adContainerView2.getWidth();
        }


        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    public void fillAll(String response, Button b1, Button b2, Button b3, Button b4, TextView tv) {
        System.out.println("RRRRRRRRRRRR"+response);
        if (response == null || response.isEmpty()) {
            Log.e(TAG, "Response is null NULL");
            return;
        }
        if (response.startsWith("Chapitre 1:")) {
            // Supprimer "Chapitre 1" de la chaîne
            response = response.substring("Chapitre 1:".length()).trim();
        }
        if (response.startsWith("Chapitre 1")) {
            // Supprimer "Chapitre 1" de la chaîne
            response = response.substring("Chapitre 1".length()).trim();
        }
        String response2 = response.replace("\\n", "SEP");

        Log.d(TAG, "Fill all reply: " + response);

        String[] responseParts = response2.split("SEP");
        if (responseParts.length < 4) {
            Log.e(TAG, "Response isn't good");
            return;
        }

        String texte = responseParts[0].trim()+responseParts[1].trim();
        tv.setText(texte);
        last = texte;
        saveData.setScenario(last);
        saveData.writeFileOnInternalStorage(MainActivity.getAppContext(),"save.txt",saveData);

        b1.setText(responseParts[2]);
        b2.setText(responseParts[3]);
        b3.setText(responseParts[4]);
        b4.setText(responseParts[5]);

        state++;
    }

    public void onBackPressed(View view) {
        onBackPressed(); // Cela appelle la méthode onBackPressed() par défaut
    }
    public static void setRandomBackgroundColor(RelativeLayout rlv) {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        int randomColor = Color.rgb(red, green, blue);
        rlv.setBackgroundColor(randomColor);}

    public static void setRandomFontColor(TextView rlv) {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        int randomColor = Color.rgb(red, green, blue);
        rlv.setTextColor(randomColor);}
}