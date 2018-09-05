package com.piwikpro.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import pro.piwik.sdk.Tracker;
import pro.piwik.sdk.extra.PiwikApplication;

public class DeviceIDActivity extends Activity {

    private TextView deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceid);
        ButterKnife.bind(this);
        deviceId = (TextView) findViewById(R.id.deviceId);
        Switch state = (Switch) findViewById(R.id.use_deviceId);
        boolean isTrackDeviceId = getTracker().isTrackDeviceId();
        state.setChecked(isTrackDeviceId);
        showDeviceIdIfChecked(isTrackDeviceId);


    }

    @OnCheckedChanged(R.id.use_deviceId)
    void onUseDeviceID(CompoundButton buttonView, boolean isChecked) {
        getTracker().setTrackDeviceId(isChecked);
        showDeviceIdIfChecked(isChecked);

    }

    private void showDeviceIdIfChecked(boolean isChecked) {
        if (isChecked) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String advertId = null;
                try {
                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(DeviceIDActivity.this);
                    advertId = adInfo != null ? adInfo.getId() : null;
                    Message message = handler.obtainMessage();
                    Bundle data = new Bundle();
                    data.putString("KEY", advertId);
                    message.setData(data);
                    handler.sendMessage(message);
                } catch (Exception e) {
                    advertId = null;
                }

            }
        }).start();

        } else {
            deviceId.setVisibility(View.GONE);
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            Bundle data = message.getData();
            String advertId = data.getString("KEY", null);
            if (advertId != null) {
                deviceId.setVisibility(View.VISIBLE);
                deviceId.setText(String.format("Current device ID is: \n %s", advertId));

            } else {
                deviceId.setVisibility(View.GONE);
            }
        }

    };

    private Tracker getTracker() {
        return ((PiwikApplication) getApplication()).getTracker();
    }
}
