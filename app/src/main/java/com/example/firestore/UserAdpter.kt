package com.example.firestore

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.User_Interface

class UserAdpter(userlist1: AppCompatActivity, var userlist: MutableList<User_data> ,var context:Context,var onclick:User_Interface,
    var ondelete :User_Interface

):RecyclerView.Adapter<UserAdpter.MyViewHolder>() {
    class MyViewHolder( item:android.view.View):RecyclerView.ViewHolder(item){

         val userIdTextView: TextView = itemView.findViewById(R.id.userIdTextView)
         val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
         val userAddressTextView: TextView = itemView.findViewById(R.id.userAddressTextView)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.userlayout, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
       return userlist.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.userIdTextView.text=userlist[position].id
        holder.userNameTextView.text=userlist[position].name
        holder.userAddressTextView.text=userlist[position].address

            holder.userIdTextView.setOnClickListener {
                userlist[position].name?.let { it1->onclick.Onupdate(it1,position) }
            }

            holder.userAddressTextView.setOnClickListener {
                userlist[position]?.name?.let { it -> ondelete.Ondelete(it, position) }

            }
        }
    }
