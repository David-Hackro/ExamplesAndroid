package hackro.tutorials.adaptermvp1.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hackro.tutorials.adaptermvp1.Interfaces.OnRecyclerViewItemClickListener;
import hackro.tutorials.adaptermvp1.Models.Person;
import hackro.tutorials.adaptermvp1.R;

/**
 * Created by david on 22/08/16.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> implements View.OnClickListener {

    private List<Person> listPerson;
    private OnRecyclerViewItemClickListener<Person> itemClickListener;
    private int itemLayout;

    public PersonAdapter(int itemLayout) {

        this.itemLayout = itemLayout;
    }

    public void setListPerson(List<Person> listPerson) {
        this.listPerson = listPerson;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Person item = listPerson.get(position);
        holder.itemView.setTag(item);
        holder.name.setText(item.getName());


    }

    @Override
    public int getItemCount() {
        if (listPerson != null)
            return listPerson.size();
        else
            return 0;
    }

    @Override
    public void onClick(View view) {

        if (itemClickListener != null) {
            Person model = (Person) view.getTag();
            itemClickListener.onItemClick(view, model);
        }

    }



    public void add(Person item, int position) {
        listPerson.add(position, item);
        //notifyItemInserted(position);

    }

    public void remove(Person item) {
        int position = listPerson.indexOf(item);
        listPerson.remove(position);
        //notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener<Person> listener) {
        this.itemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //@BindView(R.id.name_people)
        TextView name;



        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_people);
        }
    }
}
