package ayd.back.taller.service;

import ayd.back.taller.dto.request.reports.ClientJobHistoryRequestDto;
import ayd.back.taller.dto.response.reports.ClientRatingResponseDto;
import ayd.back.taller.dto.response.reports.ClientJobHistoryResponseDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.mappers.ClientHistoryMapper;
import ayd.back.taller.repository.crud.JobLogsRepository;
import ayd.back.taller.repository.entities.JobEntity;
import ayd.back.taller.repository.entities.JobLogsEntity;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.crud.JobRepository;
import ayd.back.taller.repository.crud.UserCrud;
import ayd.back.taller.repository.enums.LogTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClientReportsService {

    private final SessionService sessionService;
    private final UserCrud userRepo;
    private final JobRepository jobRepository;
    private final JobLogsRepository jobLogsRepository;
    private final ClientHistoryMapper mapper;

    public List<ClientJobHistoryResponseDto> getClientJobHistory(String token, ClientJobHistoryRequestDto dto) {
        sessionService.isAdmin(token);

        // Buscar cliente
        UserEntity client = null;
        if (dto.getClientId() != null) {
            client = userRepo.findById(dto.getClientId()).orElse(null);
        } else if (StringUtils.hasText(dto.getEmail())) {
            client = userRepo.getUserByEmail(dto.getEmail()).orElse(null);
        } else if (StringUtils.hasText(dto.getName())) {
            client = userRepo.findByName(dto.getName()).orElse(null);
        } else if (dto.getNit() != null) {
            client = userRepo.getUserByNit(dto.getNit()).orElse(null);
        }

        if (client == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }

        List<JobEntity> jobs = jobRepository.findByClientId(client.getId());

        return jobs.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<ClientRatingResponseDto> getUserRatings(String token, Integer userId) {
        sessionService.isAdmin(token);

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<JobLogsEntity> ratings = jobLogsRepository.findByUserAndLogType(user.getId(), LogTypeEnum.CALIFICACION);

        // Mapear a DTO
        return ratings.stream()
                .map(log -> new ClientRatingResponseDto(
                        log.getId(),
                        log.getJob().getId(),
                        log.getDescription(),
                        log.getOccurredAt()
                ))
                .collect(Collectors.toList());
    }
}
