package ru.news.domains;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "news")
public class News implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_news")
    private Long idNews;

    @Column(name = "label")
    private String label;

    @Column(name = "text_news")
    private String textNews;

    @Column(name = "date_news")
    private ZonedDateTime dateOfCreation;

    @Column(name = "path_image")
    private String pathImage;

    public News(){}

    public News(String label, String textNews, String pathImage) {
        this.label = label;
        this.textNews = textNews;
        this.pathImage = pathImage;
        this.dateOfCreation = ZonedDateTime.now();
    }

    public Long getIdNews() {
        return idNews;
    }

    public void setIdNews(Long idNews) {
        this.idNews = idNews;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTextNews() {
        return textNews;
    }

    public void setTextNews(String textNews) {
        this.textNews = textNews;
    }

    public ZonedDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getDateOfCreationSimple() {
        return dateOfCreation == null ? "" : DateTimeFormatter.ofPattern("dd-MM-yyyy - hh:mm").format(dateOfCreation);
    }
}
