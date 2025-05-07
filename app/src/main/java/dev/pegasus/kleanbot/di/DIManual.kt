package dev.pegasus.kleanbot.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dev.pegasus.kleanbot.data.dataSources.DataSourceRemoteOpenAI
import dev.pegasus.kleanbot.data.dataSources.retrofit.RetrofitInstanceOpenAI
import dev.pegasus.kleanbot.data.repository.RepositoryOpenAIImpl
import dev.pegasus.kleanbot.domain.useCases.UseCaseOpenAI
import dev.pegasus.kleanbot.presentation.viewModel.ViewModelOpenAI
import dev.pegasus.kleanbot.presentation.viewModel.ViewModelProviderOpenAI
import dev.pegasus.kleanbot.utilities.InternetManager

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class DIManual {

    fun getViewModelOpenAI(context: Context, owner: ViewModelStoreOwner): ViewModelOpenAI {
        val apiInterface = RetrofitInstanceOpenAI.api
        val dataSource = DataSourceRemoteOpenAI(apiInterface)
        val repository = RepositoryOpenAIImpl(dataSource)
        val internetManager = InternetManager(context)
        val useCase = UseCaseOpenAI(repository, internetManager)
        val factory = ViewModelProviderOpenAI(useCase)
        return ViewModelProvider(owner, factory)[ViewModelOpenAI::class.java]
    }
}