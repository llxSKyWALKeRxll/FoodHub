package com.internshala.activitylifecycle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internshala.activitylifecycle.R
import com.internshala.activitylifecycle.model.Restaurant
import com.squareup.picasso.Picasso



class HomeRecyclerAdapter(val context: Context, val itemList: ArrayList<Restaurant>):
    RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val rowImage: ImageView = view.findViewById(R.id.rowImage)
        val rowName: TextView = view.findViewById(R.id.rowName)
        val rowPrice: TextView = view.findViewById(R.id.rowPrice)
        val rowRating: TextView = view.findViewById(R.id.rowRating)

        val rowLayout: LinearLayout = view.findViewById(R.id.rowLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_row_layout_restaurant, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val restaurant =itemList[position]
        holder.rowName.text = restaurant.resName
        holder.rowRating.text = restaurant.resRating
        holder.rowPrice.text = restaurant.resCostForOne
        Picasso.get().load(restaurant.imageUrl).error(R.drawable.ic_app_icon_foreground).into(holder.rowImage)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}