/* Autor : Javier Arroyo (Jarroyo@hiberus.com)
 * 
 * Fecha: 14/07/2014,14:09:20
 * 
 * Name: MenuLateralItemsAdapter.java
 * 
 * Comentarios:
 * 
 * M�todos: 
 */
package com.eventerzgz.view.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.eventerzgz.model.commons.Category;
import com.eventerzgz.view.R;
import com.eventerzgz.view.activities.ListEventsActivity;

/**
 * @author jarroyo
 * 
 */
public class MenuLateralItemsAdapter extends BaseAdapter {

	private Context context;
	private List<Category> categoryList;

	public MenuLateralItemsAdapter(Context context,List<Category> categoryList) {
		this.context = context;
		this.categoryList = categoryList;
	}

	@Override
	public int getCount() {
		if(categoryList!=null){
            return categoryList.size();
        }
        return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {

		LayoutInflater mInflater;
		TextView textViewCategoria;

		mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_menu_lateral, null);
		}

		textViewCategoria = (TextView) convertView.findViewById(R.id.textViewCategoria);


		textViewCategoria.setText(categoryList.get(position).getsTitle().trim());
        textViewCategoria.setTag(position);
        textViewCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int)view.getTag();
                ((ListEventsActivity)context).searchCategory(categoryList.get(position).getId());
            }
        });

		return convertView;
	}

}
