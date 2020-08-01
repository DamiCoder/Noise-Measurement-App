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
import dcwiek.noisemeasurmentapp.ui.fragment.standards.InitStandardsDiscoveryFragment

class MainMenuFragment : ExtendedFragment() {


    private lateinit var bottomNavigationView: BottomNavigationView
    var currentMenuItem: MenuItem? = null

    private var isFirstTimeOpened = true

    companion object {
        private val TAG: String = MainMenuFragment::class.java.name
        private lateinit var instance: MainMenuFragment
        fun getInstance(): MainMenuFragment {
            if(!this::instance.isInitialized) {
                Log.d(TAG, "Creating new MainMenuFragment instance")
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

        this.bottomNavigationView = view.findViewById(R.id.bottom_navigation)
        this.bottomNavigationView.setOnNavigationItemSelectedListener {
                menuItem -> onNavigationItemSelected(menuItem)
        }
        if(isFirstTimeOpened) {
            initializeArchiveView()
        }
    }

    fun initializeArchiveView() {
        currentMenuItem = null
        this.bottomNavigationView.selectedItemId = R.id.item_bottomnav_history
    }

    fun initializeStandardsView() {
        currentMenuItem = null
        this.bottomNavigationView.selectedItemId = R.id.item_bottomnav_standards
    }

    fun initializeRecordView() {
        currentMenuItem = null
        this.bottomNavigationView.selectedItemId = R.id.item_bottomnav_record
    }

    fun initializeHelpView() {
        currentMenuItem = null
        this.bottomNavigationView.selectedItemId = R.id.item_bottomnav_help
    }

    private fun onNavigationItemSelected(item: MenuItem) : Boolean {
        isFirstTimeOpened = false
        Log.d(TAG, "Current menuItem: ${item.title}")

        if (currentMenuItem != null) {
            if (currentMenuItem == item) {
                return false
            }
        }

        currentMenuItem = item
        item.isChecked = true
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
            R.id.item_bottomnav_standards -> {
                replaceFragment(R.id.mainmenu_constraintlayout, InitStandardsDiscoveryFragment.newInstance())
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
