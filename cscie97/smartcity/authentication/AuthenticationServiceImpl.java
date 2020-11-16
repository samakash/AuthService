package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.*;
import cscie97.smartcity.model.utils.SmartCityUtils;

import java.util.*;

public class AuthenticationServiceImpl implements AuthenticationService{

    public static AuthenticationServiceImpl instance;
    private HashMap<String, User> usersMap;
    private HashMap<String, Entitlement> entitlementsMap;
    private HashMap<String, AuthToken> authTokensMap;



    private AuthenticationServiceImpl() {
        this.usersMap = new HashMap<>();
        this.entitlementsMap = new HashMap<>();
        this.authTokensMap = new HashMap<>();
    }

    public static AuthenticationServiceImpl getInstance() {
        if (instance == null) {
            instance = new AuthenticationServiceImpl();
        }
        return instance;
    }

    public HashMap<String, User> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(HashMap<String, User> usersMap) {
        this.usersMap = usersMap;
    }

    public HashMap<String, Entitlement> getEntitlementsMap() {
        return entitlementsMap;
    }

    public void setEntitlementsMap(HashMap<String, Entitlement> entitlementsMap) {
        this.entitlementsMap = entitlementsMap;
    }

    public HashMap<String, AuthToken> getAuthTokensMap() {
        return authTokensMap;
    }

    public void setAuthTokensMap(HashMap<String, AuthToken> authTokensMap) {
        this.authTokensMap = authTokensMap;
    }

