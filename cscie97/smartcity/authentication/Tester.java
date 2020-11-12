package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.*;

public class Tester {

    public static void main(String[] args) {

        User user = new User("user1","sam akash");
//        AuthToken authToken = new AuthToken("a1","a1","time1",TokenState.active);
//        user.setAuthToken(authToken);
//        Credential login = new Login("c1","sam","password");
//        Credential voice = new VoicePrint("v1","v1");
//        Credential face = new FacePrint("f1","f1");
//
//        user.getCredentials().add(login);
//        user.getCredentials().add(voice);
//        user.getCredentials().add(face);
//
//        Entitlement e1 = new Role("role1","role1","role1");
//        Entitlement e2 = new Permission("per1","per1","per2");
//        Entitlement e3 = new Permission("per2","per2","per2");
//        Entitlement e4 = new ResourceRole("rr1","rr1","rr1");
//
//        Resource resource = new Resource("res1","res1");
//
//        e4.getResources().add(resource);
//        e1.getEntitlementsList().add(e2);
//        e1.getEntitlementsList().add(e4);
//
//        user.getEntitlements().add(e4);
//        System.out.println(user);

        Visitor v1 = new InventoryAdd();
        Visitor v2 = new InventoryUpdate();
        Visitor v3 = new CheckAccess();

//        user.accept(v1);
//        user.accept(v2);
//        user.accept(v3);
//
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        authenticationService.createUser("user1","user1");
        authenticationService.createUser("user2","user2");

        authenticationService.createPermission("per1","per1","per1");
        authenticationService.createRole("role1","role1","role1");
        authenticationService.createResourceRole("rr1","rr1","rr1");

        System.out.println(authenticationService.getEntitlementList());


    }


}
