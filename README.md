# Github-Client
Github Client for Rails Issues
# Android App: Github-Client for Viewing Issues
Displays Issues for the repo '/rails' and comments on it.

This app is created as a solution to the following excercise for an interview:
 
 ![github_client_assignment](https://cloud.githubusercontent.com/assets/16222434/22141301/668182ac-df17-11e6-9544-e26f09ab7cad.png)

Android App created for this exercise can be downloaded from [here](https://github.com/surabhiagrawal60/Github-Client/blob/master/app/Github_IssuesClient_By_Surabhi_20012017.apk).

## What it does:
 * Shows list of issues for '/rails' GitHub repository sorted by last updated time. (repo is hard coded)
 * Issues list will have Issue Title, Issue Body, Issue Author and number of comments.
 * Clicking any issue will show comments on it.
 * Comments will have comments body and author.
 * To load more issues swipe down from the top, new issues will come at the bottom.

## Error Scenarios:
 * Internet Connectivity on Activity loading, Comment loading and Page refresh.
 * Zero comment check on comment loading.

## What it does not do (Not part of the requirement):
 * It does not show updated time and created time of the issue.
 * It does not show updated time of the comment.
 * It does not show full body of the issue.
 * It does not store issues/comments locally so it fetches from internet on every call

## Solution Screenshots: ##
![scr github no comments on comment load](https://cloud.githubusercontent.com/assets/16222434/22142721/53f65896-df1d-11e6-842f-e6a0c5ed50dc.jpeg)
![scr github no internet on comment load](https://cloud.githubusercontent.com/assets/16222434/22142720/53f3db8e-df1d-11e6-84b3-fb68ee8a9bb0.jpeg)
![scr github no internet on page load](https://cloud.githubusercontent.com/assets/16222434/22142722/53f72064-df1d-11e6-9712-6888d087406f.jpeg)
![scr github comments dialog](https://cloud.githubusercontent.com/assets/16222434/22142723/54240764-df1d-11e6-878e-b670df1eebc9.jpeg)
