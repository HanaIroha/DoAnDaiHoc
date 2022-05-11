package haui.iroha.service.impl;

import haui.iroha.domain.Products;
import haui.iroha.repository.ProductsRepository;
import haui.iroha.service.ProductsService;
import haui.iroha.service.dto.ProductsDTO;
import haui.iroha.service.mapper.ProductsMapper;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Products}.
 */
@Service
@Transactional
public class ProductsServiceImpl implements ProductsService {

    private final Logger log = LoggerFactory.getLogger(ProductsServiceImpl.class);

    private final ProductsRepository productsRepository;

    private final ProductsMapper productsMapper;

    public ProductsServiceImpl(ProductsRepository productsRepository, ProductsMapper productsMapper) {
        this.productsRepository = productsRepository;
        this.productsMapper = productsMapper;
    }

    @Override
    public ProductsDTO save(ProductsDTO productsDTO) {
        log.debug("Request to save Products : {}", productsDTO);
        Products products = productsMapper.toEntity(productsDTO);
        products = productsRepository.save(products);
        return productsMapper.toDto(products);
    }

    @Override
    public Optional<ProductsDTO> partialUpdate(ProductsDTO productsDTO) {
        log.debug("Request to partially update Products : {}", productsDTO);

        return productsRepository
            .findById(productsDTO.getIdProduct())
            .map(existingProducts -> {
                productsMapper.partialUpdate(existingProducts, productsDTO);

                return existingProducts;
            })
            .map(productsRepository::save)
            .map(productsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productsRepository.findAll(pageable).map(productsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductsDTO> findOne(Long id) {
        log.debug("Request to get Products : {}", id);
        return productsRepository.findById(id).map(productsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Products : {}", id);
        productsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductsDTO> findAllActive(Pageable pageable) {
        log.debug("Request to get all Active Products");
        return productsRepository.findAllActive(pageable).map(productsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductsDTO> findAllActiveWithFilter(String filterKey, String filterProducer, String minprice, String maxprice, String filterRam, String filterRom, Pageable pageable) {
        log.debug("Request to get all Active Products");
        return productsRepository.findAllActiveWithFilter(filterKey, filterProducer, minprice, maxprice, filterRam,filterRom, pageable).map(productsMapper::toDto);
    }

    @Override
    public void safeDelete(long id) {
        Products pr = productsRepository.findById(id).get();
        pr.setIsDisable(true);
        pr.setUpdatedAt(ZonedDateTime.now());
        productsRepository.save(pr);
    }

    @Override
    public long getAmountPhoneByOS(String os) {
        return productsRepository.getAmountPhoneByOS(os);
    }

    @Override
    public long getPriceById(long id) {
        return productsRepository.getPriceById(id);
    }
}
