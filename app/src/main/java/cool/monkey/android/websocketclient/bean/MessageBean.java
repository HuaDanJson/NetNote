package cool.monkey.android.websocketclient.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class MessageBean {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "MessageBean")
    private int userId;
    private String message;
    private int messageId;
    private long createAt;
    private int sendUserId;
    public boolean isRead;
    @Generated(hash = 2011970318)
    public MessageBean(Long id, int userId, String message, int messageId,
                       long createAt, int sendUserId, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.messageId = messageId;
        this.createAt = createAt;
        this.sendUserId = sendUserId;
        this.isRead = isRead;
    }
    @Generated(hash = 1588632019)
    public MessageBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getMessageId() {
        return this.messageId;
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
    public long getCreateAt() {
        return this.createAt;
    }
    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }
    public int getSendUserId() {
        return this.sendUserId;
    }
    public void setSendUserId(int sendUserId) {
        this.sendUserId = sendUserId;
    }
    public boolean getIsRead() {
        return this.isRead;
    }
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", messageId=" + messageId +
                ", createAt=" + createAt +
                ", sendUserId=" + sendUserId +
                ", isRead=" + isRead +
                '}';
    }
}
