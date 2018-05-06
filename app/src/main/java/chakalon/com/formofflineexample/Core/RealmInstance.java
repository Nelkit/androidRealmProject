package chakalon.com.formofflineexample.Core;

import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;

public class RealmInstance {
    public static final String TAG = "REALM_INTNACE";
    private static ArrayList<Realm> realmInstances = new ArrayList<Realm>();

    public static Realm getDefault(){
        Realm realm = Realm.getDefaultInstance();
        realmInstances.add(realm);
        return realm;
    }

    public static void close(Realm realm){
        int index = realmInstances.indexOf(realm);
        if(index >= 0)
            realmInstances.remove(realm);
        try {
            realm.close();
        }catch(Exception e){
            Log.e(TAG, "Couldn't close realm instance, error: " + e.getMessage(), e);
            if(!realm.isClosed())
                realmInstances.add(realm);
        }

    }

    public static int getCount(){
        return realmInstances.size();
    }

    public static void closeAll(){
        try {
            for (Realm r : realmInstances) {
                r.close();
            }
            realmInstances.clear();
        }catch(Exception e){
            Log.e(TAG,"Couldn't close all realm instances, error: " + e.getMessage(),e);
        }
    }
}
