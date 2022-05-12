package haui.iroha.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BannersMapperTest {

    private BannersMapper bannersMapper;

    @BeforeEach
    public void setUp() {
        bannersMapper = new BannersMapperImpl();
    }
}
