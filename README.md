# Backup-Finder: Advanced Webserver File Analysis Tool

**Backup-Finder** is an innovative Burp Suite extension designed to meticulously scan webservers for backup, old, temporary, and unreferenced files that may contain sensitive information. By leveraging both dynamic and static payload generation, this tool surpasses traditional backup finder tools, adapting its search patterns to the specific target being tested. It's an indispensable tool for security professionals and penetration testers aiming to uncover potential vulnerabilities in web applications.

## Key Features:
- **Dynamic Payload Generation**: Adapts search patterns based on the web application's structure, enhancing the likelihood of discovering critical files.
- **Comprehensive Scanning**: Employs a variety of methods to detect overlooked files that could pose security risks.
- **Customizable Options**: Offers adjustable settings to balance between thoroughness and efficiency, catering to different security assessment needs.

## Why Should I Use This Extension?
Backup-Finder excels where other tools might fall short. Consider a web application with the following directory structure:

```
/
├───upload
│   ├───index.php
│   ├───index.php~
│   ├───index.php.bkup
│   └───upload.zip
├───users
│   └───catalog.zip
└───WeirdDirName
    ├───index.php
    ├───captcha.php
    ├───captcha.php.old
    └───WeirdDirName.tar.gz
```
A crawler will detect this structure:
```
/
├───upload    #Dir
├───├─── /
├───└───users    #Dir
├───────├─── /
├───WeirdDirName    #Dir
├───├─── /
├───├───captcha.php
```
While most tools rely on static payloads, Backup-Finder dynamically generates payloads based on this structure, effectively identifying files like `index.php~`, `upload.zip`, and `WeirdDirName.tar.gz` that others might miss.

This extension will find all backup, old and temp files in this scenario:</br>
* /upload/index.php~ (Dynamic, finds possible extension and index file)
* /upload/index.php.bkup (Dynamic, finds possible extension and index file)
* /upload/upload.zip (Dynamic, using current dir name)
* /upload/users/catalog.zip (Static, using dictionary)
* /WeirdDirName.tar.gz (Dynamic, using child dir name)
* /WeirdDirName/captcha.php.old (Dynamic, using current dir name)


## References

"Backup-Finder" aligns with key security standards and methodologies, underscoring its effectiveness in web application security:

1. **OWASP Web Security Testing Guide (WSTG)**:
   - **WSTG-CONF-04**: Testing for File and Directory Information Leakage - Directly relevant to "Backup-Finder's" core functionality. [More Info](https://owasp.org/www-project-web-security-testing-guide/v42/4-Web_Application_Security_Testing/02-Configuration_and_Deployment_Management_Testing/04-Test_for_Default_or_Uncommon_Files)

2. **OWASP Top Ten - A6:2017-Security Misconfiguration**: 
   - Addresses issues related to insecure default configurations and misconfigurations leading to information leakage. [More Info](https://owasp.org/www-project-top-ten/2017/A6_2017-Security_Misconfiguration)

3. **CWE (Common Weakness Enumeration)**:
   - **CWE-538**: File and Directory Information Exposure - Concerns the exposure of sensitive information through files and directories. [More Info](https://cwe.mitre.org/data/definitions/538.html)
   - **CWE-212**: Improper Removal of Sensitive Information Before Storage or Transfer - Relevant for identifying sensitive data in backup files. [More Info](https://cwe.mitre.org/data/definitions/212.html)

4. **WASC (Web Application Security Consortium)**:
   - **WASC-13**: Information Leakage - Includes the exposure of sensitive information through improperly secured files. [More Info](http://projects.webappsec.org/w/page/13246978/Threat%20Classification)

5. **MITRE ATT&CK Framework**:
   - **Unsecured Credentials (T1552)**: Relevant if the tool helps identify backup files containing sensitive credentials. [More Info](https://attack.mitre.org/techniques/T1552/)

By adhering to these standards and methodologies, "Backup-Finder" demonstrates its commitment to robust web application security practices, making it a valuable tool for security professionals and penetration testers.


## Installation

### Using BApp Store [Quickest]
1. Open Burp Suite.
2. Navigate to `Extender -> BApp Store`.
3. Search for and install the "Backup Finder" Extension.

### Load The JAR File
1. Download the `BackupFinder.jar` file from the repository or build it from the source code.
2. Open Burp Suite.
3. Go to `Extender -> Extensions -> Add`.
4. Select the `BackupFinder.jar` file and add it to Burp Suite.
5. A new tab for "Backup Finder" will be added to the Burp Suite interface.

## How to Use Backup-Finder
1. **Select a Request**: Choose a target host request from any tab in Burp Suite.
2. **Configure Options**: In the "backupFinder -> Finder -> options" tab, set your preferred configurations.
3. **Start Scanning**: Navigate to the "backupFinder -> Finder -> Finder" tab and initiate the scanning process.

### Pro Tips [Fine Tuning]:
- **Pre-Scanning Preparation**: For optimal results, crawl the target website using Burp Spider or manually navigate through its pages. Backup-Finder leverages the site map and proxy history as its primary feed.
- **Handling Non-Standard Responses**: If the target website uses non-standard status codes for not-found pages, specify a unique identifier in the options tab to ensure accurate detection.
- **Method Selection**: The extension defaults to the "Head" method for speed. Switch to "GET" if the target does not support "Head".

## Customization Options
- **Scanning Levels**: Choose from 5 levels of scanning intensity to balance between speed and thoroughness.
- **Brute-Force Zip Files**: Utilize a built-in dictionary or load your own for targeted brute-forcing.
- **Request Handling**: Adjust threads and throttle settings to accommodate server limitations and optimize scanning speed.
- **Response Filtering**: Define specific status codes or texts for inclusion or exclusion during the scan.

## Build From Source Code
1. Ensure you have [Gradle](https://gradle.org/install/) installed.
2. Clone the repository: `git clone https://github.com/moeinfatehi/Backup-Finder`
3. Navigate to the main directory (where `build.gradle` exists) and run: `gradle makeJar`
4. The Jar file will be generated in `build/libs/Backup-finder.jar`

## Disclaimer
This program is for educational purposes ONLY. Do not use it without permission. The usual disclaimer applies, especially the fact that I'm not liable for any damages caused by the direct or indirect use of the information or functionality provided by these programs. The author or any Internet provider bears NO responsibility for content or misuse of these programs or any derivatives thereof. By using these programs you accept the fact that any damage (data loss, system crash, system compromise, etc.) caused by the use of this program is not my responsibility.

## Contact
If you have any further questions, please don't hesitate to contact me via my [Twitter account](https://twitter.com/MoeinFatehi).


