package dcwiek.noisemeasurmentapp.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.loading.LoadingFragment
import dcwiek.noisemeasurmentapp.ui.fragment.login.LoginFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switchToLoadingFragment()
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions,0)
        }
        Handler().postDelayed({
            switchToLoginFragment()
        }, 2000)
    }

    private fun switchToLoginFragment() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fade_out
            )
            .replace(R.id.framelayout_main, LoginFragment.newInstance(), FragmentKeys.LOGIN_FRAGMENT)
            .commit()
    }

    private fun switchToLoadingFragment() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out
            )
            .replace(R.id.framelayout_main, LoadingFragment.newInstance(), FragmentKeys.LOADING_FRAGMENT)
            .commit()
    }

    override fun onBackPressed() {
    }


}
