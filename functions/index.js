const functions = require('firebase-functions');

const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

/*
*  /messages/:pushId에 추가되는 새로운 메시지를 Listen하고 구독하는 사용자에게 notification을 보낸다.
*/
exports.pushNotification =
  functions.database.ref('/help/{helpId}').onWrite((change, context) => {

     console.log('Push notification event triggered');

     /*
      *  Realtime Database에 생성된 현재의 값을 얻어온다.
      */
      var valueObject = change.after.val();

     /*
        *  notification과 data payload를 을 생성한다.
        *  그것들은 notification 정보를 포함하고 보내질 메시지를 포함한다.
        */
      const payload = {
         notification: {
            title: valueObject.message,
            body: valueObject.reward,
            sound: "default"
         },
         data: {
            title: valueObject.message,
            message: valueObject.reward
         }
      };

      /*
       *  알림의 유효 시간과 우선순위를 포함하는 옵션 객체를 생성
       */
       const options = {
          priority: "high",
          timeToLive: 60 * 60 * 24     // 24 시간
       };

     return admin.messaging().sendToTopic("push", payload, options);
  });
