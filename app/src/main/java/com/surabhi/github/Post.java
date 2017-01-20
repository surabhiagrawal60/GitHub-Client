package com.surabhi.github;

import java.util.Date;

/**
 * Created by Surabhi Agrawal on 1/19/2017.
 */
// Model Class for Data in the form of Json
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
    Date getUpdated_at()
    {
        return this.updated_at;
    }

    void setUpdated_at(Date updated_at)
    {
        this.updated_at = updated_at;
    }

}
