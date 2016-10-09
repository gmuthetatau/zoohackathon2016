package org.gmuthetatau.wildface;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ReportingService extends Service
{
    public ReportingService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
