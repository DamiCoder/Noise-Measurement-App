package dcwiek.noisemeasurmentapp.service.probe

import dcwiek.noisemeasurmentapp.application.dto.ProbeDto
import dcwiek.noisemeasurmentapp.domain.model.Probe
import dcwiek.noisemeasurmentapp.service.standard.StandardService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProbeMapper(private val standardService: StandardService) {

    fun mapProbeDtoToProbe(probeDto: ProbeDto): Probe {

        val healthHazard = standardService.determineHealthHazard(probeDto.result, probeDto.place)
        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern(ProbeDto.RECEIVED_DATE_PATTERN)
        val createdDate = LocalDateTime.parse(probeDto.createdDate, dtf)
        return Probe(probeDto.id,
            probeDto.location,
            probeDto.place,
            probeDto.standards,
            healthHazard,
            probeDto.result,
            probeDto.comment,
            probeDto.userRating,
            createdDate)
    }
}