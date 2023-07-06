package am.myOffice.shopJDBC;

import am.myOffice.shopJDBC.model.User;
import am.myOffice.shopJDBC.service.user.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        var userService = context.getBean(UserService.class);

        User user = User.builder()
                .name("name")
                .age(21)
                .balance(65465)
                .email("test@gmail.com")
                .lastname("adsasd")
                .password("asfasfasf")
                .build();

        userService.register(user);

    }

}
