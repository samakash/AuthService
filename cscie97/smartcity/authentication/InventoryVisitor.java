package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.Resource;
import cscie97.smartcity.authentication.domain.User;

/**
 * this class is an implementation for the visitor interface. this class is responsible to manage the inventory but updating its values when called.
 */
public class InventoryVisitor implements Visitor {
    /**
     * this method is used to update a user in the inventory
     * @param user
     */
    @Override
    public void visit(User user) {
            AuthenticationService authenticationService = AuthenticationService.getInstance();
            authenticationService.getUsersMap().put(user.getId(), user);
    }

    /**
     * this method is used to update an entitlement in the inventory
     * @param entitlement
     */
    @Override
    public void visit(Entitlement entitlement) {
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        authenticationService.getEntitlementsMap().put(entitlement.getId(),entitlement);
    }

    /**
     * this method is used to update an AuthToken in the inventory
     * @param authToken
     */
    @Override
    public void visit(AuthToken authToken) {
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        authenticationService.getAuthTokensMap().put(authToken.getId(), authToken);
    }

    /**
     * this method has no function. required for compiling only in order to implement the visitor pattern.
     * @param resource
     */
    @Override
    public void visit(Resource resource) {

    }

    /**
     * this method has no function. required for compiling only in order to implement the visitor pattern.
     * @return
     */
    @Override
    public boolean isValidStatus() {
        return false;
    }

    /**
     * this method has no function. required for compiling only in order to implement the visitor pattern.
     * @return
     */
    @Override
    public boolean isValidExpTime() {
        return false;
    }

    /**
     * this method has no function. required for compiling only in order to implement the visitor pattern.
     * @return
     */
    @Override
    public boolean isHasPermissions() {
        return false;
    }

    /**
     * this method has no function. required for compiling only in order to implement the visitor pattern.
     * @return
     */
    @Override
    public boolean isHasResources() {
        return false;
    }

    /**
     * this method has no function. required for compiling only in order to implement the visitor pattern.
     * @return
     */
    @Override
    public User getUserUnderValidation() {
        return null;
    }


}
