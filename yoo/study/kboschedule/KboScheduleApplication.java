package study.kboschedule;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import study.kboschedule.api.SearchNaverAPI;

@SpringBootApplication
@RequiredArgsConstructor
public class KboScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(KboScheduleApplication.class, args);

    }

}
