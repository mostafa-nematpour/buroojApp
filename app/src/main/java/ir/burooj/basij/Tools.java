package ir.burooj.basij;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.EditText;

import androidx.core.content.FileProvider;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import ir.burooj.basij.apiClass.Servis2;


class Tools {
    public static SharedPreferences shPref;
    public static SharedPreferences shPref2;
    public static final String MyPref = "BuroojPrefers1";
    public static final String MyPref2 = "BuroojPrefers2";

    private static final String servis2Alghadir = "servis2Alghadir";
    private static final String servis2Sorkhnkolate = "servis2Sorkhnkolate";
    private static final String InvitationCodeName = "InvitationCode";

    public static final String tokenName = "tokenNameKey";
    public static final String userIdName = "userIdNameKey";
    public static final String haveAccountName = "haveAccount";


    static String toSmall(int number, String text) {

        String rText = "";
        try {
            if (text == null)
                return "";
            String[] arr = text.trim().split(" ");
            if (arr.length > number) {
                for (int i = 0; i < number; i++) {
                    rText += arr[i] + " ";
                }
                rText += "...";
            } else {
                rText = text;
            }
            return rText;
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            return text;
        }
    }

    static boolean ifNumberTrue(String number) {
        return number.matches("(\\+98|0)?9\\d{9}");
    }

    static List<Item> convert(String[] strings) {
        List<Item> a = new ArrayList<>();
        for (String s : strings) {
            String[] arr = s.split("mn3#/!mn3");
            //  a.add(0, new Item(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9]));
        }
        return a;
    }

    static void sharedPreference(Context context, String haveAccount, String token, String userId) {
        shPref = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = shPref.edit();
        sEdit.putString(haveAccountName, haveAccount);
        sEdit.putString(tokenName, token);
        sEdit.putString(userIdName, userId);
        sEdit.apply();
        Log.d("shared", "\n" + context.toString() + "\n       " + haveAccount + "\n       " + token + userId);
    }

    static void sharedPreferenceServis(Context context, int i, String s) {
        shPref2 = context.getSharedPreferences(MyPref2, Context.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = shPref2.edit();
        if (i == 1) {
            sEdit.putString(servis2Alghadir, s);
        } else if (i == 2) {
            sEdit.putString(servis2Sorkhnkolate, s);
        }
        sEdit.apply();
    }

    static String getSharedPreferenceServis(Context context, int i) {
        String s = "";
        try {
            shPref2 = context.getSharedPreferences(MyPref2, Context.MODE_PRIVATE);
            if (i == 1) {
                if (shPref2.contains(servis2Alghadir)) {
                    s = shPref2.getString(servis2Alghadir, null);
                    return s;
                }
            } else if (i == 2) {
                if (shPref2.contains(servis2Sorkhnkolate)) {
                    s = shPref2.getString(servis2Sorkhnkolate, null);
                    return s;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static String servisLiostToString(List<Servis2> servis2s, String key) {
        String s = "";
        for (Servis2 servis2 : servis2s) {
            s += servis2.getFirst();
            s += ("m@mss&&321");
            s += (servis2.getSecond());
            s += (key);
        }
        return s;
    }

    static List<Servis2> stringToServisList(String s, String key) {
        List<Servis2> servis2s = new ArrayList<Servis2>();
        String[] strings = s.split(key);
        for (int i = 0; i < strings.length; i++) {
            String[] strings1 = strings[i].split("m@mss&&321");
            servis2s.add(new Servis2(strings1[0], strings1[1]));
        }
        return servis2s;
    }


    static void sendApplication(Activity activity) {
        ApplicationInfo app = activity.getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);

        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.setType("*/*");

        // Append file and send Intent
        File originalApk = new File(filePath);

        try {
            //Make new directory in new location
            File tempFile = new File(activity.getExternalCacheDir() + "/ExtractedApk");
            //If directory doesn't exists create new
            if (!tempFile.isDirectory()) {
                if (!tempFile.mkdirs()) {
                    return;
                }
            }
            //Get application's name and convert to lowercase
            tempFile = new File(tempFile.getPath() + "/" + activity.getString(app.labelRes).replace(" ", "").toLowerCase() + ".apk");
            //If file doesn't exists create new
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }
            //Copy file to new location
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
            //Open share dialog

            Uri uri = FileProvider.getUriForFile(activity.getApplicationContext(), activity.getPackageName(), tempFile);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            activity.grantUriPermission(activity.getPackageManager().toString(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //برای تکمیل حساب یا تغییر

    public static boolean check(EditText editText, TextInputLayout textInputLayout, int i1, int i2,
                                String s1, String s2, String s3) {
        String s = "";
        s = editText.getText().toString();
        if (s.equals("")) {
            textInputLayout.setError(s1);
            return false;
        } else if (s.length() < i1) {
            textInputLayout.setError(s2);
            return false;
        } else if (s.length() > i2) {
            textInputLayout.setError(s3);
            return false;
        } else {
            return true;
        }
    }


}
