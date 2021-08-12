package com.nickwang.nestedspinner.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nickwang.nestedspinner.BaseNestedSpinnerAdapter
import com.nickwang.nestedspinner.ExpandableListAdapter
import com.nickwang.nestedspinner.NestedSpinnerView
import com.nickwang.nestedspinner.example.databinding.ActivityMainBinding

/**
 * @author nickwang
 * Created 10/07/21
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var nestedSpinner: NestedSpinnerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupNestedSpinner()
    }

    private fun setupNestedSpinner() {
        nestedSpinner = binding.nestedSpinner
        val nestedSpinnerAdapter = BaseNestedSpinnerAdapter<Entity>(this, createDataTrees())
        nestedSpinner.setNestedAdapter(nestedSpinnerAdapter)
        nestedSpinner.mOnItemSelectedListener = { subItem ->
            Log.d("MainActivity", "subItem" + subItem.toString())
        }
    }

    private fun createDataTrees(): List<ExpandableListAdapter.DataSource<String, Entity>> {
        val dataTrees = ArrayList<ExpandableListAdapter.DataSource<String, Entity>>()
        var list = ArrayList<Entity>()
        list.add(Entity("Style1", "Data-Style1", "#03A9F4", "#FFFFFF"))
        list.add(Entity("Style2", "Data-Style2", "#FFBF3E", "#FFFFFF"))
        list.add(Entity("Style3", "Data-Style3", "#5CAB00", "#FFFFFF"))
        var dt1 = ExpandableListAdapter.DataSource<String, Entity>("group1", list)
        dataTrees.add(dt1)
        list = ArrayList()
        list.add(Entity("Style1", "Data-Style1", "#03A9F4", "#FFFFFF"))
        list.add(Entity("Style2", "Data-Style2", "#FFBF3E", "#FFFFFF"))
        list.add(Entity("Style3", "Data-Style3", "#C36221", "#FFFFFF"))
        dt1 = ExpandableListAdapter.DataSource<String, Entity>("group2", list)
        dataTrees.add(dt1)
        return dataTrees
    }
}