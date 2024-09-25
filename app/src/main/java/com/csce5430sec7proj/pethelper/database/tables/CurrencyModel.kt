package com.csce5430sec7proj.pethelper.database.tables

import com.csce5430sec7proj.pethelper.database.AppDatabase
import com.dbflow5.annotation.Table
import com.dbflow5.annotation.PrimaryKey
import com.dbflow5.annotation.Column
import com.dbflow5.annotation.Unique
import com.dbflow5.config.DBFlowDatabase
import com.dbflow5.coroutines.defer
import com.dbflow5.transaction.Transaction
import com.dbflow5.paging.QueryDataSource
import com.dbflow5.query.Where
import kotlinx.coroutines.Deferred
import com.dbflow5.paging.toDataSourceFactory
import com.dbflow5.query.select
import com.dbflow5.reactivestreams.transaction.asSingle
import io.reactivex.rxjava3.core.Single

//@Table(database = AppDatabase::class)
//class Currency(@PrimaryKey(autoincrement = true) var id: Long = 0,
//               @Column @Unique var symbol: String? = null,
//               @Column var shortName: String? = null,
//               @Column @Unique var name: String = "")
//    companion object {
//        val symbol: Any = TODO()
//    }

// nullability of fields are respected. We will not assign a null value to this field.


// DAO

/**
 *  Create this class in your own database module.
 */
//interface DBProvider<out T: DBFlowDatabase> {
//    val database: T
//}
//
//interface CurrencyDAO : DBProvider<AppDatabase> {
//
//    /**
//     *  Utilize coroutines package
//     */
//    fun coroutineRetrieveUSD(): Deferred<MutableList<Currency>> =
//        database.beginTransactionAsync {
//            (select from Currency::class
//                    where (Currency.symbol eq "$")).queryList(it)
//        }.defer()
//
//    /**
//     *  Utilize RXJava2 package.
//     * Also can use asMaybe(), or asFlowable() (to register for changes and continue listening)
//     */
//    fun rxRetrieveUSD(): Single<MutableList<Currency>> =
//        database.beginTransactionAsync {
//            (select from Currency::class
//                    where (Currency.symbol eq "$"))
//                .queryList(it)
//        }.asSingle()
//
//    /**
//     *  Utilize Vanilla Transactions.
//     */
//    fun retrieveUSD(): Transaction.Builder<MutableList<Currency>> =
//        database.beginTransactionAsync {
//            (select from Currency::class
//                    where (Currency.symbol eq "$"))
//                .queryList(it)
//        }
//
//    /**
//     *  Utilize Paging Library from paging artifact.
//     */
//    fun pagingRetrieveUSD(): QueryDataSource.Factory<Currency, Where<Currency>> = (select from Currency::class
//            where (Currency.symbol eq "$"))
//        .toDataSourceFactory(database)
//
//}