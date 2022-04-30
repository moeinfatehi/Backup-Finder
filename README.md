# Backup-Finder
A burp suite extension that reviews backup, old, temporary, and unreferenced files on the webserver for sensitive information.</br></br>
OWASP references:
* <b>Classification</b>: Web Application Security Testing > 02-Configuration and Deployment Management Testing
* <b>OTG v4</b>: OWASP OTG-CONFIG-004
* <b>WSTG</b>: WSTG-CONF-04


# Why should I use this extension?
As I analyzed some of "backup finder" tools, I realized that almost all of the available tools use <b>only</b> static payloads (they use built-in dictionaries) and they don't generate dynamic payloads based on target which is being tested.</br>
</br>For example, suppose this is hierarchy tree of our web application:</br>
</br>/</br>
├───<b>upload</b>&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
├───├───index.php</br>
├───├───index.php~</br>
├───├───index.php.bkup</br>
├───├───upload.zip</br>
├───└───<b>users</b>&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
├───────├───index.php</br>
├───────├───catalog.zip</br>
├───<b>WeirdDirName</b>&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
├───├───index.php</br>
├───├───captcha.php</br>
├───├───captcha.php.old</br>
├───WeirdDirName.tar.gz</br></br>
and this is the result of a crawler:</br>
/</br>
├───<b>upload</b>&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
├───├─── /</br>
├───└───<b>users</b>&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
├───────├─── /</br>
├───<b>WeirdDirName</b>&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
├───├─── /</br>
├───├───captcha.php</br></br>

This extension will find all backup, old and temp files in this scenario:</br>
* /upload/index.php~ (Dynamic, finds possible extension and index file)
* /upload/index.php.bkup (Dynamic, finds possible extension and index file)
* /upload/upload.zip (Dynamic, using current dir name)
* /upload/users/catalog.zip (Static, using dictionary)
* /WeirdDirName.tar.gz (Dynamic, using child dir name)
* /WeirdDirName/captcha.php.old (Dynamic, using current dir name)

# Installation
The quickest way is to load the jar file (BackupFinder.jar) in the extender tab of the Burpsuite.</br>
Extender -> Extensions -> Add</br>
A new tab will be added to the burp suite.</br>


# Quick Start
1. Select a request of a target host from any tab of the burp suite
2. In "backupFinder -> Finder -> options" tab, apply your configurations.
3. Go to "backupFinder -> Finder -> Finder" tab and click on "start" button.

# Fine Tuning (To have the best results)
This extension uses the datas in the target tab (which are collected actively or passively) under the domain name that you call extension for as its feed.
to have the best result, it's recommended that you first do these steps before starting the extension process:
* crawl the domain actively using the Burpsuite crawler. (in the target tab: right click on the domain -> scan -> crawl only)
* If the application needs authentication, login via browser and submit the forms to see if there are more files or directories to be listed under our target.


# Some of the options
These options can be used to customize the detection:
* Level: Level of tests to perform (1-5, default 3)
* Thread: num of threads (1-50, default 10)
* Built-in dictionary: there is a built-in dictionary containing the most used directory and file names to be used for static payload generation.
* Loadable dictionary: you can use your dictionary file for static payload generation.
* HTTP method: HTTP method to be used in requests (HEAD, GET)(default: Head)
* Includable extensions
* Excludable extensions
* Includable status codes
* Excludable status codes

# Disclaimer
This program is for educational purposes ONLY. Do not use it without permission. The usual disclaimer applies, especially the fact that I'm not liable for any damages caused by the direct or indirect use of the information or functionality provided by these programs. The author or any Internet provider bears NO responsibility for content or misuse of these programs or any derivatives thereof. By using these programs you accept the fact that any damage (data loss, system crash, system compromise, etc.) caused by the use of this program is not my responsibility.

# Contact
If you have any further questions, please don't hesitate to contact me via my <a href="https://twitter.com/MoeinFatehi">twitter</a> account.
