package chakalon.com.formofflineexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import chakalon.com.formofflineexample.Adapters.CustomerAdapter;
import chakalon.com.formofflineexample.Api.DataManager;
import chakalon.com.formofflineexample.Bus.CustomerSyncedFailure;
import chakalon.com.formofflineexample.Bus.CustomerSyncedSuccess;
import chakalon.com.formofflineexample.Bus.NoCustomerPending;
import chakalon.com.formofflineexample.Core.BaseActivity;
import chakalon.com.formofflineexample.Entities.Customer;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity {
    private RecyclerView mListCustomer;
    private CustomerAdapter mAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("example.realm")
                .schemaVersion(42)
                .build();

        Realm.setDefaultConfiguration(config);

        setupUI();
    }

    public void setupUI(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Home");

        mListCustomer = findViewById(R.id.list_customer);

        mListCustomer.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CustomerAdapter(this,true);
        mListCustomer.setAdapter(mAdapter);
        mAdapter.setOriginalSet( queryCustomer() );

        registerToDefaultBus();
    }

    public RealmResults<Customer> queryCustomer(){
        Realm realm = getRealm();
        RealmResults<Customer> customers = realm.where(Customer.class).findAll();
        return customers;
    }

    public void goToAddCostumer(View v){
        Intent i = new Intent(this,FormActivity.class);
        startActivityForResult(i,1000);
    }

    public void sync(View v){
        DataManager.syncAllData(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mAdapter.setOriginalSet( queryCustomer() );
    }

    //Event Bus
    public void onEvent(CustomerSyncedSuccess event){
        mAdapter.setOriginalSet( queryCustomer() );
        toastLong("El cliente se guardo en el servidor correctamente");
    }

    public void onEvent(CustomerSyncedFailure event){
        toastLong("El cliente no se guardo en el servidor");
    }

    public void onEvent(NoCustomerPending event){
        toastLong("No hay datos pendientes de sincronizar");
    }
}
