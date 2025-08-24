package com.settery.adappapr.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.settery.adappapr.data.ApiService
import com.settery.adappapr.domain.ContentRepository
import com.settery.adappapr.domain.GetContentUseCase
import com.settery.adappapr.domain.SettingsRepository
import com.settery.adappapr.domain.impl.ContentRepositoryImpl
import com.settery.adappapr.domain.impl.SettingsRepositoryImpl
import com.settery.adappapr.presentation.MainViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile("settings")
        }
    }
}
val viewModelModule = module {
    viewModel {
        MainViewModel(getContentUseCase = get())
    }
}
val domainModule = module {
    factory { GetContentUseCase(repository = get()) }
}

val networkModule = module{
    single {
        OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://example.com/") // todo url
            .client(get()) // Koin сам найдет OkHttpClient, определенный выше
//            .addConverterFactory(MoshiConverterFactory.create(get())) // Koin найдет Moshi
            .build()
    }

    single<ApiService> {
        // Koin возьмет Retrofit (определен выше) и создаст реализацию сервиса
        get<Retrofit>().create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single<SettingsRepository> {
        SettingsRepositoryImpl(dataStore = get())
    }

    single<ContentRepository> {
        ContentRepositoryImpl(apiService = get())
    }
}


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                networkModule,
                repositoryModule,
                domainModule,
                viewModelModule
            )
        }
    }
}