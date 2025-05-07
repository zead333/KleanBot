package dev.pegasus.kleanbot.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.pegasus.kleanbot.data.entities.Message
import dev.pegasus.kleanbot.databinding.ItemHomeLeftBinding
import dev.pegasus.kleanbot.databinding.ItemHomeRightBinding
import dev.pegasus.kleanbot.presentation.enums.OpenAIRole
import dev.pegasus.kleanbot.utilities.TypeWriter

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class AdapterOpenAI : ListAdapter<Message, RecyclerView.ViewHolder>(DiffCallback) {

    private val typedContentList = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> CustomViewHolderUser(ItemHomeRightBinding.inflate(layoutInflater, parent, false))
            else -> CustomViewHolderAssistant(ItemHomeLeftBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)

        when (holder) {
            is CustomViewHolderUser -> holder.binding.bindViews(currentItem)
            is CustomViewHolderAssistant -> holder.binding.bindViews(currentItem)
        }
    }

    private fun ItemHomeRightBinding.bindViews(message: Message) {
        mtvRightTextItemHome.text = message.content
    }

    private fun ItemHomeLeftBinding.bindViews(message: Message) {
        typedContentList.find { it == message.content }?.let {
            mtvLeftTextItemHomeLeft.text = message.content
        } ?: run {
            typedContentList.add(message.content)
            val typeWriter = TypeWriter(mtvLeftTextItemHomeLeft)
            typeWriter.animateText(message.content, characterDelay = 20)
        }
    }

    inner class CustomViewHolderUser(val binding: ItemHomeRightBinding) : RecyclerView.ViewHolder(binding.root)
    inner class CustomViewHolderAssistant(val binding: ItemHomeLeftBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].role == OpenAIRole.USER.value) 1 else 0
    }

    object DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.content == newItem.content
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}
