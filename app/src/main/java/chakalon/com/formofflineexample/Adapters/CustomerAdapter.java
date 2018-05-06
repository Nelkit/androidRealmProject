package chakalon.com.formofflineexample.Adapters;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import chakalon.com.formofflineexample.Core.BaseActivity;
import chakalon.com.formofflineexample.Core.BaseAdapter;
import chakalon.com.formofflineexample.Entities.Customer;
import chakalon.com.formofflineexample.R;

public class CustomerAdapter extends BaseAdapter<CustomerAdapter.viewHolder,Customer> {

    public CustomerAdapter(BaseActivity pParent, boolean leaveEmpty) {
        super(pParent, leaveEmpty);
    }

    static class viewHolder extends RecyclerView.ViewHolder{
        TextView name, age, genre, country, uploaded;
        public viewHolder(View root) {
            super(root);
            name = (TextView)root.findViewById(R.id.customer_item_name);
            age = (TextView)root.findViewById(R.id.customer_item_age);
            genre = (TextView)root.findViewById(R.id.customer_item_genre);
            country = (TextView)root.findViewById(R.id.customer_item_country);
            uploaded = (TextView)root.findViewById(R.id.customer_item_uploaded);
        }
    }

    @Override
    protected Class getModelClass() {
        return Customer.class;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(mInflater.inflate(R.layout.customer_item,parent,false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Customer customer = mItems.get(position);

        holder.name.setText( customer.getFirstname() + " " + customer.getLastname() );
        holder.age.setText( customer.getAge() + "" );
        holder.genre.setText( customer.getGenre() );
        holder.country.setText( customer.getCountry() );

        if (customer.getUploaded()){
            holder.uploaded.setText("Sincronizado");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.uploaded.setBackground(mParent.getDrawable(R.drawable.badge_blue));
            }else{
                holder.uploaded.setBackgroundColor(R.color.colorPrimary);
            }
        }else{
            holder.uploaded.setText("Pendiente");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.uploaded.setBackground(mParent.getDrawable(R.drawable.badge_red));
            }else{
                holder.uploaded.setBackgroundColor(R.color.colorAccent);
            }
        }
    }
}
