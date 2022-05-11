package haui.iroha.service.impl;

import haui.iroha.domain.Producers;
import haui.iroha.repository.ProducersRepository;
import haui.iroha.service.ProducersService;
import haui.iroha.service.dto.ProducersDTO;
import haui.iroha.service.mapper.ProducersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Producers}.
 */
@Service
@Transactional
public class ProducersServiceImpl implements ProducersService {

    private final Logger log = LoggerFactory.getLogger(ProducersServiceImpl.class);

    private final ProducersRepository producersRepository;

    private final ProducersMapper producersMapper;

    public ProducersServiceImpl(ProducersRepository producersRepository, ProducersMapper producersMapper) {
        this.producersRepository = producersRepository;
        this.producersMapper = producersMapper;
    }

    @Override
    public ProducersDTO save(ProducersDTO producersDTO) {
        log.debug("Request to save Producers : {}", producersDTO);
        Producers producers = producersMapper.toEntity(producersDTO);
        producers = producersRepository.save(producers);
        return producersMapper.toDto(producers);
    }

    @Override
    public Optional<ProducersDTO> partialUpdate(ProducersDTO producersDTO) {
        log.debug("Request to partially update Producers : {}", producersDTO);

        return producersRepository
            .findById(producersDTO.getIdProducer())
            .map(existingProducers -> {
                producersMapper.partialUpdate(existingProducers, producersDTO);

                return existingProducers;
            })
            .map(producersRepository::save)
            .map(producersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProducersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Producers");
        return producersRepository.findAll(pageable).map(producersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProducersDTO> findOne(Long id) {
        log.debug("Request to get Producers : {}", id);
        return producersRepository.findById(id).map(producersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Producers : {}", id);
        producersRepository.deleteById(id);
    }
}
