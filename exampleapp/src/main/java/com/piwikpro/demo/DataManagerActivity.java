package com.piwikpro.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.piwik.sdk.Tracker;
import pro.piwik.sdk.extra.PiwikApplication;
import pro.piwik.sdk.extra.TrackHelper;

/**
 * Created by kshumelchyk on 10/15/17.
 */

public class DataManagerActivity extends Activity {

    private EditText userId;
    private EditText userMail;
    private EditText name;
    private EditText value;
    private Button track;
    private TextView profileAttributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datamanager);
        ButterKnife.bind(this);

        userId = (EditText) findViewById(R.id.userId);
        userMail = (EditText) findViewById(R.id.userMail);
        name = (EditText) findViewById(R.id.name);
        value = (EditText) findViewById(R.id.value);
        track = (Button) findViewById(R.id.trackDataManagerEvent);
        profileAttributes = (TextView) findViewById(R.id.profileAttributes);

        userId.setText(getTracker().getUserId());
        userMail.setText(getTracker().getUserMail());
        track.setEnabled(!TextUtils.isEmpty(getTracker().getUserMail()) || !TextUtils.isEmpty(getTracker().getUserId()));
    }

    private Tracker getTracker() {
        return ((PiwikApplication) getApplication()).getTracker();
    }

    @OnClick(R.id.setUserData)
    void onSetUserDataButton(View view) {
        getTracker().setUserId(userId.getText().toString());
        getTracker().setUserMail(userMail.getText().toString());
        track.setEnabled(!TextUtils.isEmpty(getTracker().getUserMail()) || !TextUtils.isEmpty(getTracker().getUserId()));
    }

    @OnClick(R.id.trackDataManagerEvent)
    void onTrackDataManagerEventButton(View view) {
        TrackHelper.track().audienceManagerSetProfileAttribute(name.getText().toString(), value.getText().toString()).with(getTracker());
    }

    @OnClick(R.id.getDataManagerProfileAttributes)
    void onGetDataProfileAttributes(View view) {
        profileAttributes.setText("");
        getTracker().audienceManagerGetProfileAttributes(new Tracker.OnGetProfileAttributes() {
            @Override
            public void onAttributesReceived(Map<String, String> attributes) {
                profileAttributes.setText(attributes.toString());
            }

            @Override
            public void onError(String errorData) {
                errorData = TextUtils.isEmpty(errorData) ? "Network error": errorData;
                profileAttributes.setText(errorData);
            }
        });
    }

}
