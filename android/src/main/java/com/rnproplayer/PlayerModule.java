package com.rnproplayer;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import javax.annotation.Nonnull;

public class PlayerModule extends ReactContextBaseJavaModule {

    ReactApplicationContext context=getReactApplicationContext();
    public PlayerModule(@Nonnull ReactApplicationContext reactContext){
        super(reactContext);
    }

    @Nonnull
    public String getName(){
        return "RnProPlayer";
    }

    @ReactMethod
    public void multiply(double a, double b, Promise promise) {
      promise.resolve(a * b);
    }

    @ReactMethod
    public void PlayVideo(String url){
        Intent intent =new Intent(context,PlayerActivity.class);
        Log.d("enter",url);
         if(intent.resolveActivity(context.getPackageManager())!=null){
             intent.putExtra("videourl",url);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             context.startActivity(intent);
         }
    }
}
