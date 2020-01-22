package ru.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.news.domains.News;
import ru.news.repos.NewsRepo;
import ru.news.util.PaginationBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HelloAdminController {
    @Autowired
    private NewsRepo newsRepo;

    @Value("${pagination.number_of_records_shown}")
    private Integer countRecordsShown;

    @GetMapping("/adminpanel")
    public String getStartPageHello(HttpServletRequest httpServletRequest, Model model) {
        return getNumberPageHello(httpServletRequest, model, 0);
    }

    @GetMapping("/adminpanel/{page}")
    public String getNumberPageHello(HttpServletRequest httpServletRequest, Model model,
                                     @PathVariable("page") Integer page) {

        Pageable pageable = setPaginationPropertiesNews(httpServletRequest, model, page);
        model.addAttribute("news", newsRepo.findAllByOrderByDateOfCreationDesc(pageable));
        return "startAdminPage";
    }

    private List<News> getNewsWithPaginationProperty(Pageable pageable) {

        //List<News> news = newsRepo.findAllBy(pageable);
        /*List<List<UserTask>> columnUserTaskSheet = new ArrayList<>();
        for (int i = 0; i < userTasks.size() - 1; i += 2) {
            List<UserTask> twoTasks = new ArrayList<>();
            twoTasks.add(userTasks.get(i));
            twoTasks.add(userTasks.get(i + 1));
            columnUserTaskSheet.add(twoTasks);
        }*/
        return newsRepo.findAllBy(pageable);
    }

    private Pageable setPaginationPropertiesNews(HttpServletRequest httpServletRequest, Model model,
                                                 @PathVariable("page") Integer page) {
        Pageable pageable = new PageRequest(page, countRecordsShown);
        model.addAttribute("pagination",
                new PaginationBuilder(httpServletRequest,
                        newsRepo.countAllBy(), page,
                        countRecordsShown,"adminpanel"));
        return pageable;
    }
}
