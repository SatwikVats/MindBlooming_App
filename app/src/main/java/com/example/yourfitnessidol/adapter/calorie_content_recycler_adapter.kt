package com.example.yourfitnessidol.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfitnessidol.R
import java.util.ArrayList
import com.example.yourfitnessidol.fragment.CalorieContentFragment
import com.example.yourfitnessidol.model.food


class CCRecyclerAdapter(val context:Context,val foodList:ArrayList<food> ) : RecyclerView.Adapter<CCRecyclerAdapter.CCViewHolder>()
{
   

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CCViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_calorie_content_single_row,parent ,false)
        return CCViewHolder(view)

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: CCViewHolder, position: Int) {
        val food=foodList[position]
        holder.foodName.text=food.foodName
        holder.foodQuantity.text=food.foodQuantity
        holder.foodCalorie.text=food.foodCalorie
        holder.foodFats.text=food.foodFats
        holder.foodSource.text=food.foodSource
        holder.foodImage.setImageResource(food.foodImage)


    }

 





    class CCViewHolder(view:View):RecyclerView.ViewHolder(view)
    {val foodName: TextView=view.findViewById(R.id.txtRecyclerRowItem)
        val foodQuantity: TextView=view.findViewById(R.id.quantity)
        val foodCalorie: TextView=view.findViewById(R.id.calories)
        val foodFats: TextView=view.findViewById(R.id.fats)
        val foodSource: TextView=view.findViewById(R.id.source)
        val foodImage: ImageView=view.findViewById(R.id.recycler_image)


    }
}




