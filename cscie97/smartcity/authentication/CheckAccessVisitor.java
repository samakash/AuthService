package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.*;
import java.util.*;

/**
 * this class is an implementation for the visitor interface. This class is used to check access for a userUnderValidation.
 */
public class CheckAccessVisitor implements Visitor {

    boolean validStatus = false;
    boolean validExpTime = false;
    boolean hasPermissions = false;
    boolean hasResources = false;
    User userUnderValidation = null;
    List<String> extractedResources = new ArrayList<>();

    /**
     * getter for the result of authToken status
     * @return
     */
    public boolean isValidStatus() {
        return validStatus;
    }

    /**
     * getter for the result of authToken expiration status
     * @return
     */
    public boolean isValidExpTime() {
        return validExpTime;
    }

    /**
     * getter for the result of validating permissions for a userUnderValidation
     * @return
     */
    public boolean isHasPermissions() {
        return hasPermissions;
    }

    /**
     * getter for the result of validation resources
     * @return
     */
    public boolean isHasResources() {
        return hasResources;
    }

    /**
     * getter for the userUnderValidation that is under validation while executing the validation.
     * @return
     */
    public User getUserUnderValidation() {
        return userUnderValidation;
    }

    /**
     * This method will verify if userUnderValidation exists before start validating its authToken.
     * @param user
     */
    @Override
    public void visit(User user) {
        try{
            AuthenticationService authenticationService = AuthenticationService.getInstance();
            if(authenticationService.getUsersMap().containsKey(user.getId())){
                this.userUnderValidation = authenticationService.getUsersMap().get(user.getId());
            } else {
                throw new AuthenticationException("Check Access Failed","User is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    //

    /**
     * this method will verify if authroken is not expired and is in active status. The result will update the validStatus & validExpTime class member values
     * @param authToken
     */
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

    /**
     * this method will search if the userUnderValidation has the required permssion. The result will update the hasPermssion class member value
     * @param entitlement
     */
    @Override
    public void visit(Entitlement entitlement) {
            //extract all associated permission for this userUnderValidation
            List<String> tempEntitlementsList = new ArrayList<>();
            List<String> tempResourcesList = new ArrayList<>();
            AuthToken authToken1 = userUnderValidation.getAuthToken();

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

    /**
     * this method will verify if the resource is found in the resource role entitlement if called. The result will update the hasResources class member value
     * @param resource
     */
    @Override
    public void visit(Resource resource) {
        try{
            if(extractedResources.contains(resource.getId())){
                hasResources = true;
            } else {
                throw new AuthenticationException("Authentication Failed","Required resource is not associated with any resource roles for this userUnderValidation");
            }
        } catch (AuthenticationException ex){
            System.out.println(ex);
        }
    }



}
