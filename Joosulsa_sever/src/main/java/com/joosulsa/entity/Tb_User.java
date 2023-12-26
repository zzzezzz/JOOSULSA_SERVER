package com.joosulsa.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
public class Tb_User {
	
	@Id
    @Column(name = "user_id", length = 30, nullable = false)
    private String userId;

    @Column(name = "user_pw", length = 40, nullable = false)
    private String userPw;

    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    @Column(name = "user_addr", length = 1000, nullable = false)
    private String userAddr;

    @Column(name = "user_nick", length = 30, nullable = false)
    private String userNick;

    @Column(name = "joined_at", nullable = false)
    private String joinedAt;
    
    @OneToMany(mappedBy = "userId")
    private List<Tb_Quiz> quizNum;
    
    @OneToMany(mappedBy = "userId")
    private List<Tb_Recycling> recycleNum;
    
    @OneToMany(mappedBy = "userId")
    private List<Tb_Attendance> attNum;
    
    @OneToMany(mappedBy = "userId")
    private List<Tb_Point_History> histNum;
    
    
    @Override
    public String toString() {
    	return "Tb_User";
    }
	
}
