package twitter4j.examples;

//import twitter4j.ResponseList;
//import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class PostTweet {

	public static void main(String[] args) {
	
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("TdjHm37CpapW5QGwpo7bSa0lT")
		  .setOAuthConsumerSecret("Qa4zs7wYg4b6TIgNTSUn3AgFuv8eG6o7ymudPUwYQ82ugUspOw")
		  .setOAuthAccessToken("760656101104726016-9qkxE3z0ozIL2evEdAPC5fptDHQXsXA")
		  .setOAuthAccessTokenSecret("iyjUUBYVi5aXNcC4l9XLwXpWu4usVE78c4twsZj9Iftj0");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		try{
				
			twitter.updateStatus("I am posting through UP mobile app");
			//System.out.println(twitter.getScreenName());
			 //ResponseList<Status> statuses = twitter.getHomeTimeline();
		    //System.out.println("Showing home .");
		    //for (Status status : statuses) {
		       // System.out.println(status.getUser().getName() + ":" +
		                          // status.getText());}
		        
		    
		    
		}
		 catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
		}

	}

}
