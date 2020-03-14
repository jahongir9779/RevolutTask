package com.revolut.testapp.base
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}