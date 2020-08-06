package dcwiek.noisemeasurmentapp.domain

import androidx.lifecycle.MutableLiveData
import dcwiek.noisemeasurmentapp.domain.model.*
import java.util.stream.Collectors
import javax.inject.Inject

class DataStorage @Inject constructor() {

//    val archivedProbes: MutableLiveData<List<Probe>> = MutableLiveData(mutableListOf(Probe.mockGoodProbe(), Probe.mockBadProbe(), Probe.mockAverageProbe(), Probe.mockGoodProbe()))
    val archivedProbes: MutableLiveData<List<Probe>> = MutableLiveData()

    val standards: MutableLiveData<List<Standard>> = MutableLiveData()

//    val standards: MutableLiveData<List<Standard>> = MutableLiveData(mutableListOf(
//        Standard.mockStandard(dcwiek.noisemeasurmentapp.domain.constants.Regulation.LAW),
//        Standard.mockStandard(dcwiek.noisemeasurmentapp.domain.constants.Regulation.SCIENTIFIC),
//        Standard.mockStandard(dcwiek.noisemeasurmentapp.domain.constants.Regulation.SCIENTIFIC),
//        Standard.mockStandard(dcwiek.noisemeasurmentapp.domain.constants.Regulation.SCIENTIFIC)))

    val places: MutableLiveData<List<Place>> = MutableLiveData()
    val regulations: MutableLiveData<List<Regulation>> = MutableLiveData()
    //    val archivedProbesData: LiveData<List<Probe>> = MutableLiveData(mutableListOf())

    lateinit var currentlySelectedPlace: Place
    //TODO: should we also keep there password or cookie is stored somehow in http client
    var currentUser: MutableLiveData<AppUser> = MutableLiveData()

    fun getPlacesByRegulation(regulation: Regulation): List<Place>? {
        return places.value?.stream()?.filter{place -> place.regulation.name == regulation.name}?.collect(Collectors.toList())
    }

    fun getRegulationByName(name: String): Regulation {
        return regulations.value?.stream()?.filter{ regulation -> regulation.name == name}?.findAny()?.orElse(null)!!
    }
}