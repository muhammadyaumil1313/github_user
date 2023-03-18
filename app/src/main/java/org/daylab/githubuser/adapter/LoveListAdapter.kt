package org.daylab.githubuser.adapter

import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.daylab.githubuser.R
import org.daylab.githubuser.models.Love

class LoveListAdapter(private val context: Context,private val listLovers : ArrayList<Love>) : RecyclerView.Adapter<LoveListAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photo : ImageView = itemView.findViewById(R.id.photo_users)
        var username : TextView = itemView.findViewById(R.id.name_users)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_love,parent,false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (avatar,username) = listLovers[position]
        Glide.with(holder.itemView.context).load(avatar).into(holder.photo)
        holder.username.text = username
        holder.itemView.setOnLongClickListener {
            listLovers.removeAt(position)
            vibrate(context = context)
            notifyItemRemoved(position)
            true
        }
    }

    override fun getItemCount() = listLovers.size

    @RequiresApi(Build.VERSION_CODES.O)
    private fun vibrate(context: Context, duration: Long = 1000L){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorManager.defaultVibrator
            vibrator.vibrate(VibrationEffect.createOneShot(duration,VibrationEffect.DEFAULT_AMPLITUDE))
            Toast.makeText(context,"Berhasil Menghapus!",Toast.LENGTH_SHORT).show()
        } else {
            val vibration = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
            vibration.vibrate(VibrationEffect.createOneShot(duration,VibrationEffect.DEFAULT_AMPLITUDE))
            Toast.makeText(context,"Berhasil Menghapus!",Toast.LENGTH_SHORT).show()
        }
        
    }

}