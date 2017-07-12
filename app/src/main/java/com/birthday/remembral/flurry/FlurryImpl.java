package com.birthday.remembral.flurry;

import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;

import com.flurry.android.FlurryAgent;
import com.flurry.android.FlurryAgentListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Map;

import static android.util.Log.VERBOSE;

public class FlurryImpl {

    private FlurryImpl() {
        // sonar
    }

    /*
    * initialize flurry analytics with flurry key in application class
    *
    * call this method in onCreate method of Application class
    * */

    public static void flurryInitializationInApplication(Context context,String flurryKey) {
        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .withCaptureUncaughtExceptions(true)
                .withContinueSessionMillis(10)
                .withLogLevel(VERBOSE)
                .withListener(new FlurryAgentListener() {
                    @Override
                    public void onSessionStarted() {
                        Log.d("REMEMBRAL", "session-started at : "
                                + new Timestamp(System.currentTimeMillis()));
                    }
                })
                .build(context, flurryKey);
    }

    /*
    *  call this method in onStart method of activity to start flurry session
    *   it sets device id as user id for flurry and starts flurry session
    * */
    public static void flurryStartSession(Context context) {
        flurrySetUserId(context);

        FlurryAgent.onStartSession(context);
    }

    /*
    *  call this method in onStop method of activity to stop flurry session
    * */
    public static void flurryStopSession(Context context) {
        FlurryAgent.onEndSession(context);
    }

    private static void flurrySetUserId(Context context) {
        final String uniqueAndroidId =
                Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        final String sha256UserId = FlurryImpl.sha256(uniqueAndroidId);
        if (sha256UserId != null) {
            FlurryAgent.setUserId(sha256UserId);
        }
    }

    private static String sha256(String input) {
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("SHA256");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aResult : result) {
                sb.append(Integer.toString((aResult & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            FlurryAgent.onError(FlurryError.FLURRY_SHA_256.name(),
                    FlurryError.FLURRY_SHA_256.name(), new NoSuchAlgorithmException());
            return null;
        }
    }

    public static void flurryLogEvent(String eventName) {
        FlurryAgent.logEvent(eventName);
    }


    /*
    *  to log error in flurry
    * */
    public static void flurryLogError(String errorId,
                                      String errorMessage, Exception errorException) {
        FlurryAgent.onError(errorId, errorMessage, errorException);
    }

    /*
     * if we call logEvent method with timedEvent parameter ,
     * then we should call endTimeEvent method also
     */
    public static void flurryTimedLogEvent(String eventName,
                                           Map<String, String> eventMap, boolean timedEvent) {
        FlurryAgent.logEvent(
                eventName,
                eventMap, timedEvent
        );
    }

    /*
    * should be called if logEvent gets called
    */
    public static void flurryEndTimedEvent(String eventName) {
        FlurryAgent.endTimedEvent(
                eventName
        );
    }

}
