# **Project2: (phase1) sandbag_server**
---------------------------
My partnet for this project was Nook, u5580025
===================

Remark:<br/>
1. make sure you install 'gradle'<br/>
2. now clone the submitted repo and run 'gradle installDist'<br/>
3. as the final step on the server-side, run the following command<br/>
<./build/install/p2.1-starter/bin/p2.1-starter<br/>
4. to perform a test, please choose '7777' as the port along with server ip<br/>


Features:<br/>
1. This is the modified version of the original server<br/>
2. I did a remake on the datastructure from ArrayList to a concurrentHashmap along with atomic integer to allow concurrency between threads<br/>
3. The script were tested with ddos script from the previous assignment. As a result it can handle roughly 4000-6000 requests per second on the given server</br>

