package dcwiek.noisemeasurmentapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.application.NoiseMeasurementApplication
import dcwiek.noisemeasurmentapp.data.DataStorage
import dcwiek.noisemeasurmentapp.service.SharedPreferencesService

open class ExtendedFragment: Fragment() {

    lateinit var sharedPreferencesService: SharedPreferencesService
    lateinit var dataStorage: DataStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val storageComponent = (requireContext().applicationContext as NoiseMeasurementApplication)
            .getStorageComponent()
        sharedPreferencesService = storageComponent.getSharedPreferencesService()
        dataStorage = storageComponent.getDataStorage()
    }

    fun replaceFragment(containerId: Int, replacement: Fragment){
        fragmentManager
            ?.beginTransaction()
            ?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            ?.replace(containerId, replacement)
            ?.commit()
    }

    fun replaceFragment(containerId: Int, replacement: Fragment, tag: String){
        fragmentManager
            ?.beginTransaction()
            ?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            ?.replace(containerId, replacement, tag)
            ?.commit()
    }
}