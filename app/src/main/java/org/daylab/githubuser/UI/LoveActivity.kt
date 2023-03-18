package org.daylab.githubuser.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.daylab.githubuser.adapter.LoveListAdapter
import org.daylab.githubuser.databinding.ActivityLoveBinding
import org.daylab.githubuser.models.Love

class LoveActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoveBinding
    private lateinit var rvLoves : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoveBinding.inflate(layoutInflater)
        supportActionBar?.title = "Love History"
        setContentView(binding.root)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        rvLoves = binding.rvLoves
        rvLoves.setHasFixedSize(true)
        val dataDummy = Love("yaumil","https://unsplash.com/photos/wWpNyj0HVQg")
        val dataList = ArrayList<Love>()
        dataList.add(dataDummy)
        setItem(dataList)
        val layoutManager = LinearLayoutManager(this)
        rvLoves.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        rvLoves.addItemDecoration(itemDecoration)

    }

    private fun setItem(modelItemArray: ArrayList<Love>) {
        val listLoversAdapter = LoveListAdapter(this,modelItemArray)
        rvLoves.adapter = listLoversAdapter
        listLoversAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}