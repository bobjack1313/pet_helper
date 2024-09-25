package com.csce5430sec7proj.pethelper.database.tables

import com.csce5430sec7proj.pethelper.database.AppDatabase
import com.dbflow5.annotation.Column
import com.dbflow5.annotation.PrimaryKey
import com.dbflow5.annotation.Table
import com.dbflow5.structure.BaseModel
import io.reactivex.rxjava3.annotations.NonNull

//@Table(database = AppDatabase::class)
//class Dog(@PrimaryKey var name: String = "", @PrimaryKey var breed: String = "")
//
@Table(database = AppDatabase::class)
class Dog(@PrimaryKey var id: Int = 0, var name: String? = null)

//
//@Table(database = AppDatabase::class)
//data class User(
//    @PrimaryKey(autoincrement = true)
//    @Column
//    var _id: Long = 0,
//
//    @Column
//    var id: Int = 0,
//
//    @Column
//    var name: String? = null,
//
//    @Column
//    var age: Int = 0,
//
//    @Column
//    var address: String? = null
//) : BaseModel() {
//
//    // Secondary constructor
//    constructor(id: Int, name: String) : this(0, id, name)
//
//    @NonNull
//    override fun toString(): String {
//        return "_id = $_id id = $id name = $name age = $age address = $address"
//    }
//}