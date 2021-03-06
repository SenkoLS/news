package ru.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.news.domains.News;
import ru.news.repos.NewsRepo;
import ru.news.util.GetApplicationPaths;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private HelloAdminController helloAdminController;

    @GetMapping("/addnews")
    public String getTask(HttpServletRequest httpServletRequest,
                          Model model) {
        return "addnews";
    }

    @GetMapping("/delete/{idNews}")
    public RedirectView getTask(HttpServletRequest httpServletRequest,
                                Model model,
                                @PathVariable("idNews") Long idNews) {

        newsRepo.deleteById(idNews);
        return new RedirectView("/adminpanel");
    }

    @GetMapping("/getedit/{idNews}")
    public String getEditFormWithNews(HttpServletRequest httpServletRequest,
                                      Model model,
                                      @PathVariable("idNews") Long idNews) {

        News news = newsRepo.findByIdNewsEquals(idNews);
        model.addAttribute("news", news);
        return "editnews";
    }

    @PostMapping("/editnews")
    public RedirectView editNews(HttpServletRequest httpServletRequest,
                                 Model model,
                                 @RequestParam("label") String label,
                                 @RequestParam("text_news") String textNews,
                                 @RequestParam("image_news") MultipartFile imageFile,
                                 @RequestParam("id_news") Long idNews) throws Exception {

        News news = newsRepo.findByIdNewsEquals(idNews);
        news.setLabel(label);
        news.setTextNews(textNews);
        if (!imageFile.getOriginalFilename().equalsIgnoreCase("")) {
            byte[] bytes = imageFile.getBytes();
            Map<String, String> propFileImage = getUniquePathPropFileImage(imageFile);
            Files.write(Paths.get(propFileImage.get("path")), bytes);
            news.setPathImage(propFileImage.get("uniqueNameFile"));
        }
        //news.setDateOfCreation(ZonedDateTime.now());
        newsRepo.save(news);
        return new RedirectView("/adminpanel");
    }

    @PostMapping("/addnews")
    public RedirectView addNews(HttpServletRequest httpServletRequest,
                                Model model,
                                @RequestParam("label") String label,
                                @RequestParam("text_news") String textNews,
                                @RequestParam("image_news") MultipartFile imageFile) throws Exception {

        byte[] bytes = imageFile.getBytes();
        Map<String, String> propFileImage = getUniquePathPropFileImage(imageFile);
        Files.write(Paths.get(propFileImage.get("path")), bytes);
        newsRepo.save(new News(label, textNews, propFileImage.get("uniqueNameFile")));
        return new RedirectView("/adminpanel");
    }

    private Map<String, String> getUniquePathPropFileImage(@RequestParam("image_news") MultipartFile imageFile) throws Exception {
        Map<String, String> propFileImage = new HashMap<>();
        String uniqueNameFile = String.valueOf(1 + new Random().nextInt(1000)) + imageFile.getOriginalFilename();
        propFileImage.put("path", GetApplicationPaths.getApplicationImagesPath() + uniqueNameFile);
        propFileImage.put("uniqueNameFile", uniqueNameFile);

        return propFileImage;
    }

    private String getFolder() throws Exception {
        String folder = System.getProperty("user.dir") + "\\IMG_NEWS_PROJECT\\";
        new File(folder).mkdir();
        return folder;
    }
}
