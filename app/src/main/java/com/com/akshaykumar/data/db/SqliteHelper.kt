package com.com.akshaykumar.data.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.com.akshaykumar.data.model.Workshop

class SqliteHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        // creating a constant variables for our database.
        // below variable is for our database name.
        private val DB_NAME = "WORKSHOP_DATA"

        // below int is our database version
        private val DB_VERSION = 1

        // below variable is for our table name.
        private val TABLE_NAME = "WORKSHOPS"

        // below variable is for our id column.
        private val ID_COL = "id"

        // below variable is for our WORKSHOP name column
        private val NAME_COL = "WORKSHOP_NAME"

        // below variable id for our WORKSHOP COMPANY column.
        private val COMPANY_COL = "WORKSHOP_COMPANY"

        private val DESCRIPTION_COL = "WORKSHOP_DESCRIPTION"

        private val DATE_COL = "WORKSHOP_DATE"

        private val APPLIED = "WORKSHOP_APPLIED"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_COL + " TEXT," +
                COMPANY_COL + " TEXT," +
                DESCRIPTION_COL + " TEXT," +
                DATE_COL + " TEXT," +
                APPLIED + " INTEGER" + ")")

        // we are calling sqlite
        // method for executing our query
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(p0)
    }

    fun addWorkshops(workshop: List<Workshop>) {

        val db = this.writableDatabase
        workshop.forEach {
            it.apply {
                val values = ContentValues()
                values.put(NAME_COL, name)
                values.put(COMPANY_COL, company)
                values.put(DESCRIPTION_COL, description)
                values.put(DATE_COL, date)
                values.put(APPLIED, applied)
                // all values are inserted into database
                db.insert(TABLE_NAME, null, values)
            }
        }
        // at last we are
        // closing our database
        db.close()
    }

    fun applied(id: Int) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(APPLIED, 1)

        db.update(TABLE_NAME, values, "id=?", arrayOf(id.toString()))
        db.close()
    }

    fun reset() {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(APPLIED, 0)

        db.update(TABLE_NAME, values, null, null)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllWorkShop(): ArrayList<Workshop>  {
        val db = this.readableDatabase
        val data =  db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val list = ArrayList<Workshop>()
        if (data.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                list.add(
                    Workshop(
                        data.getInt(data.getColumnIndex(ID_COL)),
                        data.getString(data.getColumnIndex(NAME_COL)),
                        data.getString(data.getColumnIndex(COMPANY_COL)),
                        data.getString(data.getColumnIndex(DESCRIPTION_COL)),
                        data.getString(data.getColumnIndex(DATE_COL)),
                        data.getInt(data.getColumnIndex(APPLIED))
                    )
                )
            } while (data.moveToNext())
        }

        data.close();
        return list;
    }

    @SuppressLint("Range")
    fun getAppliedWorkshops(): ArrayList<Workshop>  {
        val db = this.readableDatabase
        val data =  db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $APPLIED = 1", null)

        val list = ArrayList<Workshop>()
        if (data.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                list.add(
                    Workshop(
                        data.getInt(data.getColumnIndex(ID_COL)),
                        data.getString(data.getColumnIndex(NAME_COL)),
                        data.getString(data.getColumnIndex(COMPANY_COL)),
                        data.getString(data.getColumnIndex(DESCRIPTION_COL)),
                        data.getString(data.getColumnIndex(DATE_COL)),
                        data.getInt(data.getColumnIndex(APPLIED))
                    )
                )
            } while (data.moveToNext())
        }

        data.close();
        return list;
    }
}