package cscie97.smartcity.authentication;

import cscie97.smartcity.model.cmdProcessor.CommandProcessor;

public class Tester {

    public static void main(String[] args) {

        User user = new User("user1","sam akash");
        AuthToken authToken = new AuthToken("a1","a1","time1",TokenState.active);
        user.setAuthToken(authToken);
        Credential login = new Login("c1","sam","password");
        Credential voice = new VoicePrint("v1","v1");
        Credential face = new FacePrint("f1","f1");

        user.getCredentials().add(login);
        user.getCredentials().add(voice);
        user.getCredentials().add(face);

        System.out.println(user);

    }


}
