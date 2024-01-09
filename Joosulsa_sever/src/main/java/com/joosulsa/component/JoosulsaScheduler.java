package com.joosulsa.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.joosulsa.entity.Tb_User;
import com.joosulsa.repository.UserRepository;

@Component
public class JoosulsaScheduler {

	@Autowired
	private UserRepository userRepository;

	// 매월 1일 00시 01분에 실행
	@Scheduled(cron = "0 1 0 1 * ?")
	public void resetMonthlyAttendance() {
		List<Tb_User> users = userRepository.findAll();

		for (Tb_User user : users) {
			// 이달 출석 횟수 초기화
			user.setMonthlyAttendance(0);
		}

		// 변경된 정보를 저장
		userRepository.saveAll(users);
	}

	// 매일정해진 시간에 메소드를 실행시켜주는 스케줄러
	@Scheduled(cron = "0 1 0 * * ?")
	public void markAttendance() {
        List<Tb_User> users = userRepository.findAll();
        for (Tb_User user : users) {
            // 출석 및 퀴즈 참여 여부를 false로 설정
            user.setAttendance(false);
            user.setQuizParticipation(false);

            // 변경된 정보를 저장
            userRepository.save(user);
        }
    }

}
