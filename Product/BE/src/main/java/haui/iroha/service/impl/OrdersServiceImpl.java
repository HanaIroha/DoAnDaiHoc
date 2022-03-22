package haui.iroha.service.impl;

import haui.iroha.domain.Orders;
import haui.iroha.repository.OrdersRepository;
import haui.iroha.service.OrdersService;
import haui.iroha.service.dto.OrdersDTO;
import haui.iroha.service.mapper.OrdersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Orders}.
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    private final Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    public OrdersServiceImpl(OrdersRepository ordersRepository, OrdersMapper ordersMapper) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
    }

    @Override
    public OrdersDTO save(OrdersDTO ordersDTO) {
        log.debug("Request to save Orders : {}", ordersDTO);
        Orders orders = ordersMapper.toEntity(ordersDTO);
        orders = ordersRepository.save(orders);
        return ordersMapper.toDto(orders);
    }

    @Override
    public Optional<OrdersDTO> partialUpdate(OrdersDTO ordersDTO) {
        log.debug("Request to partially update Orders : {}", ordersDTO);

        return ordersRepository
            .findById(ordersDTO.getIdOrder())
            .map(existingOrders -> {
                ordersMapper.partialUpdate(existingOrders, ordersDTO);

                return existingOrders;
            })
            .map(ordersRepository::save)
            .map(ordersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrdersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return ordersRepository.findAll(pageable).map(ordersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdersDTO> findOne(Long id) {
        log.debug("Request to get Orders : {}", id);
        return ordersRepository.findById(id).map(ordersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Orders : {}", id);
        ordersRepository.deleteById(id);
    }

    @Override
    public Page<OrdersDTO> findAllByOrderStatus(String status, Pageable pageable) {
        log.debug("Request to get all Orders By Status {}", status);
        return ordersRepository.findAllOrdersByStatus(status, pageable).map(ordersMapper::toDto);
    }

    @Override
    public Page<OrdersDTO> findAllByLogin(String login, Pageable pageable) {
        log.debug("Request to get all Orders By LOGIN {}",login);
        return ordersRepository.findAllOrdersByLogin(login, pageable).map(ordersMapper::toDto);
    }

    @Override
    public long getUserOrderCount(String login) {
        log.debug("Request to get all Orders COUNT by login{}",login);
        return ordersRepository.getUserOrderCount(login);
    }
}
