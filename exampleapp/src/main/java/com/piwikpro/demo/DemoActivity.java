/*
 * Android SDK for Piwik
 *
 * @link https://github.com/piwik/piwik-android-sdk
 * @license https://github.com/piwik/piwik-sdk-android/blob/master/LICENSE BSD-3 Clause
 */

package com.piwikpro.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.piwik.sdk.Tracker;
import pro.piwik.sdk.extra.DownloadTracker;
import pro.piwik.sdk.extra.EcommerceItems;
import pro.piwik.sdk.extra.PiwikApplication;
import pro.piwik.sdk.extra.TrackHelper;
import timber.log.Timber;


public class DemoActivity extends ActionBarActivity {
    int cartItems = 0;
    private EcommerceItems items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        items = new EcommerceItems();
    }

    private Tracker getTracker() {
        return ((PiwikApplication) getApplication()).getTracker();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.trackMainScreensViewButton)
    void onTrackMainScreensClicked(View view) {
        TrackHelper.track()
                .screens(Arrays.asList("android_test/test1", "android_test/test2"))
                .with(getTracker());
    }

    @OnClick(R.id.trackMainScreenViewButton)
    void onTrackMainScreenClicked(View view) {
        TrackHelper.track().screen("Main screen").title("test view").with(getTracker());
    }

    @OnClick(R.id.trackCustomVarsButton)
    void onTrackCustomVarsClicked(View view) {
        TrackHelper.track()
                .screen("/custom_vars")
                .title("Custom Vars")
                .variable(1, "first", "var")
                .variable(2, "second", "long value")
                .with(getTracker());
    }

    @OnClick(R.id.raiseExceptionButton)
    void onRaiseExceptionClicked(View view) {
        TrackHelper.track().exception(new Exception("OnPurposeException")).description("Send a fake exception from my app").fatal(false).with(getTracker());
    }

    @OnClick(R.id.trackGoalButton)
    void onTrackGoalClicked(View view) {
        float revenue;
        try {
            revenue = Integer.valueOf(
                    ((EditText) findViewById(R.id.goalTextEditView)).getText().toString()
            );
        } catch (Exception e) {
            TrackHelper.track().exception(e).description("wrong revenue").with(getTracker());
            revenue = 0;
        }
        TrackHelper.track().goal(1).revenue(revenue).with(getTracker());
    }

    @OnClick(R.id.addEcommerceItemButton)
    void onAddEcommerceItemClicked(View view) {
        List<String> skus = Arrays.asList("00001", "00002", "00003", "00004");
        List<String> names = Arrays.asList("Silly Putty", "Fishing Rod", "Rubber Boots", "Cool Ranch Doritos");
        List<String> categories = Arrays.asList("Toys & Games", "Hunting & Fishing", "Footwear", "Grocery");
        List<Integer> prices = Arrays.asList(449, 3495, 2450, 250);

        int index = cartItems % 4;
        int quantity = (cartItems / 4) + 1;

        items.addItem(new EcommerceItems.Item(skus.get(index))
                .name(names.get(index))
                .category(categories.get(index))
                .price(prices.get(index))
                .quantity(quantity));
        cartItems++;
    }

    @OnClick(R.id.trackEcommerceCartUpdateButton)
    void onTrackEcommerceCartUpdateClicked(View view) {
        TrackHelper.track().cartUpdate(8600).items(items).with(getTracker());
    }

    @OnClick(R.id.completeEcommerceOrderButton)
    void onCompleteEcommerceOrderClicked(View view) {
        TrackHelper.track()
                .order(String.valueOf(10000 * Math.random()), 10000)
                .subTotal(1000)
                .tax(2000)
                .shipping(3000)
                .discount(500)
                .items(items)
                .with(getTracker());
    }

    @OnClick(R.id.socialInteractionButton)
    void onTrackSocialInteraction(View view) {
        TrackHelper.track().socialInteraction("Like", "Facebook").target("Piwik app").with(getTracker());
    }

    @OnClick(R.id.campaignButton)
    void onTrackCampaign(View view) {
        try {
            URL url = new URL("http://example.org/offer?pk_campaign=Best-Seller4&pk_kwd=greenShoes4&pk_source=Newsletter_7&pk_medium=email&pk_content=discount");
            TrackHelper.track().campaign(url).with(getTracker());
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @OnClick(R.id.trackCustomDimenButton)
    void onTrackCustomDimension(View view) {
        TrackHelper.track().dimension(1, "visit").dimension(2, "screen").screen("/path").with(getTracker());
    }

    @OnClick(R.id.trackEventsButton)
    void onTrackEvent(View view) {
        TrackHelper.track().event("Android category", "Android action").with(getTracker());
    }

    @OnClick(R.id.trackDownloadsButton)
    void onTrackDownloads(View view) {
        DownloadTracker downloadTracker = new DownloadTracker(getTracker());
        DownloadTracker.Extra extra = new DownloadTracker.Extra.Custom() {
            @Override
            public boolean isIntensiveWork() {
                return false;
            }

            @Nullable
            @Override
            public String buildExtraIdentifier() {
                return "Demo Android download";
            }
        };

        TrackHelper.track().download(downloadTracker).identifier(extra).force().with(getTracker());
    }

    @OnClick(R.id.trackOutlinksButton)
    void onTrackOutlinks(View view) {
        try {
            TrackHelper.track().outlink(new URL("https://www.google.com")).with(getTracker());
        } catch (Exception ex) {
        }
    }

    @OnClick(R.id.trackSearchButton)
    void onTrackSearch(View view) {
        TrackHelper.track().search(((EditText) findViewById(R.id.searchText)).getText().toString()).with(getTracker());
    }

    @OnClick(R.id.trackContentButton)
    void onTrackContent(View view) {
        TrackHelper.track().impression("Android content impression").piece("banner").target("https://www.dn.se/").with(getTracker());
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dn.se/"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.dataManagerButton)
    void onLaunchDataManagerActivityButton(View view) {
        startActivity(new Intent(this, DataManagerActivity.class));
    }

    @OnClick(R.id.deviceIdButton)
    void  onDeviceIdButton(View view){
        startActivity(new Intent(this, DeviceIDActivity.class));
    }


    @OnClick(R.id.checkAudienceButton)
    void onCheckAudienceActivityButton(View view) {
        startActivity(new Intent(this, CheckAudienceActivity.class));
    }
}
