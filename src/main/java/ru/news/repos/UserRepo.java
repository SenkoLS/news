package ru.news.repos;

import org.springframework.data.repository.CrudRepository;
import ru.news.domains.User;

public interface UserRepo extends CrudRepository<User, Long> {
    public User findByLoginEquals(String login);

    public User findByTokenEquals(String token);

    public boolean deleteByLoginEquals(String login);

    public boolean existsByTokenEquals(String token);

    public boolean existsByLoginEquals(String login);
}
