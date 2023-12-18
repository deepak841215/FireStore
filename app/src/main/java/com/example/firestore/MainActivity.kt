package com.example.firestore

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.User_Interface
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import java.util.UUID

class MainActivity : AppCompatActivity(),User_Interface {
lateinit var userData :ArrayList<User_data>
    lateinit var submitBt: Button
     lateinit var cutomAdapter: UserAdpter
     val db = FirebaseFirestore.getInstance()
    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         userData = arrayListOf<User_data>()

        val recyciler = findViewById<RecyclerView>(R.id.recycler)
        recyciler .layoutManager =LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        cutomAdapter = UserAdpter(this, userData,this,this,this)
        recyciler.adapter = cutomAdapter




        submitBt = findViewById(R.id.submit)
        submitBt.setOnClickListener {


            val layout = LayoutInflater.from(this).inflate(R.layout.alertdilog,null,false)
            val namel = layout.findViewById<EditText>(R.id.namel)
            val address = layout.findViewById<EditText>(R.id.addressl)
            val submitl = layout.findViewById<Button>(R.id.submitl)
            val alertDialog = AlertDialog.Builder(this)
            val alert = alertDialog.create()
            alert.setView(layout)
            alert.show()
            submitl.setOnClickListener {

                val id = UUID.randomUUID().toString()
                val user_data = User_data(id,namel.text.toString(),address.text.toString())
                db.collection("user").document(id).set(user_data)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Recycler data added", Toast.LENGTH_SHORT).show()
                        userData.add(user_data)
                        cutomAdapter.notifyDataSetChanged()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()

                    }
                alert.dismiss()
            }

        }

        db.collection("user").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val userModel = document.toObject(User_data::class.java)
                    userData.add(userModel)
                }
                cutomAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error fetching initial data: ${exception.message}", Toast.LENGTH_SHORT).show()}


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun Onupdate(name: String, position: Int) {

val alertd = AlertDialog .Builder(this)
        alertd.setPositiveButton("Yes"){v,n->
        val layout = LayoutInflater.from(this).inflate(R.layout.alertdilog,null,false)
        val namel = layout.findViewById<EditText>(R.id.namel)
        val address = layout.findViewById<EditText>(R.id.addressl)
        val submitl = layout.findViewById<Button>(R.id.submitl)
        val alertDialog = AlertDialog.Builder(this)
        val alert = alertDialog.create()

       db.collection("user").document(userData[position].id.toString()).get()
           .addOnSuccessListener {
               val data = it.toObject(User_data::class.java)
               namel.setText(data?.name)
               address.setText(data?.address)
           }
           .addOnFailureListener{
               Snackbar.make(submitl,it.message.toString(),Snackbar.LENGTH_SHORT).show()
           }
        alert.setView(layout)
        alert.show()
        submitl.setOnClickListener {
            val id =userData[position].id.toString()
            val usermodel = User_data(id,namel.text.toString(),address.text.toString())
            db.collection("user").document(id).set(usermodel)
                .addOnSuccessListener {
                    startActivity(Intent(this,MainActivity::class.java))
                    Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            cutomAdapter.notifyDataSetChanged()
            alert.dismiss()}}
        alertd.create()
        alertd.setNegativeButton("No"){n,m->}
        alertd.setTitle("Update")
        alertd.setMessage("Are you sure Update")
        alertd.show()



    }

    @SuppressLint("NotifyDataSetChanged")
    override fun Ondelete(name: String, position: Int) {


        val dilog = AlertDialog.Builder(this)
        dilog.setNegativeButton("No"){
                e,t->
        }
        dilog.setPositiveButton("Yes"){n,t->
        db.collection("user").document(userData[position].id.toString()).delete()
            .addOnSuccessListener {

                Toast.makeText(this, "On Successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, "On Un Successful", Toast.LENGTH_SHORT).show()

            }

        userData.removeAt(position)
        cutomAdapter.notifyDataSetChanged()}
        dilog.setTitle("Delete")
        dilog.setMessage("Do you sure Delete")
        dilog.show()
    }


}
    
