package com.awcology.smvd.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
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
        binding!!.cvWhatsapp.setOnClickListener {
           // Util.showAlertDialog(mContext, "ALERT", "This feature is under development.")
            val pm: PackageManager = mContext.getPackageManager()
            val isInstalled = isPackageInstalled("com.app_4art.technologies", pm)
            if (isInstalled){
                Toast.makeText(mContext, "Installed", Toast.LENGTH_SHORT).show()
            }else{

                Toast.makeText(mContext, "Not Installed", Toast.LENGTH_SHORT).show()
            }
        }
        binding!!.tvPrivacyPolicy.setOnClickListener {
            Util.showAlertDialog(mContext, "Privacy Policy", getString(R.string.privacy_policy))
        }
    }

    private fun initialization() {
        mContext = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}