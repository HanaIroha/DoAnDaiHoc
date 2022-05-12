package haui.iroha.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link haui.iroha.domain.Banners} entity.
 */
public class BannersDTO implements Serializable {

    private Long idBanner;

    private String upperTitle;

    private String mainTitle;

    private String image;

    private String linkNavigate;

    public Long getIdBanner() {
        return idBanner;
    }

    public void setIdBanner(Long idBanner) {
        this.idBanner = idBanner;
    }

    public String getUpperTitle() {
        return upperTitle;
    }

    public void setUpperTitle(String upperTitle) {
        this.upperTitle = upperTitle;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLinkNavigate() {
        return linkNavigate;
    }

    public void setLinkNavigate(String linkNavigate) {
        this.linkNavigate = linkNavigate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BannersDTO)) {
            return false;
        }

        BannersDTO bannersDTO = (BannersDTO) o;
        if (this.idBanner == null) {
            return false;
        }
        return Objects.equals(this.idBanner, bannersDTO.idBanner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idBanner);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BannersDTO{" +
            "idBanner=" + getIdBanner() +
            ", upperTitle='" + getUpperTitle() + "'" +
            ", mainTitle='" + getMainTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", linkNavigate='" + getLinkNavigate() + "'" +
            "}";
    }
}
