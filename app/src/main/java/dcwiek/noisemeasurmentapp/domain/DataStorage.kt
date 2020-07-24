package dcwiek.noisemeasurmentapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dcwiek.noisemeasurmentapp.domain.model.Probe
import javax.inject.Inject

class DataStorage @Inject constructor() {

    val archivedProbesData: LiveData<List<Probe>> = MutableLiveData(mutableListOf(Probe.mockGoodProbe(), Probe.mockBadProbe(), Probe.mockAverageProbe(), Probe.mockGoodProbe()))
}