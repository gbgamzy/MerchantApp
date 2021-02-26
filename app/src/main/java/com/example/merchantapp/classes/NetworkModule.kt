package com.example.ajubamerchant.classes



import android.content.Context
import androidx.room.Room
import com.example.merchantapp.classes.AppDatabase
import com.example.merchantapp.classes.HomeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {

    @Provides
    fun providesBaseUrl(): String {
        return "http://192.168.1.8:1234"
    }



    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofitClient(baseUrl: String, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)

            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideRestAiService(retrofit: Retrofit): Network {
        return retrofit.create(Network::class.java)

    }

    /*@Provides
    @Singleton
    fun providesContext(): Application? {
        return Application()
    }*/

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "DbReader"
        ).build()
    }

    @Provides
    fun provideMenuDAO(appDatabase: AppDatabase): HomeDao {
        return appDatabase.homeDao()
    }


}