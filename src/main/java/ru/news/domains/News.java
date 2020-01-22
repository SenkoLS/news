package ru.news.domains;

import org.apache.commons.text.StringEscapeUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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

    public News() {
    }

    public News(String label, String textNews, String pathImage) {
        this.label = StringEscapeUtils.escapeHtml4(label);
        this.textNews = StringEscapeUtils.escapeHtml4(textNews);
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
        this.label = StringEscapeUtils.escapeHtml4(label);
    }

    public String getTextNews() {
        return textNews;
    }

    public String getTextNewsWithBBCodeToHtml() {
        String html = textNews;
        Map<String, String> bbMap = new HashMap<String, String>() {{
            put("(\r\n|\r|\n|\n\r)", "<br/>");
            put("\\[b\\](.+?)\\[/b\\]", "<strong>$1</strong>");
            put("\\[i\\](.+?)\\[/i\\]", "<span style='font-style:italic;'>$1</span>");
            put("\\[u\\](.+?)\\[/u\\]", "<span style='text-decoration:underline;'>$1</span>");
            put("\\[h1\\](.+?)\\[/h1\\]", "<h1>$1</h1>");
            put("\\[h2\\](.+?)\\[/h2\\]", "<h2>$1</h2>");
            put("\\[h3\\](.+?)\\[/h3\\]", "<h3>$1</h3>");
            put("\\[h4\\](.+?)\\[/h4\\]", "<h4>$1</h4>");
            put("\\[h5\\](.+?)\\[/h5\\]", "<h5>$1</h5>");
            put("\\[h6\\](.+?)\\[/h6\\]", "<h6>$1</h6>");
            put("\\[quote\\](.+?)\\[/quote\\]", "<blockquote>$1</blockquote>");
            put("\\[p\\](.+?)\\[/p\\]", "<p>$1</p>");
            put("\\[p=(.+?),(.+?)\\](.+?)\\[/p\\]", "<p style='text-indent:$1px;line-height:$2%;'>$3</p>");
            put("\\[center\\](.+?)\\[/center\\]", "<div align='center'>$1");
            put("\\[align=(.+?)\\](.+?)\\[/align\\]", "<div align='$1'>$2");
            put("\\[color=(.+?)\\](.+?)\\[/color\\]", "<span style='color:$1;'>$2</span>");
            put("\\[size=(.+?)\\](.+?)\\[/size\\]", "<span style='font-size:$1;'>$2</span>");
            put("\\[img\\](.+?)\\[/img\\]", "<img src='$1' />");
            put("\\[img=(.+?),(.+?)\\](.+?)\\[/img\\]", "<img width='$1' height='$2' src='$3' />");
            put("\\[email\\](.+?)\\[/email\\]", "<a href='mailto:$1'>$1</a>");
            put("\\[email=(.+?)\\](.+?)\\[/email\\]", "<a href='mailto:$1'>$2</a>");
            put("\\[url\\](.+?)\\[/url\\]", "<a href='$1'>$1</a>");
            put("\\[url=(.+?)\\](.+?)\\[/url\\]", "<a href='$1'>$2</a>");
            put("\\[youtube\\](.+?)\\[/youtube\\]", "<object width='640' height='380'><param name='movie' value='http://www.youtube.com/v/$1'></param><embed src='http://www.youtube.com/v/$1' type='application/x-shockwave-flash' width='640' height='380'></embed></object>");
            put("\\[video\\](.+?)\\[/video\\]", "<video src='$1' />");
        }};

        for (Map.Entry entry : bbMap.entrySet()) {
            html = html.replaceAll(entry.getKey().toString(), entry.getValue().toString());
        }

        return html;
    }

    public void setTextNews(String textNews) {
        this.textNews = StringEscapeUtils.escapeHtml4(textNews);
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
