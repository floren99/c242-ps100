package com.mcaps.mmm.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mcaps.mmm.data.pref.SettingPreferences
import com.mcaps.mmm.data.pref.dataStore
import com.mcaps.mmm.data.repository.ApiUserRepository
import com.mcaps.mmm.data.repository.UserRepository
import com.mcaps.mmm.di.Injection
import com.mcaps.mmm.view.auth.login.LoginViewModel
import com.mcaps.mmm.view.auth.register.RegisterViewModel

class ViewModelFactory(
    private val pref: SettingPreferences? = null,
    private val apiUserRepository: ApiUserRepository? = null,
    private val userRepository: UserRepository? = null
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                pref?.let { MainViewModel(it) } as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(apiUserRepository!!, userRepository!!) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(apiUserRepository!!) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    val apiUserRepository = Injection.provideApiUserRepository()
                    val userRepository = Injection.provideUserRepository(context)
                    val pref = SettingPreferences.getInstance(context.dataStore)
                    INSTANCE = ViewModelFactory(pref, apiUserRepository, userRepository)
                }
            }
            return INSTANCE as ViewModelFactory
        }

        @JvmStatic
        fun getRegisterViewModelFactory(apiUserRepository: ApiUserRepository): ViewModelFactory {
            return ViewModelFactory(null, apiUserRepository)
        }
    }
}