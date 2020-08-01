package dcwiek.noisemeasurmentapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dcwiek.noisemeasurmentapp.domain.constants.Place
import dcwiek.noisemeasurmentapp.domain.constants.Regulation
import dcwiek.noisemeasurmentapp.domain.model.Probe
import dcwiek.noisemeasurmentapp.domain.model.Standard
import javax.inject.Inject

class DataStorage @Inject constructor() {

    val archivedProbesData: LiveData<List<Probe>> = MutableLiveData(mutableListOf(Probe.mockGoodProbe(), Probe.mockBadProbe(), Probe.mockAverageProbe(), Probe.mockGoodProbe()))
    val standards: LiveData<List<Standard>> = MutableLiveData(mutableListOf(Standard.mockStandard(Regulation.LAW), Standard.mockStandard(Regulation.SCIENTIFIC), Standard.mockStandard(Regulation.SCIENTIFIC), Standard.mockStandard(Regulation.SCIENTIFIC)))
//    val archivedProbesData: LiveData<List<Probe>> = MutableLiveData(mutableListOf())
    lateinit var currentlySelectedPlace: Place
}