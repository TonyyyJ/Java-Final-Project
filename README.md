# Spam Email Checker
# Description
The Spam Email Checker is a Java-based application designed to identify and filter spam emails efficiently. By leveraging advanced computational techniques and machine learning, this system provides robust email security, minimizing the impact of spam emails on users' productivity and security.
# Adevanced Concepts
Machine Learning: To predict whether an email is spam or not.

Network Communication: To ensure data integrity and privacy.

Concurrent Processing: Supports handling multiple emails simultaneously using multi-threading, ensuring high performance.

Database Optimization: To quickly retrieve and store email data

User-Friendly Interface: Features a simple and intuitive Java Swing interface that allows users to configure settings and view email statuses.

# Technical Stack
Network Communication: WE use JavaMail API to interact with 163 mail servers for fetching and parsing emails.

Machine Learning Libraries: We applied Weka's API into our maven repository, we use Weka provided APIs to load dataset for our data training models, train the model make based on using Naive Bayes algorithm and make predictions based on using Naive Bayes algorithm for classifying emails as spam or non-spam. 
# Please note that due to the limitation of the Naive Bayes algorithm and the size of our training data, the accuracy of our spam detectors will not be 100%.

Database: For database operations. In this project, we are using SQLite Studio.

Concurrent Processing: We create a separate EmailCheckerThread thread for each email message. These threads are executed concurrently.

User-Friendly Interface: We used Java Swing for the graphical user interface.

# How to Run
First, download our zip and unzip it into a desired location on your computer.
# 1.Open Eclipse

# 2. Import the Project: 

Go to the menu and select File > Import....

In the import wizard, expand the Maven folder, and select Existing Maven Projects. Then click Next.

Click Browse and navigate to the root directory where you extracted your project files. 

 # 3. Configure the Project
Eclipse may automatically start downloading the necessary dependencies as specified in the pom.xml。

If not, Right-click on the project in the Project Explorer.

Select Maven > Update Project... from the context menu.

In the update project dialog box, check your project and click OK.

# 4. Change Database Path in code.
Locate your unzip file and find the target file, EmailChecker.db and copy the path of that.

Find the DatabaseManager.java in the scr/main/java folder. In line 12, paste the path you just copied into the highlighted space. 
![image](https://github.com/TonyyyJ/Java-Final-Project/assets/77677230/58a66c89-0703-4923-904a-f406806d77c3)

(Note: The path should be ended with \\\EmailChecker.db". If you copy the file path from you file explorer, add \\\EmailChecker.db at the end of your path)

# 5. Connect to Database
We are using SQLite Studio here, we click the "Database" on the left side corner and hit "Add a database", then add the database in the cosponding loccation. 
![1715384905844](https://github.com/TonyyyJ/Java-Final-Project/assets/112592243/42b7e64b-2cbd-4628-834f-81542e608bc3)

Then the new database should be connected. As showing in our demo video.

# 6. Runnign the Project
We use our test email account.
   
Find the SpamCheckerGUI.java in the scr/main/java folder.

Right click on this file, and select Run As > Java Application.


![image](https://github.com/TonyyyJ/Java-Final-Project/assets/112592243/a29033aa-8abe-40fd-a312-d3a1d38acac3)

We are using 163 email as a test incicator of this project. 163 Mailbox is a free e-mail service provided by NetEase, mainly for the Chinese market. 

We chose 163 email because its POP3 service is easy to access. 

For Host, please the POP3 server:pop.163.com.

For Username, please use:yydeng_0490@163.com, it's the eamil we used for this project.

For password, please use the the third-party authorization password we got from the server:HIOQBGFNUIXFKZBZ.



![image](https://github.com/TonyyyJ/Java-Final-Project/assets/112592243/61238a14-b4aa-4cee-a567-592bae65d604)


After we click Check Emails, we will be connected to database by our DatbaseManager.java. The terminal will shows the connection to SQLite is success.

The terminal will also show the progress of email, how many percentage of emails have been processed, and the time used to process all the emails.

A user interface will them showed up 

![1715385093539](https://github.com/TonyyyJ/Java-Final-Project/assets/112592243/e53ea49f-f7f9-4f6f-9187-472e30d6a07f)

Hit "Refresh Data", then it will show the result of the spam email checking, it will show the % of spam email and not_spam email.

By hitting the "- spam emails out of - total emails" or "- not_spam emails out of - total emails", 

it will show you the lists that has all the emails that been defined as spam/not_spam listing all the subject of the emails.

Doudble clicking on any of the email subject will gives you the details of that email, the Subject, Sender, Content.

All the data used by these UI presented are extrcted from the database.

![1715385570158](https://github.com/TonyyyJ/Java-Final-Project/assets/112592243/2f392eb0-3a61-42e1-a5b0-50cf8f1b30a7)


