package com.nickwang.nestedspinner.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nickwang.nestedspinner.BaseNestedSpinnerAdapter
import com.nickwang.nestedspinner.ExpandableListAdapter
import com.nickwang.nestedspinner.NestedSpinnerData
import com.nickwang.nestedspinner.example.databinding.ActivityMainBinding

/**
 * @author nickwang
 * Created 12/08/21
 */
class BasicExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Basic Usage"
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNestedSpinner()
    }

    private fun setupNestedSpinner() {
        val nestedSpinner = binding.nestedSpinner
        val nestedSpinnerAdapter = BaseNestedSpinnerAdapter(this, createDataSource())
        nestedSpinner.setNestedAdapter(nestedSpinnerAdapter)
        nestedSpinner.onItemSelectedListener = { subItem ->
            run {
                if (subItem is NestedSpinnerData) {
                    Log.d("MainActivity", "onItemSelected subItem: " + subItem.data)
                }
            }
        }
    }

    private fun createDataSource(): List<ExpandableListAdapter.DataSource<String, Entity>> {
        val dataTrees = ArrayList<ExpandableListAdapter.DataSource<String, Entity>>()
        var list = ArrayList<Entity>()
        list.add(Entity("Style1", "Data-1", "#03A9F4", "#FFFFFF"))
        list.add(Entity("Style2", "Data-2", "#FFBF3E", "#FFFFFF"))
        list.add(Entity("Style3", "Data-3", "#5CAB00", "#FFFFFF"))
        var dt1 = ExpandableListAdapter.DataSource("group1", list)
        dataTrees.add(dt1)
        list = ArrayList()
        list.add(Entity("Style1", "Data-1", "#03A9F4", "#FFFFFF"))
        list.add(Entity("Style2", "Data-2", "#FFBF3E", "#FFFFFF"))
        list.add(Entity("Style3", "Data-3", "#C36221", "#FFFFFF"))
        dt1 = ExpandableListAdapter.DataSource("group2", list)
        dataTrees.add(dt1)
        return dataTrees
    }
}
