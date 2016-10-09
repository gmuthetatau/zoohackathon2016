package org.gmuthetatau.wildface;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.WearableListView;
import android.widget.Toast;

import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IncidentReportActivity extends WearableActivity implements WearableListView.ClickListener {

    private static final SimpleDateFormat REPORT_DATE_FORMAT =
            new SimpleDateFormat("YYYY-MM-DD HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private WearableListView mListView;
    String[] elements = {"Shop/Market", "Restaurant/Eatery", "Live Animal Display", "Poaching", "Other"};
    private SendToPhone sendToPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_report);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mListView = (WearableListView) findViewById(R.id.wearable_list);
        mListView.setAdapter(new Adapter(this, elements));
        mListView.setClickListener(this);
        sendToPhone = new SendToPhone(this);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
//            mTextView.setTextColor(getResources().getColor(android.R.color.white));
//            mClockView.setVisibility(View.VISIBLE);
//
//            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
//            mTextView.setTextColor(getResources().getColor(android.R.color.black));
//            mClockView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Integer tag = (Integer) viewHolder.itemView.getTag();
//        Toast.makeText(getApplicationContext(), "Clicked item " + tag, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Clicked item " + tag);
        startActivity(intent);
        PutDataMapRequest pdmr = sendToPhone.getDataMap("/report/");
        DataMap map = pdmr.getDataMap();
        map.putString("report_type", coerceTag(tag));
        map.putString("event_time", REPORT_DATE_FORMAT.format(new Date()));
        sendToPhone.sendData(pdmr);



    }
    public String coerceTag(int x)
    {
        switch(x)
        {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            default:
                return "E";
        }
    }

    @Override
    public void onTopEmptyRegionClick() {
    }
}
