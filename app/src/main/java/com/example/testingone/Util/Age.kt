/*
 * Created by KDMS on 15/11/18 10:00 AM
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 15/11/18 10:00 AM
 */

package com.example.testingone.Util

class Age {

    var days: Int = 0
    var months: Int = 0
    var years: Int = 0

    private constructor() {
        //Prevent default constructor
    }

    constructor(days: Int, months: Int, years: Int) {
        this.days = days
        this.months = months
        this.years = years
    }

    override fun toString(): String {
        return "$years Years, $months Months, $days Days"
    }
}
