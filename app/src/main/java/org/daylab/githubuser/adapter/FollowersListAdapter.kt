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

class FollowersListAdapter(private val listUsers : ArrayList<Item>) : RecyclerView.Adapter<FollowersListAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photo : ImageView = itemView.findViewById(R.id.photo_followers)
        var username : TextView = itemView.findViewById(R.id.name_followers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_followers,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_,avatar_url,login) = listUsers[position]
        Glide.with(holder.itemView.context).load(avatar_url).into(holder.photo)
        holder.username.text = login
    }
}