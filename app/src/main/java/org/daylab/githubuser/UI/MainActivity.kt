package org.daylab.githubuser.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import org.daylab.githubuser.R

class MainActivity : AppCompatActivity() {
    companion object{
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}