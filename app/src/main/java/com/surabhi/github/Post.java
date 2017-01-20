package com.surabhi.github;

import java.util.Date;

/**
 * Created by Surabhi Agrawal on 1/19/2017.
 */
public class Post {

    Date updated_at;
    String title;
    String commentsUrl;
    String titleBody;
    int comments;
    String user;


    String getUser()
    {
        return this.user;
    }

    void setUser(String _user)
    {
        this.user = _user;
    }

    int getComments()
    {
        return this.comments;
    }

    void setComments(int _comments)
    {
        this.comments = _comments;
    }
    String getTitle()
    {
        return title;
    }

    void setTitle(String _title)
    {
        this.title = _title;
    }

    String getTitleBody()
    {
        return titleBody;
    }

    void setTitleBody(String _titleBody)
    {
        this.titleBody = _titleBody;
    }

    String getCommentsUrl()
    {
        return commentsUrl;
    }

    void setCommentsUrl(String _url)
    {
        this.commentsUrl = _url;
    }
//
//    long getUpdateTime()
//    {
//        Date currentTime = new Date();
//
//
//        long diff = currentTime.getTime() - updated_at.getTime();
//
//        long diffSeconds = diff / 1000 % 60;
//        long diffMinutes = diff / (60 * 1000) % 60;
//        long diffHours = diff / (60 * 60 * 1000) % 24;
//        long diffDays = diff / (24 * 60 * 60 * 1000);
//
//        if (diffDays == 0)
//            if (diffHours == 0)
//                if(diffMinutes ==0)
//                    return 0;
//                else if (diffMinutes == 1)
//                    return diffMinutes;
//                else
//                    return diffMinutes ;
//            else if (diffHours == 1)
//                return diffHours;
//            else
//                return diffHours;
//        else if (diffDays == 1)
//            return diffDays;
//        else
//            return diffDays;
//
//    }

    Date getUpdated_at()
    {
        return this.updated_at;
    }

    void setUpdated_at(Date updated_at)
    {
        this.updated_at = updated_at;
    }

}
