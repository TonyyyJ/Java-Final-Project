# Spam Email Checker
# Description
The Spam Email Checker is a Java-based application designed to identify and filter spam emails efficiently. By leveraging advanced computational techniques and machine learning, this system provides robust email security, minimizing the impact of spam emails on users' productivity and security.
# Key Features
Spam Detection: Utilizes machine learning to accurately identify spam emails, reducing false positives and negatives.

Email Client Integration: Designed for easy integration with existing email clients to enhance user workflow.

Concurrent Email Processing: Supports handling multiple emails simultaneously using multi-threading, ensuring high performance.

SQLlite:

User-Friendly Interface: Features a simple and intuitive Java Swing interface that allows users to configure settings and view email statuses.

# Technical Stack
JavaMail API: For fetching and parsing emails.

Machine Learning Libraries: For classifying emails as spam or non-spam.

SQLIite: For database operations.

Java Swing: For the graphical user interface.

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

# 4. Runnign the Project
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

