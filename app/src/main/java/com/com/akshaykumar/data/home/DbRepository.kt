package com.com.akshaykumar.data.home

import com.com.akshaykumar.data.db.SqliteHelper
import com.com.akshaykumar.data.model.Workshop

class DbRepository(
    private val DBHelper: SqliteHelper
) {

    fun addWorkShops(workShops: ArrayList<Workshop>) = DBHelper.addWorkshops(workShops)

    fun apply(id: Int) = DBHelper.applied(id)

    fun reset() = DBHelper.reset()

    fun getWorkShops(): ArrayList<Workshop> = DBHelper.getAllWorkShop()

    fun appliedWorkshops(): ArrayList<Workshop> = DBHelper.getAppliedWorkshops()
}