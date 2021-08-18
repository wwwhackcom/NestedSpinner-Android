package com.nickwang.nestedspinner.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nickwang.nestedspinner.BaseNestedSpinnerAdapter
import com.nickwang.nestedspinner.ExpandableListAdapter
import com.nickwang.nestedspinner.example.databinding.ActivityMainBinding

/**
 * @author nickwang
 * Created 10/07/21
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                if (subItem is Entity) {
                    Log.d("MainActivity", "onItemSelected subItem: " + subItem.userInfo)
                    when (subItem.data) {
                        1 -> startActivity(
                            Intent(
                                this@MainActivity,
                                BasicExampleActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }

    private fun createDataSource(): List<ExpandableListAdapter.DataSource<String, Entity>> {
        val dataTrees = ArrayList<ExpandableListAdapter.DataSource<String, Entity>>()
        var list = ArrayList<Entity>()
        list.add(Entity("Default Style", TAG1))
        list.add(Entity("Custom Attributes", TAG2))
        list.add(Entity("Init Expanded", TAG3))
        var dt1 = ExpandableListAdapter.DataSource("Basic Usage", list)
        dataTrees.add(dt1)
        list = ArrayList()
        list.add(Entity("Delegate Colours", TAG4))
        list.add(Entity("Custom Adapter", TAG5))
        dt1 = ExpandableListAdapter.DataSource("Customised", list)
        dataTrees.add(dt1)
        return dataTrees
    }

    companion object {
        const val TAG1 = 1
        const val TAG2 = 2
        const val TAG3 = 3
        const val TAG4 = 4
        const val TAG5 = 5
    }
}
