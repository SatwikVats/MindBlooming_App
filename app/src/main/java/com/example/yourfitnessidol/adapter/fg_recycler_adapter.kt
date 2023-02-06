package com.example.yourfitnessidol.adapter

import android.app.TimePickerDialog
import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfitnessidol.R
import com.example.yourfitnessidol.model.food
import com.example.yourfitnessidol.model.goaldetail
import kotlinx.android.synthetic.main.goal_list_single_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class fg_recycler_adapter(val context: Context, val goal_list: ArrayList<goaldetail>) : RecyclerView.Adapter<fg_recycler_adapter.fgViewHolder>()

{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): fgViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.goal_list_single_row,parent ,false)
        return fgViewHolder(view)

    }

    override fun getItemCount(): Int {
        return goal_list.size
    }

    override fun onBindViewHolder(holder: fgViewHolder, position: Int) {
        val goal=goal_list[position]
        holder.goal_rep.text=goal.goalrepititions
        holder.goal_nam.text=goal.goalname
        holder.goal_fre.text=goal.goalfrequency
        holder.goal_timebox.text=goal.goalexercisetiming
        /*holder.foodFats.text=food.foodFats
        holder.foodSource.text=food.foodSource
        holder.foodImage.setImageResource(food.foodImage)*/

        /*holder.goal_timebox.setOnClickListener(){

            val now=Calendar.getInstance()
            val timeform=SimpleDateFormat("hh:mm a",Locale.US)

            val timepicker= TimePickerDialog(this.context,TimePickerDialog.OnTimeSetListener{view, hourOfDay, minute ->
                val selectedtime=Calendar.getInstance()
                selectedtime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedtime.set(Calendar.HOUR_OF_DAY,hourOfDay)

                val time_for_exercise=timeform.format(selectedtime.time)


            },
            now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
}*/



    }







    class fgViewHolder(view: View):RecyclerView.ViewHolder(view)
    {val goal_rep: TextView =view.findViewById(R.id.goal_rep)
        val goal_nam: TextView =view.findViewById(R.id.goal_nam)
        val goal_fre: TextView =view.findViewById(R.id.goal_fre)
        val goal_timebox: TextView=view.findViewById(R.id.goal_timebox)





    }
}