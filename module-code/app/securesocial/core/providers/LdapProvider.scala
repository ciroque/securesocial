package securesocial.core.providers

import play.api.Application
import securesocial.core.{IdentityId, SocialUser, AuthenticationMethod, IdentityProvider}
import play.api.mvc.{Result, Request}

class LdapProvider(application: Application) extends IdentityProvider(application) {
  override def id: String = LdapProvider.LDAP

  /**
   * Subclasses need to implement this method to populate the User object with profile
   * information from the service provider.
   *
   * @param user The user object to be populated
   * @return A copy of the user object with the new values set
   */
  override def fillProfile(user: SocialUser): SocialUser = {
    user
  }

  /**
   * Subclasses need to implement the authentication logic. This method needs to return
   * a User object that then gets passed to the fillProfile method
   *
   * @param request
   * @tparam A
   * @return Either a Result or a User
   */
  override def doAuth[A]()(implicit request: Request[A]): Either[Result, SocialUser] = {

    Right(SocialUser(IdentityId("uid", "prid"), "firstName", "lastName", "fullName", None, None, AuthenticationMethod.Ldap, None, None, None))
  }

  /**
   * Subclasses need to implement this to specify the authentication method
   * @return
   */
  override def authMethod: AuthenticationMethod = AuthenticationMethod.Ldap
}

object LdapProvider {
  val LDAP = "LDAP"
}