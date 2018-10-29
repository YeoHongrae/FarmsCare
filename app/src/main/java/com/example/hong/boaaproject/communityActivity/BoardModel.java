package com.example.hong.boaaproject.communityActivity;

public class BoardModel {

    String BoardContent, BoardName, BoardDate;

    public BoardModel(String boardContent, String boardName, String boardDate) {

        BoardContent = boardContent;
        BoardName = boardName;
        BoardDate = boardDate;
    }

    public String getBoardContent() {
        return BoardContent;
    }

    public void setBoardContent(String boardContent) {
        BoardContent = boardContent;
    }

    public String getBoardName() {
        return BoardName;
    }

    public void setBoardName(String boardName) {
        BoardName = boardName;
    }

    public String getBoardDate() {
        return BoardDate;
    }

    public void setBoardDate(String boardDate) {
        BoardDate = boardDate;
    }




}
