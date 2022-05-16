package haui.iroha.service.impl;

import haui.iroha.domain.OrderDetails;
import haui.iroha.repository.OrderDetailsRepository;
import haui.iroha.service.OrderDetailsService;
import haui.iroha.service.dto.OrderDetailsDTO;
import haui.iroha.service.mapper.OrderDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderDetails}.
 */
@Service
@Transactional
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(OrderDetailsServiceImpl.class);

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderDetailsMapper orderDetailsMapper;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository, OrderDetailsMapper orderDetailsMapper) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderDetailsMapper = orderDetailsMapper;
    }

    @Override
    public OrderDetailsDTO save(OrderDetailsDTO orderDetailsDTO) {
        log.debug("Request to save OrderDetails : {}", orderDetailsDTO);
        OrderDetails orderDetails = orderDetailsMapper.toEntity(orderDetailsDTO);
        orderDetails = orderDetailsRepository.save(orderDetails);
        return orderDetailsMapper.toDto(orderDetails);
    }

    @Override
    public Optional<OrderDetailsDTO> partialUpdate(OrderDetailsDTO orderDetailsDTO) {
        log.debug("Request to partially update OrderDetails : {}", orderDetailsDTO);

        return orderDetailsRepository
            .findById(orderDetailsDTO.getIdOrderDetail())
            .map(existingOrderDetails -> {
                orderDetailsMapper.partialUpdate(existingOrderDetails, orderDetailsDTO);

                return existingOrderDetails;
            })
            .map(orderDetailsRepository::save)
            .map(orderDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderDetails");
        return orderDetailsRepository.findAll(pageable).map(orderDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDetailsDTO> findOne(Long id) {
        log.debug("Request to get OrderDetails : {}", id);
        return orderDetailsRepository.findById(id).map(orderDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderDetails : {}", id);
        orderDetailsRepository.deleteById(id);
    }

    @Override
    public Page<OrderDetailsDTO> findAllByOrderId(long id, Pageable pageable) {
        log.debug("Request to get all OrderDetails BY ORDER ID: {}", id);
        return orderDetailsRepository.findAllByOrderId(id, pageable).map(orderDetailsMapper::toDto);
    }

    @Override
    public long orderValue(long id) {
        log.debug("Request to get all ORDERVALUE: {}", id);
        return orderDetailsRepository.orderValue(id);
    }

    @Override
    public long orderValueByTime(long month, long year) {
        return orderDetailsRepository.orderValueByTime(month, year);
    }

    @Override
    public long orderAmountByTime(long month, long year) {
        return orderDetailsRepository.orderAmountByTime(month, year);
    }

    @Override
    public long orderItemAmountByTime(long month, long year) {
        return orderDetailsRepository.orderItemAmountByTime(month, year);
    }
}
