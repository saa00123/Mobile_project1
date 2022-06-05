package kr.ac.kpu.foodtukorea.domain

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kpu.foodtukorea.MainActivity
import kr.ac.kpu.foodtukorea.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val hd = Handler()
        hd.postDelayed(splashhandler(),3000)

        supportActionBar?.setIcon(R.drawable.restaurant2)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportActionBar?.show()

    }

    private inner class splashhandler : Runnable {
        override fun run() {
            startActivity(Intent(getApplication(), MainActivity::class.java))
            this@SplashActivity.finish()
        }
    }

    override fun onBackPressed() {}
}