package dcwiek.noisemeasurmentapp.ui.fragment.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.ui.fragment.archive.ArchiveFragment
import dcwiek.noisemeasurmentapp.ui.fragment.archive.EmptyArchiveFragment
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import dcwiek.noisemeasurmentapp.ui.fragment.record.InitRecordProbeFragment

class MainMenuFragment : ExtendedFragment() {

    private val TAG: String = MainMenuFragment::class.java.name

    private var currentMenuItemId: Int? = null

    companion object {
        private lateinit var instance: MainMenuFragment
        fun getInstance(): MainMenuFragment {
            if(!this::instance.isInitialized) {
                instance = MainMenuFragment()
            }
            return instance
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_mainmenu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener {
                menuItem -> onNavigationItemSelected(menuItem)
        }
        initializeArchiveView()
    }

    fun initializeArchiveView() {
        view?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.selectedItemId = R.id.item_bottomnav_history
        if(dataStorage.archivedProbesData.value.isNullOrEmpty()) {
            replaceFragment(R.id.mainmenu_constraintlayout, EmptyArchiveFragment.newInstance())
        } else {
            replaceFragment(R.id.mainmenu_constraintlayout, ArchiveFragment.newInstance())
        }
    }

    private fun onNavigationItemSelected(item: MenuItem) : Boolean {
        if (currentMenuItemId != null) {
            if (currentMenuItemId == item.itemId) {
                return false
            }
        }
        currentMenuItemId = item.itemId
        when(item.itemId) {
            R.id.item_bottomnav_record -> {
                replaceFragment(R.id.mainmenu_constraintlayout, InitRecordProbeFragment.newInstance())
                return true
            }
            R.id.item_bottomnav_history -> {
                if(dataStorage.archivedProbesData.value.isNullOrEmpty()) {
                    replaceFragment(R.id.mainmenu_constraintlayout, EmptyArchiveFragment.newInstance())
                } else {
                    replaceFragment(R.id.mainmenu_constraintlayout, ArchiveFragment.newInstance())
                }
                return true
            }
            R.id.item_bottomnav_norms -> {
                return true
            }
            R.id.item_bottomnav_help -> {
                return true
            }
            else -> {
                Log.wtf(TAG, item.itemId.toString())
                return false
            }
        }
    }
}
