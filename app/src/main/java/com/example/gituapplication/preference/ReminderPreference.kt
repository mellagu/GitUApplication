package com.example.gituapplication.preference

import android.content.Context
import com.example.gituapplication.model.ReminderModel

class ReminderPreference(context: Context) {

    companion object{
        const val PREFS_NAME = "reminder_pref"
        private const val REMINDER = "isRemind"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: ReminderModel){
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value.isReminded)
        editor.apply()
    }

    fun getReminder(): ReminderModel{
        val model = ReminderModel()
        model.isReminded = preference.getBoolean(REMINDER, false)
        return model
    }
}