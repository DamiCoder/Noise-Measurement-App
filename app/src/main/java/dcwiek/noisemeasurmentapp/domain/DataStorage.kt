package dcwiek.noisemeasurmentapp.domain

import androidx.lifecycle.MutableLiveData
import dcwiek.noisemeasurmentapp.domain.model.*
import java.util.*
import java.util.stream.Collectors
import javax.inject.Inject

class DataStorage @Inject constructor() {

    val archivedProbes: MutableLiveData<LinkedList<Probe>> = MutableLiveData()

    val standards: MutableLiveData<List<Standard>> = MutableLiveData()

    val places: MutableLiveData<List<Place>> = MutableLiveData()
    val regulations: MutableLiveData<List<Regulation>> = MutableLiveData()

    lateinit var currentlySelectedPlace: Place
    var currentUser: MutableLiveData<AppUser> = MutableLiveData()

    fun getPlacesByRegulation(regulation: Regulation): List<Place>? {
        return places.value?.stream()?.filter{place -> place.regulation.name == regulation.name}?.collect(Collectors.toList())
    }

    fun getRegulationByName(name: String): Regulation {
        return regulations.value?.stream()?.filter{ regulation -> regulation.name == name}?.findAny()?.orElse(null)!!
    }
}