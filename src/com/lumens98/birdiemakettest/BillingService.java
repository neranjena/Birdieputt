package com.lumens98.birdiemakettest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
  
import com.android.vending.billing.IMarketBillingService;
  
public class BillingService extends Service implements ServiceConnection{
        
        private static final String TAG = "BillingService";
        
        /** The service connection to the remote MarketBillingService. */
        private IMarketBillingService mService;
        
        @Override
        public void onCreate() {
                super.onCreate();
                Log.i(TAG, "Service starting with onCreate");
                
                try {
                        boolean bindResult = bindService(new Intent("com.android.vending.billing.MarketBillingService.BIND"), this, Context.BIND_AUTO_CREATE);
                        Log.d(getPackageName(), "Binding: "+String.valueOf(bindResult));
                        if(bindResult){
                                Log.i(TAG,"Market Billing Service Successfully Bound");
                        } else {
                                Log.e(TAG,"Market Billing Service could not be bound.");
                                //TODO stop user continuing
                        }
                } catch (SecurityException e){
                        Log.e(TAG,"Market Billing Service could not be bound. SecurityException: "+e);
                        //TODO stop user continuing
                }
        }
        
        public void setContext(Context context) {
        attachBaseContext(context);
    }
        
        @Override
        public IBinder onBind(Intent intent) {
                return null;
        }
  
        public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "Market Billing Service Connected.");
                mService = IMarketBillingService.Stub.asInterface(service);
                BillingHelper.instantiateHelper(getBaseContext(), mService);
                Log.d(getPackageName(), "onServiceConnected");
        }
  
        public void onServiceDisconnected(ComponentName name) {
                
        }
  
}
