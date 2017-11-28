package com.piwikpro.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.piwik.sdk.Tracker;
import pro.piwik.sdk.extra.PiwikApplication;

/**
 * Created by kshumelchyk on 10/24/17.
 */

public class CheckAudienceActivity extends Activity {

    private EditText audienceId;
    private EditText audienceRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkaudience);
        ButterKnife.bind(this);

        audienceId = (EditText) findViewById(R.id.audienceId);
        audienceRes = (EditText) findViewById(R.id.audienceRes);
    }

    private Tracker getTracker() {
        return ((PiwikApplication) getApplication()).getTracker();
    }

    @OnClick(R.id.checkMembership)
    void onCheckMembershipButton(View view) {
        audienceRes.setText("");
        getTracker().checkAudienceMembership(audienceId.getText().toString(), new Tracker.OnCheckAudienceMembership() {
            @Override
            public void onChecked(boolean isMember) {
                audienceRes.setText(String.valueOf(isMember));
            }

            @Override
            public void onError(String errorData) {
                errorData = TextUtils.isEmpty(errorData) ? "Network error": errorData;
                audienceRes.setText(errorData);
            }
        });
    }

}
