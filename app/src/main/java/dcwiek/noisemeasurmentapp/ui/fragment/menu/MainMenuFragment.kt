package dcwiek.noisemeasurmentapp.ui.fragment.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.ui.fragment.ExtendedFragment

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
//        bottom_navigation.setOnNavigationItemSelectedListener {
//                menuItem -> onNavigationItemSelected(menuItem)
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainMenuViewModel::class.java)
        // TODO: Use the ViewModel
    }

//    fun onNavigationItemSelected(item: MenuItem) : Boolean {
//        when(item.itemId) {
//            R.id.item_bottomnav_record -> {
//
//            }
//            R.id.item_bottomnav_history -> {
//
//            }
//            R.id.item_bottomnav_norms -> {
//
//            }
//            R.id.item_bottomnav_help -> {
//
//            }
//            else -> {
//                Log.wtf(TAG, item.itemId.toString())
//                return false
//            }
//        }
//    }

}
