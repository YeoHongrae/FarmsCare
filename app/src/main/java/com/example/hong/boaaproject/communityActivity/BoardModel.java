package com.example.hong.boaaproject.communityActivity;

public class BoardModel {

    String boardContent;
    String userID;
    String boardDate;
    String boardNum;
    String boardComment;
    String boardImgURL;

    public BoardModel(String boardContent, String userID, String boardDate, String boardNum, String boardComment, String boardImgURL) {
        this.boardContent = boardContent;
        this.userID = userID;
        this.boardDate = boardDate;
        this.boardNum = boardNum;
        this.boardComment = boardComment;
        this.boardImgURL = boardImgURL;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        boardContent = boardContent;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBoardDate() {
        return boardDate;
    }

    public void setBoardDate(String boardDate) {
        boardDate = boardDate;
    }

    public String getBoardNum() {
        return boardNum;
    }

    public void setBoardNum(String boardNum) {
        this.boardNum = boardNum;
    }

    public String getBoardComment() {
        return boardComment;
    }

    public void setBoardComment(String boardComment) {
        this.boardComment = boardComment;
    }

    public String getBoardImgURL() {
        return boardImgURL;
    }

    public void setBoardImgURL(String boardImgURL) {
        this.boardImgURL = boardImgURL;
    }


}
