package com.mahmoudi.medalcaseui.model

import com.mahmoudi.medalcaseui.R

sealed class RecordCollection {
    abstract val id: Long
    abstract val name: String

}

data class Medal(
    override val id: Long,
    override val name: String,
    val recordValue: String,
    val imageUrl: Int,
    val isCompleted: Boolean
) : RecordCollection()

data class Header(
    override val id: Long,
    override val name: String,
    val completedCount: Int? = 0,
    val totalCount: Int? = 0
) : RecordCollection()

private val personalRecordsList = listOf(
    Medal(1, "Longest Run", "00:00", R.drawable.ic_longest_run, true),
    Medal(2, "Highest Elevation", "00:00", R.drawable.ic_highest_elevation, true),
    Medal(3, "Fastest 5K", "00:00", R.drawable.ic_baseline_5k_24, true),
    Medal(4, "10K", "00:00", R.drawable.ic_baseline_10k_24, true),
    Medal(5, "Half Marathon", "00:00:00", R.drawable.ic_fastest_half_marathon, true),
    Medal(6, "Marathon", "00:00", R.drawable.ic_fastest_marathon, false)
)

private val virtualRacesList = listOf(
    Medal(7, "Virtual Half Marathon Race", "00:00", R.drawable.ic_virtual_half_marathon_race, true),
    Medal(8, "Tokyo-Hakone Ekiden 2020", "00:00:00", R.drawable.ic_tokyo_kakone_ekiden, true),
    Medal(9, "Virtual 10K Race", "00:00:00", R.drawable.ic_virtual_10k_race, true),
    Medal(10, "Hakone Ekiden", "00:00", R.drawable.ic_hakone_ekiden, true),
    Medal(11, "Mizuno Singapore Ekiden 2015", "00:00:00", R.drawable.ic_mizuno_singapore_ekiden, true),
    Medal(12, "Virtual 5K Race", "23:07", R.drawable.ic_virtual_5k_race, true)
)

private val recordCollection = hashMapOf(
    Header(0, "Personal records") to personalRecordsList,
    Header(1, "Virtual Races") to virtualRacesList
)

object RecordsRepo {
    fun getRecords(): List<RecordCollection> {
        val recordList = arrayListOf<RecordCollection>()

        val iterator = recordCollection.keys.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            val value = recordCollection[key]
            val recordCount = value?.size
            val recordCompleted = value?.filter { it.isCompleted }?.size
            recordList.add(Header(key.id, key.name, recordCompleted, recordCount))
            value?.let { recordList.addAll(it) }
        }

        return recordList
    }
}