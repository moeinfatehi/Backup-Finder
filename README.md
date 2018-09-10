# Backup-Finder
A burp suite extension that reviews backup, old, temporary and unreferenced files on web server for sensitive information (OWASP OTG-CONFIG-004)


# Why this extension?
As I checked some tools in this field, I realized that almost all of tools use static payloads (they use built-in dictionaries) and they don't generate dynamic payloads based on target which is being tested.</br>
Suppose this is hierachy tree of web server:</br>
/</br>
>upload&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
>>index.php</br>
│───├───index.php~</br>
│───├───index.php.bkup</br>
│───├───upload.zip</br>
│───└───users&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
│───────├───index.php</br>
│───────├───catalog.zip</br>
└───WeirdDirName&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
│───├───index.php</br>
│───├───captcha.php</br>
│───├───captcha.php.old</br>
└───WeirdDirName.tar.gz</br></br>
and this is the result of crawler:</br>
/</br>
├───upload&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
│───├───index.php</br>
│───└───users&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
│───────├───index.php</br>
└───WeirdDirName&nbsp;&nbsp;&nbsp;&nbsp;#Dir</br>
│───├───index.php</br>
│───├───captcha.php</br></br>

This extension will find all backup, old and temp files in this scenario:</br>
* /upload/index.php~
* /upload/index.php.bkup
* /upload/upload.zip
* /upload/catalog.zip
* /upload/
* /WeirdDirName.tar.gz
* /WeirdDirName/captcha.php.old

Here is a screenshot of extension's results.</br></br>
<img src="https://cdn1.imggmi.com/uploads/2018/9/10/e86bc9fdfec3ae10d84dbad11ca21540-full.png">

# Some of options
These options can be used to customize the detection:
* Level: Level of tests to perform (1-5, default 3)
* Thread: num of threads (1-50, default 10)
* Built-in dictionary: there is a built-in dictionary containing most used directory and file names to be used for static payload generation.
* Loadable dictionary: you can use your own dictionary file for static payload generation.
* HTTP method: HTTP method to be used in requests (HEAD, GET)(default: Head)
* Includable extensions
* Excludable extensions
* Includable status codes
* Excludable status codes

# Disclaimer
This program is for Educational purpose ONLY. Do not use it without permission. The usual disclaimer applies, especially the fact that I'm not liable for any damages caused by direct or indirect use of the information or functionality provided by these programs. The author or any Internet provider bears NO responsibility for content or misuse of these programs or any derivatives thereof. By using these programs you accept the fact that any damage (dataloss, system crash, system compromise, etc.) caused by the use of this program is not my responsibility.

# Contact
If you have any further questions, please don't hesitate to contact me via my <a href="https://twitter.com/MoeinFatehi">twitter</a> account.
