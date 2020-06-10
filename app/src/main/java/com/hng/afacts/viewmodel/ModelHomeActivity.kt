package com.hng.afacts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hng.afacts.Event


class ModelHomeActivity: ViewModel() {

    val message = MutableLiveData<String>()//or Any(in place of String)

    fun myMessage(msg: String) {
        message.value = msg
    }

    val message2 = MutableLiveData<Event<String>>()

    fun myMessage2(msg: String) {
        message2.value = Event(msg)
    }

    val navigateToMediaItem: LiveData<Event<String>> get() = _navigateToMediaItem
    private val _navigateToMediaItem = MutableLiveData<Event<String>>()
    fun navigateToMediaItem(msg: String) {
        _navigateToMediaItem.value = Event(msg)
    }







}