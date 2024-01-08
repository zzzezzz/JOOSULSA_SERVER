package com.joosulsa.dto;

public class UserRankDTO {

    public static class UserRankData {
        private String userNick;
        private Long totalPoints;

        public String getUserNick() {
            return userNick;
        }

        public void setUserNick(String userNick) {
            this.userNick = userNick;
        }

        public Long getTotalPoints() {
            return totalPoints;
        }

        public void setTotalPoints(Long totalPoints) {
            this.totalPoints = totalPoints;
        }
    }

    // 다른 내용들...
}