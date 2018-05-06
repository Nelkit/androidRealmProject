package chakalon.com.formofflineexample.Entities;

import chakalon.com.formofflineexample.Core.RealmInstance;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Customer extends RealmObject {
    @PrimaryKey private int id;
    private String          firstname;
    private String          lastname;
    private int             age;
    private String          genre;
    private String          country;
    private Boolean         uploaded=false;

    public static int getNextID(){
        Realm realm = RealmInstance.getDefault();
        int nextID = 1;
        Number n = realm.where(Customer.class).max("id");
        RealmInstance.close(realm);
        if( n != null)
            nextID =  n.intValue() + 1;
        return nextID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getUploaded() {
        return uploaded;
    }

    public void setUploaded(Boolean uploaded) {
        this.uploaded = uploaded;
    }
}
