package com.mahmoudi.medalcaseui.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoudi.medalcaseui.model.RecordCollection
import com.mahmoudi.medalcaseui.model.RecordsRepo

class RecordsViewModel(): ViewModel(){

    private val _recordsLiveData = MutableLiveData<List<RecordCollection>>(RecordsRepo.getRecords())

    val recordsLiveData: LiveData<List<RecordCollection>>
        get() = _recordsLiveData

}