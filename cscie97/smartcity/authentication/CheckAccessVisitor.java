package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.*;

import java.util.*;

public class CheckAccessVisitor implements Visitor {

    boolean validStatus = false;
    boolean validExpTime = false;
    boolean hasPermissions = false;
    boolean hasResources = false;
    User user = null;
    List<String> extractedResources = new ArrayList<>();

    public boolean isValidStatus() {
        return validStatus;
    }

    public boolean isValidExpTime() {
        return validExpTime;
    }

    public boolean isHasPermissions() {
        return hasPermissions;
    }

    public boolean isHasResources() {
        return hasResources;
    }


    public User getUserUnderValidation() {
        return user;
    }

    //This will verify if user exists
    @Override
    public void visit(User user) {
        try{
            AuthenticationService authenticationService = AuthenticationService.getInstance();
            if(authenticationService.getUsersMap().containsKey(user.getId())){
                this.user = authenticationService.getUsersMap().get(user.getId());
            } else {
                throw new AuthenticationException("Check Access Failed","User is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    //this will verify if authroken is not expired and is in active status
    @Override
    public void visit(AuthToken authToken) {
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        HashMap<String, AuthToken> authTokensMap = authenticationService.getAuthTokensMap();

        try {
            if(authTokensMap.containsKey(authToken.getId())) {
                //validate if token status is expired
                if (authTokensMap.get(authToken.getId()).getState().equals(TokenState.active)) {
                    validStatus = true;
                } else{
                    throw new AuthenticationException("Authentication Failed","AuthToken is expired.");
                }
                //validate if expiration time is due
                Calendar calendar = Calendar.getInstance();
                Date now = calendar.getTime();
                Date authTokenExpTime = new Date(authTokensMap.get(authToken.getId()).getExpirationTime());
                if(authTokenExpTime.after(now)) {
                    validExpTime = true;
                }
             }
        }
        catch (AuthenticationException e){
            System.out.println(e);
        }

    }

    @Override
    public void visit(Entitlement entitlement) {
            //extract all associated permission for this user
            List<String> tempEntitlementsList = new ArrayList<>();
            List<String> tempResourcesList = new ArrayList<>();
            AuthToken authToken1 = user.getAuthToken();

            for (Entitlement ent : authToken1.getUser().getEntitlements()) {
                tempEntitlementsList.addAll(ent.extractComposite(authToken1.getUser().getEntitlements()));
                if(entitlement instanceof ResourceRole){
                    for(Resource resource1 : ent.getResources()){
                        tempResourcesList.add(resource1.getId());
                    }
                }
            }
            //validate permission is found
            if(tempEntitlementsList.contains(entitlement.getId())){
                hasPermissions = true;
            }
            //share extracted resources
        extractedResources = tempResourcesList;

    }

    @Override
    public void visit(Resource resource) {
        try{
            if(extractedResources.contains(resource.getId())){
                hasResources = true;
            } else {
                throw new AuthenticationException("Authentication Failed","Required resource is not associated with any resource roles for this user");
            }
        } catch (AuthenticationException ex){
            System.out.println(ex);
        }
    }



}
