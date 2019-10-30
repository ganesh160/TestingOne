package com.example.testingone.Util;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.util.LongSparseArray;

import androidx.annotation.RequiresApi;

import com.karvy.atwl.sessionmanager.TimezOut;

import java.lang.reflect.Field;

public class AppController extends Application {

    // Defining sans as the normal (default) typeface.
    private static final String DEFAULT_NORMAL_BOLD_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_NORMAL_BOLD_ITALIC_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_NORMAL_ITALIC_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_NORMAL_NORMAL_FONT_FILENAME = "fonts/Lato-Regular.ttf";

    private static final String DEFAULT_SANS_BOLD_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_SANS_BOLD_ITALIC_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_SANS_ITALIC_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_SANS_NORMAL_FONT_FILENAME = "fonts/Lato-Regular.ttf";

    private static final String DEFAULT_SERIF_BOLD_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_SERIF_BOLD_ITALIC_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_SERIF_ITALIC_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_SERIF_NORMAL_FONT_FILENAME = "fonts/Lato-Regular.ttf";

    private static final String DEFAULT_MONOSPACE_BOLD_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_MONOSPACE_BOLD_ITALIC_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_MONOSPACE_ITALIC_FONT_FILENAME = "fonts/Lato-Regular.ttf";
    private static final String DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME = "fonts/Lato-Regular.ttf";

    // Constants found in the Android documentation
    // http://developer.android.com/reference/android/widget/TextView.html#attr_android:typeface
    private static final int normal_idx = 0;
    private static final int sans_idx = 1;
    private static final int serif_idx = 2;
    private static final int monospace_idx = 3;


    public static String TAG = AppController.class.getName();
    // helper methods
    static AppController instance;


    // constructor
    public AppController() {
        instance = this;
    }


    public static synchronized AppController getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        //Fabric.with(this, new Crashlytics());
        //session = new SessionManager(this);
        TimezOut.initialize(this, new TimezOut.Configuration(2 * 60 * 1000));
//        try {
//            setDefaultFonts();
//            // The following code is only necessary if you are using the android:typeface attribute
//            setDefaultFontForTypeFaceSans();
//            setDefaultFontForTypeFaceSansSerif();
//            setDefaultFontForTypeFaceMonospace();
//        } catch (Throwable e) {
//            // Must not crash app if there is a failure with overriding fonts!
//            logFontError(e);
//        }
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    private void setDefaultFonts() throws NoSuchFieldException, IllegalAccessException {
        final Typeface bold = Typeface.createFromAsset(getAssets(), DEFAULT_NORMAL_BOLD_FONT_FILENAME);
        final Typeface italic = Typeface.createFromAsset(getAssets(), DEFAULT_NORMAL_ITALIC_FONT_FILENAME);
        final Typeface boldItalic = Typeface.createFromAsset(getAssets(), DEFAULT_NORMAL_BOLD_ITALIC_FONT_FILENAME);
        final Typeface normal = Typeface.createFromAsset(getAssets(), DEFAULT_NORMAL_NORMAL_FONT_FILENAME);

        Field defaultField = Typeface.class.getDeclaredField("DEFAULT");
        defaultField.setAccessible(true);
        defaultField.set(null, normal);

        Field defaultBoldField = Typeface.class.getDeclaredField("DEFAULT_BOLD");
        defaultBoldField.setAccessible(true);
        defaultBoldField.set(null, bold);

        Field sDefaults = Typeface.class.getDeclaredField("sDefaults");
        sDefaults.setAccessible(true);
        sDefaults.set(null, new Typeface[]{normal, bold, italic, boldItalic});

        final Typeface normal_sans = Typeface.createFromAsset(getAssets(), DEFAULT_SANS_NORMAL_FONT_FILENAME);
        Field sansSerifDefaultField = Typeface.class.getDeclaredField("SANS_SERIF");
        sansSerifDefaultField.setAccessible(true);
        sansSerifDefaultField.set(null, normal_sans);

        final Typeface normal_serif = Typeface.createFromAsset(getAssets(), DEFAULT_SERIF_NORMAL_FONT_FILENAME);
        Field serifDefaultField = Typeface.class.getDeclaredField("SERIF");
        serifDefaultField.setAccessible(true);
        serifDefaultField.set(null, normal_serif);

        final Typeface normal_monospace = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME);
        Field monospaceDefaultField = Typeface.class.getDeclaredField("MONOSPACE");
        monospaceDefaultField.setAccessible(true);
        monospaceDefaultField.set(null, normal_monospace);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setDefaultFontForTypeFaceSans() throws NoSuchFieldException, IllegalAccessException {
        final Typeface bold = Typeface.createFromAsset(getAssets(), DEFAULT_SANS_BOLD_FONT_FILENAME);
        final Typeface italic = Typeface.createFromAsset(getAssets(), DEFAULT_SANS_ITALIC_FONT_FILENAME);
        final Typeface boldItalic = Typeface.createFromAsset(getAssets(), DEFAULT_SANS_BOLD_ITALIC_FONT_FILENAME);
        final Typeface normal = Typeface.createFromAsset(getAssets(), DEFAULT_SANS_NORMAL_FONT_FILENAME);

        setTypeFaceDefaults(normal, bold, italic, boldItalic, sans_idx);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setDefaultFontForTypeFaceSansSerif() throws NoSuchFieldException, IllegalAccessException {
        final Typeface bold = Typeface.createFromAsset(getAssets(), DEFAULT_SERIF_BOLD_FONT_FILENAME);
        final Typeface italic = Typeface.createFromAsset(getAssets(), DEFAULT_SERIF_ITALIC_FONT_FILENAME);
        final Typeface boldItalic = Typeface.createFromAsset(getAssets(), DEFAULT_SERIF_BOLD_ITALIC_FONT_FILENAME);
        final Typeface normal = Typeface.createFromAsset(getAssets(), DEFAULT_SERIF_NORMAL_FONT_FILENAME);

        setTypeFaceDefaults(normal, bold, italic, boldItalic, serif_idx);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setDefaultFontForTypeFaceMonospace() throws NoSuchFieldException, IllegalAccessException {
        final Typeface bold = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_BOLD_FONT_FILENAME);
        final Typeface italic = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_ITALIC_FONT_FILENAME);
        final Typeface boldItalic = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_BOLD_ITALIC_FONT_FILENAME);
        final Typeface normal = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME);

        setTypeFaceDefaults(normal, bold, italic, boldItalic, monospace_idx);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setTypeFaceDefaults(Typeface normal, Typeface bold, Typeface italic, Typeface boldItalic, int typefaceIndex) throws NoSuchFieldException, IllegalAccessException {
        Field typeFacesField = Typeface.class.getDeclaredField("MONOSPACE");
        typeFacesField.setAccessible(true);
        LongSparseArray<LongSparseArray<Typeface>> sTypefaceCacheLocal = new LongSparseArray<LongSparseArray<Typeface>>(3);
        typeFacesField.get(sTypefaceCacheLocal);
        LongSparseArray<Typeface> newValues = new LongSparseArray<Typeface>(4);
        newValues.put(Typeface.NORMAL, normal);
        newValues.put(Typeface.BOLD, bold);
        newValues.put(Typeface.ITALIC, italic);
        newValues.put(Typeface.BOLD_ITALIC, boldItalic);
        sTypefaceCacheLocal.put(typefaceIndex, newValues);

        typeFacesField.set(null, normal);
    }

    private void logFontError(Throwable e) {
        Log.e("font_override", "Error overriding fonts", e);
    }
}
