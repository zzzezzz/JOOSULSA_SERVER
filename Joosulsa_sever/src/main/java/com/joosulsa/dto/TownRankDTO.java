package com.joosulsa.dto;

public class TownRankDTO {

	public static class TownRankData {
        private String townName;
        private Long totalPoints;

        public String getTownName() {
            return townName;
        }

        public void setTownName(String townName) {
            this.townName = townName;
        }

        public Long getTotalPoints() {
            return totalPoints;
        }

        public void setTotalPoints(Long totalPoints) {
            this.totalPoints = totalPoints;
        }
    }
	
	
}
