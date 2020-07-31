package dcwiek.noisemeasurmentapp.ui.fragment.common.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.application.NoiseMeasurementApplication
import dcwiek.noisemeasurmentapp.domain.DataStorage
import dcwiek.noisemeasurmentapp.service.SharedPreferencesService

open class ExtendedFragment: Fragment() {

    lateinit var sharedPreferencesService: SharedPreferencesService
    lateinit var dataStorage: DataStorage

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val storageComponent = (requireContext().applicationContext as NoiseMeasurementApplication)
//            .getStorageComponent()
//        sharedPreferencesService = storageComponent.getSharedPreferencesService()
//        dataStorage = storageComponent.getDataStorage()
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val storageComponent = (requireContext().applicationContext as NoiseMeasurementApplication)
            .getStorageComponent()
        sharedPreferencesService = storageComponent.getSharedPreferencesService()
        dataStorage = storageComponent.getDataStorage()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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

    fun reinitializeServices() {
        val storageComponent = (requireContext().applicationContext as NoiseMeasurementApplication)
            .getStorageComponent()
        sharedPreferencesService = storageComponent.getSharedPreferencesService()
        dataStorage = storageComponent.getDataStorage()
    }

    fun reattach() {
        fragmentManager?.beginTransaction()?.attach(this)?.commit()
    }
}