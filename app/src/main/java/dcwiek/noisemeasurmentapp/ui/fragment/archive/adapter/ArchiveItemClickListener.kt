package dcwiek.noisemeasurmentapp.ui.fragment.archive.adapter

import dcwiek.noisemeasurmentapp.domain.model.Probe

interface ArchiveItemClickListener {

    fun switchToProbeDetailsFragment(probe: Probe)
}