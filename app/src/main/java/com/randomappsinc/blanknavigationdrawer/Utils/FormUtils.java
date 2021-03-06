package com.randomappsinc.blanknavigationdrawer.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.randomappsinc.blanknavigationdrawer.R;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alexanderchiou on 12/20/15.
 */
public class FormUtils {
    public static void showSnackbar(View parent, String message) {
        Context context = MyApplication.getAppContext();
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        View rootView = snackbar.getView();
        snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color.app_blue));
        TextView tv = (TextView) rootView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String parsedPhoneNumber = phoneNumber.replaceAll("[^0-9]+", "");
        return parsedPhoneNumber.length() == 10;
    }

    public static String getPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) MyApplication.getAppContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            String phoneNumber = telephonyManager.getLine1Number();
            return phoneNumber.length() == 11 ? phoneNumber.substring(1) : phoneNumber;
        }
        else {
            return "";
        }
    }

    public static String formatUSNumber(String input) {
        StringBuilder formattedString = new StringBuilder();

        // Parse out non-digits
        input = input.replaceAll("[^0-9]+", "");
        int totalDigitCount = input.length();

        // The first 3 numbers beyond '1' must be enclosed in brackets "()"
        if (totalDigitCount > 0) {
            formattedString.append("(");
            if (totalDigitCount < 3) {
                formattedString.append(input.substring(0, totalDigitCount));
            } else {
                formattedString.append(input.substring(0, 3));
            }
        }
        // There must be a '-' inserted after the next 3 numbers
        if (totalDigitCount > 3) {
            formattedString.append(") ");
            if (totalDigitCount < 6) {
                formattedString.append(input.substring(3, totalDigitCount));
            } else {
                formattedString.append(input.substring(3, 6));
            }
        }

        // There must be a '-' inserted after the next 3 numbers
        if (totalDigitCount > 6) {
            formattedString.append("-");
            if (totalDigitCount < 10) {
                formattedString.append(input.substring(6, totalDigitCount));
            } else {
                formattedString.append(input.substring(6, 10));
            }
        }
        return formattedString.toString();
    }

    public static String getMD5Hash(String input) {
        String hashtext = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(input.getBytes());
            byte[] digest = messageDigest.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
        }
        catch (NoSuchAlgorithmException ignored) {}
        return hashtext;
    }

    public static int getGenderIndex(String gender) {
        switch (gender) {
            case "Male":
                return 0;
            case "Female":
                return 1;
            default:
                return -1;
        }
    }

    public static String getGenderIcon(String gender) {
        switch (gender) {
            case "Male":
                return "{fa-male}";
            case "Female":
                return "{fa-female}";
            default:
                return "{fa-male}";
        }
    }

    public static String getZipCodeFromLocation(Location location) {
        Address address = getAddressFromLocation(location);
        return address.getPostalCode() == null ? "" : address.getPostalCode();
    }

    public static Address getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(MyApplication.getAppContext());
        Address address = new Address(Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                address = addresses.get(0);
            }
        } catch (IOException ignored) {}
        return address;
    }

    public static String getCurrentZip() {
        LocationManager lm = (LocationManager) MyApplication.getAppContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                return getZipCodeFromLocation(location);
            }
        }
        catch (SecurityException e) {
            return "";
        }
        return "";
    }
}
