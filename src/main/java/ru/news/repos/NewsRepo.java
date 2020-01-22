package ru.news.repos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.news.domains.News;

import java.util.ArrayList;

public interface NewsRepo extends CrudRepository<News, Long> {
    public ArrayList<News> findAllBy();

    public ArrayList<News> findAllBy(Pageable pageable);

    public ArrayList<News> findAllByOrderByDateOfCreationDesc(Pageable pageable);

    public Integer countAllBy();

    public News findByIdNewsEquals(Long idNews);
}
