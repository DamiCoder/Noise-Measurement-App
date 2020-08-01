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
import dcwiek.noisemeasurmentapp.ui.constants.FragmentKeys
import dcwiek.noisemeasurmentapp.ui.fragment.menu.MainMenuFragment

open class ExtendedFragment: Fragment() {

    lateinit var sharedPreferencesService: SharedPreferencesService
    lateinit var dataStorage: DataStorage

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

    fun loadMainMenuFragment() {
        val fragment = MainMenuFragment.getInstance()
        replaceFragment(R.id.framelayout_main, fragment, FragmentKeys.MAIN_MENU_FRAGMENT)
        when(fragment.currentMenuItem?.itemId) {
            R.id.item_bottomnav_record -> fragment.initializeRecordView()
            R.id.item_bottomnav_history -> fragment.initializeArchiveView()
            R.id.item_bottomnav_standards -> fragment.initializeStandardsView()
            R.id.item_bottomnav_help -> fragment.initializeHelpView()
        }
    }
}