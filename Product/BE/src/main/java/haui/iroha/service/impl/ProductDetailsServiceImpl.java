package haui.iroha.service.impl;

import haui.iroha.domain.ProductDetails;
import haui.iroha.repository.ProductDetailsRepository;
import haui.iroha.service.ProductDetailsService;
import haui.iroha.service.dto.ProductDetailsDTO;
import haui.iroha.service.mapper.ProductDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductDetails}.
 */
@Service
@Transactional
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final Logger log = LoggerFactory.getLogger(ProductDetailsServiceImpl.class);

    private final ProductDetailsRepository productDetailsRepository;

    private final ProductDetailsMapper productDetailsMapper;

    public ProductDetailsServiceImpl(ProductDetailsRepository productDetailsRepository, ProductDetailsMapper productDetailsMapper) {
        this.productDetailsRepository = productDetailsRepository;
        this.productDetailsMapper = productDetailsMapper;
    }

    @Override
    public ProductDetailsDTO save(ProductDetailsDTO productDetailsDTO) {
        log.debug("Request to save ProductDetails : {}", productDetailsDTO);
        ProductDetails productDetails = productDetailsMapper.toEntity(productDetailsDTO);
        productDetails = productDetailsRepository.save(productDetails);
        return productDetailsMapper.toDto(productDetails);
    }

    @Override
    public Optional<ProductDetailsDTO> partialUpdate(ProductDetailsDTO productDetailsDTO) {
        log.debug("Request to partially update ProductDetails : {}", productDetailsDTO);

        return productDetailsRepository
            .findById(productDetailsDTO.getIdProductDetail())
            .map(existingProductDetails -> {
                productDetailsMapper.partialUpdate(existingProductDetails, productDetailsDTO);

                return existingProductDetails;
            })
            .map(productDetailsRepository::save)
            .map(productDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductDetails");
        return productDetailsRepository.findAll(pageable).map(productDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDetailsDTO> findOne(Long id) {
        log.debug("Request to get ProductDetails : {}", id);
        return productDetailsRepository.findById(id).map(productDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductDetails : {}", id);
        productDetailsRepository.deleteById(id);
    }
}
