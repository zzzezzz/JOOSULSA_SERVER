package com.joosulsa.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.joosulsa.entity.Tb_User;
import com.joosulsa.repository.UserRepository;

@Component
public class AttendanceScheduler {
	
	@Autowired
	private UserRepository userRepo;
	
	// 매일정해진 시간에 메소드를 실행시켜주는 스케줄러
	@Scheduled(cron = "0 1 0 * * ?")
	public void markAttendance() {
		List<Tb_User> users = userRepo.findAll();
		for (int i = 0; i < users.size(); i++) {
			Tb_User user = users.get(i);
            // 출석 체크 로직 예시: 특정 조건을 만족하면 출석으로 표시
//            if (여기에 조건을 작성해줘) {
//                userRepo.markAttendance(user.getUserId());
//            }
        }
	}
	
}
