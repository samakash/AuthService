package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.*;
import cscie97.smartcity.model.utils.SmartCityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try{
            if(entitlementList.containsKey(id)){
                throw new AuthenticationException("Create Permission failed","Permission Id already used");
            }else{
                Entitlement permission = new Permission(id,name,description);
                Visitor visitor = new Inventory();
                visitor.visit(permission);
                System.out.println("New permission is added Id: "+id);
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void createRole(String id, String name, String description) {
        try{
            if(entitlementList.containsKey(id)){
                throw new AuthenticationException("Create Role failed","Role Id already used");
            }else {
                Entitlement role = new Role(id, name, description);
                Visitor visitor = new Inventory();
                visitor.visit(role);
                System.out.println("New Role is added Id: "+id);
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void addPermissionToRole(String permissionId, String roleId) {
        try{
            if(entitlementList.containsKey(permissionId) && entitlementList.containsKey(roleId)){
                Entitlement role = entitlementList.get(roleId);
                role.getEntitlementsList().add(entitlementList.get(permissionId));
                Visitor visitor = new Inventory();
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
        try{
            if(userList.containsKey(id)){
                throw new AuthenticationException("Add User Failed","User Id already exists");
            } else{
                User user = new User(id,name);
                Visitor visitor = new Inventory();
                visitor.visit(user);
                System.out.println("New User is created Id: "+id);
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void addUserCredential(String userId, String credentialId, String credentialType, String password) {
        try{
            if(userList.containsKey(userId)){
                    if(credentialType.equals("password")){
                        Credential credential = new Login(credentialId,userId,hashCredential(password));
                        User user = userList.get(userId);
                        user.getCredentials().add(credential);
                        user.accept(new Inventory());
                        System.out.println("New Password credentials added to user: "+userId);
                    }else if (credentialType.equals("faceprint")){
                        Credential credential = new FacePrint(credentialId,hashCredential(password));
                        User user = userList.get(userId);
                        user.getCredentials().add(credential);
                        user.accept(new Inventory());
                        System.out.println("New faceprint credentials added to user: "+userId);
                    }else if (credentialType.equals("voiceprint")){
                        Credential credential = new VoicePrint(credentialId,hashCredential(password));
                        User user = userList.get(userId);
                        user.getCredentials().add(credential);
                        user.accept(new Inventory());
                        System.out.println("New voiceprint credentials added to user: "+userId);
                    } else {
                        throw new AuthenticationException("Add User Credentials failed","Authentication type is not supported");
                    }
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void addRoleToUser(String userId, String roleId) {
        try{
            if(entitlementList.containsKey(roleId) && userList.containsKey(userId)) {
                User user = userList.get(userId);
                Entitlement role = entitlementList.get(roleId);
                user.getEntitlements().add(role);
                user.accept(new Inventory());
                System.out.println("Role "+roleId+" has been added to User "+userId+" Successfully");
            } else {
                throw new AuthenticationException("Add Role to User failed","role id or user id is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void createResourceRole(String id, String name, String description) {
        try{
            if(entitlementList.containsKey(id)){
                throw new AuthenticationException("Create Resource Role failed","Role Id already used");
            }else {
                Entitlement resourceRole = new ResourceRole(id, name, description);
                Visitor visitor = new Inventory();
                visitor.visit(resourceRole);
                System.out.println("New Resource Role is added Id: "+id);
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void addResourceToResourceRole(String roleId, String resourceId, String resourceDescription) {
        try{
            if(entitlementList.containsKey(roleId)){
                if(entitlementList.get(roleId) instanceof ResourceRole){
                    Resource resource = new Resource(resourceId,resourceDescription);
                    Entitlement role =  entitlementList.get(roleId);
                    role.getResources().add(resource);
                    role.accept(new Inventory());
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

                for (Credential credential: user.getCredentials()) {
                    if (credential instanceof Login){
                        if(((Login)credential).getUsername().equals(username)
                                && ((Login)credential).getPassword().equals(hashCredential(password))){
                            authToken = new AuthToken(username, username, "", TokenState.active);
                            authToken.setUser(user);
                            authToken.accept(new Inventory());
                            user.setAuthToken(authToken);
                            user.accept(new Inventory());
                            break;
                        } else {
                            throw new AuthenticationException("Login Failed","please check username and password");
                        }
                    } else if (credential instanceof FacePrint){
                        if(((FacePrint)credential).getFacePrintValue().equals(hashCredential(password))){
                            authToken = new AuthToken(username, username, "", TokenState.active);
                            authToken.setUser(user);
                            authToken.accept(new Inventory());
                            user.setAuthToken(authToken);
                            user.accept(new Inventory());
                        }
                    } else if (credential instanceof VoicePrint){
                        if(((VoicePrint)credential).getVoicePrintValue().equals(hashCredential(password))){
                            authToken = new AuthToken(username, username, "", TokenState.active);
                            authToken.setUser(user);
                            authToken.accept(new Inventory());
                            user.setAuthToken(authToken);
                            user.accept(new Inventory());
                        }
                    }
                }

            } else{
                throw new AuthenticationException("Login Failed","please check username and password");
            }
        } catch (Exception ex){
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
                user.accept(new Inventory());
            } else{
                throw new AuthenticationException("logout Failed","username is not found");
            }
        } catch (AuthenticationException ex){
            System.out.println(ex);
        }
    }

    @Override
    public boolean checkAccess(String authToken, String requiredPermission, String resource) {
        boolean passedExp = false;
        boolean passedPer = false;
        boolean passedRes = false;

        try {
            if(authTokenList.containsKey(authToken)) {
                //validate if token expired
                if (getAuthTokenList().get(authToken).getState().equals(TokenState.active)) {
                    passedExp = true;
                } else{
                    throw new AuthenticationException("Authentication Failed","AuthToken is expired.");
                }
                //check required permission adn resources
                List<String> tempEntitlementsList = new ArrayList<>();
                List<String> tempResourcesList = new ArrayList<>();
                AuthToken authToken1 = authTokenList.get(authToken);
                for (Entitlement entitlement : authToken1.getUser().getEntitlements()) {
                    tempEntitlementsList.addAll(entitlement.extractComposite(authToken1.getUser().getEntitlements()));
                    if(entitlement instanceof ResourceRole){
                        for(Resource resource1 : entitlement.getResources()){
                            tempResourcesList.add(resource1.getId());
                        }
                    }
                }

                if(tempEntitlementsList.contains(requiredPermission)){
                    passedPer = true;
                } else{
                    throw new AuthenticationException("Authentication Failed","User doesn't have required permissions");
                }
                if(!resource.equals("") && tempResourcesList.contains(resource)){
                    passedRes = true;
                } else if (!resource.equals("") && !tempResourcesList.contains(resource)){
                    throw new AuthenticationException("Authentication Failed","Required resource is not associated with any resource roles for this user");
                }

            } else{
                throw new AuthenticationException("Authentication Failed","authToken is not valid");
            }

        } catch (AuthenticationException e){
            System.out.println(e);
        }

        if (resource.equals("")){
            if(passedExp && passedPer){
                return true;
            } else {
                return false;
            }
        } else{
            if(passedExp && passedPer && passedRes){
                return  true;
            } else{
                return false;
            }
        }

    }


    private String hashCredential(String value) throws Exception {
        return SmartCityUtils.encrypt(value,"passwordpassword");
    }

}
