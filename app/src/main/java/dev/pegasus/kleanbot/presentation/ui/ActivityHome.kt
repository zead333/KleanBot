package dev.pegasus.kleanbot.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import dev.pegasus.kleanbot.R
import dev.pegasus.kleanbot.data.entities.Message
import dev.pegasus.kleanbot.databinding.ActivityHomeBinding
import dev.pegasus.kleanbot.di.DIManual
import dev.pegasus.kleanbot.presentation.adapter.AdapterOpenAI
import dev.pegasus.kleanbot.presentation.uiStates.ApiResponse
import dev.pegasus.kleanbot.utilities.ConstantUtils.TAG
import dev.pegasus.kleanbot.utilities.extensions.scrollToEnd

class ActivityHome : AppCompatActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val viewModel by lazy { DIManual().getViewModelOpenAI(applicationContext, this) }

    private val adapter by lazy { AdapterOpenAI() }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setPadding()

        initRecyclerView()
        initObservers()

        binding.mbSubmitHome.setOnClickListener { onSubmitClick() }
        binding.etInputHome.doAfterTextChanged { binding.mbSubmitHome.isEnabled = it.isNullOrEmpty().not() }
    }

    private fun setPadding() {
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val cutout = insets.getInsets(WindowInsetsCompat.Type.displayCutout())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            // Use the maximum of nav bar or IME for bottom padding
            val topPadding = maxOf(systemBars.top, cutout.top, systemBars.top)
            val bottomPadding = maxOf(systemBars.bottom, imeInsets.bottom)

            v.updatePadding(
                left = systemBars.left,
                top = topPadding,
                right = systemBars.right,
                bottom = bottomPadding
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun initRecyclerView() {
        binding.rcvListHome.adapter = adapter
        binding.nsvContainerHome.scrollToEnd(true)
    }

    private fun initObservers() {
        viewModel.queryLiveData.observe(this) { binding.etInputHome.setText(it) }
        viewModel.chatResponseLiveData.observe(this) { response ->
            resetViews()
            when (response) {
                is ApiResponse.Loading -> binding.incLoadingHome.root.visibility = View.VISIBLE
                is ApiResponse.Failure -> onErrorResult(response.messageResId)
                is ApiResponse.Success -> onSuccessResult(response.data)
            }
        }
    }

    private fun onSuccessResult(messages: List<Message>) {
        adapter.submitList(messages)
        binding.nsvContainerHome.scrollToEnd(false)
    }

    private fun resetViews() {
        binding.incPlaceholderHome.root.visibility = View.GONE
        binding.incErrorHome.root.visibility = View.GONE
        binding.incLoadingHome.root.visibility = View.GONE
    }

    private fun onErrorResult(messageResId: Int) {
        binding.incLoadingHome.root.visibility = View.GONE
        Log.e(TAG, "onErrorResult: ${getString(messageResId)}")
        when (messageResId) {
            R.string.toast_no_internet_connection -> Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
            else -> binding.incErrorHome.root.visibility = View.VISIBLE
        }
    }

    private fun onSubmitClick() {
        val query = binding.etInputHome.text.toString()
        viewModel.fetchChatResponse(query)

        binding.etInputHome.text?.clear()
    }
}