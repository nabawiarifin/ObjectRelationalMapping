package com.binar.objectrelationalmapping

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.binar.objectrelationalmapping.activities.EditActivity
import com.binar.objectrelationalmapping.data.Student
import com.binar.objectrelationalmapping.data.StudentDatabase
import com.binar.objectrelationalmapping.databinding.StudentItemBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class StudentAdapter(val listStudent : List<Student>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = StudentItemBinding.bind(itemView) //XML student_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listStudent.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.tvID.text = listStudent[position].id.toString()
            binding.tvName.text = listStudent[position].name
            binding.tvEmail.text = listStudent[position].email

            binding.ivEdit.setOnClickListener{
                val intentToEditActivity = Intent(it.context, EditActivity::class.java)

                intentToEditActivity.putExtra("student", listStudent[position])
                it.context.startActivity(intentToEditActivity)
            }

            binding.ivDelete.setOnClickListener{
                AlertDialog.Builder(it.context).setPositiveButton("Yes") {po, p1 ->
                    val mDb = StudentDatabase.getInstance(holder.itemView.context)

                Thread(Runnable {
                    val result = mDb?.studentDao()?.deleteStudent(listStudent[position])

                    (holder.itemView.context as MainActivity).runOnUiThread {
                        if (result != 0) {
                            Toast.makeText(it.context,"Data ${listStudent[position].name} has been deleted",Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(it.context,"Data ${listStudent[position].name} has failed to be deleted",Toast.LENGTH_SHORT).show()
                        }
                    }

                    (holder.itemView.context as MainActivity).fetchData()
                }).start()

                }.setNegativeButton("No"){
                    p0, p1 ->
                    p0.dismiss()
                }
                    .setMessage("Are you sure you want to delete ${listStudent[position].name}").setTitle("Confirm Delete").create().show()
            }
        }

    }
}