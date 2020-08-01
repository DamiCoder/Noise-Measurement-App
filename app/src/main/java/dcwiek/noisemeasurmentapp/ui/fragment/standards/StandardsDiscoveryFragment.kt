package dcwiek.noisemeasurmentapp.ui.fragment.standards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.constants.Regulation
import dcwiek.noisemeasurmentapp.ui.fragment.common.fragment.ExtendedFragment
import dcwiek.noisemeasurmentapp.ui.fragment.standards.adapter.StandardsAdapter
import kotlinx.android.synthetic.main.fragment_standardsdiscovery.*
import java.util.stream.Collectors

class StandardsDiscoveryFragment(val regulation: Regulation): ExtendedFragment() {

    companion object {
        fun newInstance(regulation: Regulation) : StandardsDiscoveryFragment = StandardsDiscoveryFragment(regulation)
    }

    private lateinit var standardsRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_standardsdiscovery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        standardsRecyclerView = view.findViewById(R.id.recyclerview_standardsdiscovery)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        standarddiscovery_headertext.text = "Normy ${regulation.label}"
        standardsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = StandardsAdapter(dataStorage.standards.value!!
                .stream()
                .filter{it.regulation == regulation.name}
                .collect(Collectors.toList()))
        }

        standardsdiscovery_backbutton.setOnClickListener {
            loadMainMenuFragment()
        }
    }

}