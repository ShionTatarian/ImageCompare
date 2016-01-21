package fi.qvik.imagecompare.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * QuickLog
 * Convenience static service for logging and toggling log visibility.
 * <p/>
 * Created by Tommy on 03/05/15.
 */
public class QLog {

    private static boolean debug = true;

    public static void setDebug(boolean isDebugging) {
        debug = isDebugging;
    }

    public static boolean getDebug() {
        return debug;
    }

    public enum LogLevel {
        d, e, w, i, v
    }

    public static void d(String tag, String message, Object... args) {
        log(null, tag, message, LogLevel.d, debug, args);
    }

    public static void d(Throwable e, String tag, String message,Object... args) {
        log(e, tag, message, LogLevel.d, debug, args);
    }

    public static void i(String tag, String message, Object... args) {
        log(null, tag, message, LogLevel.i, debug, args);
    }

    public static void i(Throwable e, String tag, String message,Object... args) {
        log(e, tag, message, LogLevel.i, debug, args);
    }

    public static void w(String tag, String message, Object... args) {
        log(null, tag, message, LogLevel.w, debug, args);
    }

    public static void w(Throwable e, String tag, String message,Object... args) {
        log(e, tag, message, LogLevel.w, debug, args);
    }

    public static void e(String tag, String message, Object... args) {
        log(null, tag, message, LogLevel.e, debug, args);
    }

    public static void e(Throwable e, String tag, String message,Object... args) {
        log(e, tag, message, LogLevel.e, debug, args);
    }

    public static void log(Throwable e, String tag, String message, LogLevel level, boolean debug, Object... args) {
        if (!debug && level != LogLevel.e) {
            return;
        }
        message = String.format(message, args);

        switch (level) {
            case v:
                Log.v(tag, message);
                break;
            case i:
                Log.i(tag, message);
                break;
            case w:
                if (e != null) {
                    Log.w(tag, message, e);
                } else {
                    Log.w(tag, message);
                }
                break;
            case e:
                if (e != null) {
                    Log.e(tag, message, e);
                } else {
                    Log.e(tag, message);
                }
                break;
            case d:
            default:
                if(e != null) {
                    Log.d(tag, message, e);
                } else {
                    Log.d(tag, message);
                }
                break;
        }
    }

    public static void logJson(QLog.LogLevel level, String TAG, JSONObject json, String message) {
        logJson(level, TAG, json, message, debug);
    }

    public static void logJson(LogLevel level, String TAG, JSONObject json, String message, boolean debug) {
        QLog.log(null, TAG, message, QLog.LogLevel.d, debug);
        if(json != null && debug) {
            try {
                switch (level) {
                    case v:
                        Log.v(TAG, json.toString(2));
                        break;
                    case i:
                        Log.i(TAG, json.toString(2));
                        break;
                    case w:
                        Log.w(TAG, message);
                        break;
                    case e:
                        Log.e(TAG, json.toString(2));
                        break;
                    case d:
                    default:
                        Log.d(TAG, json.toString(2));
                        break;
                }
            } catch (JSONException e) {
                Log.w(TAG, "Error printing JSONObject", e);
            }
        }
    }

    public static void logJson(QLog.LogLevel level, String TAG, JSONArray json, String message) {
        logJson(level, TAG, json, message, debug);
    }

    public static void logJson(QLog.LogLevel level, String TAG, JSONArray json, String message, boolean debug) {
        QLog.log(null, TAG, message, QLog.LogLevel.d, debug);
        if(json != null && debug) {
            try {
                switch (level) {
                    case v:
                        Log.v(TAG, json.toString(2));
                        break;
                    case i:
                        Log.i(TAG, json.toString(2));
                        break;
                    case w:
                        Log.w(TAG, json.toString(2));
                        break;
                    case e:
                        Log.e(TAG, json.toString(2));
                        break;
                    case d:
                    default:
                        Log.d(TAG, json.toString(2));
                        break;
                }
            } catch (JSONException e) {
                Log.w(TAG, "Error printing JSONArray", e);
            }
        }
    }

}
