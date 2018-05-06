package chakalon.com.formofflineexample;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import chakalon.com.formofflineexample.Api.DataManager;
import chakalon.com.formofflineexample.Api.HttpApi;
import chakalon.com.formofflineexample.Api.HttpFactory;
import chakalon.com.formofflineexample.Core.BaseActivity;
import chakalon.com.formofflineexample.Entities.Customer;
import chakalon.com.formofflineexample.Helpers.Utils;
import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FormActivity extends BaseActivity {
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mAge;
    private RadioGroup mGenre;
    private Spinner mCountry;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        setupUI();
        setContentCombo();
    }

    public void setupUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Nuevo Cliente");
        Utils.bindUpButtonAsBack(this, mToolbar);

        mFirstName = findViewById(R.id.customer_first_name);
        mLastName = findViewById(R.id.customer_last_name);
        mAge = findViewById(R.id.customer_age);
        mGenre = findViewById(R.id.customer_genre);
        mCountry = findViewById(R.id.customer_country);
    }

    public void setContentCombo(){
        ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountry.setAdapter(adapterCountry);
    }

    public void saveCustomer(View v){
        int genreSelected = mGenre.getCheckedRadioButtonId();
        if (mFirstName != null && mLastName != null && mAge != null && genreSelected != -1) {
            RadioButton genre = (RadioButton) findViewById(genreSelected);

            saveInRealm(
                    mFirstName.getText().toString(),
                    mLastName.getText().toString(),
                    Integer.parseInt(mAge.getText().toString()),
                    genre.getText().toString(),
                    mCountry.getSelectedItem().toString()
            );

            toastLong("Datos guardados localmente");
        }
        else{
            toastLong("Por favor ingrese todos los campos");
        }

    }

    public void saveInRealm(String _firstname, String _lastname, int _age, String _genre, String _country){
        Realm realm = getRealm();
        Customer customer = new Customer();
        realm.beginTransaction();
        int nextId = Customer.getNextID();
        customer.setId(nextId);
        customer.setFirstname(_firstname);
        customer.setLastname(_lastname);
        customer.setAge(_age);
        customer.setGenre(_genre);
        customer.setCountry(_country);
        realm.copyToRealm(customer);
        realm.commitTransaction();

        DataManager.syncAllData(this);
        finish();
    }

}
