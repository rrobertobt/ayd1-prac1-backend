package ayd.back.taller.service;

import ayd.back.taller.repository.crud.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class JobService {


    private final JobRepository jobRepository;

    private final VehicleService vehicleService;



}
