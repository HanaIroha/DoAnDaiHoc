package haui.iroha.service.impl;

import haui.iroha.domain.Banners;
import haui.iroha.repository.BannersRepository;
import haui.iroha.service.BannersService;
import haui.iroha.service.dto.BannersDTO;
import haui.iroha.service.mapper.BannersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Banners}.
 */
@Service
@Transactional
public class BannersServiceImpl implements BannersService {

    private final Logger log = LoggerFactory.getLogger(BannersServiceImpl.class);

    private final BannersRepository bannersRepository;

    private final BannersMapper bannersMapper;

    public BannersServiceImpl(BannersRepository bannersRepository, BannersMapper bannersMapper) {
        this.bannersRepository = bannersRepository;
        this.bannersMapper = bannersMapper;
    }

    @Override
    public BannersDTO save(BannersDTO bannersDTO) {
        log.debug("Request to save Banners : {}", bannersDTO);
        Banners banners = bannersMapper.toEntity(bannersDTO);
        banners = bannersRepository.save(banners);
        return bannersMapper.toDto(banners);
    }

    @Override
    public Optional<BannersDTO> partialUpdate(BannersDTO bannersDTO) {
        log.debug("Request to partially update Banners : {}", bannersDTO);

        return bannersRepository
            .findById(bannersDTO.getIdBanner())
            .map(existingBanners -> {
                bannersMapper.partialUpdate(existingBanners, bannersDTO);

                return existingBanners;
            })
            .map(bannersRepository::save)
            .map(bannersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BannersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Banners");
        return bannersRepository.findAll(pageable).map(bannersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BannersDTO> findOne(Long id) {
        log.debug("Request to get Banners : {}", id);
        return bannersRepository.findById(id).map(bannersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Banners : {}", id);
        bannersRepository.deleteById(id);
    }
}
