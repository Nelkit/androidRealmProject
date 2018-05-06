package chakalon.com.formofflineexample.Core;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmObject;

public abstract class BaseAdapter<V extends RecyclerView.ViewHolder,T extends RealmObject> extends RecyclerView.Adapter<V>{
    protected LayoutInflater mInflater;
    protected BaseActivity mParent;
    protected List<T> mItems;
    protected List<T> mOriginalSet;
    private OnDateSetChangedListener mDataSetChangedListener;
    protected Boolean displayedOnCompactDevice = false;


    public BaseAdapter(BaseActivity pParent){
        this(pParent, false);
    }

    public BaseAdapter(BaseActivity pParent,boolean leaveEmpty){
        mInflater = pParent.getLayoutInflater();
        mParent = pParent;
        mItems = new ArrayList<T>();
        if(!leaveEmpty)
            reloadData();
    }

    public void reloadData(){
        reloadData(null, false);
    }

    public void reloadData(String sortField, boolean sortAscending){
        Realm realm = mParent.getRealm();
        mItems.clear();

        if(sortField == null || sortField.trim().length() == 0){
            mOriginalSet = realm.where(getModelClass()).findAll();
        }else{
            mOriginalSet = realm.where(getModelClass()).findAll();
        }

        mItems.addAll(mOriginalSet);

        notifyDataSetChanged();
        if(mDataSetChangedListener != null){
            mDataSetChangedListener.onDataSetChanged(mItems);
        }
    }

    public void setOriginalSet(List<T> pOriginalSet){
        mOriginalSet = pOriginalSet;
        mItems.clear();
        if(mOriginalSet != null)
            mItems.addAll(mOriginalSet);
        notifyDataSetChanged();
        if(mDataSetChangedListener != null){
            mDataSetChangedListener.onDataSetChanged(mItems);
        }
    }

    public void clear(){
        mItems.clear();
        notifyDataSetChanged();
        if(mDataSetChangedListener != null){
            mDataSetChangedListener.onDataSetChanged(mItems);
        }
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    protected abstract Class<T> getModelClass();

    public void setOnDataSetChangedListener(OnDateSetChangedListener listener){
        mDataSetChangedListener = listener;
    }

    public interface OnDateSetChangedListener{
        void onDataSetChanged(List<? extends RealmObject> elements);
    }
}
