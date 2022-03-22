package haui.iroha.service.impl;

import haui.iroha.domain.Payments;
import haui.iroha.repository.PaymentsRepository;
import haui.iroha.service.PaymentsService;
import haui.iroha.service.dto.PaymentsDTO;
import haui.iroha.service.mapper.PaymentsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Payments}.
 */
@Service
@Transactional
public class PaymentsServiceImpl implements PaymentsService {

    private final Logger log = LoggerFactory.getLogger(PaymentsServiceImpl.class);

    private final PaymentsRepository paymentsRepository;

    private final PaymentsMapper paymentsMapper;

    public PaymentsServiceImpl(PaymentsRepository paymentsRepository, PaymentsMapper paymentsMapper) {
        this.paymentsRepository = paymentsRepository;
        this.paymentsMapper = paymentsMapper;
    }

    @Override
    public PaymentsDTO save(PaymentsDTO paymentsDTO) {
        log.debug("Request to save Payments : {}", paymentsDTO);
        Payments payments = paymentsMapper.toEntity(paymentsDTO);
        payments = paymentsRepository.save(payments);
        return paymentsMapper.toDto(payments);
    }

    @Override
    public Optional<PaymentsDTO> partialUpdate(PaymentsDTO paymentsDTO) {
        log.debug("Request to partially update Payments : {}", paymentsDTO);

        return paymentsRepository
            .findById(paymentsDTO.getIdPayment())
            .map(existingPayments -> {
                paymentsMapper.partialUpdate(existingPayments, paymentsDTO);

                return existingPayments;
            })
            .map(paymentsRepository::save)
            .map(paymentsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Payments");
        return paymentsRepository.findAll(pageable).map(paymentsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentsDTO> findOne(Long id) {
        log.debug("Request to get Payments : {}", id);
        return paymentsRepository.findById(id).map(paymentsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Payments : {}", id);
        paymentsRepository.deleteById(id);
    }
}
