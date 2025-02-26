package com.example.lastrisocialproject

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textViewNode1: TextView = findViewById(R.id.suhuAtas)
        val textViewNode2: TextView = findViewById(R.id.suhuBawah)

        val database = FirebaseDatabase.getInstance("https://lastri-88-default-rtdb.asia-southeast1.firebasedatabase.app")
        val refNode1 = database.getReference("wadah1").child("suhu_atas")
        val refNode2 = database.getReference("wadah1").child("suhu_bawah")

        refNode1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val suhuAtas = snapshot.getValue(Double::class.java)
                Log.d("Firebase", "Suhu atas: $suhuAtas")
                textViewNode1.text = suhuAtas?.toString() ?: "No Data"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read suhu_atas: ${error.message}")
            }
        })

        refNode2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val suhuBawah = snapshot.getValue(Double::class.java)
                Log.d("Firebase", "Suhu bawah: $suhuBawah")
                textViewNode2.text = suhuBawah?.toString() ?: "No Data"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read suhu_bawah: ${error.message}")
            }
        })

        val reloadButton: ImageView = findViewById(R.id.perbaruiData)
        reloadButton.setOnClickListener {
            // Panggil ulang onCreate
            recreate()
        }

    }
}