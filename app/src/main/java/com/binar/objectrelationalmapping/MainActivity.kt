package com.binar.objectrelationalmapping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.objectrelationalmapping.activities.AddActivity
import com.binar.objectrelationalmapping.data.StudentDatabase
import com.binar.objectrelationalmapping.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private var mDB: StudentDatabase? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        mDB = StudentDatabase.getInstance(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        fetchData()

        binding.addButton.setOnClickListener{
            val toActivityAdd = Intent(this, AddActivity::class.java)
            startActivity(toActivityAdd)
        }

    //--------------------------------------------
//        val executor = Executors.newSingleThreadExecutor()
//
//        executor.execute {
//            Thread.sleep(3000)
//            val threadId = Thread.currentThread().name
//            Log.i("Tag","I am thread $threadId")
//            Log.i("Tag","Task 2 Completed")
//        }
          //--------------------------------------------
//        val executor = Executors.newFixedThreadPool(4)
//
//        executor.execute {
//            Thread.sleep(3000)
//            val threadId = Thread.currentThread().name
//            Log.i("Tag","I am thread $threadId")
//            Log.i("Tag","Task 1 Completed")
//        }
//
//        executor.execute {
//            Thread.sleep(500)
//            val threadId = Thread.currentThread().name
//            Log.i("Tag","I am thread $threadId")
//            Log.i("Tag","Task 2 Completed")
//        }
//
//        executor.execute {
//            Thread.sleep(1000)
//            val threadId = Thread.currentThread().name
//            Log.i("Tag","I am thread $threadId")
//            Log.i("Tag","Task 3 Completed")
//        }
//
//        executor.execute {
//            val threadId = Thread.currentThread().name
//            Log.i("Tag","I am thread $threadId")
//            Log.i("Tag","Task 1 Completed")
//        }

          //--------------------------------------------
//        val executor = Executors.newCachedThreadPool()
//
//        executor.execute{
//            Log.i("Tag","I am thread ${Thread.currentThread().name}")
//            Log.i("Tag","Task 1 Completed")
//        }
//
//        executor.execute{
//            Log.i("Tag","I am thread ${Thread.currentThread().name}")
//            Log.i("Tag","Task 2 Completed")
//        }
        //--------------------------------------------
    }


    fun fetchData(){
        Thread(Runnable {
            val listStudent = mDB?.studentDao()?.getAllStudent()

            runOnUiThread {
                listStudent?.let{
                    val adapter = StudentAdapter(it)
                    binding.recyclerView.adapter = adapter
                }
            }
        }).start()
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        StudentDatabase.destroyInstance()
    }


}