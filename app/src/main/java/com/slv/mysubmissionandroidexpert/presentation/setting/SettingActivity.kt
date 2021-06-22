package com.slv.mysubmissionandroidexpert.presentation.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.slv.mysubmissionandroidexpert.R
import com.slv.mysubmissionandroidexpert.databinding.ActivitySettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences
    private var _binding: ActivitySettingBinding? = null
    private val binding get() = _binding as ActivitySettingBinding

    companion object {
        const val PREF_NAME = "setting_pref"
        private const val DAILY = "daily"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.setting)
        }

        binding.sReminder.apply {
            isChecked = mSharedPreferences.getBoolean(DAILY, false)

        }

        binding.sReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                this.alarmReceiver.setRepeatingReminder(
                    applicationContext,
                    AlarmReceiver.EXTRA_MESSAGE
                )

                Toast.makeText(this, "Successfully activated Notification", Toast.LENGTH_SHORT)
                    .show()
            } else {
                this.alarmReceiver.cancelReminder(applicationContext, AlarmReceiver.TYPE_REPEATING)

                Toast.makeText(this, "Successfully turned off Notification", Toast.LENGTH_SHORT)
                    .show()
            }

            saveChange(isChecked)
        }
    }

    private fun saveChange(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(DAILY, value)
        editor.apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}