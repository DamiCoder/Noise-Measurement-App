package dcwiek.noisemeasurmentapp

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import dcwiek.noisemeasurmentapp.view.LoadingFragment
import dcwiek.noisemeasurmentapp.view.LoginFragment
import dcwiek.noisemeasurmentapp.view.constants.FragmentKeys


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switchToLoadingFragment()
        Handler().postDelayed({
            switchToLoginFragment()
        }, 2000)
    }

    private fun switchToLoginFragment() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.framelayout_main, LoginFragment.newInstance(), FragmentKeys.LOGIN_FRAGMENT)
            .commit()
    }

    private fun switchToLoadingFragment() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.framelayout_main, LoadingFragment.newInstance(), FragmentKeys.LOADING_FRAGMENT)
            .commit()
    }

    override fun onBackPressed() {
    }


}
