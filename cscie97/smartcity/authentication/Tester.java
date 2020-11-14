package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.*;

import java.util.List;

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
//
//        Entitlement e4 = new Permission("per3","per3","per3");
//        Entitlement e5 = new Permission("per4","per4","per4");
//
//        Entitlement e6 = new ResourceRole("RR2","role2","role2");
//
//        Resource resource = new Resource("res1","res1");
//
//        e4.getResources().add(resource);
//        e1.getEntitlementsList().add(e2);
//        e1.getEntitlementsList().add(e3);
//
//        e6.getEntitlementsList().add(e4);
//        e6.getEntitlementsList().add(e5);
//
//        e1.getEntitlementsList().add(e6);
//
//        List<String> output = e1.extractComposite(e1.getEntitlementsList());
//        System.out.println(output);
//
//        user.getEntitlements().add(e4);
//        System.out.println(user);

//        Visitor v1 = new InventoryAdd();
//        Visitor v2 = new Inventory();
//        Visitor v3 = new CheckAccess();

//        user.accept(v1);
//        user.accept(v2);
//        user.accept(v3);
//
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        authenticationService.createUser("user1","user1");
        authenticationService.createUser("user2","user2");

        authenticationService.createPermission("per1","per1","per1");
        authenticationService.createPermission("per2","per2","per2");
        authenticationService.createRole("role1","role1","role1");
        authenticationService.createResourceRole("rr1","rr1","rr1");
        authenticationService.addPermissionToRole("per1","role1");
        authenticationService.addPermissionToRole("per2","rr1");

//
        authenticationService.addRoleToUser("user1","role1");
        authenticationService.addRoleToUser("user1","rr1");
//
        authenticationService.addUserCredential("user1","user1","password","sam");
//        authenticationService.addUserCredential("user1","user1","faceprint","sam");
        authenticationService.addUserCredential("user1","c3","voiceprint","sam");
//        authenticationService.addUserCredential("user1","c3","voiceprint","sam");
//        authenticationService.addUserCredential("user1","c3","asf","sam");
//        authenticationService.createResourceRole("rr1","rr1","rr1");
        authenticationService.addResourceToResourceRole("rr1","r1","r1");
//
        System.out.println(authenticationService.login("user1","sam"));
        System.out.println(authenticationService.login("user1","sam"));
//        System.out.println(a);

//        System.out.println(a);
//
        System.out.println(authenticationService.getUserList());
//        System.out.println(authenticationService.getEntitlementList());
//        authenticationService.logout("user1");
        System.out.println(authenticationService.getAuthTokenList());





    }


}
