package com.example.gituapplication

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gituapplication.databinding.FragmentSettingsBinding
import com.example.gituapplication.model.ReminderModel
import com.example.gituapplication.preference.ReminderPreference
import com.example.gituapplication.receiver.AlarmReceiver


class SettingsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var reminder: ReminderModel
    private lateinit var alarmReceiver: AlarmReceiver


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Add The Toolbar
        (activity as AppCompatActivity).supportActionBar?.title
        binding.setting

        //calling function
        remindPreference()
        changeLanguage()


    }

    private fun changeLanguage() {
        val tvLanguage: TextView = binding.tvLanguage
        tvLanguage.setOnClickListener(this)
    }

    private fun remindPreference() {
        val reminderPreference = activity?.let { ReminderPreference(it) }
        if (reminderPreference != null) binding.switch1.isChecked =
            reminderPreference.getReminder().isReminded

        alarmReceiver = AlarmReceiver()

        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                saveReminder(true)
                //Alarm di setting untuk jam 9 pagi
                activity?.let {
                    alarmReceiver.setRepeatingAlarm(
                        it,
                        "RepeatingAlarm",
                        "09:00",
                        "Github Reminder"
                    )
                }
            } else {
                saveReminder(false)
                activity?.let { alarmReceiver.cancelAlarm(it) }
            }
        }
    }

    private fun saveReminder(state: Boolean) {
        val reminderPreference = activity?.let { ReminderPreference(it) }
        reminder = ReminderModel()

        reminder.isReminded = state
        reminderPreference?.setReminder(reminder)
    }

    override fun onClick(v: View?) {
        R.id.tv_Language
        val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(mIntent)
    }
}



