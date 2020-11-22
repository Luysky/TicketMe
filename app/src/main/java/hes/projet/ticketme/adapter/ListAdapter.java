package hes.projet.ticketme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter<T> extends ArrayAdapter<T> {

    private int resource;
    private List<T> data;

    public ListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> data) {
        super(context, resource, data);

        this.resource = resource;
        this.data = data;
    }

    public T getItem(int position) {
        return data.get(position);
    }

    public void updateData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}
