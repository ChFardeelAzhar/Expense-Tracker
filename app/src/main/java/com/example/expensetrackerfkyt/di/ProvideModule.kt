package com.example.expensetrackerfkyt.di

import android.app.Application
import androidx.room.Room
import com.example.expensetrackerfkyt.data.ExpenseDatabase
import com.example.expensetrackerfkyt.data.dao.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideModule {

    @Provides
    @Singleton
    fun provideDatabase(context : Application) : ExpenseDatabase{
        return Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            "expenseDb"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(dbDao : ExpenseDatabase) : ExpenseDao = dbDao.getDao()

}