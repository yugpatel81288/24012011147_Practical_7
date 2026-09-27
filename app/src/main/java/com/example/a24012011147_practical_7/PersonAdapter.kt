package com.example.a24012011147_practical_7

// !! ERROR FIXED: Added all missing imports
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class PersonAdapter(
    private val context: MainActivity,
    private val array: ArrayList<Person>
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    inner class PersonViewHolder(val bindingView: View) : RecyclerView.ViewHolder(bindingView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun getItemCount(): Int = array.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {

        val person = array[position]

        holder.bindingView.findViewById<TextView>(R.id.textview__phone_no).text = person.phoneNo
        holder.bindingView.findViewById<TextView>(R.id.textview_name).text = person.name
        holder.bindingView.findViewById<TextView>(R.id.textview_email).text = person.emailId
        holder.bindingView.findViewById<TextView>(R.id.textview_address).text = person.address

        holder.bindingView.findViewById<Button>(R.id.button_delete).setOnClickListener {
            // This will now work
            context.deletePerson(holder.adapterPosition)
        }
    }
}