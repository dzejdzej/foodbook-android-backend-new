package foodbook.android.service.firebase;

import java.time.Duration;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.constants.Constants;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.model.topics.Topic;
import de.bytefish.fcmjava.requests.topic.TopicUnicastMessage;
import de.bytefish.fcmjava.responses.TopicMessageResponse;

@Component
public class FirebaseService {

	
	private FcmClient client;

	
	
	
	@PostConstruct
	public void initFirebaseClient() {


        // Instantiate the FcmClient with the API Key:
        this.client = new FcmClient(new IFcmClientSettings() {
			
			@Override
			public String getFcmUrl() {
				// TODO Auto-generated method stub
				return Constants.FCM_URL;
			}
			
			@Override
			public String getApiKey() {
				//return "AAAAVhdyabY:APA91bGSwRwxAwhjOX91nzbu3w5u85Tkv2tP7jCe0ThueEdmvFaTMD40EaSebcehMNN60c93Uo4it3txRcBZjsR8OVgz9YhCyNquM01Lllrf_AdeamXNy7u8uffFc8x2VAI_8oRhngO7";
				//return "AIzaSyC96SNAHjm1-Ri281joGkscjuFrl6cJoN0";
				return "AIzaSyDlQRWL3PJo5KtToVHTDSGTpFOWg3N6Vko"; 
			}
		});
	}
	
	
	
	public void sendMessage(String userId, NotificationDTO dto) {
		   // Message Options:
        FcmMessageOptions options = FcmMessageOptions.builder()
                .setTimeToLive(Duration.ofHours(1))
                .build();

        // Send a Message:
        TopicMessageResponse response = 
        		client.send(new TopicUnicastMessage(options, new Topic("firebase_" + userId), dto));
	
	}
	
	
}
