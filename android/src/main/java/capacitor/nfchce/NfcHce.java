package capacitor.nfchce;

import android.content.Context;
import android.content.Intent;
import com.getcapacitor.Logger;

public class NfcHce {

    private static NfcHcePlugin plugin; // ইভেন্ট নির্গমনের জন্য প্লাগইনের রেফারেন্স
    private static String apduResponseData = ""; // APDU রেসপন্স হিসাবে পাঠানোর জন্য ডেটা

    public void setPlugin(NfcHcePlugin plugin) {
        NfcHce.plugin = plugin;
    }

    public String echo(String value) {
        Logger.info("Echo", value);
        return value;
    }

    public void startHceService(Context context, String aid) {
        // এই মেথডটি মূলত নিশ্চিত করবে যে পরিষেবাটি সক্ষম এবং কনফিগার করা হয়েছে।
        // আসল পরিষেবা শুরু হয় যখন একটি NFC রিডার ডিভাইসের সাথে ইন্টারঅ্যাক্ট করে।
        // আমাদের নিশ্চিত করতে হবে যে AndroidManifest.xml পরিষেবাটির জন্য সঠিকভাবে কনফিগার করা হয়েছে।
        Logger.info("NfcHce", "Starting HCE service with AID: " + aid);
        // এখানে সরাসরি পরিষেবা শুরু করার কোনো কল নেই, কারণ HostApduService সিস্টেম দ্বারা শুরু হয়।
        // আমরা AID নিবন্ধন করার জন্য AndroidManifest.xml ব্যবহার করব।
    }

    public void stopHceService(Context context) {
        Logger.info("NfcHce", "Stopping HCE service.");
        // এখানে সরাসরি পরিষেবা বন্ধ করার কোনো কল নেই, কারণ HostApduService সিস্টেম দ্বারা বন্ধ হয়।
        // আমরা ম্যানিফেস্টের উপর নির্ভর করব।
    }

    public void setApduResponse(String data) {
        apduResponseData = data;
        Logger.info("NfcHce", "APDU response data set: " + data);
    }

    // এই মেথডটি NfcHceService দ্বারা কল করা হবে যখন ডেটা প্রাপ্ত হবে
    public static void onCardDataReceived(String data) {
        if (plugin != null) {
            plugin.onCardDataReceived(data);
        } else {
            Logger.warn("NfcHce", "Plugin reference is null, cannot emit cardDataReceived event.");
        }
    }

    // এই মেথডটি NfcHceService দ্বারা কল করা হবে রেসপন্স ডেটা পাওয়ার জন্য
    public static String getApduResponseData() {
        return apduResponseData;
    }
}
