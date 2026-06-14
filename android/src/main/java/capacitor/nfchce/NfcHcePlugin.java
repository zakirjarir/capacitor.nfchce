package capacitor.nfchce;

import android.content.Context;
import android.content.Intent;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "NfcHce")
public class NfcHcePlugin extends Plugin {

    private NfcHce implementation = new NfcHce();

    @Override
    public void load() {
        super.load();
        // HCE বাস্তবায়নকে প্লাগইনের একটি রেফারেন্স দিয়ে ইনিশিয়ালাইজ করা হয় ইভেন্ট নির্গমনের জন্য
        implementation.setPlugin(this);
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void startHce(PluginCall call) {
        String aid = call.getString("aid");
        if (aid == null) {
            call.reject("AID অবশ্যই প্রদান করতে হবে");
            return;
        }
        try {
            implementation.startHceService(getContext(), aid);
            call.resolve();
        } catch (Exception e) {
            call.reject("HCE পরিষেবা শুরু করতে ব্যর্থ হয়েছে", e);
        }
    }

    @PluginMethod
    public void stopHce(PluginCall call) {
        try {
            implementation.stopHceService(getContext());
            call.resolve();
        } catch (Exception e) {
            call.reject("HCE পরিষেবা বন্ধ করতে ব্যর্থ হয়েছে", e);
        }
    }

    @PluginMethod
    public void setCardData(PluginCall call) {
        String data = call.getString("data");
        if (data == null) {
            call.reject("ডেটা অবশ্যই প্রদান করতে হবে");
            return;
        }
        try {
            implementation.setApduResponse(data);
            call.resolve();
        } catch (Exception e) {
            call.reject("কার্ড ডেটা সেট করতে ব্যর্থ হয়েছে", e);
        }
    }

    // HCE পরিষেবা থেকে ওয়েব স্তরে ইভেন্ট নির্গমনের পদ্ধতি
    public void onCardDataReceived(String data) {
        JSObject ret = new JSObject();
        ret.put("value", data);
        notifyListeners("cardDataReceived", ret);
    }
}
