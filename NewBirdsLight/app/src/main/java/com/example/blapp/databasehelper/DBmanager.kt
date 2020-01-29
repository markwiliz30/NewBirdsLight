package com.example.blapp.databasehelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.blapp.model.PgmItem
import com.example.blapp.model.StepItem


class DBmanager(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {
    companion object{
        const val DATABASE_NAME = "BIRDSLIGHT.db"
        const val DATABASE_VER = 1

        const val STEP_TABLE = "TABLE_STEP"
        const val STEP_ID = "STEP_ID"
        const val STEP_COMMAND = "STEP_COMMAND"
        const val STEP_STEP = "STEP_STEP"
        const val STEP_PAN = "STEP_PAN"
        const val STEP_TILT = "STEP_TILT"
        const val STEP_BLINK = "STEP_BLINK"
        const val STEP_TIME = "STEP_TIME"
        const val STEP_PGM_NAME = "STEP_PGM_NAME"

        const val PROGRAM_TABLE = "TABLE_PROGRAM"
        const val PROGRAM_ID = "PROGRAM_ID"
        const val PROGRAM_COMMAND = "PROGRAM_COMMAND"
        const val PROGRAM_NAME = "PROGRAM_NAME"

        const val SEQUENCE_TABLE = "TABLE_SEQUENCE"
        const val SEQUENCE_ID = "SEQUENCE_ID"
        const val SEQUENCE_COMMAND = "SEQUENCE_COMMAND"
        const val SEQUENCE_SQN = "SEQUENCE_SQN"
        const val SEQUENCE_SHOUR = "SEQUENCE_SHOUR"
        const val SEQUENCE_SMINUTE = "SEQUENCE_SMINUTE"
        const val SEQUENCE_EHOUR = "SEQUENCE_EHOUR"
        const val SEQUENCE_EMINUTE = "SEQUENCE_EMINUTE"
        const val SEQUENCE_NAME = "SEQUENCE_NAME"

        const val SEQ_PROGRAM_TABLE = "TABLE_SEQ_PROGRAM"
        const val SEQ_PROGRAM_ID = "SEQ_PROGRAM_ID"
        const val SEQ_PROGRAM_COMMAND = "SEQ_PROGRAM_COMMAND"
        const val SEQ_PROGRAM_SQN = "SEQ_PROGRAM_SQN"
        const val SEQ_PROGRAM_ORDER = "SEQ_PROGRAM_ORDER"
        const val SEQ_PROGRAM_PGM = "SEQ_PROGRAM_PGM"
        const val SEQ_PROGRAM_DURATION = "SEQ_PROGRAM_DURATION"

        const val SETUP_TABLE = "TABLE_SETUP"
        const val SETUP_ID = "SETUP_ID"
        const val SETUP_COMMAND = "SETUP_COMMAND"
        const val SETUP_STP = "SETUP_STP"
        const val SETUP_SMONTH = "SETUP_SMONTH"
        const val SETUP_SDAY = "SETUP_SDAY"
        const val SETUP_EMONTH = "SETUP_EMONTH"
        const val SETUP_EDAY = "SETUP_EDAY"
        const val SETUP_NAME = "SETUP_NAME"

        const val SETUP_SEQ_TABLE = "TABLE_SETUP_SEQ"
        const val SETUP_SEQ_ID = "SETUP_SEQ_ID"
        const val SETUP_SEQ_COMMAND = "SETUP_SEQ_COMMAND"
        const val SETUP_SEQ_STP = "SETUP_SEQ_STP"
        const val SETUP_SEQ_SQN = "SETUP_SEQ_SQN"
        const val SETUP_SEQ_DAY = "SETUP_SEQ_DAY"


    }
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(("CREATE TABLE $STEP_TABLE ($STEP_ID INTEGER PRIMARY KEY AUTOINCREMENT, $STEP_COMMAND BYTE, $STEP_STEP BYTE, $STEP_PAN BYTE,$STEP_TILT BYTE , $STEP_BLINK BYTE , $STEP_TIME BYTE , $STEP_PGM_NAME TEXT)"))
        db!!.execSQL(("CREATE TABLE $PROGRAM_TABLE ($PROGRAM_ID INTEGER PRIMARY KEY AUTOINCREMENT , $PROGRAM_COMMAND BYTE, $PROGRAM_NAME TEXT)"))
        db!!.execSQL(("CREATE TABLE $SEQUENCE_TABLE ($SEQUENCE_ID INTEGER PRIMARY KEY AUTOINCREMENT, $SEQUENCE_COMMAND BYTE , $SEQUENCE_SQN BYTE , $SEQUENCE_SHOUR BYTE,$SEQUENCE_SMINUTE BYTE, $SEQUENCE_EHOUR BYTE , $SEQUENCE_EMINUTE BYTE, $SEQUENCE_NAME TEXT)"))
        db!!.execSQL(("CREATE TABLE $SEQ_PROGRAM_TABLE ($SEQ_PROGRAM_ID INTEGER PRIMARY KEY AUTOINCREMENT , $SEQ_PROGRAM_COMMAND BYTE , $SEQ_PROGRAM_SQN BYTE , $SEQ_PROGRAM_ORDER BYTE , $SEQ_PROGRAM_PGM BYTE , $SEQ_PROGRAM_DURATION BYTE)"))
        db!!.execSQL(("CREATE TABLE $SETUP_TABLE ($SETUP_ID INTEGER PRIMARY KEY AUTOINCREMENT , $SETUP_COMMAND BYTE, SETUP_STP BYTE , $SETUP_SMONTH BYTE , $SETUP_SDAY BYTE , $SETUP_EMONTH BYTE, $SETUP_EDAY BYTE , $SETUP_NAME TEXT)"))
        db!!.execSQL(("CREATE TABLE $SETUP_SEQ_TABLE ($SETUP_SEQ_ID INTEGER PRIMARY KEY AUTOINCREMENT , $SETUP_SEQ_COMMAND BYTE , $SETUP_SEQ_STP BYTE , $SETUP_SEQ_SQN BYTE , $SETUP_SEQ_DAY BYTE)"))


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    db!!.execSQL("DROP TABLE IF EXISTS" + STEP_TABLE)
        db!!.execSQL("DROP TABLE IF EXISTS" + PROGRAM_TABLE)
        db!!.execSQL("DROP TABLE IF EXISTS" + SEQUENCE_TABLE)
        db!!.execSQL("DROP TABLE IF EXISTS" + SEQ_PROGRAM_TABLE)
        db!!.execSQL("DROP TABLE IF EXISTS" + SETUP_TABLE)
        db!!.execSQL("DROP TABLE IF EXISTS" + SETUP_SEQ_TABLE)
    }

    fun getTables(){
        val db: SQLiteDatabase = this.writableDatabase
        val arrTblNames = ArrayList<String>()
        val c =
            db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)

        if (c.moveToFirst()) {
            while (!c.isAfterLast) {
                arrTblNames.add(c.getString(c.getColumnIndex("name")))
                c.moveToNext()
            }
        }
    }
    val allStep:List<StepItem>

        get(){
            val lstStep = ArrayList<StepItem>()
            val selectQuery = "SELECT * FROM $STEP_TABLE"
            val db:SQLiteDatabase = this.writableDatabase
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if(cursor.moveToFirst()){
                do{
                    val step = StepItem()
                    step.step_id = cursor.getInt(cursor.getColumnIndex(STEP_ID.toString())).toByte()
                    step.pgm_name = cursor.getString(cursor.getColumnIndex(STEP_PGM_NAME.toString()))
                    step.step = cursor.getInt(cursor.getColumnIndex(STEP_STEP.toString())).toByte()
                    step.pan = cursor.getInt(cursor.getColumnIndex(STEP_PAN.toString())).toByte()
                    step.tilt = cursor.getInt(cursor.getColumnIndex(STEP_TILT.toString())).toByte()
                    step.blink = cursor.getInt(cursor.getColumnIndex(STEP_BLINK.toString())).toByte()
                    step.time = cursor.getInt(cursor.getColumnIndex(STEP_TIME.toString())).toByte()

                    lstStep.add(step)

                }while(cursor.moveToNext())

            }
            db.close()
            return lstStep
        }


    fun addStep(step:StepItem){
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(STEP_PGM_NAME,step.pgm_name)
        values.put(STEP_STEP, step.step)
        values.put(STEP_PAN, step.pan)
        values.put(STEP_TILT, step.tilt)
        values.put(STEP_BLINK, step.blink)
        values.put(STEP_TIME, step.time)

        db.insert(STEP_TABLE , null , values)
        db.close()
    }


    fun updateStep(step: StepItem): Int {
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(STEP_PGM_NAME,step.pgm_name)
        values.put(STEP_STEP, step.step)
        values.put(STEP_PAN, step.pan)
        values.put(STEP_TILT, step.tilt)
        values.put(STEP_BLINK, step.blink)
        values.put(STEP_TIME, step.time)

        return db.update(STEP_TABLE , values , "$STEP_PGM_NAME =" , arrayOf(step.pgm_name.toString()))
        db.close()
    }

    fun deleteStep(step: StepItem){
        val db:SQLiteDatabase = this.writableDatabase

        db.delete(STEP_TABLE , "$STEP_PGM_NAME = ?" , arrayOf(step.pgm_name.toString()))
        db.close()
    }


    val allpgm:List<PgmItem>

        get() {
            val lstpgm = ArrayList<PgmItem>()
            val selectQuery = "SELECT * FROM $PROGRAM_TABLE"
            val db: SQLiteDatabase = this.writableDatabase
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if(cursor.moveToFirst()){
                do{
                    val pgm = PgmItem()
                    pgm.pgm_id = cursor.getInt(cursor.getColumnIndex(PROGRAM_ID.toString())).toByte()
                    pgm.command = cursor.getInt(cursor.getColumnIndex(PROGRAM_COMMAND.toString())).toByte()
                    pgm.name = cursor.getString(cursor.getColumnIndex(PROGRAM_NAME.toString()))

                    lstpgm.add(pgm)
                }while(cursor.moveToNext())

            }
            db.close()
            return lstpgm
        }

    fun addPgm(pgm: PgmItem){
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(PROGRAM_COMMAND,pgm.command)
        values.put(PROGRAM_NAME, pgm.name)
        db.insert(PROGRAM_TABLE, null , values)
        db.close()

    }

    fun updatePgm(pgm:PgmItem): Int{
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(PROGRAM_ID, pgm.pgm_id)
        values.put(PROGRAM_COMMAND,pgm.command)
        values.put(PROGRAM_NAME, pgm.name)

        return db.update(PROGRAM_TABLE, values , "$PROGRAM_ID = ?" , arrayOf(pgm.pgm_id.toString()))
        db.close()
    }

    fun deletePgm(pgm: PgmItem){
        val db:SQLiteDatabase = this.writableDatabase

        db.delete(PROGRAM_TABLE, "$PROGRAM_ID = ?", arrayOf(pgm.pgm_id.toString()))
        db.close()
    }

    fun deleteAllPgm(){
        val db:SQLiteDatabase = this.writableDatabase

        db.execSQL("DELETE from $PROGRAM_TABLE")
    }

    fun deleteAllStep(){
        val db:SQLiteDatabase = this.writableDatabase
        db.execSQL("DELETE from $STEP_TABLE")
    }
}