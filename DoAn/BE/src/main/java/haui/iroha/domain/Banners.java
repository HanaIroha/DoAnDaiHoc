package haui.iroha.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Banners.
 */
@Entity
@Table(name = "banners")
public class Banners implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banner")
    private Long idBanner;

    @Column(name = "upper_title")
    private String upperTitle;

    @Column(name = "main_title")
    private String mainTitle;

    @Column(name = "image")
    private String image;

    @Column(name = "link_navigate")
    private String linkNavigate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getIdBanner() {
        return this.idBanner;
    }

    public Banners idBanner(Long idBanner) {
        this.setIdBanner(idBanner);
        return this;
    }

    public void setIdBanner(Long idBanner) {
        this.idBanner = idBanner;
    }

    public String getUpperTitle() {
        return this.upperTitle;
    }

    public Banners upperTitle(String upperTitle) {
        this.setUpperTitle(upperTitle);
        return this;
    }

    public void setUpperTitle(String upperTitle) {
        this.upperTitle = upperTitle;
    }

    public String getMainTitle() {
        return this.mainTitle;
    }

    public Banners mainTitle(String mainTitle) {
        this.setMainTitle(mainTitle);
        return this;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getImage() {
        return this.image;
    }

    public Banners image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLinkNavigate() {
        return this.linkNavigate;
    }

    public Banners linkNavigate(String linkNavigate) {
        this.setLinkNavigate(linkNavigate);
        return this;
    }

    public void setLinkNavigate(String linkNavigate) {
        this.linkNavigate = linkNavigate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banners)) {
            return false;
        }
        return idBanner != null && idBanner.equals(((Banners) o).idBanner);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Banners{" +
            "idBanner=" + getIdBanner() +
            ", upperTitle='" + getUpperTitle() + "'" +
            ", mainTitle='" + getMainTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", linkNavigate='" + getLinkNavigate() + "'" +
            "}";
    }
}
