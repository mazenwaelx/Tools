package com.tools.dtos;

public class FriendRequestDTO {

    private Long senderId;
    private Long receiverId;
    private String status; 

    public FriendRequestDTO() {}

    public FriendRequestDTO(Long senderId, Long receiverId, String status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
    }


    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }
    public Long getReceiverId() { return receiverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
