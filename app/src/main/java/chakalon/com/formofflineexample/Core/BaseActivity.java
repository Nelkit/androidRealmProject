package chakalon.com.formofflineexample.Core;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import chakalon.com.formofflineexample.R;
import de.greenrobot.event.EventBus;
import io.realm.Realm;
import retrofit.ErrorHandler;

public abstract class BaseActivity extends AppCompatActivity {
    protected Realm mRealm;
    protected boolean registeredToDefaultBus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Realm getRealm(){
        if(mRealm == null){
            mRealm = RealmInstance.getDefault();
        }
        return mRealm;
    }

    protected void registerToDefaultBus(){
        registeredToDefaultBus = true;
        if(!EventBus.getDefault().isRegistered(this)) {
            try {
                EventBus.getDefault().register(this);
            }catch (Exception e){

            }
        }
    }

    protected void toastLong(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    protected void toastTopLong(String text){
        Toast toast = Toast.makeText(this,text,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 130);
        toast.show();
    }

    @Override
    public void startActivity(Intent i){
        super.startActivity(i);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_left);
    }

    @Override
    public void finish(){
        finish(false);
    }

    public void finish(boolean noTransition){
        super.finish();
        if(!noTransition)
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
    }

    @Override
    public void onDestroy(){
        if(mRealm != null)
            RealmInstance.close(mRealm);
        mRealm = null;

        if(registeredToDefaultBus) {
            EventBus.getDefault().unregister(this);
            registeredToDefaultBus = false;
        }

        super.onDestroy();
    }
}
