package ru.news.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.news.domains.User;
import ru.news.exceptions.CurrentUserError;
import ru.news.util.MessageCreator;
import ru.news.repos.UserRepo;

import java.util.HashMap;

/**
 * REST-контрроллер операций с учетной записью пользователя
 * (обновление данных, удаление учетной записи)
 */
@RestController
@RequestMapping("uac")
public class UserAccountEndpoint {
    @Autowired
    private UserRepo userRepo;
    private Object principal;
    private User currentUser;

    private void setCurrentUser() {
        principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            currentUser = userRepo.findByLoginEquals(((UserDetails) principal).getUsername());
        } else {
            throw new CurrentUserError("Ошибка получения текущего пользователя!");
        }
    }

    /**
     * Получение данных текущего пользователя
     *
     * @return Возвращает данные пользователя в формате JSON
     */
    @PostMapping
    public HashMap<String, String> getUserAsJson() throws JsonProcessingException {
        setCurrentUser();
        //return currentUser;
        return new HashMap<String, String>(){{
            put("id_user", currentUser.getId_user().toString());
            put("login", currentUser.getLogin());
            put("first_name", currentUser.getFirst_name());
            put("last_name", currentUser.getLast_name());
            put("patronomic", currentUser.getPatronomic());
            put("registration_date", currentUser.getRegistration_date().toString());
        }};
    }

    @GetMapping
    public HashMap<String, String> getUserAsJsonByGet() throws JsonProcessingException {
        return getUserAsJson();
    }

    /**
     * Обновление данных по текущему пользователю (НЕ РЕАЛИЗОВАНО)
     *
     * @param login      - Логин пользователя
     * @param first_name - Имя пользователя
     * @param last_name  - Фамилия пользователя
     * @param pat        - Отчество пользователя
     * @param password   - Пароль пользователя
     * @param active     - Признак подтверждения учетной записи
     * @return Возвращает статус обновления (OK - обновление успешно, FAIL - ошибка при обновлении)
     */
    @PutMapping
    public MessageCreator updateUserInfo(String login, String first_name, String last_name, String pat, String password, String active) {
        return new MessageCreator(MessageCreator.Status.OK,
                "Метод PUT работает в качестве заглушки!");
    }

    /**
     * Удаление текущего пользователя
     *
     * @return Возвращает статус удаления (OK - удаление успешно, FAIL - ошибка при удалении)
     */
    @DeleteMapping
    public MessageCreator deleteUser() {
        if (userRepo.deleteByLoginEquals(currentUser.getLogin())) {
            return new MessageCreator(MessageCreator.Status.OK,
                    "Учетная запись была удалена!");
        } else {
            return new MessageCreator(MessageCreator.Status.FAIL,
                    "Учетную запись не возможно удалить!");
        }
    }
}
