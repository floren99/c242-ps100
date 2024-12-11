package com.mcaps.mmm.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mcaps.mmm.data.pref.SettingPreferences
import com.mcaps.mmm.data.pref.TestPreference
import com.mcaps.mmm.data.pref.dataStore
import com.mcaps.mmm.data.repository.ApiUserRepository
import com.mcaps.mmm.data.repository.MajorRepository
import com.mcaps.mmm.data.repository.PredictRepository
import com.mcaps.mmm.data.repository.QuestionPrefRepository
import com.mcaps.mmm.data.repository.UserDataRepository
import com.mcaps.mmm.data.repository.UserRepository
import com.mcaps.mmm.di.Injection
import com.mcaps.mmm.view.auth.login.LoginViewModel
import com.mcaps.mmm.view.auth.register.RegisterViewModel
import com.mcaps.mmm.view.auth.reset.ResetPassViewModel
import com.mcaps.mmm.view.dashboard.home.HomeViewModel
import com.mcaps.mmm.view.dashboard.path.PathViewModel
import com.mcaps.mmm.view.dashboard.test.TestViewModel
import com.mcaps.mmm.view.question.QuizViewModel
import com.mcaps.mmm.view.question.repository.QuestionRepository

class ViewModelFactory(
    private val pref: SettingPreferences? = null,
    private val apiUserRepository: ApiUserRepository? = null,
    private val userRepository: UserRepository? = null,
    private val majorRepository: MajorRepository? = null,
    private val questionPrefRepository: QuestionPrefRepository? = null,
    private val questionRepository: QuestionRepository? = null,
    private val predictRepository: PredictRepository? = null,
    private val testPreference: TestPreference? = null,
    private val userDataRepository: UserDataRepository? = null

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
            modelClass.isAssignableFrom(ResetPassViewModel::class.java) -> {
                ResetPassViewModel(apiUserRepository!!) as T
            }
            modelClass.isAssignableFrom(PathViewModel::class.java) -> {
                PathViewModel(majorRepository!!) as T
            }
            modelClass.isAssignableFrom(QuizViewModel::class.java) -> {
                QuizViewModel(questionPrefRepository!!, questionRepository!!) as T
            }
            modelClass.isAssignableFrom(TestViewModel::class.java) -> {
                TestViewModel(predictRepository!!, testPreference!!, userDataRepository!!) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userRepository!!) as T
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
                    val majorRepository = Injection.provideMajorRepository()
                    val questionPrefRepository = Injection.provideQuestionPrefRepository()
                    val questionRepository = QuestionRepository(questionPrefRepository)
                    val predictRepository = Injection.providePredictRepository()
                    val testPreference = TestPreference.getInstance(context.dataStore)
                    val userDataRepository = Injection.provideUserDataRepository(context)
                    INSTANCE = ViewModelFactory(pref, apiUserRepository, userRepository, majorRepository, questionPrefRepository, questionRepository, predictRepository, testPreference, userDataRepository)
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