package com.csce5430sec7proj.pethelper.database

import com.dbflow5.config.DBFlowDatabase
import com.dbflow5.annotation.Database

@Database(version = 1)
abstract class AppDatabase : DBFlowDatabase()