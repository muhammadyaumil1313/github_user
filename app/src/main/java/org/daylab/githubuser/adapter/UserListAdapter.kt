package org.daylab.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.daylab.githubuser.R
import org.daylab.githubuser.models.Item

class UserListAdapter(private val listUsers : ArrayList<Item>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photo : ImageView = itemView.findViewById(R.id.photo_users)
        var username : TextView = itemView.findViewById(R.id.name_users)
    }
    private lateinit var onItemClickCallback: onitemClickCallback

    fun setOnItemClickCallback(clickCallback : onitemClickCallback){
        this.onItemClickCallback = clickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_user,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listUsers.size
    fun removeItem(position : Int){
        listUsers.removeAt(position)
        notifyItemChanged(position)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name,avatar_url,login) = listUsers[position]
        Glide.with(holder.itemView.context).load(avatar_url).into(holder.photo)
        holder.username.text = login
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUsers[holder.adapterPosition])
        }
    }

    interface onitemClickCallback{
        fun onItemClicked(dataUser : Item)
    }
}