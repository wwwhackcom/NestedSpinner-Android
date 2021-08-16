package com.nickwang.nestedspinner.example

import com.nickwang.nestedspinner.NestedSpinnerData

/**
 * @author nickwang
 * Created 10/07/21
 */
class Entity : NestedSpinnerData {

    //field extension
    var userInfo: Any? = null

    constructor(name: String) : super(name)

    constructor(name: String, data: Any) : super(name, data)

    constructor(name: String, data: Any, backgroundColour: String, textColour: String) : super(
        name,
        data,
        backgroundColour,
        textColour
    )

}
