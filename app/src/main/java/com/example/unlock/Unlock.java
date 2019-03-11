package com.example.unlock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class Unlock extends Activity
{
    WifiManager wifiManager;
//    private KeyguardManager.KeyguardLock keyguardLock;
//    private KeyguardManager mKeyGuardManager;

//    private final Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set window flags to unlock screen. This works on most devices by itself.
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        // some devices needs waking up screen first before disable keyguard
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock screenLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, getLocalClassName());
        screenLock.acquire();

        if (screenLock.isHeld()) {
            screenLock.release();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // On most other devices, using the KeyguardManager + the permission in
        // AndroidManifest.xml will do the trick
        KeyguardManager mKeyGuardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//        mKeyGuardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        if (mKeyGuardManager.inKeyguardRestrictedInputMode()) {
            KeyguardManager.KeyguardLock keyguardLock = mKeyGuardManager.newKeyguardLock("");
            keyguardLock = mKeyGuardManager.newKeyguardLock("");
            keyguardLock.disableKeyguard();
        }

//        setEnablednessOfKeyguard(false);

        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }

        // Close yourself!
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Unlock.this.finish();
            }
        }, 200);*/

       /* try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
*/
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        moveTaskToBack(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }




 /*   private final Runnable runDisableKeyguard = new Runnable() {
        public void run() {
            Log.d("guixiang","++++++++++++++++++++++++++");
            keyguardLock = mKeyGuardManager.newKeyguardLock(getPackageName());
            keyguardLock.disableKeyguard();
        }
    };

    private void setEnablednessOfKeyguard(boolean enabled) {
        if (enabled) {
            if (keyguardLock != null) {
                mHandler.removeCallbacks(runDisableKeyguard);
                keyguardLock.reenableKeyguard();
                keyguardLock = null;
            }
        } else {
            if (mKeyGuardManager.inKeyguardRestrictedInputMode()) {

            } else {
                if (keyguardLock != null)
                    keyguardLock.reenableKeyguard();


                mHandler.postDelayed(runDisableKeyguard,  300);
            }
        }
    }*/
}

