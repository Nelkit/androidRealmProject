package chakalon.com.formofflineexample.Api;

import android.content.Context;
import android.widget.Toast;

import chakalon.com.formofflineexample.Bus.CustomerSyncedFailure;
import chakalon.com.formofflineexample.Bus.CustomerSyncedSuccess;
import chakalon.com.formofflineexample.Bus.NoCustomerPending;
import chakalon.com.formofflineexample.Entities.Customer;
import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DataManager {

    public static void syncAllData(final Context c) {
        DataManager.syncCustomer(c);
    }

    public static void syncCustomer(final Context c){
        HttpApi request = HttpFactory.createRetrofitService(HttpApi.class, HttpApi.SERVICE_ENDPOINT);

        final Realm realm = Realm.getDefaultInstance();
        RealmResults<Customer> customers = realm.where(Customer.class).equalTo("uploaded",false).findAll();

        if (customers.size()==0){
            EventBus.getDefault().post(new NoCustomerPending());
            return;
        }

        for(final Customer _customer:customers){
            request.syncCustomer(
                    _customer.getFirstname(),
                    _customer.getLastname(),
                    _customer.getAge(),
                    _customer.getGenre(),
                    _customer.getCountry(),
                    new Callback<Customer>() {
                        @Override
                        public void success(Customer customer, Response response) {
                            Realm cRealm = Realm.getDefaultInstance();
                            Customer mCustomer = cRealm.where(Customer.class).equalTo("id",_customer.getId()).findFirst();
                            cRealm.beginTransaction();
                            mCustomer.setUploaded(true);
                            cRealm.commitTransaction();

                            EventBus.getDefault().post(new CustomerSyncedSuccess());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            EventBus.getDefault().post(new CustomerSyncedFailure());
                        }
                    });
        }

    }
}
