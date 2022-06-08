package com.awcology.smvd.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.awcology.smvd.R
import com.awcology.smvd.databinding.ActivityMainBinding
import com.awcology.smvd.utility.Util


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
        binding!!.cvFb.setOnClickListener {
            startActivity(Intent(mContext, FacebookVideoDownloader::class.java))
        }
        binding!!.cvWhatsapp.setOnClickListener{
            Util.showAlertDialog(mContext,"ALERT","This feature is under development.")
        }
    }

    private fun initialization() {
        mContext = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

}