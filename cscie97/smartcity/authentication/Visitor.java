package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.Resource;
import cscie97.smartcity.authentication.domain.User;

/**
 * visitor interface has 2 implementations, CheckAcessVisitor and InventoryVisitor
 */
public interface Visitor {

    /**
     * please refer to the java doc inside the implementation classes
     * @param user
     */
    void visit(User user);

    /**
     * please refer to the java doc inside the implementation classes
     * @param entitlement
     */
    void visit(Entitlement entitlement);

    /**
     * please refer to the java doc inside the implementation classes
     * @param authToken
     */
    void visit(AuthToken authToken);

    /**
     * please refer to the java doc inside the implementation classes
     * @param resource
     */
    void visit(Resource resource);

    /**
     * please refer to the java doc inside the implementation classes
     * @return
     */
    boolean isValidStatus();

    /**
     * please refer to the java doc inside the implementation classes
     * @return
     */
    boolean isValidExpTime();

    /**
     * please refer to the java doc inside the implementation classes
     * @return
     */
    boolean isHasPermissions();

    /**
     * please refer to the java doc inside the implementation classes
     * @return
     */
    boolean isHasResources();

    /**
     * please refer to the java doc inside the implementation classes
     * @return
     */
    User getUserUnderValidation();

}
