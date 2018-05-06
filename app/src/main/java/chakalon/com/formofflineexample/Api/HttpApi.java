package chakalon.com.formofflineexample.Api;

import chakalon.com.formofflineexample.Entities.Customer;
import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

public interface HttpApi {
    String SERVICE_ENDPOINT = "https://tdphn.herokuapp.com/tdp/api";

    @Multipart
    @POST("/customer/")
    void syncCustomer(@Part("firstname") String firstName,
                      @Part("lastname") String lastName,
                      @Part("age") int age,
                      @Part("genre") String genre,
                      @Part("country") String country, Callback<Customer> cb);

}
