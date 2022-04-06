package com.binar.objectrelationalmapping.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.binar.objectrelationalmapping.R
import com.binar.objectrelationalmapping.data.Student
import com.binar.objectrelationalmapping.data.StudentDatabase
import com.binar.objectrelationalmapping.databinding.ActivityMainBinding
import com.binar.objectrelationalmapping.databinding.AddStudentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AddActivity : AppCompatActivity() {

    var mDb: StudentDatabase? = null
    private lateinit var binding: AddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = AddStudentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mDb = StudentDatabase.getInstance(this)

        binding.btnSave.setOnClickListener{
            val objectStudent = Student(
                null,
                binding.nameEditText.text.toString(),
                binding.emailEditText.text.toString()
            )

            Thread(Runnable {
                val result = mDb?.studentDao()?.insertStudent(objectStudent)
                runOnUiThread {
                    if(result != 0.toLong()) {
                        Toast.makeText(this@AddActivity,"Success adding ${objectStudent.name}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@AddActivity, "Failed adding ${objectStudent.name}", Toast.LENGTH_LONG).show()
                    }
                    finish()
                }
            }).start()
        }

    }
}