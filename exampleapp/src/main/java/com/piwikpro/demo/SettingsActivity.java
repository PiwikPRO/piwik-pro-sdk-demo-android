/*
 * Android SDK for Piwik
 *
 * @link https://github.com/piwik/piwik-android-sdk
 * @license https://github.com/piwik/piwik-sdk-android/blob/master/LICENSE BSD-3 Clause
 */

package com.piwikpro.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.piwik.sdk.dispatcher.Packet;
import pro.piwik.sdk.extra.PiwikApplication;
import pro.piwik.sdk.extra.TrackHelper;
import timber.log.Timber;


public class SettingsActivity extends Activity {

    private void refreshUI(final Activity settingsActivity) {
        // auto track button
        Button button = (Button) findViewById(R.id.bindtoapp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackHelper.track().screens(getApplication()).with(((PiwikApplication) getApplication()).getTracker());
            }
        });

        // Dry run
        CheckBox dryRun = (CheckBox) findViewById(R.id.dryRunCheckbox);
        dryRun.setChecked(((PiwikApplication) getApplication()).getTracker().getDryRunTarget() != null);
        dryRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PiwikApplication) getApplication()).getTracker().setDryRunTarget(((CheckBox) v).isChecked() ? Collections.synchronizedList(new ArrayList<Packet>()) : null);
            }
        });

        // out out
        CheckBox optOut = (CheckBox) findViewById(R.id.optOutCheckbox);
        optOut.setChecked(((PiwikApplication) getApplication()).getTracker().isOptOut());
        optOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PiwikApplication) getApplication()).getTracker().setOptOut(((CheckBox) v).isChecked());
            }
        });

        // prefixing
        CheckBox prefixing = (CheckBox) findViewById(R.id.prefixingCheckbox);
        prefixing.setChecked(((PiwikApplication) getApplication()).getTracker().isPrefixing());
        prefixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PiwikApplication) getApplication()).getTracker().setPrefixing(((CheckBox) v).isChecked());
            }
        });

        // Default custom vars
        CheckBox customVars = (CheckBox) findViewById(R.id.defaultCustomVarsCheckbox);
        customVars.setChecked(((PiwikApplication) getApplication()).getTracker().getIncludeDefaultCustomVars());
        optOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PiwikApplication) getApplication()).getTracker().setIncludeDefaultCustomVars(((CheckBox) v).isChecked());
            }
        });

        // dispatch interval
        EditText inputDispatchInterval = (EditText) findViewById(R.id.dispatchIntervallInput);
        String text = Long.toString(
                ((PiwikApplication) getApplication()).getTracker().getDispatchInterval() / 1000
        );
        inputDispatchInterval.setText(text);
        inputDispatchInterval.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        try {
                            int interval = Integer.valueOf(charSequence.toString().trim());
                            ((PiwikApplication) getApplication()).getTracker()
                                    .setDispatchInterval(interval * 1000);
                        } catch (NumberFormatException e) {
                            Timber.d("not a number: %s", charSequence.toString());
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                }

        );


        //session Timeout Input

        EditText inputSessionTimeout = (EditText) findViewById(R.id.sessionTimeoutInput);
        inputSessionTimeout.setText(Long.toString(
                (((PiwikApplication) getApplication()).getTracker().getSessionTimeout() / 60000)
        ));
        inputSessionTimeout.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        try {
                            int timeoutMin = Integer.valueOf(charSequence.toString().trim());
                            timeoutMin = Math.abs(timeoutMin);
                            ((PiwikApplication) getApplication()).getTracker()
                                    .setSessionTimeout(timeoutMin * 60000);
                        } catch (NumberFormatException e) {
                            Timber.d("not a number: %s", charSequence.toString());
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                }

        );

        // host inputSessionTimeout
        final EditText inputHost = (EditText) findViewById(R.id.hostInput);
        inputHost.setText(((DemoApp) getApplication()).getHost());

        //  siteId inputSessionTimeout
        final EditText inputSiteId = (EditText) findViewById(R.id.siteIdInput);
        inputSiteId.setText(((DemoApp) getApplication()).getSiteId());

        // update Host and siteId button
        Button buttonUpdateHostandSiteId = (Button) findViewById(R.id.updateHostSiteIdBtn);
        buttonUpdateHostandSiteId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = String.valueOf(inputHost.getText());
                String siteId = String.valueOf(inputSiteId.getText());
                if (!checkUrl(host)) {
                    inputHost.setError("Invalid host pattern");
                    return;
                }

                // todo add siteId check
                ((DemoApp) getApplication()).setHost(host);
                ((DemoApp) getApplication()).setSiteId(siteId);
                ((DemoApp) getApplication()).getNewTracker();

                try {
                    int interval = Integer.valueOf(((EditText) findViewById(R.id.dispatchIntervallInput)).getText().toString().trim());
                    ((PiwikApplication) getApplication()).getTracker().setDispatchInterval(interval * 1000);
                } catch (NumberFormatException e) {
                    Timber.d("not a number: %s", ((EditText) findViewById(R.id.dispatchIntervallInput)).getText().toString());
                }
                try {
                    int session = Integer.valueOf(((EditText) findViewById(R.id.sessionTimeoutInput)).getText().toString().trim());
                    ((PiwikApplication) getApplication()).getTracker().setSessionTimeout(session * 60000);
                } catch (NumberFormatException e) {
                    Timber.d("not a number: %s", ((EditText) findViewById(R.id.sessionTimeoutInput)).getText().toString());
                }

                refreshUI(SettingsActivity.this);


            }
        });
    }

    private boolean checkUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url);
        return m.matches();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        refreshUI(this);
    }

}
