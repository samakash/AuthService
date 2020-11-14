package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

public class AuthenticationServiceImpl implements AuthenticationService{

    public static AuthenticationServiceImpl instance;
    private HashMap<String, User> userList;
    private HashMap<String, Entitlement> entitlementList;
    private HashMap<String, AuthToken> authTokenList;



    private AuthenticationServiceImpl() {
        this.userList = new HashMap<>();
        this.entitlementList = new HashMap<>();
        this.authTokenList = new HashMap<>();
    }

    public static AuthenticationServiceImpl getInstance() {
        if (instance == null) {
            instance = new AuthenticationServiceImpl();
        }
        return instance;
    }

    public HashMap<String, User> getUserList() {
        return userList;
    }

    public void setUserList(HashMap<String, User> userList) {
        this.userList = userList;
    }

    public HashMap<String, Entitlement> getEntitlementList() {
        return entitlementList;
    }

    public void setEntitlementList(HashMap<String, Entitlement> entitlementList) {
        this.entitlementList = entitlementList;
    }

    public HashMap<String, AuthToken> getAuthTokenList() {
        return authTokenList;
    }

    public void setAuthTokenList(HashMap<String, AuthToken> authTokenList) {
        this.authTokenList = authTokenList;
    }

    @Override
    public void createPermission(String id, String name, String description) {
        Entitlement permission = new Permission(id,name,description);
        Visitor visitor = new InventoryAdd();
        visitor.visit(permission);

    }

    @Override
    public void createRole(String id, String name, String description) {
        Entitlement role = new Role(id,name,description);
        Visitor visitor = new InventoryAdd();
        visitor.visit(role);
    }

    @Override
    public void addPermissionToRole(String permissionId, String roleId) {
        try{
            if(entitlementList.containsKey(permissionId) && entitlementList.containsKey(roleId)){
                Entitlement role = entitlementList.get(roleId);
                role.getEntitlementsList().add(entitlementList.get(permissionId));
                Visitor visitor = new InventoryUpdate();
                role.accept(visitor);
                System.out.println("Added Permission "+permissionId+" to Role "+roleId+ " successfully");
            } else{
                throw new AuthenticationException("add Permission to Role failed","permission id or role id is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }

    }

    @Override
    public void createUser(String id, String name) {
        User user = new User(id,name);
        Visitor visitor = new InventoryAdd();
        visitor.visit(user);
    }

    @Override
    public void addUserCredential(String userId, String credentialId, String credentialType, String password) {
        try{
            if(userList.containsKey(userId)){
                    if(credentialType.equals("password")){
                        Credential credential = new Login(credentialId,userId,hashCredential(password));
                        User user = userList.get(userId);
                        user.getCredentials().add(credential);
                        user.accept(new InventoryUpdate());
                    }else if (credentialType.equals("faceprint")){
                        Credential credential = new FacePrint(credentialId,hashCredential(password));
                        User user = userList.get(userId);
                        user.getCredentials().add(credential);
                        user.accept(new InventoryUpdate());
                    }else if (credentialType.equals("voiceprint")){
                        Credential credential = new VoicePrint(credentialId,hashCredential(password));
                        User user = userList.get(userId);
                        user.getCredentials().add(credential);
                        user.accept(new InventoryUpdate());
                    } else {
                        throw new AuthenticationException("Add User Credentials failed","Authentication type is not supported");
                    }
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void addRoleToUser(String userId, String roleId) {
        if(entitlementList.containsKey(roleId) && userList.containsKey(userId)) {
            User user = userList.get(userId);
            Entitlement role = entitlementList.get(roleId);
            user.getEntitlements().add(role);
            user.accept(new InventoryUpdate());
            System.out.println("Role "+roleId+" has been added to User "+userId+" Successfully");
        }
    }

    @Override
    public void createResourceRole(String id, String name, String description) {
        Entitlement resourceRole = new ResourceRole(id,name,description);
        Visitor visitor = new InventoryAdd();
        visitor.visit(resourceRole);
    }

    @Override
    public void addResourceToResourceRole(String roleId, String resourceId, String resourceDescription) {
        try{
            if(entitlementList.containsKey(roleId)){
                if(entitlementList.get(roleId) instanceof ResourceRole){
                    Resource resource = new Resource(resourceId,resourceDescription);
                    Entitlement role =  entitlementList.get(roleId);
                    role.getResources().add(resource);
                    role.accept(new InventoryUpdate());
                    System.out.println("Resource "+resourceId+" has been added to resource Role "+roleId);
                } else{
                    throw new AuthenticationException("Add Resource to ResourceRole Failed","Role id is not for a Resource Role");
                }
            } else {
                throw new AuthenticationException("Add Resource to ResourceRole Failed","Role Id is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }

    }

    @Override
    public AuthToken login(String username, String password) {
        AuthToken authToken = null;
        try{
            if(userList.containsKey(username)) {
                User user = userList.get(username);
                Credential userCredential = null;
                for (Credential credential: user.getCredentials()) {
                    if (credential instanceof Login){
                        userCredential = credential;
                    }
                }
                if(userCredential != null && ((Login)userCredential).getUsername().equals(username)
                        && ((Login)userCredential).getPassword().equals(hashCredential(password))){
                    authToken = new AuthToken(username, username, "", TokenState.active);
                    authToken.setUser(user);
                    authToken.accept(new InventoryAdd());

                    user.setAuthToken(authToken);
                    user.accept(new InventoryUpdate());

                } else {
                    throw new AuthenticationException("Login Failed","please check username and password");
                }
            } else{
                throw new AuthenticationException("Login Failed","please check username and password");
            }
        } catch (AuthenticationException ex){
            System.out.println(ex);
        }
            return authToken;
        }



    @Override
    public void logout(String userId) {
        try{
            if(userList.containsKey(userId)) {
                User user = userList.get(userId);
                user.getAuthToken().setState(TokenState.expired);
                user.accept(new InventoryUpdate());
            } else{
                throw new AuthenticationException("logout Failed","username is not found");
            }
        } catch (AuthenticationException ex){
            System.out.println(ex);
        }
    }

    @Override
    public boolean checkAccess(String authToken, String requiredPermission, String resource) {
        for(Map.Entry<String,User> entry : userList.entrySet()){

        }
        return false;
    }


    private String hashCredential(String value){
        return "passwordSam";
    }

}
