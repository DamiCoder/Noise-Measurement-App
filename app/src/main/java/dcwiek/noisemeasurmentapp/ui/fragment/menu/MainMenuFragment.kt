package dcwiek.noisemeasurmentapp.ui.fragment.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.ui.fragment.archive.ArchiveFragment
import dcwiek.noisemeasurmentapp.ui.fragment.archive.EmptyArchiveFragment
import dcwiek.noisemeasurmentapp.ui.fragment.common.ExtendedFragment
import kotlinx.android.synthetic.main.fragment_mainmenu.*

class MainMenuFragment : ExtendedFragment() {

    private val TAG: String = MainMenuFragment::class.java.name

    companion object {
        fun newInstance() = MainMenuFragment()
    }

    private lateinit var viewModel: MainMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mainmenu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottom_navigation.setOnNavigationItemSelectedListener {
                menuItem -> onNavigationItemSelected(menuItem)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainMenuViewModel::class.java)
    }

    fun onNavigationItemSelected(item: MenuItem) : Boolean {
        when(item.itemId) {
            R.id.item_bottomnav_record -> {
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
