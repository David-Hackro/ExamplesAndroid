package hackro.tutorials.adaptermvp1.Interfaces;

import android.view.View;

/**
 * Created by david on 22/08/16.
 */

public interface OnRecyclerViewItemClickListener<Model> {
    void onItemClick(View view, Model model);
}