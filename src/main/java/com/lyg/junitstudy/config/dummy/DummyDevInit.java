package com.lyg.junitstudy.config.dummy;

import com.lyg.junitstudy.domain.user.User;
import com.lyg.junitstudy.domain.user.UserEnum;
import com.lyg.junitstudy.domain.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DummyDevInit extends  DummyObject{

    @Profile("dev") // prod 환경에서는 실행되면 안된다.
    @Bean
    CommandLineRunner init(UserRepository userRepository){
        return (args) -> {
            // 서버 실행시에 무조건 실행된다.
            User ssar = userRepository.save(newUser("ssar", "ssar"));
        };
    }
}
