package com.joosulsa.dto;

public class TownRankDTO {

	public static class TownRankData {
        private String townName;
        private Long totalPoints;
        private String townX;
        private String townY;

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
        
        
        
        public String getTownX() {
            return townX;
        }
        
        public void setTownX(String townX) {
            this.townX = townX;
        }
        
        public String getTownY() {
            return townX;
        }
        
        public void setTownY(String townY) {
            this.townY = townY;
        }
        
        
        
    }
	
	
}