    @Override
    public void createPermission(String id, String name, String description) {
        try{
            if(entitlementsMap.containsKey(id)){
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
            if(entitlementsMap.containsKey(id)){
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
            if(entitlementsMap.containsKey(permissionId) && entitlementsMap.containsKey(roleId)){
                Entitlement role = entitlementsMap.get(roleId);
                role.getEntitlementsList().add(entitlementsMap.get(permissionId));
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
            if(usersMap.containsKey(id)){
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
        try {
            if (usersMap.containsKey(userId)) {
                if (credentialType.equals("password")) {
                    Credential credential = new Login(userId + credentialType, userId, hashCredential(password));
                    User user = usersMap.get(userId);
                    user.getCredentials().add(credential);
                    user.accept(new Inventory());
                    System.out.println("New Password credentials added to user: " + userId);
                } else if (credentialType.equals("faceprint")) {
                    Credential credential = new FacePrint(userId + credentialType, hashCredential(password));
                    User user = usersMap.get(userId);
                    user.getCredentials().add(credential);
                    user.accept(new Inventory());
                    System.out.println("New faceprint credentials added to user: " + userId);
                } else if (credentialType.equals("voiceprint")) {
                    Credential credential = new VoicePrint(userId + credentialType, hashCredential(password));
                    User user = usersMap.get(userId);
                    user.getCredentials().add(credential);
                    user.accept(new Inventory());
                    System.out.println("New voiceprint credentials added to user: " + userId);
                } else {
                    throw new AuthenticationException("Add User Credentials failed", "Authentication type is not supported");
                }
            } else {
                throw new AuthenticationException("Add credentials to user failed","User is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    @Override
    public void addRoleToUser(String userId, String roleId) {
        try{
            if(entitlementsMap.containsKey(roleId) && usersMap.containsKey(userId)) {
                User user = usersMap.get(userId);
                Entitlement role = entitlementsMap.get(roleId);
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
            if(entitlementsMap.containsKey(id)){
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
            if(entitlementsMap.containsKey(roleId)){
                if(entitlementsMap.get(roleId) instanceof ResourceRole){
                    Resource resource = new Resource(resourceId,resourceDescription);
                    Entitlement role =  entitlementsMap.get(roleId);
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
            if(usersMap.containsKey(username)) {
                User user = usersMap.get(username);

                //get timestamo and add 1 hour to it as an expiration time for the autToken
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, 1);
                Date exptime = calendar.getTime();

                for (Credential credential: user.getCredentials()) {
                    if (credential instanceof Login){
                        if(((Login)credential).getUsername().equals(username)
                                && ((Login)credential).getPassword().equals(hashCredential(password))){
                            authToken = new AuthToken(username, username, exptime.toString(), TokenState.active);
                            authToken.setUser(user);
                            authToken.accept(new Inventory());
                            user.setAuthToken(authToken);
                            user.accept(new Inventory());
                            System.out.println("New authToken is generated for user: "+user.getId());
                            break;
                        } else {
                            throw new AuthenticationException("Login Failed","please check username and password");
                        }
                    } else if (credential instanceof FacePrint){
                        if(((FacePrint)credential).getFacePrintValue().equals(hashCredential(password))){
                            authToken = new AuthToken(username, username, exptime.toString(), TokenState.active);
                            authToken.setUser(user);
                            authToken.accept(new Inventory());
                            user.setAuthToken(authToken);
                            user.accept(new Inventory());
                            System.out.println("New authToken is generated for user: "+user.getId());
                        }
                    } else if (credential instanceof VoicePrint){
                        if(((VoicePrint)credential).getVoicePrintValue().equals(hashCredential(password))){
                            authToken = new AuthToken(username, username, exptime.toString(), TokenState.active);
                            authToken.setUser(user);
                            authToken.accept(new Inventory());
                            user.setAuthToken(authToken);
                            user.accept(new Inventory());
                            System.out.println("New authToken is generated for user: "+user.getId());
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
            if(usersMap.containsKey(userId)) {
                User user = usersMap.get(userId);
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
    public boolean checkAccess(String authToken, Object requiredPermission, String resource) {
        boolean validStatus = false;
        boolean validExpTime = false;
        boolean hasPermissions = false;
        boolean hasResources = false;
        String userId = null;

        try {
            if(authTokensMap.containsKey(authToken)) {
                //extract all associated permission for this user
                List<String> tempEntitlementsList = new ArrayList<>();
                List<String> tempResourcesList = new ArrayList<>();
                AuthToken authToken1 = authTokensMap.get(authToken);
                userId = authToken1.getUser().getId();
                for (Entitlement entitlement : authToken1.getUser().getEntitlements()) {
                    tempEntitlementsList.addAll(entitlement.extractComposite(authToken1.getUser().getEntitlements()));
                    if(entitlement instanceof ResourceRole){
                        for(Resource resource1 : entitlement.getResources()){
                            tempResourcesList.add(resource1.getId());
                        }
                    }
                }
                if(requiredPermission instanceof String){
                    //validate if token status is expired
                    if (getAuthTokensMap().get(authToken).getState().equals(TokenState.active)) {
                        validStatus = true;
                    } else{
                        throw new AuthenticationException("Authentication Failed","AuthToken is expired.");
                    }
                    //validate if expiration time is due
                    Calendar calendar = Calendar.getInstance();
                    Date now = calendar.getTime();
                    Date authTokenExpTime = new Date(getAuthTokensMap().get(authToken).getExpirationTime());
                    if(authTokenExpTime.after(now)) {
                        validExpTime = true;
                    }
                    //check required permission and resources
                    if(tempEntitlementsList.contains(requiredPermission)){
                        hasPermissions = true;
                    } else{
                        throw new AuthenticationException("Authentication Failed","User doesn't have required permissions");
                    }
                    if(!resource.equals("") && tempResourcesList.contains(resource)){
                        hasResources = true;
                    } else if (!resource.equals("") && !tempResourcesList.contains(resource)){
                        throw new AuthenticationException("Authentication Failed","Required resource is not associated with any resource roles for this user");
                    }
                } else if (requiredPermission instanceof List){
                    //validate if token expired
                    if (getAuthTokensMap().get(authToken).getState().equals(TokenState.active)) {
                        validStatus = true;
                    } else{
                        throw new AuthenticationException("Authentication Failed","AuthToken is expired.");
                    }
                    //validate if expiration time is due
                    Calendar calendar = Calendar.getInstance();
                    Date now = calendar.getTime();
                    Date authTokenExpTime = new Date(getAuthTokensMap().get(authToken).getExpirationTime());
                    if(authTokenExpTime.after(now)) {
                        validExpTime = true;
                    }
                    //check required permission and resources
                    List<String> ls = (List<String>) requiredPermission;
                    for(String val : ls){
                        if(tempEntitlementsList.contains(val)){
                            hasPermissions = true;
                        }
                    }
                    if(!hasPermissions){
                        throw new AuthenticationException("Authentication Failed","User doesn't have required permissions");
                    }
                    if(!resource.equals("") && tempResourcesList.contains(resource)){
                        hasResources = true;
                    } else if (!resource.equals("") && !tempResourcesList.contains(resource)){
                        throw new AuthenticationException("Authentication Failed","Required resource is not associated with any resource roles for this user");
                    }
                }
            } else{
                throw new AuthenticationException("Authentication Failed","authToken is not valid");
            }

        } catch (AuthenticationException e){
            System.out.println(e);
        }

        if (resource.equals("")){
            if(validStatus && validExpTime && hasPermissions){
                //expire authToken after each use to simplify the command line demo
                logout(userId);
                return true;
            } else {
                return false;
            }
        } else{
            if(validStatus && validExpTime && hasPermissions && hasResources){
                //expire authToken after each use to simplify the command line demo
                logout(userId);
                return  true;
            } else{
                return false;
            }
        }

    }


    private String hashCredential(String value) throws Exception {
        return SmartCityUtils.encrypt(value);
    }

}
