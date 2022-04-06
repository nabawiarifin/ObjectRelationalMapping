package com.binar.objectrelationalmapping.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.binar.objectrelationalmapping.R
import com.binar.objectrelationalmapping.data.Student
import com.binar.objectrelationalmapping.data.StudentDatabase
import com.binar.objectrelationalmapping.databinding.ActivityMainBinding
import com.binar.objectrelationalmapping.databinding.EditStudentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class EditActivity : AppCompatActivity() {
    var mDb : StudentDatabase? = null
    private lateinit var binding: EditStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = EditStudentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mDb = StudentDatabase.getInstance(this)

        val objectStudent = intent.getParcelableExtra<Student>("student")

        binding.nameEditText.setText(objectStudent?.name)
        binding.emailEditText.setText(objectStudent?.email)

        binding.btnEdit.setOnClickListener {
            objectStudent?.name = binding.nameEditText.text.toString()
            objectStudent?.email = binding.emailEditText.text.toString()

            Thread(Runnable {
                val result = objectStudent?.let { it1 -> mDb?.studentDao()?.updateStudent(it1) }

                runOnUiThread {
                    if (result != 0) {
                        Toast.makeText(this@EditActivity, "Success editing ${objectStudent?.name}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@EditActivity, "Failed editing ${objectStudent.name}", Toast.LENGTH_LONG).show()
                    }

                    finish()
                }
            }).start()
        }



    }
}