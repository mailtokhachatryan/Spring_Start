package am.myOffice.shopJDBC;

import am.myOffice.shopJDBC.model.User;
import am.myOffice.shopJDBC.service.user.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        var userService = context.getBean(UserService.class);

        System.out.println(userService.getUser(1L));


    }

}
