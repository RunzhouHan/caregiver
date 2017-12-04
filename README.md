# What is this app?
This is caregiver version of Alzheimer's Troubleshooter.

Click here to review old version:
https://github.com/RunzhouHan/MyApplication

Click here to explore patient part of new version:
https://github.com/RunzhouHan/Patient

Following Prof.Osama's proposal, I simplified our app by seperating different part of user from the original app.
This part is designed for caregivers. It can be used as a real time notification sender.

#  Functions
 ## 1. Sign in
This function is based on Firebase google account sign in function.
![Sign in](https://raw.github.com/RunzhouHan/Patient/master/sign%20in.png)

 ## 2. Notification sender
 This function is based on Firebase database and storage.
 Caregiver can send text notification.
 ![Sign in](https://raw.github.com/RunzhouHan/Patient/master/Screen%20Shot%202017-12-03%20at%2010.21.29%20PM.png)

 When there is a new child found in database, The 'click me' button on patient side will appear.
  ![Sign in](https://raw.github.com/RunzhouHan/Patient/master/Screen%20Shot%202017-12-03%20at%2010.26.31%20PM.png)

 The 'click me' button will be hidden after the patient clicks it and it will send a signal to Firebase as a response to let caregiver know that the patient has recieved the notification.
  ![Sign in](https://raw.github.com/RunzhouHan/Patient/master/Screen%20Shot%202017-12-03%20at%2010.26.44%20PM.png)
  
Caregiver can also send image notification.
  ![Sign in](https://raw.github.com/RunzhouHan/Patient/master/Screen%20Shot%202017-12-03%20at%2010.20.54%20PM.png)
  ![Sign in](https://raw.github.com/RunzhouHan/Patient/master/Screen%20Shot%202017-12-03%20at%2010.21.07%20PM.png)
  


 
 
